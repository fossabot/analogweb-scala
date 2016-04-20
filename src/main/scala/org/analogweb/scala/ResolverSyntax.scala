package org.analogweb.scala

import scala.reflect.ClassTag
import org.analogweb.{ RequestValueResolver, MediaType, TypeMapper }
import org.analogweb.core.{ SpecificMediaTypeRequestValueResolver, UnsupportedMediaTypeException, UnresolvableValueException }

trait ResolverSyntax[T <: RequestValueResolver] {

  def resolverType: Class[T]
  def request: Request
  def get(name: String): String = of(name).getOrElse("")
  def of(name: String): Option[String] = as[String](name)

  def as[T](implicit ctag: ClassTag[T]): Option[T] = {
    as("")(ctag)
  }

  def as[T](name: String)(implicit ctag: ClassTag[T]): Option[T] = {
    Option(request.resolvers.findRequestValueResolver(resolverType)).map { implicit resolver =>
      verifyMediaType
      Option(resolver.resolveValue(request.context, request.metadata, name, ctag.runtimeClass, Array())).flatMap { i =>
        i match {
          case t: T => Some(t)
          case Some(o) => o match {
            case t: T => Some(t)
            case _ => Option(request.converters.mapToType(classOf[TypeMapper], o, ctag.runtimeClass, Array())).flatMap { e =>
              e match {
                case t: T => Some(t)
              }
            }
          }
          case _ => Option(request.converters.mapToType(classOf[TypeMapper], i, ctag.runtimeClass, Array())).flatMap { e =>
            e match {
              case t: T => Some(t)
            }
          }
        }
      }
    }.getOrElse(throw new IllegalArgumentException)
  }

  private def verifyMediaType(implicit resolver: RequestValueResolver) = {
    resolver match {
      case x: SpecificMediaTypeRequestValueResolver => request.contentTypeOption.map { c =>
        if (x.supports(c) == false) throw new UnsupportedMediaTypeException(request.requestPath)
      }
      case _ => // nop.
    }
  }

}

case class DefaultResolverSyntax[T <: RequestValueResolver](override val resolverType: Class[T], override val request: Request) extends ResolverSyntax[T]