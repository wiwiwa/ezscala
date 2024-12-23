package com.wiwiwa.ezscala.codec

import com.github.plokhotnyuk.jsoniter_scala.core.{JsonValueCodec, readFromString, writeToString}
import com.github.plokhotnyuk.jsoniter_scala.macros.JsonCodecMaker

object Json:
  inline def encode[T](inline obj:T): String =
    writeToString(obj)(JsonCodecMaker.make[T])
  inline def decode[T](json:String): T =
    readFromString(json)(JsonCodecMaker.make[T])
