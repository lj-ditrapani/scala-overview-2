package info.ditrapani.overview2.output

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

private case class ColoredString(str: String, color: Color)

type Text = String | ColoredString
type Line = List[Text]
type Output = List[Line]

extension (str: String)
  def withColor(color: Color): ColoredString =
    ColoredString(str, color)

  def asOutput(color: Color): List[Line] =
    List(List(str.withColor(color)))

def display(lines: List[Line]): Unit =
  lines.foreach { line => println(lineToString(line)) }

private[output] def lineToString(line: Line): String =
  line
    .map { text =>
      text match
        case s: String => s
        case ColoredString(str, color) => colorize(str, color)
    }
    .mkString(" ")
