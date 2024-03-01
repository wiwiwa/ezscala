package com.wiwiwa.ezscala.extensions

import com.wiwiwa.ezscala.extensions.AnyExt.*

import scala.quoted.{Expr, Quotes, Type}

trait AnyExt:
  extension[T] (value:T)
    /** if `value` is truthy, return `truthyValue`, else return null */
    transparent inline def `&&`[V](truthyValue: =>V): Any =
      if isTruthy(value) then truthyValue else defaultValue
    /** if `value` is truthy, return `value`, else return rightValue */
    inline def `||`(rightValue: =>T): T =
      if isTruthy(value) then value else rightValue

object AnyExt:
  inline def isTruthy[T](value: T): Boolean = inline value match
    case b: Boolean => b
    case i: Int => i != 0
    case s: String => s != null && s.nonEmpty
    case v: Seq[_] => v != null && v.nonEmpty
    case x => x != null
  transparent inline def defaultValue[T]: Any = ${defaultValueMacro[T]}
  def defaultValueMacro[T:Type](using Quotes): Expr[Any] = Type.of[T] match
    case '[Int] => Expr(0)
    case '[Long] => Expr(0)
    case '[String] => Expr("")
    case _ => '{null}
