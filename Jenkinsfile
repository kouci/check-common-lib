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
                configFileProvider([configFile(fileId: '82705a2a-6c49-4ba6-accb-bd65fc82a529', variable: 'MAVEN_SETTINGS')]) {
                    sh """
                        mvn clean deploy \
                          -s $MAVEN_SETTINGS \
                          -Dgpg.passphrase=$GPG_PASSPHRASE
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
