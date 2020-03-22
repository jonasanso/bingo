package bingo.domain

sealed trait GameCommand

object GameCommand {

  case object Menu    extends GameCommand
  case class Linea(name: String)    extends GameCommand
  case object Next    extends GameCommand
  case object Invalid extends GameCommand
}
