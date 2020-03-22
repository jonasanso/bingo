package bingo

import bingo.domain.State
import zio.{App, UIO, ZIO, console, ZEnv}

object Bingo extends App {

  val program = {
    def loop(state: State): ZIO[bingo.app.RunLoop, Nothing, Unit] =
      app.RunLoop.>.step(state).foldM(
          _         => UIO.unit
        , nextState => loop(nextState)
      )

    loop(State.default)
  }

  def run(args: List[String]): ZIO[ZEnv, Nothing, Int] =
    for {
      env <- prepareEnvironment
      out <- program.provide(env).foldM(
          error => console.putStrLn(s"Execution failed with: $error") *> UIO.succeed(1)
        , _     => UIO.succeed(0)
      )
    } yield out

  private val prepareEnvironment =
    UIO.succeed(
      new bingo.app.ControllerLive
        with bingo.app.RunLoopLive
        with bingo.cli.TerminalLive
        with bingo.logic.GameLogicLive
        with bingo.mode.ConfirmModeLive
        with bingo.mode.GameModeLive
        with bingo.mode.MenuModeLive
        with bingo.parser.ConfirmCommandParserLive
        with bingo.parser.GameCommandParserLive
        with bingo.parser.MenuCommandParserLive
        with bingo.view.ConfirmViewLive
        with bingo.view.GameViewLive
        with bingo.view.MenuViewLive
        with zio.console.Console.Live
        with zio.random.Random.Live {}
    )
}
