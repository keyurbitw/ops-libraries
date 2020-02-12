def call(body) {  
    pipeline {
      agent {
        kubernetes {
          cloud: 'kubernetes'
          yaml opsUtils.getPodTemplate()
        }
      }
      parameters {
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
