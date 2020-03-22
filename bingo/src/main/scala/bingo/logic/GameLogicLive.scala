package bingo.logic

import bingo.domain.{GameMessage, State}
import zio.{IO, UIO}

trait GameLogicLive extends GameLogic {

  val gameLogic = new GameLogic.Service[Any] {

    override def next(game: State.Game): UIO[State.Game] = IO.succeed(game.next)

    override def linea(game: State.Game, name: String): UIO[State.Game] = IO.succeed(game.copy(message = GameMessage.Linea(name)))
  }
}
