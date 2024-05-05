package com.wiwiwa.ezscala

import com.wiwiwa.ezscala.extensions.*

object EzScala extends PipeExt, GenericExt,
  StringExt, IoExt, ThreadExt

export EzScala.*
export net.HttpClient
