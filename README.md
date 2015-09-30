# Quick Start

- Install Apache nifi (from https://nifi.apache.org/)
- Install Apache Spark (from http://spark.apache.org/)
- Clone this repository and build

```
$ git clone git@github.com:emethk/spark-streaming-wordcount-on-nifi.git
$ cd spark-streaming-wordcount-on-nifi
$ sbt assembly
```
- Edit `/path/to/nifi/conf/nifi.properties` as

```
...
nifi.remote.input.socket.host=
nifi.remote.input.socket.port=8090
nifi.remote.input.secure=false
...
```
- Start nifi

```
$ /path/to/nifi/bin/nifi.sh start
```

- Add `Port` at `http://localhost:8080/nifi` named `Data For Spark` which is the same as in the Scala code.

- Start the spark streaming job

```
$ spark-submit target/scala-2.10/spark-streaming-wordcount-on-nifi-assembly-1.0.jar
```

# references
- https://blogs.apache.org/nifi/entry/stream_processing_nifi_and_spark
- http://spark.apache.org/docs/latest/streaming-programming-guide.html
