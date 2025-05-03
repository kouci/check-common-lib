pipeline {
    agent any
    environment {
        MAVEN_HOME = tool name: 'Maven 3', type: 'Tool'
    }

    def GIT_COMMIT_HASH = ""

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                script {
                    GIT_COMMIT_HASH = sh(script: "git log -n 1 --pretty=format:'%H'", returnStdout: true).trim()

                }
            }
        }

        stage('Build') {
            steps {
                script {

                    sh "'${MAVEN_HOME}/bin/mvn' clean install"
                }
            }
        }

        stage('Test') {
            steps {
                script {

                    sh "'${MAVEN_HOME}/bin/mvn' test"
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                script {

                    sh "'${MAVEN_HOME}/bin/mvn' clean deploy"
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
