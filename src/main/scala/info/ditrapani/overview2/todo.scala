package info.ditrapani.overview2

import output.{asOutput, withColor, Color, Line, Output}

enum Result:
  case Exit
  case Continue(output: Output, items: Vector[Item])

enum State:
  case Todo
  case Done

case class Item(description: String, state: State):
  def toLine(index: Int): Line =
    state match
      case State.Todo =>
        List(
          s"${index + 1}",
          description.withColor(Color.Green),
        )
      case State.Done =>
        List(
          s"${index + 1}",
          description.withColor(Color.Blue),
          "(done)",
        )

def todo(items: Vector[Item], input: String): Result =
  parse(input).process(items)

private def parse(input: String): Command =
  val parts = input.trimn.splitn("\\s+", 2)
  parts match
    case Array(firstWord, arg) =>
      firstWord match
        case Command.add => AddCommand(arg)
        case Command.done => DoneCommand(arg)
        case Command.help | Command.list | Command.quit =>
          UnexpectedArgCommand(firstWord)
        case _ => UnknownCommand
    case Array(firstWord) =>
      firstWord match
        case Command.help => HelpCommand
        case Command.list => ListCommand
        case Command.quit => QuitCommand
        case Command.add | Command.done =>
          MissingArgCommand(firstWord)
        case _ => UnknownCommand

private def error(text: String): Output =
  text.asOutput(Color.Red)

extension (items: Vector[Item])
  def toOutput: Output =
    items.zipWithIndex.map { case (item, index) => item.toLine(index) }.toList
