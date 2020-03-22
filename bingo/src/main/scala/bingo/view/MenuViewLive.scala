package bingo.view

import bingo.domain.MenuMessage
import zio.UIO

trait MenuViewLive extends MenuView {

  val menuView = new MenuView.Service[Any] {

    val header =
      UIO.succeed(
        """
          # _______   __
          #/       \ /  |
          #$$$$$$$  |$$/  _______    ______    ______
          #$$ |__$$ |/  |/       \  /      \  /      \
          #$$    $$< $$ |$$$$$$$  |/$$$$$$  |/$$$$$$  |
          #$$$$$$$  |$$ |$$ |  $$ |$$ |  $$ |$$ |  $$ |
          #$$ |__$$ |$$ |$$ |  $$ |$$ \__$$ |$$ \__$$ |
          #$$    $$/ $$ |$$ |  $$ |$$    $$ |$$    $$/
          #$$$$$$$/  $$/ $$/   $$/  $$$$$$$ | $$$$$$/
          #                        /  \__$$ |
          #                        $$    $$/
          #                         $$$$$$/
          #""".stripMargin('#')
      )

    def content(isSuspended: Boolean) =
      UIO.effectTotal {
        val commands =
          if (isSuspended) List("new game", "resume", "quit")
          else List("new game", "quit")

        commands
          .map(cmd => s"* $cmd")
          .mkString("\n")
      }

    def footer(message: MenuMessage) =
      UIO.succeed(message) map {
        case MenuMessage.Empty => ""
        case MenuMessage.InvalidCommand => "Invalid command. Try again."
      }
  }
}
