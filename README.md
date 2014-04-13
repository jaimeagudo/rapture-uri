[![Build Status](https://travis-ci.org/propensive/rapture-uri.png?branch=master)](https://travis-ci.org/propensive/rapture-uri)

# Rapture URI

Rapture URI provides idiomatic and extensible support for working with URIs and URLs in Scala.

### Status

Rapture URI is *managed*. This means that the API is expected to continue to evolve, but all API changes will be documented with instructions on how to upgrade.

### Availability

Rapture URI 0.9.0 is available under the Apache 2.0 License from Maven Central with group ID `com.propensive` and artifact ID `rapture-uri_2.10`.

#### SBT

You can include Rapture URI as a dependency in your own project by adding the following library dependency to your build file:

```scala
libraryDependencies ++= Seq("com.propensive" %% "rapture-uri" % "0.9.0")
```

#### Maven

If you use Maven, include the following dependency:

```xml
<dependency>
  <groupId>com.propensive</groupId>
  <artifactId>rapture-uri_2.10</artifactId>
  <version>0.9.0<version>
</dependency>
```

#### Download

You can download Rapture URI directly from the [Rapture website](http://rapture.io/)
Rapture URI depends on Scala 2.10 and Rapture Core, but has no other dependencies.

#### Building from source

To build Rapture URI from source, follow these steps:

```
git clone git@github.com:propensive/rapture-uri.git
cd rapture-uri
sbt package
```

If the compilation is successful, the compiled JAR file should be found in target/scala-2.10
