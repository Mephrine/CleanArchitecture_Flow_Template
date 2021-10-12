package mephrine.template.domain.utilities

import kotlinx.coroutines.flow.Flow

abstract class UseCase<out Type, in Params> where Type : Any {
  abstract suspend fun execute(params: Params): Flow<Type>
}