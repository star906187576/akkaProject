package com.bigdata.spark.worker

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object Worker {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("actorSystem", ConfigFactory.load())
    val workerActor = actorSystem.actorOf(Props(WorkerActor), "workerActor")
  }
}
