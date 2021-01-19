package com.bigdata.spark.master

import akka.actor.Actor
import com.bigdata.spark.commons.{RegisterSuccessMessage, WorkerHeartBeatMessage, WorkerInfo, WorkerRegisterMessage}

object MasterActor extends Actor {
  //  1.定义一个可变的Map集合，用来保存注册成功好的worker信息
  private val regWorkerMap = collection.mutable.Map[String, WorkerInfo]()

  override def receive: Receive = {
    case WorkerRegisterMessage(workid, cpu, mem) => {
      //        2.打印接受到的注册信息
      println(s"收到注册信息，${workid},${cpu},${mem}")
      //      3.把注册成功后的保存信息保存到workInfo中
      regWorkerMap += workid -> WorkerInfo(workid, cpu, mem,System.currentTimeMillis())
      //      4.回复一个注册成功的消息
      sender ! RegisterSuccessMessage
    }
    //      接收心跳消息
    case WorkerHeartBeatMessage(workid, cpu, mem) => {
      //      打印输出
      println(s"接收到${workid}的心跳信息")
      //      更新指定worker最后一次心跳时间
      regWorkerMap += workid -> WorkerInfo(workid, cpu, mem, System.currentTimeMillis())
      //      测试用
      println(regWorkerMap)
    }
  }

  //  定时检查worker的心跳
  override def preStart(): Unit = {
    //    导入隐式转换和隐式参数
    import context.dispatcher
    import scala.concurrent.duration._
    //    启动定时任务
    context.system.scheduler.schedule(0 seconds, ConfigUtils.checkHeartbeatInterval seconds) {
      //    过滤出大于超时时间的worker
      val timeoutWorkerMap = regWorkerMap.filter {
        //        获取worker最后一次发送心跳的时间
        tuple =>
          val lastHeartBeatTime = tuple._2.lastHeartBeatTime
          //          判断是否超时，超时返回true
          //          当前系统时间-上次发送心跳时间
          System.currentTimeMillis() - lastHeartBeatTime > (ConfigUtils.checkHeartbeatTimeout * 1000)
      }
      //    移除超时worker
      //      判断timeoutWorkerMap是否为空 不为空 移除Worker
      if (!timeoutWorkerMap.isEmpty) {
        regWorkerMap --= timeoutWorkerMap.keys
      }
    }
    //    对现有worker按照内存排序
    //    获取map的所有值 值是对象 从对象中获取内存
    val values = regWorkerMap.values.toList
    //    按照内存排序
    val sortList = values.sortBy(_.mem).reverse
    //    输出
    println(s"按内存排序后的worker列表:$sortList")
  }
}
