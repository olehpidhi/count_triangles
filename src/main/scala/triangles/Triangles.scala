package triangles

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}

object Triangles {
  def main(args: Array[String]): Unit = {
    val conf =
    new SparkConf()
      .setAppName("Triangles")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    val graph = GraphLoader.edgeListFile(sc, args(0), true)
      .partitionBy(PartitionStrategy.RandomVertexCut)

    val triCounts = WrappedTriangleCount.wrappedRun(graph).vertices
    println(triCounts.take(6))
    println(triCounts.values.sum()/3)
    println(triCounts.map(_._2).sum()/3)
    println(triCounts.map(_._2).reduce(_ + _)/3)
  }
}