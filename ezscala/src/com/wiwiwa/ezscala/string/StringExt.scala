package com.wiwiwa.ezscala.string

import java.io.File
import java.net.URI
import java.nio.charset.StandardCharsets

trait StringExt:
    extension (string:String)
        def bytes = string.getBytes(StandardCharsets.UTF_8)
