package info.ditrapani.overview2.output

enum Color:
  case Blue
  case Green
  case Red
  case Yellow

private case class ColoredString(str: String, color: Color):
  def toRawString: String =
    val code = color match
      case Color.Blue => "94"
      case Color.Green => "32"
      case Color.Red => "31"
      case Color.Yellow => "33"
    s"\u001B[${code}m${str}\u001B[0m"

type Text = String | ColoredString
type Line = List[Text]
type Output = List[Line]

extension (str: String)
  def withColor(color: Color): ColoredString =
    ColoredString(str, color)

  def asOutput(color: Color): Output =
    List(List(str.withColor(color)))

def display(output: Output): Unit =
  output.foreach { line => println(lineToString(line)) }

private[output] def lineToString(line: Line): String =
  line
    .map { text =>
      text match
        case s: String => s
        case cs: ColoredString => cs.toRawString
    }
    .mkString(" ")
