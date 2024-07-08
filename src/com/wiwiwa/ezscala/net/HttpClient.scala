package com.wiwiwa.ezscala.extensions.net

import com.wiwiwa.ezscala.*

import java.io.InputStream
import java.net.{CookieManager, URI, URLEncoder}
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient as JdkHttpClient, HttpRequest, HttpResponse}
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets
import scala.util.Using

case class HttpRequestData(url:String,
  headers:Map[String,String]=Map.empty,
  client:JdkHttpClient=null,
):
  def header(kv:(String,String)*) = this.copy(headers=headers++kv)

  def get(params:(String,String)*) =
    val getUri =
      val encoded = urlEncode(params)
      val sep = if url.contains('?') then "&" else "?"
      url + sep + encoded
    HttpRequest.newBuilder(URI(getUri)) >> sendRequest
  def post(params:(String,String)*) =
    val form = urlEncode(params)
    HttpRequest.newBuilder(URI(url))
      .header("Content-Type","application/x-www-form-urlencoded")
      .POST(BodyPublishers.ofString(form))
      >> sendRequest
  private def urlEncode(params: Iterable[(String,String)]) =
    params.map { (k, v) => URLEncoder.encode(k, StandardCharsets.UTF_8) + '=' + URLEncoder.encode(v.toString, StandardCharsets.UTF_8) }
    .mkString("&")
  private def sendRequest(builder:HttpRequest.Builder) =
    headers.foreach{ builder.setHeader(_,_) }
    val httpClient = if client==null then JdkHttpClient.newHttpClient else client
    val response = httpClient.send(builder.build(), BodyHandlers.ofInputStream)
    new HttpResponseWrapper(response)
class HttpResponseWrapper(response:HttpResponse[InputStream]) extends AutoCloseable:
  def close() = response.body.close()

  def statusCode = response.statusCode
  def withStream[T](action: InputStream=>T): T =
    if response.statusCode>=400 then
      throw HttpStatusException(response)
    Using.resource(response.body)(action)
  def bytes = withStream(_.readAllBytes())
  def text = bytes.string

class HttpClient(baseUrl:String, headers:Map[String,String]=Map.empty)
extends HttpRequestData(baseUrl, headers=headers,
  client=JdkHttpClient.newBuilder.cookieHandler(new CookieManager()).build()
):
  def get(uri:String, params:(String,String)*) = this.copy(url=baseUrl+uri).get(params*)
  def post(uri:String, params:(String,String)*) = this.copy(url=baseUrl+uri).post(params*)

class HttpStatusException(response: HttpResponse[?]) extends Exception(s"http status ${response.statusCode}")