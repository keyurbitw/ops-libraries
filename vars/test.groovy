def call(body) {  
    pipeline {
      agent {
          label 'docker-slave'
      }
      stages {
          stage('Checkout'){
              steps {
                script {
                    sh 'echo "Hello Checkout"'
		    println env.WORKSPACE
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
