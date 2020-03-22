package bingo.view

import bingo.domain._
import zio.UIO

trait GameViewLive extends GameView {

  val gameView = new GameView.Service[Any] {

    override def board(shown: Vector[Int], current: Option[Int]): UIO[String] =
      UIO.effectTotal {
        val start = "\n╔════╦════╦════╦════╦════╦════╦════╦════╦════╦════╗\n"
        val sep   = "\n╠════╬════╬════╬════╬════╬════╬════╬════╬════╬════╣\n"
        val end   = "\n╚════╩════╩════╩════╩════╩════╩════╩════╩════╩════╝\n"
        Numbers.values
          .map(number => shown.contains(number) -> number)
          .map {
            case (true, n)                         => "%2d".format(n)
            case (false, n) if current.contains(n) => "\u001b[38;5;196m" + "%2d".format(n) + "\u001B[0m"
            case (false, _)                        => "  "
          }
          .sliding(10, 10)
          .map(numbers => s"""${numbers.mkString("║ ", " ║ ", " ║")}""")
          .mkString(start, sep, end)
      }

    def footer(message: GameMessage): UIO[String] =
      UIO.succeed(message) map {
        case GameMessage.Empty          => ""
        case GameMessage.Linea(n)       => s"Linea to $n"
        case GameMessage.End            => "Bingo 🎁"
        case GameMessage.InvalidCommand => "Invalid command. Try again."
      }

  }
}
