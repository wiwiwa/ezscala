package com.wiwiwa.ezscala.string

import java.io.File
import java.net.URI

trait StringExt:
    extension (string:String)
        def file = new File(string)
        def url = new URI(string).toURL
