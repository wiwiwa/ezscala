package com.wiwiwa.ezscala.codec

object Base64:
  private val encoder = java.util.Base64.getEncoder
  private val decoder = java.util.Base64.getDecoder
  private lazy val urlEncoder = java.util.Base64.getUrlEncoder.withoutPadding
  private lazy val urlDecoder = java.util.Base64.getUrlDecoder

  def encode(value:Array[Byte]): String = encoder.encodeToString(value)
  def urlEncode(value:Array[Byte]): String = urlEncoder.encodeToString(value)
  def decode(base64:String): Array[Byte] =
    val dec = if base64.exists{c=> c=='-'||c=='_'} then urlDecoder else decoder
    dec.decode(base64)
  def isBase64(s:String) = s.forall{c=> c>='0'&&c<='9' || c>='A'&&c<='Z' || c>='a'&&c<='z' || c=='+' || c=='/' || c=='=' || c=='\n' || c=='\r'}
