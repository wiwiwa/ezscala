package com.wiwiwa.ezscala.extensions

import com.wiwiwa.ezscala.*

import java.io.{File, FileInputStream, InputStream}
import java.nio.charset.StandardCharsets
import scala.util.Using

trait IoExt:
    extension (string: String)
        def file = new File(string)
        def http = net.HttpRequestData(string)
    extension (file:File)
        def withStream[R](action: InputStream=>R): R =
            Using.resource( new FileInputStream(file) )(action)
        def bytes = file.withStream{ _.readAllBytes }
        def text = new String(file.bytes, StandardCharsets.UTF_8)
    extension (stream:InputStream)
        def bytes = new Iterator[Byte]:
            var _next = 1
            override def hasNext =
                _next = stream.read()
                _next>=0
            override def next() = _next.toByte
