object project extends ProjectSettings {
  def scalaVersion = "2.10.4"
  def version = "0.10.0"
  def name = "uri"
  def description = "Rapture URI provides idiomatic and extensible support for working with URIs and URLs in Scala."
  
  def dependencies = Seq(
    "core" -> "0.10.0"
  )
  
  def thirdPartyDependencies = Nil

  def imports = Seq(
    "rapture.core._"
  )
}
