package com.wiwiwa.ezscala.extensions

import com.wiwiwa.ezscala.codec.Base64

import java.nio.charset.StandardCharsets

trait StringExt:
    extension (string:String)
        def bytes = string.getBytes(StandardCharsets.UTF_8)
    extension (buf:Array[Byte])
        def string = new String(buf,StandardCharsets.UTF_8)
        def base64 = Base64.encode(buf)
        def base64Url = Base64.urlEncode(buf)
