pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        OSSRH = credentials('ossrh')
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
                        gpg --batch --import $GPG_KEY
                        export GPG_TTY=\$(tty)
                        mvn clean deploy -Dgpg.passphrase=$GPG_PASSPHRASE
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully."
        }
        failure {
            echo "Pipeline failed."
        }
    }
}