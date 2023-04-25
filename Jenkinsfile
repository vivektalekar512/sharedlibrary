pipeline {
    agent {
        label 'masternodes'
    }
    parameters {
        // string(name: 'packageName', description: 'Name of the package to install', defaultValue: 'postgresql')
        choice(
            name: 'packageName', 
            choices: ['postgresql', 'postgresql1'], 
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
                    def installed = sh(returnStatus: true, script: "dpkg -s ${params.packageName} | grep 'Status: install ok installed'")
                    if (installed == 0) {
                        echo "${params.packageName} is already installed"
                    } else {
                        sh "sudo apt update"
                        sh "sudo apt install ${params.packageName} -y"
                    }
                }
            }
        }
        stage('Start/Stop Service') {
            steps {
                script {
                    if (params.serviceAction == 'start') {
                        sh "sudo systemctl start ${params.packageName}"
                        echo "Started ${params.packageName} service"
                    } else if (params.serviceAction == 'stop') {
                        sh "sudo systemctl stop ${params.packageName}"
                        echo "Stopped ${params.packageName} service"
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                def serviceName = sh(returnStdout: true, script: "sudo systemctl status ${params.packageName} | grep 'Active' | awk '{print \$2}'")
                echo "PostgreSQL service is now ${serviceName}"
            }
        }
    }
}
