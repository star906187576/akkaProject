package com.bigdata.spark.worker

import scala.util.Random
import java.util.UUID

import akka.actor.{Actor, ActorSelection}
import com.bigdata.spark.commons.{RegisterSuccessMessage, WorkerHeartBeatMessage, WorkerRegisterMessage}

object WorkerActor extends Actor {
  //1.定义成员变量
  private var masterActorRef: ActorSelection = _ //表示MasterActor的引用
  private var workid: String = _ //表示WorkerActor的id
  private var cpu: Int = _ //表示WorkerActor的cpu数
  private var mem: Int = _ //表示WorkerActor的内存大小
  private val cpu_list = List(1, 2, 3, 4, 6, 8) //表示cpu核心数的取值范围
  private val mem_list = List(512, 1024, 2048, 4096) //内存大小范围
//  接收注册成功的消息，开始向master发送心跳
  override def receive: Receive = {
    case RegisterSuccessMessage => {
      println("注册成功!")
//      使用定时任务向Master发送心跳消息
//      导入隐式转换，隐式参数
      import context.dispatcher
      import scala.concurrent.duration._
//      定时给Master发送消息
//      自定义的方式执行定时任务
      context.system.scheduler.schedule(0 seconds,ConfigUtils.heartbeatInterval seconds){
        masterActorRef ! WorkerHeartBeatMessage(workid,cpu,mem)
      }
    }
  }
  //2.重写preStart（）方法，里面的内容在Actor启动之前就执行
  override def preStart(): Unit = {
    //    3.获取Master的引用
    masterActorRef = context.system.actorSelection("akka.tcp://actorSystem@127.0.0.1:7000/user/masterActor")
    //4.构建注册信息
    workid = UUID.randomUUID.toString
    val r = new Random()
    cpu = cpu_list(r.nextInt(cpu_list.length))
    mem = mem_list(r.nextInt(mem_list.length))
    //5.将WorkerActor的提交信息封装成WorkRegisterMessage对象
    var registerMessage = WorkerRegisterMessage(workid,cpu,mem)
    masterActorRef ! registerMessage
  }
}
