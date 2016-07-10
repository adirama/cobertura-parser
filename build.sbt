name := "cobertura-parser"
organization := "com.snacktrace"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6" % Test

pomExtra in Global := {
  <url>https://github.com/s-nel/cobertura-parser</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:github.com/s-nel/cobertura-parser.git</connection>
    <developerConnection>scm:git:git@github.com:s-nel/cobertura-parser.git</developerConnection>
    <url>https://github.com/s-nel/cobertura-parser</url>
  </scm>
  <developers>
    <developer>
      <id>s-nel</id>
      <name>Samuel Nelson</name>
      <url>https://snacktrace.com/</url>
    </developer>
  </developers>
}
