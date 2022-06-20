package info.ditrapani.overview2.output

import info.ditrapani.overview.Spec

class OuputSpec extends Spec:
  "String.asOutput" - {
    "constructs an Output from a ColoredString" in {
      val str =
        """well...
        hello there"""
      val output = str.asOutput(Color.Yellow)
      val cString = str.withColor(Color.Yellow)
      output shouldBe List(List(cString))
      println("visual test:")
      display(output)
      println("end visual test")
      // This is a visual test.  You must look at the test output.
    }
  }

  "lineToString" - {
    "converts a single line into a colorized String ready to be printed" in {
      val line = List[Text](
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
    "colors and prints out the output lines" in {
      val line1 = List[Text](
        "default1",
        "red".withColor(Color.Red),
        "yellow".withColor(Color.Yellow),
        "default2",
      )
      val line2 = List[Text](
        "blue".withColor(Color.Blue),
        "green".withColor(Color.Green),
        "default3",
      )
      val output: Output = List(line1, line2)
      println("visual test:")
      display(output)
      println("end visual test")
      // This is a visual test.  You must look at the test output.
    }
  }
