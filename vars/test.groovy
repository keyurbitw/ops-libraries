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
                }
              }
          } 
          stage('Deploy'){
              steps {
                script {
                    sh 'echo "Hello Deploy"'
                }   
              }
          }
      }
    }
}
