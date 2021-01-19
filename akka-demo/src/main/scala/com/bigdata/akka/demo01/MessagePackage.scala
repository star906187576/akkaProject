package com.bigdata.akka.demo01

  /**
   * 记录发送消息的样例类
   * @param msg 具体要发送的信息
   */
  case class SubmitTaskMessage(msg:String)

  /**
   * 记录回执信息的样例类
   * @param msg 具体的回执信息
   */
  case class SuccessSubmitTaskMessage(msg:String)
