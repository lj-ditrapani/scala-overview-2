package info.ditrapani.overview2

import message.Color

sealed trait Command2:
  val name: String
  def process(items: Vector[Item]): Result

object HelpCommand extends Command2:
  val name = "help"

  def process(items: Vector[Item]): Result =
    Result.Continue(items, help)

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

object ListCommand extends Command2:
  val name = "help"
  def process(items: Vector[Item]): Result =
    Result.Continue(items, itemsToLines(items))

case class AddCommand(arg: String) extends Command2:
  val name = "add"
  def process(items: Vector[Item]): Result =
      val newItems = items.appended(Item(arg, State.Todo))
      Result.Continue(newItems, itemsToLines(newItems))

case class DoneCommand(arg: String) extends Command2:
  val name = "done"
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

object QuitCommand extends Command2:
  val name = "quit"
  def process(items: Vector[Item]): Result =
    Result.Exit

object DoneCommand:
  def missingArg(items: Vector[Item]) =
      val lines = error(
        "Done command must have space after `done` with " +
          "a valid item index that follows.\nExample: done 3",
      )
      Result.Continue(items, lines)

object AddCommand:
  def missingArg(items: Vector[Item]) =
      val lines = error(
        "Add command must have space after `add` with " +
          "a description that follows.\nExample: add buy hot dogs.",
      )
      Result.Continue(items, lines)
