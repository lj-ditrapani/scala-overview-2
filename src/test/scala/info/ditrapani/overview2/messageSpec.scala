package info.ditrapani.overview2

import info.ditrapani.overview.Spec

class MessageSpec extends Spec:
  "display" - {
    "colors and prints out the messages" in {
      val messages = List[Message](
        "black1",
        ColoredString("red", Color.Red),
        ColoredString("yellow\n", Color.Yellow),
        "black2",
        ColoredString("blue", Color.Blue),
        ColoredString("green", Color.Green),
        "black3",
      )
      display(messages)
      // This is a visual test.  You must look at the test output.
    }
  }
