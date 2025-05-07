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

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                sh """
                    mvn clean deploy \
                      -Dgpg.passphrase=$GPG_PASSPHRASE \
                      -Dossrh.username=$OSSRH_USR \
                      -Dossrh.password=$OSSRH_PSW
                """
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
