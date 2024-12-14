package com.wiwiwa.ezscala.os

import com.wiwiwa.ezscala.EzScala.*

def run(cmdLine:String): String = new Command(cmdLine).run()
  .getInputStream.readAllBytes().string
def sh(cmdLine:String): String =
  val args = "/bin/sh" :: "-c" :: cmdLine :: Nil
  new Command(args).run()
    .getInputStream.readAllBytes().string

class Command(process: ProcessBuilder):
  def this(cmdLine:Seq[String]) =
    this(ProcessBuilder(cmdLine: _*))
  def this(cmdLine:String) = this(cmdLine.split("\\s"))
  def run() = process.start()

object Command:
  def apply(cmdLine:String): Command =
    val args = cmdLine.split("\\s")
    new Command(ProcessBuilder(args:_*))
