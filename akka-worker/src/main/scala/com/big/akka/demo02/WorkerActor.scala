package com.big.akka.demo02

import akka.actor.Actor

object WorkerActor extends Actor {
  override def receive: Receive = {
    //        1.接收Entrance发来的消息并打印
    case "setup" => {
      println("WorkerActor收到指令")
      //      2.获取MAsterActor的引用
      val masterActor = context.system.actorSelection("akka.tcp://actorSystem@127.0.0.1:8888/user/masterActor")
      //      3.给MasterActor发送一句话
      masterActor ! "connect"
    }
//      4.接收MasterActor回执消息
    case "success" => println("好了知道了")
  }
}
