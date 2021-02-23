pipeline {
    agent any
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }
    triggers {
       pollSCM('* * * * *')
    }
    parameters {
        gitParameter branchFilter: 'origin/(.*)', defaultValue: 'master', name: 'BRANCH', type: 'PT_BRANCH'
        string(defaultValue: 'chrome', description: '', name: 'BROWSER', trim: false)
        string(defaultValue: 'BMW', description: '', name: 'BRAND', trim: false)
        string(defaultValue: '5000', description: '', name: 'FROM_PRICE', trim: false)
        string(defaultValue: '12000', description: '', name: 'TO_PRICE', trim: false)
    }
    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'git@github.com:MikhailKlimentyev/Onliner.git'
                // Run Maven on a Unix agent.
                // sh "mvn -Dmaven.test.failure.ignore=true clean package"
                // To run Maven on a Windows agent, use
                bat "mvn clean install -DsuiteXmlFile=src/main/resources/testng.xml -Dbrowser=%BROWSER% -Dbrand=%BRAND% -DfromPrice=%FROM_PRICE% -DtoPrice=%TO_PRICE%"
            }
        }
    }
    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
        }
        failure {
            emailext attachLog: true, body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS
            Find attached log file''', subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', to: 'mikhail.klimentyev@gmail.com mikhail_klimentsyeu@epam.com'
        }
    }
}