 
spark-shell --master local[2]



import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import StreamingContext._

case class Sensor(resid: String, date: String, time: String, hz: Double, disp: Double, flo: Double, sedPPM: Double, psi: Double, chlPPM: Double)extends Serializable

val ssc = new StreamingContext(sc, Seconds(2))
val textDStream = ssc.textFileStream("/user/user01/stream") 
textDStream.print()
textDStream.foreachRDD(rdd=>{
 val srdd =rdd.map(_.split(",")).map(p => Sensor(p(0), p(1), p(2), p(3).toDouble, p(4).toDouble, p(5).toDouble, p(6).toDouble, p(7).toDouble, p(8).toDouble))
 srdd.take(2).foreach(println)  
})
ssc.start()
ssc.awaitTermination()


