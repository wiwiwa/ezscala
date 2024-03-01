package com.wiwiwa.ezscala.extensions

import java.nio.charset.StandardCharsets

trait StringExt:
    extension (string:String)
        def bytes = string.getBytes(StandardCharsets.UTF_8)
    extension (buf:Array[Byte])
        def string = new String(buf,StandardCharsets.UTF_8)