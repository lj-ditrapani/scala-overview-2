package info.ditrapani.overview2

import message.Line

enum Result:
  case Exit
  case Continue(items: Vector[Item], lines: List[Line])

enum State:
  case Todo
  case Done

case class Item(description: String, state: State)

enum Command(firstWord: String):
  case NoArgCommand(firstWord: String) extends Command(firstWord)
  case CommandWithArg(firstWord: String, val arg: String) extends Command(firstWord)

def todo(items: Vector[Item], input: String): Result =
  if input == "quit" then Result.Exit
  else
    Result.Continue(
      items.appended(Item("flug", State.Todo)),
      message.singleLine("flug", message.Color.Blue),
    )
