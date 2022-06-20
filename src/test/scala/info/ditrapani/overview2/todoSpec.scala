package info.ditrapani.overview2

import info.ditrapani.overview.Spec

class TodoSpec extends Spec:
  "todo" - {
    val items = Vector(Item("one", State.Todo), Item("two", State.Done))

    "when the input is `help`, displays the help text" in {
      todo(items, "help") shouldBe Result.Continue(HelpCommand.output, items)
    }

    "when the input is `list`" - {
      "and the list is not empty, displays the items" in {
        todo(items, "list") shouldBe Result.Continue(items.toOutput, items)
      }

      "and the list is empty, displays a hint" in {
        todo(Vector(), "list") shouldBe Result.Continue(ListCommand.emptyListHint, Vector())
      }
    }

    "when the input is `add`, adds the item description to the items vector" in {
      val newItems = items.appended(Item("brew tea", State.Todo))
      todo(items, "add brew tea") shouldBe Result.Continue(newItems.toOutput, newItems)
    }

    "when the input is `done`" - {
      "and the index is valid, updates the item to done" in {
        val newItems = items.updated(0, Item("one", State.Done))
        todo(items, "done 1") shouldBe Result.Continue(newItems.toOutput, newItems)
      }

      "and the index is not a number, returns a helpful error" in {
        val output = error("Done command must have a valid item index")
        todo(items, "done cat") shouldBe Result.Continue(output, items)
      }

      "and the index is not in range of the items vector, returns a helpful error" in {
        val output = error("Done command must have a valid item index")
        todo(items, "done 3") shouldBe Result.Continue(output, items)
      }
    }

    "when the input is `quit`, return a Result.Exit" in {
      todo(items, "quit") shouldBe Result.Exit
    }

    "when the input is a 0-arg command but includes an arg, returns a helpful error" in {
      val output = error("`list` command does not take any arguments")
      todo(items, "list all") shouldBe Result.Continue(output, items)
    }

    "when the input is a 1-arg command but is missing an arg, returns a helpful error" in {
      val output = error("`add` command requires an argument")
      todo(items, "add") shouldBe Result.Continue(output, items)
    }

    "when the input is a 0-arg unknown command, returns a helpful error" in {
      todo(items, "cull") shouldBe Result.Continue(UnknownCommand.output, items)
    }

    "when the input is a 1-arg unknown command, returns a helpful error" in {
      todo(items, "brew tea") shouldBe Result.Continue(UnknownCommand.output, items)
    }

    "when parsing input" - {
      "handles tabs and trims off whitespace before and after both command word and arg" in {
        val newItems = items.appended(Item("brew tea", State.Todo))
        todo(items, "\t  add\t\t  brew tea\t \t").shouldBe(
          Result.Continue(newItems.toOutput, newItems),
        )
      }
    }
  }
