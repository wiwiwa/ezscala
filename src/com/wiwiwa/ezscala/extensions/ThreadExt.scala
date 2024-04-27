package com.wiwiwa.ezscala.extensions

import java.util.concurrent.{Callable, Executors, Future}

trait ThreadExt:
  /** run task in new thread */
  def go[T](task: =>T): Future[T] =
    val callable: Callable[T] = () => task
    ThreadExt.defaultExecutor.submit(callable)

  extension[T] (iter:Iterable[T])
    def goEach[R](task: T=>R): Future[Iterable[R]] = goEach(Int.MaxValue)(task)
    def goEach[R](maxThread: Int)(task: T=>R): Future[Iterable[R]] = go:
      val nThread = ThreadCounter()
      val futures = iter.map: item=>
        nThread.synchronized:
          nThread.increment()
          if nThread.value>maxThread then
            nThread.wait()
        go:
          val ret = task(item)
          nThread.synchronized:
            nThread.decrement()
            nThread.notify()
          ret
      futures.map(_.get())

object ThreadExt:
  val defaultExecutor =
    val factory = Thread.ofVirtual.name("VirtualThreadExecutor-", 0).factory
    Executors.newThreadPerTaskExecutor(factory)

class ThreadCounter(var value:Int=0):
  def increment() = value += 1
  def decrement() = value -= 1
