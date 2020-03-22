package bingo.mode

import bingo.domain.{GameCommand, GameMessage, MenuMessage, State}
import bingo.logic.GameLogic
import bingo.parser.GameCommandParser
import bingo.view.GameView
import zio.UIO

trait GameModeLive extends GameMode {

  val gameCommandParser: GameCommandParser.Service[Any]
  val gameLogic: GameLogic.Service[Any]
  val gameView: GameView.Service[Any]

  val gameMode = new GameMode.Service[Any] {

    def process(input: String, state: State.Game): UIO[State] =
        gameCommandParser.parse(input) >>= {
          case GameCommand.Menu       => UIO.succeed(State.Menu(Some(state), MenuMessage.Empty))
          case GameCommand.Next => gameLogic.next(state)
          case GameCommand.Linea(name) => gameLogic.linea(state, name)
          case GameCommand.Invalid    => UIO.succeed(state.copy(message = GameMessage.InvalidCommand))
        }

    def render(state: State.Game): UIO[String] = {
      for {
        header  <- gameView.board(state.shown, state.remaining.headOption)
        footer  <- gameView.footer(state.message)
      } yield List(header, footer).mkString("\n\n")
    }
  }
}
