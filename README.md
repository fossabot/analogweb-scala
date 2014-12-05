Analogweb Framework Scala
===============================================

[![Build Status](https://travis-ci.org/analogweb/scala-plugin.svg?branch=master)](https://travis-ci.org/analogweb/scala-plugin)

This plugin enables to execute Analogweb's route written in Scala.

Add to SBT dependency.

```scala
val scalaplugin = "org.analogweb" %% "analogweb-scala" % "0.9.2-SNAPSHOT"
```
## Example

```scala
import org.analogweb.core.httpserver.HttpServers
import org.analogweb.scala.Analogweb
import java.net.URI

object Run {
  def main(args: Array[String]): Unit = {
    HttpServers.create(URI.create("http://localhost:8080")).start()
  }
}

class Hello extends Analogweb {
  def hello = get("/hello") { request => 
    s"Hello, ${request.query("name")} !"
  }
}
```
