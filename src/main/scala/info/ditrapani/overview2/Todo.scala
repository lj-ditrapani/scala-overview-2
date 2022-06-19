package info.ditrapani.overview2

enum Result:
  case Exit
  case Continue(val output: Output)

enum State:
  case TODO
  case DONE

case class Item(val description: String, var state: State)

sealed trait Command:
    val firstWord: String

case class NoArgCommand(override val firstWord: String) extends Command
case class CommandWithArg(override val firstWord: String, val arg: String) extends Command

class Todo {
    private val list = mutableListOf[Item]()

    def dispatch(line: String): Result =
        val command = parse(line)
        command.firstWord match
          case "help" => Continue(Help)
          case "list" => Continue(ListItems(list))
          case "add" => Continue(add(command))
          case "done" => Continue(done(command))
          case "quit" => Exit
          case _ => Continue(
                Error(
                    "I do not understand your command.  " +
                        "Enter help to display available commands."
                )
            )

    private def add(command: Command): Output = when (command) {
        is NoArgCommand -> Error(
            "Add command must have space after add with " +
                "a description that follows.\nExample: add buy hot dogs."
        )
        is CommandWithArg -> {
            list.add(Item(command.arg, State.TODO))
            Noop
        }
    }

    private def done(command: Command): Output =
        val error = Error(
            "Done command must have space after done with " +
                "a valid index that follows.\nExample: done 3"
        )
        command match
          case NoArgCommand => error
          case CommandWithArg => {
                val number: Int? = command.arg.toIntOrNull()
                val maybeItem: Item? = number?.let { num -> list.getOrNull(num - 1) }
                val noop: Output? = maybeItem?.let { item ->
                    item.state = State.DONE
                    Noop
                }
                noop ?: error
            }

    private def parse(line: String): Command =
        val parts = line.trim().split(' ', limit = 2)
        parts.size match
          case 2 => CommandWithArg(parts[0], parts[1].trim())
          case _ => NoArgCommand(parts[0])
}
