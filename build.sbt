name := "spark-streaming-wordcount-on-nifi"

version := "1.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.4.1" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.4.1" % "provided",
  "org.apache.nifi" % "nifi-spark-receiver" % "0.3.0" exclude("org.apache.spark", "*")
)



    