package com.wiwiwa.ezscala

import com.wiwiwa.ezscala.extensions.*

object EzScala extends PipeExt,
  StringExt, IoExt

export EzScala.*
export net.HttpClient