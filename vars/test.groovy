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
            try{
              sh 'cd elk-stack/ && ./checkYaml.sh'
            } finally {
                echo '[FAILURE] Yaml validation failed'
                env.skipRemainingStages = true
            }
          }
        }
      }
      stage('Check Pod Status'){
        when{
          if(env.skipRemainingStages != true){
            steps {
              script {
                sh 'kubectl get po --all-namespaces --kubeconfig=/home/.kube/config'
              }   
            }
          }
        }
      }
      stage('Deploy'){
        when{
          if(env.skipRemainingStages != true){
            steps {
              script{
                sh 'cd pwd && ls -al'
                sh 'cd elk-stack/ && mkdir -p Deployment && mv *.yaml Deployment'
                sh 'cd elk-stack/Deployment && kubectl apply -f . --config=/home/.kube/config'
              }
            } 
          }
        }
      }
      stage('Apply Changes'){
        when{
          if(env.skipRemainingStages != true){
            steps {
              script{
                sh 'kubectl rollout deploy --all -n obs --config=/home/.kube/config'
                sh 'kubectl rollout ds --all -n obs --config=/home/.kube/config'
              }
            } 
          }
        }
      }
    }
  }
}
