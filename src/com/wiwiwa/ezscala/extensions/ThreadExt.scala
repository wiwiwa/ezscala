package com.wiwiwa.ezscala.extensions

import java.util.concurrent.{Callable, Executors, TimeUnit}

trait ThreadExt:
  lazy val go = ThreadExt

object ThreadExt:
  private val defaultExecutor =
    val factory = Thread.ofVirtual.name("VirtualThreadExecutor-",0).factory
    Executors.newThreadPerTaskExecutor(factory)

  def apply[T](task: =>T) =
    val callable: Callable[T] = () => task
    defaultExecutor.submit(callable)
  /** Wait all tasks submited by `go` to finish. Wait forever If `milliSeconds < 0` */
  def await(milliSeconds:Long) =
    defaultExecutor.shutdown()
    if milliSeconds<0 then
      while ! defaultExecutor.awaitTermination(Long.MaxValue,TimeUnit.MILLISECONDS) do {}
    else
      defaultExecutor.awaitTermination(milliSeconds,TimeUnit.MILLISECONDS)
