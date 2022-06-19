package info.ditrapani.overview2.line

enum Color:
  case Blue
  case Green
  case Red
  case Yellow

def colorize(message: String, color: Color): String =
  val code = color match
    case Color.Blue => "94"
    case Color.Green => "32"
    case Color.Red => "31"
    case Color.Yellow => "33"
  s"\u001B[${code}m${message}\u001B[0m"

case class ColoredString(message: String, color: Color)

type Message = String | ColoredString
type Line = List[Message]

def display(lines: List[Line]): Unit =
  lines.foreach { displayLine(_) }

private def lineToString(line: Line): String =
  line
    .map { message =>
      message match
        case s: String => s
        case ColoredString(message, color) => colorize(message, color)
    }
    .mkString(" ")

private def displayLine(line: Line): Unit =
  println(lineToString(line))
