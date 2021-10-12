package mephrine.template.utility.errors

import java.lang.Exception


class ServerError(message: String? = null) : Exception()
class NetworkDisconnect : Exception()
class EmptyBodyError : Exception()
class LoginError : Exception()
class CacheError : Exception()
class ParseError : Exception()
