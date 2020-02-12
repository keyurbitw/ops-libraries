def getPodTemplate(){

  """
apiVersion: v1
kind: Pod
metadata:
  labels:
    jenkins_role: slave
spec:
  containers:
    - name: jenkins_slave
      image: keyurbitw/jenkinsdockerslave
"""
}
