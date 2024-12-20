package com.wiwiwa.ezscala.extensions

trait PipeExt:
  extension[T] (value:T)
    /** Map T to R */
    def `|` [R](mapFn: T=>R): R = mapFn(value)
    /** apply action to T, and return T */
    def `|=` [R](action: T=>R): T = { action(value); value }
    /** assertion */
    inline def `|==`(expected: T): Unit = if value!=expected then
      val msg = "Assertion failed:\n" +
        s" actual value:   $value\n" +
        s" expected value: $expected"
      throw new AssertionError(msg)

  extension[T] (iter: Iterable[T])
    /** map */
    def `|>`[R](predicate:T=>R): Iterable[R] = iter.map(predicate)
    /** flatmap */
    def `|>|`[R](predicate:T=>IterableOnce[R]): Iterable[R] = iter.flatMap(predicate)
    /** filter */
    def `|?`(predicate:T=>Boolean): Iterable[T] = iter.filter(predicate)
    /** tapEach */
    def `|!`[R](action:T=>R): Iterable[T] = iter.tapEach(action)
    /** foreach */
    def `|!!`[R](action:T=>R): Unit = iter.foreach(action)
  extension[T] (iter: Iterator[T])
    /** map */
    def `|>`[R](predicate:T=>R): Iterator[R] = iter.map(predicate)
    /** flatmap */
    def `||`[R](predicate:T=>IterableOnce[R]): Iterator[R] = iter.flatMap(predicate)
    /** filter */
    def `|?`(predicate:T=>Boolean): Iterator[T] = iter.filter(predicate)
    /** tapEach */
    def `|!`[R](action:T=>R): Iterator[T] = iter.tapEach(action)
    /** foreach */
    def `|!!`[R](action:T=>R): Unit = iter.foreach(action)
