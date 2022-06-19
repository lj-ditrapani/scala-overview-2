package info.ditrapani.overview2

import message.{Color, Line}

enum Result:
  case Exit
  case Continue(items: Vector[Item], lines: List[Line])

enum State:
  case Todo
  case Done

case class Item(description: String, state: State)

enum Command(firstWord: String):
  case NoArg(firstWord: String) extends Command(firstWord)
  case WithArg(firstWord: String, val arg: String) extends Command(firstWord)

def todo(items: Vector[Item], input: String): Result =
  if input == "quit" then Result.Exit
  else
    Result.Continue(
      items.appended(Item("flug", State.Todo)),
      message.singleLine("flug", Color.Blue),
    )

private def parse(line: String): Command =
  val parts = line.trim().nn.split(" ", 2).nn
  parts.size match
    case 2 => Command.WithArg(parts(0).nn, parts(1).nn.trim().nn)
    case _ => Command.NoArg(parts(0).nn)
