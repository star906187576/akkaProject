package com.bigdata.akka.demo01

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

//整个程序的入口
object Entrance {
  def main(args: Array[String]): Unit = {
    //    1.实现一个Acotr Trait,其实就是创建两个Actor对象
    //    2.创建ActorSystem
    //    两个参数的意思分别是：ActorSystem的名字，加载配置文件
    val actorSystem = ActorSystem("actorSystem", ConfigFactory.load())
    //    3.加载Actor
    //    actorOf方法的两个参数的意思是：1.具体的Actor对象，2.该Actor对象的名字
    val senderActor = actorSystem.actorOf(Props(SenderActor), "senderActor")
    val receiverActor = actorSystem.actorOf(Props(ReceiverActor), "receiverActor")
    //    4.由ActorSystem给SenderActor发送一句话“start”
    senderActor ! "start"
  }
}
