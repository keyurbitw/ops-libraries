def call(body) {  
    def opsUtils = new podtemplate.OpsUtils()
    pipeline {
      agent {
        labels 'master'
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
	 stage('Run Python Script'){
	     script {
	         try{
		   String request = libraryResource script/Main.py
		   def cmd = "/usr/bin/python request"
  		   echo "Executing: ${cmd}"
		   sh cmd
		 }catch(Exception e){
		      sh 'exit 1'
		 }
   	     }
	 }
      }
    }
}
