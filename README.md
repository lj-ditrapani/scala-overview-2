Scala Overview 2
=================

Develop:

Start sbt console with `sbt`.
From sbt console you can run these useful commands while you develop:

    compile
    test
    run
    fmt

Deploying:

Stage a package that you can run
directly with `target/universal/stage/bin/scala-overview`

    sbt stage

Generate a zip artifact
packaged here: `target/universal/scala-overview-<version>.zip`

    sbt Universal / packageBin

Dependencies:

check for outdated dependencies

    sbt dependencyUpdates
