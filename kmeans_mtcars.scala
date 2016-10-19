/*
   1 ) Enter these instructions on Spark Shell... one by one.
        So you can see what's going on.

   2) Also keep an eye on Spark Shell UI (port 4040) as you are executing the statements..

    3) After you got it working, you can run the entire script as 
            $   ~/spark/bin/spark-shell  -i  kmeans_mtcars.scala
*/

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Vector

// MT Cars Data is as follows:
//"name","mpg","cyl","disp","hp","drat","wt","qsec","vs","am","gear","carb"
//Header row does NOT appear in dataset
def parseData(vals : RDD[String]) : RDD[(String, Vector)] = {
  vals.map { s =>
    val splitData = s.split(',')
    val name = splitData(0)
    val mpg = splitData(1)
    val cyl = splitData(2)
    // let's just consider mpg and cylinders for clustering
    val doubles = Array(mpg,cyl).map(_.toDouble)
    val vectors = Vectors.dense(doubles)
    (name, vectors) // (key , value) RDD

    // # If you want to consider all vectors, try this
    //val numericFields = splitData.drop(1)
    //val doubles = numericFields.map(_.toDouble)
    // (name, doubles)
  }
}


// Load and parse the data
val data = sc.textFile("data/mtcars/mtcars.csv")
data.foreach (println)

// parse the data and print
val namesAndData = parseData(data)
namesAndData.foreach(println)


// Get only the vector out of mpg and cyl
val onlyVectors = namesAndData.map { case (name, vector) => vector } 
//one-liner: 
//      val onlyVectors = data.map(s => Vectors.dense(s.split(',').drop(1).map(_.toDouble)))
onlyVectors.foreach(println)


// ## TODO-last : Enable caching as a last step
// ##  Since we are doing multiple iterations, may be caching will help
// onlyVectors.cache
// onlyVectors.count  // force caching


//  Run Kmeans
//  Kmeans (vectors,   number_of_clusters,  num_iterations)
//      number_of_clusters : (2)  this is the 'K' in K-Means.  Start with 2
//      number_if_iterations : (20) start with 10 , and try 20
val clusters = KMeans.train(onlyVectors, 2, 10)

// Evaluate clustering by computing Within Set Sum of Squared Errors
val WSSSE = clusters.computeCost(onlyVectors)
println("Within Set Sum of Squared Errors = " + WSSSE)
// we will plot WSSSE vs k  and choose 'elbow'

// Print out a list of the clusters and each point of the clusters
// val groupedClusters = namesAndData.groupBy{rdd => clusters.predict(rdd._2)}
// groupedClusters.collect

// pretty print
val carsByCluster =namesAndData.map(s => (clusters.predict(s._2), (s._1,s._2))).sortByKey().collect()
carsByCluster.foreach { println }

// TODO - evaluate : How are the cars clustering?
// change the value of 'k' in line 61 and try it again


exit
