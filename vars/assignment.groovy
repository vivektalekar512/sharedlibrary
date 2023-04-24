def call() {
    printVar(var_email)
    println("Current login user: ${env.USER}")
    println("Current job name: ${env.JOB_NAME}")
}
