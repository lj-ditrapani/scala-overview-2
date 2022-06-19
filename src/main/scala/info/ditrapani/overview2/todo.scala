package info.ditrapani.overview2

import message.{Color, ColoredString, Line, Message}

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
        List[Message](
          s"${index + 1}",
          ColoredString(description, Color.Green),
        )
      case State.Done =>
        List[Message](
          s"${index + 1}",
          ColoredString(description, Color.Blue),
          "(done)",
        )

enum Command(val firstWord: String):
  case NoArg(fw: String) extends Command(fw)
  case WithArg(fw: String, val arg: String) extends Command(fw)

def todo(items: Vector[Item], input: String): Result =
  val command = parse(input)
  command.firstWord match
    case "help" => Result.Continue(items, help)
    case "list" => Result.Continue(items, itemsToLines(items))
    case "add" => addItem(command, items)
    // case "done" => Continue(done(command))
    case "quit" => Result.Exit
    case _ =>
      val lines = error(
        "I do not understand your command.  " +
          "Enter help to display available commands.",
      )
      Result.Continue(items, lines)

private val help =
  val text =
    """
    Available commands:
        help                              Displays this help
        list                              Display the todo list
        add <todo item description>       Adds the item to the todo list
        done <todo item number>           Marks the item as done
        quit                              Exit the program"""
  message.singleLine(text, Color.Yellow)

private def error(text: String): List[Line] =
  message.singleLine(text, Color.Red)

private def itemsToLines(items: Vector[Item]): List[Line] =
  items.zipWithIndex.map { case (item, index) => item.toLine(index) }.toList

private def parse(line: String): Command =
  val parts = line.trim().nn.split(" ", 2).nn
  parts.size match
    case 2 => Command.WithArg(parts(0).nn, parts(1).nn.trim().nn)
    case _ => Command.NoArg(parts(0).nn)

private def addItem(command: Command, items: Vector[Item]): Result =
  command match
    case Command.NoArg(_) =>
      val lines = error(
            "Add command must have space after add with " +
                "a description that follows.\nExample: add buy hot dogs."
        )
      Result.Continue(items, lines)
    case Command.WithArg(_, arg) =>
      val newItems = items.appended(Item(arg, State.Todo))
      Result.Continue(newItems, itemsToLines(newItems))
