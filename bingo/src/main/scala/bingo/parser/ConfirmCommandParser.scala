package bingo.parser

import bingo.domain.ConfirmCommand
import zio.ZIO
import zio.macros.annotation.{accessible, mockable}

@accessible(">")
@mockable
trait ConfirmCommandParser {

  val confirmCommandParser: ConfirmCommandParser.Service[Any]
}

object ConfirmCommandParser {

  trait Service[R] {

    def parse(input: String): ZIO[R, Nothing, ConfirmCommand]
  }
}
