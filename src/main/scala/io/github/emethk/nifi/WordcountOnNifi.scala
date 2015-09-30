package io.github.emethk.nifi

import org.apache.nifi.remote.client.SiteToSiteClient
import org.apache.nifi.spark.{NiFiDataPacket, NiFiReceiver}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordcountOnNifi {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("NiFi-Spark Streaming example")
    val ssc = new StreamingContext(sparkConf, Seconds(1))

    val nifiConf = new SiteToSiteClient.Builder()
      .url("http://localhost:8080/nifi")
      .portName("Data For Spark")
      .buildConfig()

    val packetStream: ReceiverInputDStream[NiFiDataPacket] =
      ssc.receiverStream(new NiFiReceiver(nifiConf, StorageLevel.MEMORY_ONLY))

    val lines: DStream[String] = packetStream.flatMap(packet => new String(packet.getContent).split("\n"))
    val words: DStream[String] = lines.flatMap(_.split(" "))
    val pairs: DStream[(String, Int)] = words.map(word => (word, 1))
    val wordCounts: DStream[(String, Int)] = pairs.reduceByKey(_ + _)

    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()

  }

}
