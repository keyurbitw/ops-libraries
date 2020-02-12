def call(body) {  
    pipeline {
      agent {
        kubernetes {
          cloud: 'kubernetes'
          yaml opsUtils.getPodTemplate()
        }
      }
      parameters {
        string(name: 'INCIDENT', defaultValue: 'ABCDXYZ', description: 'Give the value from incident URL: https://mcloud.pagerduty.com/incidents/P0EKMUC')
        string(name: 'JOB', defaultValue: '', description: 'MANDATORY: if not, alerts from all services will be silenced')
        choice(name: 'EXPIRATION_TIMEOUT', choices: ['0.25','0.5','1','1.5','2','2.5','3','24'], description: 'Select the amount in hours')
        string(name: 'CUSTOM_TIMEOUT', defaultValue: '', description: 'Value in hours, for ex: 48/72/96 | Empty value will take above EXPIRATION_TIMEOUT')
        string(name: 'REASON', defaultValue: 'silencing this alert because runbook is not solving the issue', description: 'provide a valid reason for silencing the alert on the cluste')
        choice(name: 'CREATE_WEBEX_ROOM', choices: ['YES', 'NO'], description: 'Select this to skip webex space creation')
      }
      stages {
          stage('Checkout'){
              steps {
                script {
                    sh 'echo "Hello Checkout"'
		    sh 'curl --silent ${BUILD_URL}api/json'
                }
              }
          } 
          stage('Deploy'){
              steps {
                script {
                    sh 'echo "Hello Deploy"'
		    println env.WORKSPACE
                }   
              }
          }
      }
    }
}
