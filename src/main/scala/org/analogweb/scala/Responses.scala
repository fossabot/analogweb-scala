package org.analogweb.scala

import java.io.{ File, InputStream, OutputStream, FileInputStream, ByteArrayInputStream }
import scala.collection.mutable.Map
import scala.collection.convert.decorateAsJava._
import scala.concurrent.Future
import scala.xml.NodeSeq
import org.analogweb.{ Renderable, ResponseFormatter, RequestContext, ResponseContext, ResponseEntity }
import org.analogweb.core.response._

trait Responses {
  def asText(obj: String) = Text.`with`(obj)
  def asHtmlEntity(obj: String) = Html.`with`(obj)
  def asHtml(templatePath: String) = Html.as(templatePath)
  def asHtml(templatePath: String, context: Map[String, AnyRef]) = Html.as(templatePath, context.asJava)
  def asXml(obj: AnyRef) = org.analogweb.core.response.Xml.as(obj)
  def asResource(stream: InputStream) = Resource.as(stream, "").withoutContentDisposition
  def asResource(stream: InputStream, filename: String) = Resource.as(stream, filename)
  def Status(statusCode: Int) = HttpStatus.valueOf(statusCode)
  def Status(status: HttpStatus): HttpStatus = Status(status.getStatusCode())
  def Status(statusCode: Int, responseBody: Renderable) = HttpStatus.valueOf(statusCode).`with`(responseBody)
  def Status(status: HttpStatus, responseBody: Renderable): HttpStatus = Status(status.getStatusCode(), responseBody)
  def RedirectTo(url: String) = Redirect.to(url)
  def Ok(responseBody: Renderable) = HttpStatus.OK.`with`(responseBody)
  def Ok = HttpStatus.OK
  def BadRequest(obj: Renderable) = HttpStatus.BAD_REQUEST.`with`(obj)
  def BadRequest = HttpStatus.BAD_REQUEST
  def NotFound(obj: Renderable) = HttpStatus.NOT_FOUND.`with`(obj)
  def NotFound = HttpStatus.NOT_FOUND
  def Forbidden(obj: Renderable) = HttpStatus.FORBIDDEN.`with`(obj)
  def Forbidden = HttpStatus.FORBIDDEN
  def InternalServerError(obj: Renderable) = HttpStatus.INTERNAL_SERVER_ERROR.`with`(obj)
  def InternalServerError = HttpStatus.INTERNAL_SERVER_ERROR
}
object Responses extends Responses

class ScalaJsonObject(obj: AnyRef) extends Json(obj)

class ScalaJsonText(text: String) extends Json(text)

