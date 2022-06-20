package info.ditrapani.overview2

import message.Color

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
    Result.Continue(items, helpLines)

  private val helpLines =
    val text =
      """
      Available commands:
          help                              Displays this help
          list                              Display the todo list
          add <todo item description>       Adds the item to the todo list
          done <todo item number>           Marks the item as done
          quit                              Exit the program"""
    message.singleLine(text, Color.Yellow)

object ListCommand extends Command:
  def process(items: Vector[Item]): Result =
    Result.Continue(items, itemsToLines(items))

case class AddCommand(arg: String) extends Command:
  def process(items: Vector[Item]): Result =
    val newItems = items.appended(Item(arg, State.Todo))
    Result.Continue(newItems, itemsToLines(newItems))

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
        val lines = error("Done command must have a valid item index")
        Result.Continue(items, lines)
      case Some(newItems) =>
        Result.Continue(newItems, itemsToLines(newItems))

object QuitCommand extends Command:
  def process(items: Vector[Item]): Result =
    Result.Exit

case class UnexpectedArgCommand(commandName: String) extends Command:
  def process(items: Vector[Item]): Result =
    val lines = error(s"`$commandName` command does not take any arguments")
    Result.Continue(items, lines)

case class MissingArgCommand(commandName: String) extends Command:
  def process(items: Vector[Item]): Result =
    val lines = error(s"`$commandName` command requires an argument")
    Result.Continue(items, lines)

object UnknownCommand extends Command:
  def process(items: Vector[Item]): Result =
    val lines = error(
      "I do not understand your command.  " +
        "Enter help to display available commands.",
    )
    Result.Continue(items, lines)
