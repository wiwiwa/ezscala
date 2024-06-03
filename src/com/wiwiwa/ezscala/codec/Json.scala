package com.wiwiwa.ezscala.codec

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonValueCodec, readFromString, writeToString}
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker

import scala.reflect.{ClassTag, classTag}

object Json:
  private var encoderCache: Map[String, JsonValueCodec[?]] = Map.empty

  inline def encode[T:ClassTag](inline obj:T): String =
    writeToString(obj)(getEncoder())
  inline def decode[T:ClassTag](json:String): T =
    readFromString(json)(getEncoder())

  inline private def getEncoder[T:ClassTag](): JsonValueCodec[T] =
    val cls = classTag[T].runtimeClass.getName
    encoderCache.getOrElse(cls,{
        val encoder = JsonCodecMaker.make[T]
        encoderCache += cls -> encoder
        encoder
      }).asInstanceOf[JsonValueCodec[T]]