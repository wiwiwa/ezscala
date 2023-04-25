package com.wiwiwa.ezscala.any

import scala.annotation.targetName

trait GenericExt:
  extension[T] (value:T)
    def `|>` [R](action: T=>R): R = action(value)
    def `|!` [R](action: T=>R): T = { action(value); value }
