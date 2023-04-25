package com.wiwiwa.ezscala.pipe

import scala.annotation.targetName

trait PipeExt:
  extension[T] (value:T)
    /** Map T to R */
    def `|>` [R](mapFn: T=>R): R = mapFn(value)
    /** apply action to T, and return T */
    def `|!` [R](action: T=>R): T = { action(value); value }
  extension[T] (iter: Iterable[T])
    /** map */
    def `|`[R](predicate: T => R): Iterable[R] = iter.map(predicate)
    /** flatmap */
    def `||`[R](predicate: T => IterableOnce[R]): Iterable[R] = iter.flatMap(predicate)
    /** filter */
    def `|?`(predicate: T=>Boolean): Iterable[T] = iter.filter(predicate)
    /** tapEach */
    def `|!`[R](action: T=>R): Iterable[T] = iter.tapEach(action)
