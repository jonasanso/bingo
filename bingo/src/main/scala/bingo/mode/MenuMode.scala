package bingo.mode

import bingo.domain.State
import bingo.domain.State.Menu
import zio.ZIO
import zio.macros.annotation.{accessible, mockable}

@accessible(">")
@mockable
trait MenuMode {

  val menuMode: MenuMode.Service[Any]
}

object MenuMode {

  trait Service[R] {

    def process(input: String, state: Menu): ZIO[R, Nothing, State]

    def render(state: State.Menu): ZIO[R, Nothing, String]
  }
}
