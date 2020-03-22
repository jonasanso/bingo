package bingo.logic

import bingo.domain.State
import zio.ZIO
import zio.macros.annotation.{accessible, mockable}

@accessible(">")
@mockable
trait GameLogic {

  val gameLogic: GameLogic.Service[Any]
}

object GameLogic {

  trait Service[R] {

    def next(game: State.Game): ZIO[R, Nothing, State.Game]
    def linea(game: State.Game, name: String): ZIO[R, Nothing, State.Game]
  }
}
