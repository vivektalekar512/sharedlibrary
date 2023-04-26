pipeline {
    agent {
        label 'masternodes'
    }
    parameters {
        choice(
            name: 'packageName',
            choices: ['postgresql'],
            description: 'Name of the package to install'
        )
        choice(
            name: 'serviceAction',
            choices: ['start', 'stop'],
            description: 'Action to perform on the package'
        )
    }
    stages {
        stage('Package Installation') {
            steps {
                script {
                    def installed = sh(
                        returnStatus: true, 
                        script: "dpkg -s ${params.packageName} | grep 'Status: install ok installed'"
                        )
                    if (installed == 0) {
                        echo "${params.packageName} is already installed"
                    } else {
                        sh "sudo apt update"
                        sh "sudo apt install ${params.packageName} -y"
                        echo "${params.packageName} is installed successfully"
                    }
                }
            }
        }
        stage('Start/Stop Service') {
            steps {
                script {
                    if (params.serviceAction == 'start') {
                        if (sh(script: "sudo systemctl status ${params.packageName} | grep 'Active: active'", returnStatus: true) == 0) {
                            echo "${params.packageName} is already running"
                        } else {
                            sh "sudo systemctl start ${params.packageName}"
                            echo "Started ${params.packageName} service"
                        }
                    } else if (params.serviceAction == 'stop') {
                        if (sh(script: "sudo systemctl status ${params.packageName} | grep 'Active: inactive'", returnStatus: true) == 0) {
                            echo "${params.packageName} is already stopped"
                        } else {
                            sh "sudo systemctl stop ${params.packageName}"
                            echo "Stopped ${params.packageName} service"
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                def serviceName = sh(returnStdout: true, script: "sudo systemctl status ${params.packageName} | grep 'Active' | awk '{print \$2}'")
                echo "${params.packageName} service is now ${serviceName}"
            }
        }
    }
}
