package com.big.akka.demo02

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object Entrance {
  def main(args: Array[String]): Unit = {
    //    1.创建ActorSystem
    val actorSystem = ActorSystem("actorSystem", ConfigFactory.load())
    //    2.通过ActorSystem，加载自定义的WordActor
    val masterActor = actorSystem.actorOf(Props(MasterActor), "masterActor")
    //    3.向workActor发送消息
    //    masterActor ! "测试"
  }
}
