pipeline {
    agent any
    
    environment {
        SONAR_TOKEN = credentials('sqp_10b9910510988459a7aa105aea4bd292ea786576')
        TOMCAT_USER = credentials('davit') 
        TOMCAT_PASS = credentials('davit') 

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

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Code Quality Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                script {
                    def warFile = findFiles(glob: 'target/*.war')[0]
                    def warFileName = warFile.getName()
                    
                    sh "curl -T ${warFile} http://${TOMCAT_USER}:${TOMCAT_PASS}@localhost:8082/manager/text/deploy?path=/test&update=true"
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.war', allowEmptyArchive: true
        }
        success {
            echo 'Deployment to Tomcat was successful!'
        }
        failure {
            echo 'Deployment to Tomcat failed.'
        }
    }
}
