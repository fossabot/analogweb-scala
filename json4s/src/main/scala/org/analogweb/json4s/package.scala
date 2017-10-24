package analogweb

import java.lang.annotation.Annotation
import java.io.{File, InputStream, OutputStream, FileInputStream, ByteArrayInputStream}
import reflect.ClassTag
import language.implicitConversions
import org.analogweb._, util._, core._, core.DefaultReadableBuffer._, scala._
import org.json4s._, jackson.{JsonMethods, Serialization}

package object json4s {

  // Resolving JSON requests.
  val json = classOf[org.analogweb.json4s.Json4sJsonValueResolver]

  implicit def asJson4sResolverSyntax[T <: RequestValueResolver](
      typeOfResolver: Class[T]
  )(implicit request: Request, formats: Formats = DefaultFormats) =
    DefaultResolverSyntax(typeOfResolver,
                          request,
                          org.analogweb.json4s.Json4sResolverContext(formats))

  // Serializing JSON responses.
  def asJson(source: AnyRef)(implicit formats: Formats = Serialization.formats(NoTypeHints)) =
    new ScalaJsonObject((source, formats))
  def asJson(jsonText: String) = new ScalaJsonText(jsonText)

}