pipeline {
    agent any
    stages {
        stage("Stage 1"){
            steps {
              script {
                  sh 'echo "Hello Stage 1"'
              }
            }
        } 
        stage("Stage 2"){
            steps {
              script {
                  sh 'echo "Hello Stage 2"'
              }   
            }
        }
    }
  }
