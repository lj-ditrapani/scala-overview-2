package info.ditrapani.overview2.message

import info.ditrapani.overview.Spec

class MessageSpec extends Spec:
  "String.asOutput" - {
    "constructs a List[Line] frome a SoloredString" in {
      val str =
        """well...
        hello there"""
      val lines = str.asOutput(Color.Yellow)
      val cString = str.withColor(Color.Yellow)
      lines shouldBe List(List(cString))
      println("visual test:")
      display(lines)
      println("end visual test")
      // This is a visual test.  You must look at the test output.
    }
  }

  "lineToString" - {
    "converts a single line into a colorized String ready to be printed" in {
      val line = List[Message](
        "default1",
        "red".withColor(Color.Red),
        "blue".withColor(Color.Blue),
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
        "red".withColor(Color.Red),
        "yellow".withColor(Color.Yellow),
        "default2",
      )
      val line2 = List[Message](
        "blue".withColor(Color.Blue),
        "green".withColor(Color.Green),
        "default3",
      )
      println("visual test:")
      display(List(line1, line2))
      println("end visual test")
      // This is a visual test.  You must look at the test output.
    }
  }
