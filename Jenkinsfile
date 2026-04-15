pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    environment {
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=true'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/p-i-k-l-u/AutomationTestingProject-DataDrivenProject-.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Generate Reports') {
            steps {
                echo 'Reports generated'
            }
        }
    }

    post {

        always {
            // ✅ TestNG reports
            junit '**/target/surefire-reports/*.xml'

            // ✅ Extent Report (IMPORTANT)
            archiveArtifacts artifacts: 'target/extent-report.html', fingerprint: true

            // ✅ TestNG HTML Reports
            archiveArtifacts artifacts: 'test-output/**/*.*', fingerprint: true
        }

        success {
            echo 'Build SUCCESS ✅'
        }

        failure {
            echo 'Build FAILED ❌'
        }
    }
}