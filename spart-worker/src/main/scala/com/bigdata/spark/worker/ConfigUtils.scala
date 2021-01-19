package com.bigdata.spark.worker

import com.typesafe.config.ConfigFactory

object ConfigUtils {
  //1.获取配置信息对象
  private val config = ConfigFactory.load()
  //  2.获取worker心跳具体周期
  val heartbeatInterval:Int = config.getInt("worker.heartbeat.interval")
}
