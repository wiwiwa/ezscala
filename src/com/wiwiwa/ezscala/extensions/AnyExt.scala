package com.wiwiwa.ezscala.extensions

import com.wiwiwa.ezscala.extensions.AnyExt.*

trait AnyExt:
  extension[T] (value:T)
    /** if `value` is truthy, return `truthyValue`, else return null */
    inline def `&&`[V](truthyValue: =>V): V =
      if isTruthy(value) then truthyValue else inline truthyValue match
        case _:Int => 0.asInstanceOf[V]
        case _ => null.asInstanceOf[V]
    /** if `value` is null, return `default`, else return null */
    inline def `||`(default: =>T): T =
      if value==null then default else value

object AnyExt:
  inline def isTruthy[T](value: T): Boolean = inline value match
    case b: Boolean => b
    case i: Int => i != 0
    case s: String => s != null && s.nonEmpty
    case v: Seq[_] => v != null && v.nonEmpty
    case x => x != null