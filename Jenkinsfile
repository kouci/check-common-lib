pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        GPG_PASSPHRASE = credentials('gpg-passphrase')
        OSSRH_CREDS = credentials('ossrh-creds')
    }

    stages {
        stage('Initialisation') {
            steps {
                echo '🔍 Vérification des credentials...'
                echo "OSSRH_USERNAME: ${OSSRH_CREDS_USR != null ? 'OK' : 'MISSING'}"
                echo "GPG_PASSPHRASE: ${GPG_PASSPHRASE != null ? 'OK' : 'MISSING'}"
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
                sh 'ls -la'  // Debug du contenu du répertoire
            }
        }

        stage('Configuration GPG') {
            steps {
                withCredentials([file(credentialsId: 'gpg-secret-key', variable: 'GPG_KEY')]) {
                    sh '''
                        mkdir -p ~/.gnupg
                        chmod 700 ~/.gnupg
                        gpg --batch --import ${GPG_KEY}
                        echo "use-agent" >> ~/.gnupg/gpg.conf
                        echo "pinentry-mode loopback" >> ~/.gnupg/gpg.conf
                        echo "allow-loopback-pinentry" >> ~/.gnupg/gpg-agent.conf
                        gpgconf --kill gpg-agent
                        gpgconf --launch gpg-agent
                        gpg --list-secret-keys
                    '''
                }
            }
        }

        stage('Test Signature GPG') {
            steps {
                sh '''
                    echo "Test file" > test.txt
                    gpg --batch --yes --pinentry-mode loopback --passphrase "${GPG_PASSPHRASE}" -ab test.txt
                    ls -la test.txt*
                '''
            }
        }

        stage('Configuration Maven') {
            steps {
                sh '''
                    cat > settings.xml <<EOF
                    <settings>
                        <servers>
                            <server>
                                <id>ossrh</id>
                                <username>${OSSRH_CREDS_USR}</username>
                                <password>${OSSRH_CREDS_PSW}</password>
                            </server>
                        </servers>
                        <profiles>
                            <profile>
                                <id>gpg</id>
                                <activation>
                                    <activeByDefault>true</activeByDefault>
                                </activation>
                                <properties>
                                    <gpg.executable>gpg</gpg.executable>
                                    <gpg.passphrase>${GPG_PASSPHRASE}</gpg.passphrase>
                                </properties>
                            </profile>
                        </profiles>
                    </settings>
                    EOF
                '''
            }
        }

        stage('Build & Deploy') {
            steps {
                sh """
                    export GPG_TTY=\$(tty)
                    mvn -s settings.xml clean deploy \
                        -Dgpg.passphrase=${GPG_PASSPHRASE} \
                        -Dossrh.username=${OSSRH_CREDS_USR} \
                        -Dossrh.password=${OSSRH_CREDS_PSW}
                """
            }
        }
    }

    post {
        always {
            sh 'rm -f settings.xml test.txt*'
        }
        success {
            echo '✅ Build et déploiement réussis !'
        }
        failure {
            echo '❌ Le build ou le déploiement a échoué.'
        }
    }
}