package bingo.mode

import bingo.domain._
import bingo.parser.MenuCommandParser
import bingo.view.MenuView
import zio.UIO

trait MenuModeLive extends MenuMode {

  val menuCommandParser: MenuCommandParser.Service[Any]
  val menuView: MenuView.Service[Any]

  val menuMode = new MenuMode.Service[Any] {

    def process(input: String, state: State.Menu): UIO[State] =
      menuCommandParser.parse(input) map {
         case MenuCommand.NewGame =>
          state.game match {
            case Some(_) => State.Confirm(ConfirmAction.NewGame, State.newGame(), state, ConfirmMessage.Empty)
            case None    => State.newGame()
          }
        case MenuCommand.Resume =>
          state.game match {
            case Some(gameState) => gameState
            case None            => state.copy(message = MenuMessage.InvalidCommand)
          }
        case MenuCommand.Quit =>
          state.game match {
            case Some(_) => State.Confirm(ConfirmAction.Quit, State.Shutdown, state, ConfirmMessage.Empty)
            case None    => State.Shutdown
          }
        case MenuCommand.Invalid => state.copy(message = MenuMessage.InvalidCommand)
      }

    def render(state: State.Menu): UIO[String] =
      for {
        header  <- menuView.header
        content <- menuView.content(state.game.nonEmpty)
        footer  <- menuView.footer(state.message)
      } yield List(header, content, footer).filterNot(_.isEmpty).mkString("\n\n")
  }
}
