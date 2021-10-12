package mephrine.template.data.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

abstract class UnitTest {
  @ExperimentalCoroutinesApi
  @get:Rule
  val coroutineRule = MainCoroutineRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  fun fail(message: String): Nothing = throw AssertionError(message)
}