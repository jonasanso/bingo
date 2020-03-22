package bingo.parser

import atto.Atto._
import atto.Parser
import bingo.domain.GameCommand
import zio.{IO, UIO}

trait GameCommandParserLive extends GameCommandParser {

  val gameCommandParser = new GameCommandParser.Service[Any] {

    def parse(input: String): UIO[GameCommand] =
      IO
        .effect(command.parse(input).done.option.get)
        .catchAll(_ => UIO.succeed(GameCommand.Invalid))

    private lazy val command: Parser[GameCommand] =
      choice(menu, linea, next)

    private lazy val menu: Parser[GameCommand] =
      (string("menu") <~ endOfInput) >| GameCommand.Menu

    private lazy val linea: Parser[GameCommand] =
      (string("linea ") ~> many(letter) <~ endOfInput).map(name => GameCommand.Linea(name.mkString))

    private lazy val next: Parser[GameCommand] =
      (letterOrDigit <~ endOfInput || endOfInput)  >| GameCommand.Next

  }
}
