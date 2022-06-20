package info.ditrapani.overview2

import message.{Color, ColoredString, Line}

enum Result:
  case Exit
  case Continue(items: Vector[Item], lines: List[Line])

enum State:
  case Todo
  case Done

case class Item(description: String, state: State):
  def toLine(index: Int): Line =
    state match
      case State.Todo =>
        List(
          s"${index + 1}",
          ColoredString(description, Color.Green),
        )
      case State.Done =>
        List(
          s"${index + 1}",
          ColoredString(description, Color.Blue),
          "(done)",
        )

def todo(items: Vector[Item], input: String): Result =
  parse(input).process(items)

private def error(text: String): List[Line] =
  message.singleLine(text, Color.Red)

private def itemsToLines(items: Vector[Item]): List[Line] =
  items.zipWithIndex.map { case (item, index) => item.toLine(index) }.toList

private def parse(line: String): Command =
  val parts = line.trimn.splitn("\\s+", 2)
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
