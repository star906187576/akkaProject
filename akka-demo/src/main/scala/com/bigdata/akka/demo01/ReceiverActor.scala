package com.bigdata.akka.demo01

import akka.actor.Actor
//接收消息的Actor
object ReceiverActor extends Actor {
  override def receive: Receive = {
      case SubmitTaskMessage(msg) => {
        println(msg)
        sender ! SuccessSubmitTaskMessage("听不见")
    }
  }
}
