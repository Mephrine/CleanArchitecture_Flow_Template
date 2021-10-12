package mephrine.template.features.loggedOut.domain.entities

data class LoginUser(
  val email: String,
  val name: String,
  var profilePhoto: String
) {
  companion object { }
}