def sendTelegram(message) {
    def encodedMessage = URLEncoder.encode(message, "UTF-8")

    withCredentials([string(credentialsId: 'telegramToken', variable: 'TOKEN'),
        string(credentialsId: 'telegramChatId', variable: 'CHAT_ID')
    ]) {

        response = httpRequest(consoleLogResponseBody: true,
            contentType: 'APPLICATION_JSON',
            httpMode: 'GET',
            url: "https://api.telegram.org/bot$TOKEN/sendMessage?text=$encodedMessage&chat_id=$CHAT_ID&disable_web_page_preview=true",
            validResponseCodes: '200')
        return response
    }
}

def getCurrentBuildFailedTests() {
    def failedTests = []
    def build = currentBuild.build()

    def action = build.getActions(hudson.tasks.junit.TestResultAction.class)
    if (action) {
        def failures = build.getAction(hudson.tasks.junit.TestResultAction.class).getFailedTests()
        println "${failures.size()} Test Results Found"
        for (def failure in failures) {
            failedTests.add(['name': failure.name, 'url': failure.url, 'details': failure.errorDetails])
        }
    }

    return failedTests
}

pipeline {
    agent {
        docker {
            image 'maven:3.6.3-jdk-11'
            args '-v /root/.m2:/root/.m2'
        }
    }

    stages {
        stage('Build') {
            steps {
                script {
                    try {
                        sh 'mvn -B -DskipTests clean package'
                    } catch (err) {
                        sendTelegram("Build Failed: Stage Build")
                        sendTelegram(err.getMessage())
                        sendTelegram(getCurrentBuildFailedTests())
                        throw err;
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    try {
                        sh 'mvn test'
                    } catch (err) {
                        sendTelegram("Build Failed: Stage Test")
                        sendTelegram(err.getMessage())
                        sendTelegram(getCurrentBuildFailedTests())
                        throw err;
                    }
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    sendTelegram("build completed for Gchat")
                    sendTelegram("Result:" + getCurrentBuildFailedTests().join(","))
                    sendTelegram(env.BUILD_URL)
                }
            }
        }
    }
}