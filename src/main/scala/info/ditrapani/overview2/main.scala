package info.ditrapani.overview2

import output.display
import scala.annotation.tailrec
import scala.io.StdIn.readLine

@main def main: Unit =
  println("\nTodo list\n")
  readEvalPrintLoop(Vector())

@tailrec def readEvalPrintLoop(items: Vector[Item]): Unit =
  val input = readLine("Enter a command. Enter help to list available commands: ")
  todo(items, input) match
    case Result.Exit =>
      (): Unit
    case Result.Continue(output, newItems) =>
      display(output)
      readEvalPrintLoop(newItems)
