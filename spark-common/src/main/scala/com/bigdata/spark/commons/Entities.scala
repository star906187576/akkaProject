package com.bigdata.spark.commons

//创建样例类: WorkerInfo, 用来保存注册成功后的WorkerActor的信息.
case class WorkerInfo(workerId:String, cpu:Int, mem:Int, lastHeartBeatTime:Long)