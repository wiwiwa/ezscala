package com.wiwiwa.ezscala.extensions

trait AnyExt:
  extension[T] (value:T)
    /** if `value` if truthy then truthyValue, else null value */
    inline def `??`[V](truthyValue: =>V): V =
      val truthy = inline value match
        case i: Int => i != 0
        case s: String => s != null && s.nonEmpty
        case v: Seq[_] => v != null && v.nonEmpty
        case x => x != null
      if truthy then truthyValue else inline truthyValue match
        case _:Int => 0.asInstanceOf[V]
        case _ => null.asInstanceOf[V]
    inline def `?=`(default: =>T): T =
      val truthy = inline value match
        case i:Int => i!=0
        case v => v!=null
      if truthy then value else default
