pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        // Utilisez le même ID que celui configuré dans Jenkins
        OSSRH_CREDS = credentials('ossrh') // Doit contenir username et password
        GPG_PASSPHRASE = credentials('gpg-passphrase')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Deploy') {
            steps {
                withCredentials([file(credentialsId: 'gpg-secret-key', variable: 'GPG_KEY')]) {
                    sh """
                        # Configuration GPG
                        gpg --batch --import $GPG_KEY
                        export GPG_TTY=\$(tty)

                        # Déploiement avec authentification
                        mvn clean deploy \
                            -Dgpg.passphrase=$GPG_PASSPHRASE \
                            -Dossrh.username=${OSSRH_CREDS_USR} \
                            -Dossrh.password=${OSSRH_CREDS_PSW}
                    """
                }
            }
        }
    }
}