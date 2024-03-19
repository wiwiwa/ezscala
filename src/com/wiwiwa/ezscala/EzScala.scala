package com.wiwiwa.ezscala

import com.wiwiwa.ezscala.extensions.*

object EzScala extends PipeExt, AnyExt,
  StringExt, IoExt, ThreadExt

export EzScala.*
export net.HttpClient
