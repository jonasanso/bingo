package bingo.domain

sealed trait GameMessage

object GameMessage {

  case object Empty          extends GameMessage
  case class Linea(name: String)          extends GameMessage
  case object End          extends GameMessage
  case object InvalidCommand extends GameMessage
}
