pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        OSSRH_CREDS = credentials('ossrh')
        GPG_PASSPHRASE = credentials('gpg-passphrase')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Configure GPG') {
            steps {
                withCredentials([file(credentialsId: 'gpg-secret-key', variable: 'GPG_KEY')]) {
                    sh """
                        # Création du répertoire GPG
                        mkdir -p ~/.gnupg
                        chmod 700 ~/.gnupg

                        # Importation de la clé
                        gpg --batch --import ${GPG_KEY}

                        # Configuration de GPG
                        echo "use-agent" >> ~/.gnupg/gpg.conf
                        echo "pinentry-mode loopback" >> ~/.gnupg/gpg.conf
                        echo "allow-loopback-pinentry" >> ~/.gnupg/gpg-agent.conf
                        gpgconf --kill gpg-agent
                        gpgconf --launch gpg-agent
                    """
                }
            }
        }

        stage('Build and Deploy') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'ossrh',
                    usernameVariable: 'OSSRH_USERNAME',
                    passwordVariable: 'OSSRH_PASSWORD')
                ]) {
                    sh """
                        export GPG_TTY=\$(tty)
                        mvn clean deploy \
                            -Dgpg.passphrase=${GPG_PASSPHRASE} \
                            -Dossrh.username=${OSSRH_USERNAME} \
                            -Dossrh.password=${OSSRH_PASSWORD}
                    """
                }
            }
        }
    }
}