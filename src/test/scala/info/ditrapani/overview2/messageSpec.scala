package info.ditrapani.overview2.line

import info.ditrapani.overview.Spec

class MessageSpec extends Spec:
  "display" - {
    "colors and prints out the messages" in {
      val line1 = List[Message](
        "default1",
        ColoredString("red", Color.Red),
        ColoredString("yellow", Color.Yellow),
        "default2",
      )
      val line2 = List[Message](
        ColoredString("blue", Color.Blue),
        ColoredString("green", Color.Green),
        "default3",
      )
      display(List(line1, line2))
      // This is a visual test.  You must look at the test output.
    }
  }
