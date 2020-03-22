package bingo.domain

import scala.util.Random

sealed trait State

object State {

  final case class Confirm(
      action: ConfirmAction
    , confirmed: State
    , declined: State
    , message: ConfirmMessage
  ) extends State

  final case class Menu(
      game: Option[Game]
    , message: MenuMessage
  ) extends State

  final case class Game(
      shown: Vector[Int]
    , remaining: Vector[Int]
    , message: GameMessage
  ) extends State {
    def next: Game = remaining match {
      case head +: tail => Game(shown :+ head, tail, message)
      case _ => this.copy(message = GameMessage.End)
    }
  }

  case object Shutdown extends State

  def default(): State =
    State.Menu(None, MenuMessage.Empty)

  def newGame(): State =
    State.Game(Vector.empty, Random.shuffle(Numbers.values), GameMessage.Empty)
}
