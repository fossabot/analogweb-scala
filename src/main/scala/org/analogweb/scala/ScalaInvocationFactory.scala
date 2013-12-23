package org.analogweb.scala

import org.analogweb._
import org.analogweb.core._

class ScalaInvocationFactory extends DefaultInvocationFactory {

  override def
    createInvocation(ca:ContainerAdaptor,im:InvocationMetadata,rc:RequestContext,rsc:ResponseContext,tc:TypeMapperContext,rvr:RequestValueResolvers):Invocation
    = {
      im match {
        case sim:ScalaInvocationMetadata => new ScalaInvocation(sim.getDefinedPath,Some(sim.action))
        case _ => super.createInvocation(ca,im,rc,rsc,tc,rvr) 
      }
   }
}
