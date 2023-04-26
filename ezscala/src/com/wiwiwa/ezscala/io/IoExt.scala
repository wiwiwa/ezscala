package com.wiwiwa.ezscala.io

import java.io.{File, FileInputStream, InputStream}
import java.net.{URI, URL}
import java.nio.charset.StandardCharsets
import scala.util.Using

trait IoExt:
    extension (string: String)
        def file = new File(string)
        def url = new URI(string).toURL
    extension (file:File)
        def withStream[R](action: InputStream=>R): R =
            Using.resource( new FileInputStream(file) )(action)
        def bytes = file.withStream{ _.readAllBytes }
        def text = new String(file.bytes, StandardCharsets.UTF_8)
    extension (url:URL)
        def withStream[R](action: InputStream => R): R =
            Using.resource(url.openStream())(action)
        def bytes = url.withStream{_.readAllBytes}
        def text = new String(url.bytes, StandardCharsets.UTF_8)
