ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "DuneOOP"
  )

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "16.0.0-R24",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.5",
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

//Compile / unmanagedResourceDirectories += baseDirectory.value / "src" / "main" / "resources"

fork := true