package mephrine.template.utility.errors

sealed class Failure {
  data class ServerFailure(val message: String? = null) : Failure()
  object NetworkConnectionFailure : Failure()
  object CacheFailure : Failure()
}