
ThisBuild / scalaVersion := "2.11.8"
ThisBuild / organization := "de.example.spark.testing"
ThisBuild / version := "0.1.0-SNAPSHOT"
name := "shared-spark-session-helper"

resolvers += Resolver.mavenLocal

val sparkVersion = "2.4.5"

// Logging
libraryDependencies +="com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"

// Spark
libraryDependencies +="org.apache.spark" %% "spark-sql" % sparkVersion % Provided
libraryDependencies +="org.apache.spark" %% "spark-core" % sparkVersion % Provided
libraryDependencies +="org.apache.spark" %% "spark-hive" % sparkVersion % Provided

//libraryDependencies += "io.circe" %% "circe-core" % "0.11.2"
//libraryDependencies += "io.circe" %% "circe-generic" % "0.11.2"
//libraryDependencies += "io.circe" %% "circe-parser" % "0.11.2"
//
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.5.2"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.13.1"
//libraryDependencies += "commons-configuration" % "commons-configuration" % "20041012.002804"

// Test
libraryDependencies += "org.mockito" %% "mockito-scala-scalatest"  % "1.11.3" % Test
libraryDependencies +="org.scalatestplus" %% "scalatestplus-scalacheck" % "3.1.0.0-RC2" % Test
libraryDependencies +="org.scalacheck" %% "scalacheck" % "1.14.2" % Test
libraryDependencies += "junit" % "junit" % "4.12" % Test
libraryDependencies +="org.scalatest" %% "scalatest" % "3.1.0" % Test
libraryDependencies +="org.mockito" %% "mockito-scala" % "1.10.0" % Test

// Settings

// Scalastyle
scalastyleFailOnError := true
scalastyleFailOnWarning := true
scalastyleConfig := file("scalastyle_config.xml")
Test / scalastyleFailOnError := true
Test / scalastyleFailOnWarning := true
Test / scalastyleConfig := file("scalastyle_config.xml")

// Testing
Test / testForkedParallel := false
IntegrationTest / testForkedParallel := false
Test / fork := true
Test / parallelExecution := false

// Assembly
import sbt.Package.ManifestAttributes
import com.typesafe.sbt.SbtGit.git
assembly / test := {}
packageOptions := Seq(ManifestAttributes(("Repository-Commit", git.gitHeadCommit.value.get)))
