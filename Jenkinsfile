pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

     environment {

            NEXUS_URL = 'http://nexus:8081'
            NEXUS_USER = 'admin'
            NEXUS_PASS = 'admin'
        }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Deploy') {
                 steps {
                     script {
                         sh "curl -I ${NEXUS_URL} || true"
                         sh "mvn clean deploy -DskipTests"
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
