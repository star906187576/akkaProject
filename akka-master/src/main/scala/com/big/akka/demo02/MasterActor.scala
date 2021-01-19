package com.big.akka.demo02

import akka.actor.Actor

object MasterActor extends Actor {
  override def receive: Receive = {
    //        1.接收workerActor发送的数据
    case "connect" => {
      println("MasterActor接收到：connect!...")
      //      2.给workerActor回执消息
      sender ! "success"
    }
  }
}
