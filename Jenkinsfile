pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        // Configuration des credentials
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
                withCredentials([
                    file(credentialsId: 'gpg-secret-key', variable: 'GPG_KEY'),
                    usernamePassword(credentialsId: 'ossrh', usernameVariable: 'OSSRH_USERNAME', passwordVariable: 'OSSRH_PASSWORD')
                ]) {
                    script {
                        // Écriture sécurisée des variables dans des fichiers temporaires
                        writeFile file: 'gpg_passphrase.txt', text: "${GPG_PASSPHRASE}"
                        writeFile file: 'ossrh_password.txt', text: "${OSSRH_PASSWORD}"

                        sh '''
                            # Importation de la clé GPG
                            gpg --batch --import "${GPG_KEY}"
                            export GPG_TTY=$(tty)

                            # Lecture des secrets depuis les fichiers
                            GPG_PASSPHRASE=$(cat gpg_passphrase.txt)
                            OSSRH_PASSWORD=$(cat ossrh_password.txt)

                            # Déploiement sécurisé
                            mvn clean deploy \
                                -Dgpg.passphrase="${GPG_PASSPHRASE}" \
                                -Dossrh.username="${OSSRH_USERNAME}" \
                                -Dossrh.password="${OSSRH_PASSWORD}"

                            # Nettoyage des fichiers temporaires
                            rm -f gpg_passphrase.txt ossrh_password.txt
                        '''
                    }
                }
            }
        }
    }
}