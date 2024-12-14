package com.wiwiwa.ezscala.extensions

import com.wiwiwa.ezscala.codec.Base64

import java.nio.charset.StandardCharsets
import scala.util.matching.Regex

trait StringExt:
    extension (string:String)
        def bytes = string.getBytes(StandardCharsets.UTF_8)

        def matches(regex: Regex): Boolean = regex.matches(string)
        def find(regex: Regex): String = regex.findFirstIn(string).getOrElse("")
        def findGroups(regex:Regex): List[String] = regex.findFirstMatchIn(string).map(_.subgroups).getOrElse(Nil)
        def find1(regex:Regex): String = findGroups(regex)(1)
        def find2(regex:Regex): String = findGroups(regex)(2)
        def find3(regex:Regex): String = findGroups(regex)(3)
        def replace(regex:Regex, replacement:String) = regex.replaceFirstIn(string,replacement)
        def replaceAll(regex:Regex, replacement:String) = regex.replaceAllIn(string,replacement)
        def replaceAll(regex:Regex, replacer:Regex.Match=>String) = regex.replaceAllIn(string,replacer)
        
        def debase64(): Array[Byte] = Base64.decode(string)

    extension (buf:Array[Byte])
        def string = new String(buf,StandardCharsets.UTF_8)
        def base64 = Base64.encode(buf)
        def base64Url = Base64.urlEncode(buf)
