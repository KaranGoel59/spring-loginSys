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

def getBuildNumber(job) {
    if (Jenkins.instance.getItem(job) != null && Jenkins.instance.getItem(job).lastSuccessfulBuild != null) {
         return Jenkins.instance.getItem(job).lastSuccessfulBuild.number.toString()
    } else {
        return "0"
    }
}

pipeline {

    environment {
        registry = "karangoel59/gchat"
        registryCredential = 'dockerhub'
        dockerImage = '' 
    }

    agent none

    parameters {
        string(defaultValue: getBuildNumber('gchat-web'), description: '', name: 'buildNumber')
    }

    stages {
        stage('Pull') {
            agent any
            when {
                expression { params.buildNumber != '0' }
            }   
            steps {
                script {
                    copyArtifacts filter: 'build.zip' , fingerprintArtifacts: true, projectName: 'gchat-web', selector: specific("${params.buildNumber}")
                    unzip zipFile: 'build.zip', dir: './web/src/main/resources/static'
                }
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'maven:3.6.3-jdk-11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
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
            post {
                always {
                    sendTelegram("Build Done Api")
                }
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'maven:3.6.3-jdk-11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
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
        stage('Docker Build') {
            steps {
                script {
                    dockerImage = docker.build registry + ":latest"
                }
            }
        }
        stage('Push') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Clean') {
            agent {
                docker {
                    image 'maven:3.6.3-jdk-11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                script {
                    sh 'mvn clean'
                }
            }
        }
        stage('Deploy') {
            agent any
            steps {
                script {
                sh "docker-compose up -d"
                }
            }
        }
    }
}