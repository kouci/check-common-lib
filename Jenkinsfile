pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        OSSRH_USERNAME = credentials('ossrh-username')
        OSSRH_PASSWORD = credentials('ossrh-password')
        GPG_PASSPHRASE = credentials('gpg-passphrase')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Configure Maven') {
            steps {
                // Crée un settings.xml temporaire avec les credentials OSSRH
                sh '''
                    cat > ${WORKSPACE}/settings.xml <<EOF
                    <settings>
                      <servers>
                        <server>
                          <id>ossrh</id>
                          <username>${OSSRH_USERNAME}</username>
                          <password>${OSSRH_PASSWORD}</password>
                        </server>
                      </servers>
                    </settings>
                    EOF
                '''
            }
        }

        stage('Build and Deploy') {
            steps {
                withCredentials([file(credentialsId: 'gpg-secret-key', variable: 'GPG_KEY')]) {
                    sh """
                        # Configuration GPG
                        gpg --batch --import ${GPG_KEY}
                        export GPG_TTY=\$(tty)

                        # Déploiement avec les settings personnalisés
                        mvn -s ${WORKSPACE}/settings.xml clean deploy \
                            -Dgpg.passphrase=${GPG_PASSPHRASE} \
                            -Dossrh.username=${OSSRH_USERNAME} \
                            -Dossrh.password=${OSSRH_PASSWORD}
                    """
                }
            }
        }
    }

    post {
        always {
            // Nettoyage du fichier settings.xml temporaire
            sh 'rm -f ${WORKSPACE}/settings.xml'
        }
        success {
            echo "Pipeline completed successfully."
        }
        failure {
            echo "Pipeline failed."
        }
    }
}