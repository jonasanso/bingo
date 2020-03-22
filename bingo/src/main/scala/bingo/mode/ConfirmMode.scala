package bingo.mode

import bingo.domain.State
import zio.ZIO
import zio.macros.annotation.{accessible, mockable}

@accessible(">")
@mockable
trait ConfirmMode {

  val confirmMode: ConfirmMode.Service[Any]
}

object ConfirmMode {

  trait Service[R] {

    def process(input: String, state: State.Confirm): ZIO[R, Nothing, State]

    def render(state: State.Confirm): ZIO[R, Nothing, String]
  }
}
