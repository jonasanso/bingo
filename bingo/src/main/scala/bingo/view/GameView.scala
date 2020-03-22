package bingo.view

import bingo.domain.GameMessage
import zio.ZIO
import zio.macros.annotation.{accessible, mockable}

@accessible(">")
@mockable
trait GameView {

  val gameView: GameView.Service[Any]
}

object GameView {

  trait Service[R] {

    def board(shown: Vector[Int], current: Option[Int]): ZIO[R, Nothing, String]

    def footer(message: GameMessage): ZIO[R, Nothing, String]
  }
}
