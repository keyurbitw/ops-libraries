def call(body) {
  pipeline {
    agent {
      label 'docker-slave'
    }
    stages {
      stage('Checkout SCM & Validate Yaml'){
        steps {
          script {
            sh 'git clone https://github.com/keyurbitw/elk-stack.git'
            sh 'pwd && ls -al'
            sh 'cd elk-stack/ && ./checkYaml.sh'
          }
        }
      } 
      stage('Check Pod Status'){
        steps {
          script {
            sh 'kubectl get po --all-namespaces --kubeconfig==/home/.kube/config'
            println env.WORKSPACE
          }   
        }
      }
    }
  }
}
