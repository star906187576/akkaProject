package com.bigdata.akka.demo01

import akka.actor.Actor

//发送消息的Actor
object SenderActor extends Actor {
  override def receive: Receive = {
    //        1.接收Entrance发送过来的start
    case "start" => {
      //      2.打印接收到的数据
      println("SenderActor收到start指令")
      //      3.获取ReceiverActor的具体路径
      //      参数：获取的Actor的具体路径
      //      格式：akka://actorSystem的名字/user/要获取的Actor的名字.
      val receiverActor = context.actorSelection("akka://actorSystem/user/receiverActor")
//      4.给ReceiverActor发送消息，采用样例类SubmitTaskMessage
      receiverActor ! SubmitTaskMessage("喂喂喂！能听见吗？")
    }
//      5.接收ReceiverActor发送回来的回执消息
      case SuccessSubmitTaskMessage(msg:String) => println(msg)
  }
}
