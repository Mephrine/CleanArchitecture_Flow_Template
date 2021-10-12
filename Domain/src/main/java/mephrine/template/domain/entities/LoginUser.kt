package mephrine.template.domain.entities

data class LoginUser(
  val email: String,
  val name: String,
  var profilePhoto: String
) {
  companion object { }
}