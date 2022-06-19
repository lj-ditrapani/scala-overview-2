package info.ditrapani.overview2

enum Color:
  case Blue
  case Green
  case Red
  case Yellow

def colorize(color: Color, message: String): String =
  val code = color match
    case Color.Blue => "94"
    case Color.Green => "32"
    case Color.Red => "31"
    case Color.Yellow => "33"
  s"\u001B[${code}m${message}\u001B[0m"

case class ColoredString(message: String, color: Color)

type Message = String | ColoredString

def display(messages: List[Message]): Unit =
  val output: String = messages
    .map { message =>
      message match
        case s: String => s
        case cs: ColoredString => colorize(cs.color, cs.message)
    }
    .mkString(" ")
  println(output)
