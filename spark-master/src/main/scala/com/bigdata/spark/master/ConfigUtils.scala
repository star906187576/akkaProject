package com.bigdata.spark.master

import com.typesafe.config.{Config, ConfigFactory}

object ConfigUtils {
//1.创建Config对象
private val config: Config = ConfigFactory.load()
//  2.添加配置
//  检查心跳时间间隔
val checkHeartbeatInterval: Int = config.getInt("master.check.heartbeat.interval")
//  worker超时时间
val checkHeartbeatTimeout: Int = config.getInt("master.check.heartbeat.timeout")
}
