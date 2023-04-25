package com.wiwiwa.ezscala.io

import java.io.{File, FileInputStream, InputStream}
import java.nio.charset.StandardCharsets
import scala.util.Using

trait FileExt:
    extension (file:File)
        def withStream[R](action: InputStream=>R): R =
            Using.resource( new FileInputStream(file) )(action)
        def bytes = file.withStream{ _.readAllBytes }
        def text = new String(file.bytes, StandardCharsets.UTF_8)
