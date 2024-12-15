package com.wiwiwa.ezscala.extensions

import com.wiwiwa.ezscala.*

import java.io.*
import java.nio.charset.StandardCharsets
import scala.util.Using

trait IoExt:
    extension[T<:AutoCloseable] (resource:T)
        /** Consume resource,and close resource */
        inline def using[V](action: T=>V): V = use(action)
        /** Consume resource,and close resource  */
        def use[V](action: T=>V): V =
            try action(resource)
            finally resource.close()

    extension (string: String)
        def file = new File(string)
        def http = net.HttpRequestData(string)

        def >>(out: OutputStream): Unit = string.getBytes(StandardCharsets.UTF_8) >> out
        def >>(file: File): Unit = string.getBytes(StandardCharsets.UTF_8) >> file

    extension (file:File)
        def bytes = new FileInputStream(file).use{ _.readAllBytes }
        def text = new String(file.bytes, StandardCharsets.UTF_8)

    extension (stream:InputStream)
        def bytes = new Iterator[Byte]:
            var _next = 1
            override def hasNext =
                _next = stream.read()
                _next>=0
            override def next() = _next.toByte
        def >>(out:OutputStream): Unit =
            stream.transferTo(out)
        def >>(file:File): Unit = new FileOutputStream(file).use(stream.>>)

    extension (buf:Iterator[Byte])
        def >>(out:OutputStream): Unit = buf.map(_.toInt).foreach(out.write)
        def >>(file:File): Unit = Using.resource(new FileOutputStream(file)):
            buf >> _
    extension (buf:Array[Byte])
        def >>(out:OutputStream): Unit = buf.iterator >> out
        def >>(file:File): Unit = buf.iterator >> file
    extension (buf:Iterable[Byte])
        def >>(out:OutputStream): Unit = buf.iterator >> out
        def >>(file:File): Unit = buf.iterator >> file
