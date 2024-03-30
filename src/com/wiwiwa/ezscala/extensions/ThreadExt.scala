package com.wiwiwa.ezscala.extensions

import java.util.concurrent.{Callable, Executors, Future}

trait ThreadExt:
  /** run task in new thread */
  def go[T](task: =>T): Future[T] =
    val callable: Callable[T] = () => task
    ThreadExt.defaultExecutor.submit(callable)

  extension[T] (iter:Iterable[T])
    def goEach[R](task: T=>R): Future[Iterable[R]] = go:
      val futures = iter.map: item=>
        go{ task(item) }
      futures.map(_.get())

object ThreadExt:
  val defaultExecutor =
    val factory = Thread.ofVirtual.name("VirtualThreadExecutor-", 0).factory
    Executors.newThreadPerTaskExecutor(factory)
