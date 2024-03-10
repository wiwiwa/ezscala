package com.wiwiwa.ezscala.extensions

import java.nio.charset.StandardCharsets
import java.util.Base64

trait StringExt:
    extension (string:String)
        def bytes = string.getBytes(StandardCharsets.UTF_8)
    extension (buf:Array[Byte])
        def string = new String(buf,StandardCharsets.UTF_8)
        def base64 = Base64.getEncoder.encodeToString(buf)
        def base64Url = Base64.getUrlEncoder.encodeToString(buf)
