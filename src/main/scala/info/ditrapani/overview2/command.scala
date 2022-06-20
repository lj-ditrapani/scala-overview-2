package info.ditrapani.overview2

import output.{Color, asOutput}

sealed trait Command:
  def process(items: Vector[Item]): Result

object Command:
  val help = "help"
  val list = "list"
  val add = "add"
  val done = "done"
  val quit = "quit"

object HelpCommand extends Command:
  def process(items: Vector[Item]): Result =
    Result.Continue(helpLines, items)

  private val helpLines =
    """
    Available commands:
        help                              Displays this help
        list                              Display the todo list
        add <todo item description>       Adds the item to the todo list
        done <todo item number>           Marks the item as done
        quit                              Exit the program"""
      .asOutput(Color.Yellow)

object ListCommand extends Command:
  def process(items: Vector[Item]): Result =
    val output =
      if items.length == 0 then "List is empty.  Try adding some items".asOutput(Color.Yellow)
      else itemsToLines(items)
    Result.Continue(output, items)

case class AddCommand(arg: String) extends Command:
  def process(items: Vector[Item]): Result =
    val newItems = items.appended(Item(arg, State.Todo))
    Result.Continue(itemsToLines(newItems), newItems)

case class DoneCommand(arg: String) extends Command:
  def process(items: Vector[Item]): Result =
    val maybeItems: Option[Vector[Item]] = for {
      number <- arg.toIntOption
      index = number - 1
      oldItem <- items.lift(index)
      newItem = Item(oldItem.description, State.Done)
    } yield items.updated(index, newItem)
    maybeItems match
      case None =>
        val output = error("Done command must have a valid item index")
        Result.Continue(output, items)
      case Some(newItems) =>
        Result.Continue(itemsToLines(newItems), newItems)

object QuitCommand extends Command:
  def process(items: Vector[Item]): Result =
    Result.Exit

case class UnexpectedArgCommand(commandName: String) extends Command:
  def process(items: Vector[Item]): Result =
    val output = error(s"`$commandName` command does not take any arguments")
    Result.Continue(output, items)

case class MissingArgCommand(commandName: String) extends Command:
  def process(items: Vector[Item]): Result =
    val output = error(s"`$commandName` command requires an argument")
    Result.Continue(output, items)

object UnknownCommand extends Command:
  def process(items: Vector[Item]): Result =
    val output = error(
      "I do not understand your command.  " +
        "Enter help to display available commands.",
    )
    Result.Continue(output, items)
