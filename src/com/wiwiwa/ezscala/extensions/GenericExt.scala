package com.wiwiwa.ezscala.extensions

import com.wiwiwa.ezscala.extensions.GenericExt.*

trait GenericExt:
  extension[T] (value:T)
    /** if `value` is truthy, return `truthyValue`, else return null */
    inline def `&&`[V](truthyValue: =>V): V =
      if isTruthy(value) then truthyValue else inline null.asInstanceOf[T] match
        case v: V => value.asInstanceOf[V]
        case _ => inline null.asInstanceOf[V] match
          case _: Boolean => false.asInstanceOf[V]
          case _: Int | _: Long => 0.asInstanceOf[V]
          case _: Float => 0.asInstanceOf[V]
          case _ => null.asInstanceOf[V]
    /** if `value` is truthy, return `value`, else return rightValue */
    inline def `||`(rightValue: =>T): T =
      if isTruthy(value) then value else rightValue

object GenericExt:
  def isTruthy[T](value: T): Boolean = value match
    case b: Boolean => b
    case i: Int => i != 0
    case s: String => s != null && s.nonEmpty
    case v: Seq[_] => v != null && v.nonEmpty
    case x => x != null
