package info.ditrapani.overview2.message

import info.ditrapani.overview.Spec

class MessageSpec extends Spec:
  "singleLine" - {
    "constructs a List[Line] frome a SoloredString" in {
      val cString = ColoredString(
        """well...
        hello there""",
        Color.Yellow,
      )
      val lines = singleLine(cString)
      lines shouldBe List(List(cString))
      println("visual test:")
      display(singleLine(cString))
      println("end visual test")
      // This is a visual test.  You must look at the test output.
    }
  }

  "lineToString" - {
    "converts a single line into a colorized String ready to be printed" in {
      val line = List[Message](
        "default1",
        ColoredString("red", Color.Red),
        ColoredString("blue", Color.Blue),
      )
      lineToString(line).shouldBe(
        "default1 \u001B[31mred\u001B[0m \u001B[94mblue\u001B[0m",
      )
    }
  }

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
      println("visual test:")
      display(List(line1, line2))
      println("end visual test")
      // This is a visual test.  You must look at the test output.
    }
  }
