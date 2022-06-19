package info.ditrapani.overview2

import scala.annotation.tailrec
import scala.io.StdIn.readLine

@main def main: Unit =
  println("\nTodo list\n")
  readEvalPrintLoop(Vector())

@tailrec def readEvalPrintLoop(items: Vector[Item]): Unit =
  val input = readLine("Enter a command. Enter help to list available commands: ")
  val result = todo(items, input)
  result match
    case Result.Exit =>
      (): Unit
    case Result.Continue(newItems, lines) =>
      message.display(lines)
      readEvalPrintLoop(newItems)
