package info.ditrapani.overview2

enum Color:
  case BLUE
  case GREEN
  case RED
  case YELLOW

def colorize(color: Color, message: String): String =
    val code = color match
      case Color.BLUE => "94"
      case Color.GREEN => "32"
      case Color.RED => "31"
      case Color.YELLOW => "33"
    "\u001B[${code}m${message}\u001B[0m"

sealed trait Output {
    def display(): Unit
}

object Help extends Output():
    override def display() =
        println(
            colorize(
                Color.YELLOW,
                """
        Available commands:
        help                              Displays this help
        list                              Display the todo list
        add <todo item description>       Adds the item to the todo list
        done <todo item number>           Marks the item as done
        quit                              Exit the program"""
            )
        )

object Noop extends Output():
    override def display(): Unit = Unit

case class Error(val err: String) extends Output() {
    override def display() =
        println(colorize(Color.RED, err))
}

case class ListItems(val list: MutableList[Item]) extends Output() {
    override def display() =
        list.forEachIndexed { index, item ->
            item.state match
                case State.TODO => {
                    val message = colorize(Color.GREEN, item.description)
                    println("${index + 1} $message")
                }
                case State.DONE => {
                    val message = colorize(Color.BLUE, item.description)
                    println("${index + 1} $message (done)")
                }
        }
}
