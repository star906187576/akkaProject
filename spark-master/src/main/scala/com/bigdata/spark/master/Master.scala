package com.bigdata.spark.master

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object Master {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("actorSystem", ConfigFactory.load())
    val masterActor = actorSystem.actorOf(Props(MasterActor), "masterActor")
  }
}
