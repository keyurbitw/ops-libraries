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
            } catch (Exception e) {
                echo '[FAILURE] Yaml validation failed'
                env.skipRemainingStages = true
                sh 'exit 1'
              }
          }
        }
      }
      stage('Check Pod Status & Verfiy Node Status'){
        steps {
          script {
            try{
              sh 'cd elk-stack/ && ./k8sValidation.sh'
            } catch (Exception e) {
                echo '[FAILURE] K8s validation failed'
                sh 'exit 1'
              }
          }   
        }
      }
      stage('Deploy'){
        steps {
          script{
            sh 'pwd && ls -al'
            sh 'cd elk-stack/ && mkdir -p Deployment && mv *.yaml Deployment'
            sh 'cd elk-stack/Deployment && kubectl apply -f . --kubeconfig=/home/.kube/config'
          }
        }
      }
      stage('Apply Changes'){
        steps {
          script{
            sh 'kubectl rollout deploy --all -n obs --config=/home/.kube/config'
            sh 'kubectl rollout ds --all -n obs --kubeconfig=/home/.kube/config'
          }
        }
      }
      stage('Validation'){
        steps{
          script{
            sh 'Checking ES Cluster Health!!'
            sh 'kubectl exec svc/elasticsearch-logging curl localhost:9200/_cluster/health --kubeconfig=/home/.kube/config'
            sh 'cd elk-stack/ && ./k8sValidation.sh'
          }
        }
      }
    }
  }
}
