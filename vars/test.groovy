def call(body) {  
    def opsUtils = new podtemplate.OpsUtils()
    pipeline {
      agent {
        label 'master'
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
<<<<<<< HEAD
	 stage('Run Python Script'){
	     script {
	         try{
		   String request = libraryResource script/Main.py
		   def cmd = "/usr/bin/python request"
  		   echo "Executing: ${cmd}"
		   sh cmd
		 }catch(Exception e){
		      println e
		 }
   	     }
	 }
=======
          stage('Run Python Script'){
              steps{
                script{
                  try {
                    String request = libraryResource script/Main.py
                    def cmd = "/usr/bin/python " + request
                    echo "Executing: ${cmd}"
                    sh cmd
                  }
                  catch(Exception e) {
                    sh 'exit'
                  }
                }
              }
          }
>>>>>>> 421156266b7ac7273f42f46fd6262565b7270537
      }
    }
}
