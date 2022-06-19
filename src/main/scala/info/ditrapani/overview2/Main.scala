package info.ditrapani.overview2

import scala.annotation.tailrec
import scala.io.StdIn.readLine

@main def main() =
    println("\nTodo list\n")
    val todo = Todo()
    readEvalPrintLoop(todo)

@tailrec def readEvalPrintLoop(todo: Todo) =
    print("Enter a command. Enter help to list available commands: ")
    val input = readLine()
    val result = todo.dispatch(input)
    result match
      case Exit =>
            Unit
      case Continue =>
            result.output.display()
            readEvalPrintLoop(todo)
