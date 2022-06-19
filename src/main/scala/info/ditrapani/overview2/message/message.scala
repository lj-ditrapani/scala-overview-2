package info.ditrapani.overview2.message

enum Color:
  case Blue
  case Green
  case Red
  case Yellow

def colorize(str: String, color: Color): String =
  val code = color match
    case Color.Blue => "94"
    case Color.Green => "32"
    case Color.Red => "31"
    case Color.Yellow => "33"
  s"\u001B[${code}m${str}\u001B[0m"

case class ColoredString(str: String, color: Color)

type Message = String | ColoredString
type Line = List[Message]

def singleLine(coloredString: ColoredString): List[Line] =
  List(List(coloredString))

def display(lines: List[Line]): Unit =
  lines.foreach { displayLine(_) }

private[message] def lineToString(line: Line): String =
  line
    .map { message =>
      message match
        case s: String => s
        case ColoredString(str, color) => colorize(str, color)
    }
    .mkString(" ")

private def displayLine(line: Line): Unit =
  println(lineToString(line))
