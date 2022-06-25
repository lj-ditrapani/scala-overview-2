Scala Overview 2
=================

Learning
--------

- This is a `read, eval, print loop` (REPL) program
- main function
    - implements the repl flow using @tailrec
    - performs all IO (console readline and println)
    - state change via recursive function instead of mutation
    - The state is just a Vector[Item]
- todo function
    - `def todo(items: Vector[Item], input: String): Result`
    - Item has a description and a state
    - Result is an enum with Exit or Continue subtypes
    - todo function returns Result effect
    - does not perform IO or mutation, only describes it
- Command
    - Command is a scala interface (trait) with a process method
    - User input is parsed into a Command
    - Uses standard OO virtual/dynamic dispatch to select the correct implementation of process.
- Output
    - The text to display to the user (can be colored)
    - A type alias for List[List[String | ColoredString]]
    - A pure "description" of the println side-effect.
- tests
    - Most tests just call todo function and assert on result
- Design for testability
    - Separate IO and mutation out of core logic (todo function)
    - Usually have to rely on dependency injection when things are more complex
      (think function parameters, not DI frameworks)
    - There is only once branch in main.  All other branching logic exists in the todo function or lower.

Developing
----------

Develop:

Start sbt console with `sbt`.
From sbt console you can run these useful commands while you develop:

    compile
    test
    run
    fmt

Deploying:

Stage a package that you can run
directly with `target/universal/stage/bin/scala-overview-2`

    sbt stage

Generate a zip artifact
packaged here: `target/universal/scala-overview-2-<version>.zip`

    sbt Universal / packageBin

Dependencies:

check for outdated dependencies

    sbt dependencyUpdates
