name := "Earth-196-fujiwara"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  filters,
  "org.mindrot" % "jbcrypt" % "0.4"
)     

play.Project.playJavaSettings
