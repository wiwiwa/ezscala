package com.wiwiwa.ezscala.os

import com.wiwiwa.ezscala.EzScala.*

import scala.jdk.CollectionConverters.*

def run(cmdLine:String): String = new Command(cmdLine).run()
def sh(cmdLine:String): String =
  val args = "/bin/sh" :: "-c" :: cmdLine :: Nil
  new Command(args).run()

class Command(process: ProcessBuilder,
  checkReturn0: Boolean = true
):
  def this(cmdLine:Seq[String]) =
    this(ProcessBuilder(cmdLine: _*))
  def this(cmdLine:String) = this(cmdLine.split("\\s"))

  /** Run this command, and return outputs as String */
  def run(): String = process.start() >> {p=>
      p.getInputStream.readAllBytes().string >>= { _=> if checkReturn0 then
        p.waitFor()
        if p.exitValue!=0 then
          throw new RuntimeException(s"Command returned ${p.exitValue}: ${process.command.asScala.mkString(" ")}")
      }
  }
