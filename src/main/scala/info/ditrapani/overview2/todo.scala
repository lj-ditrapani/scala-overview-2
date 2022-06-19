package info.ditrapani.overview2

import message.Line

enum Result:
  case Exit
  case Continue(items: Vector[Item], lines: List[Line])

enum State:
  case TODO
  case DONE

case class Item(description: String, state: State)

enum Command(firstWord: String):
  case NoArgCommand(firstWord: String) extends Command(firstWord)
  case CommandWithArg(firstWord: String, val arg: String) extends Command(firstWord)
