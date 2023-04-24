def call() {
  def currentUser = currentBuild.getBuildCauses()[0].getUserId()
  def currentJob = env.JOB_NAME
  echo "Current user: ${currentUser}"
  echo "Current job: ${currentJob}"
  echo "Var email: ${var_email}"
}
