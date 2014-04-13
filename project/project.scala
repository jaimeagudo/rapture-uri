object project extends ProjectSettings {
  def scalaVersion = "2.11.0-RC4"
  def version = "0.9.1"
  def name = "uri"
  def description = "Rapture URI provides idiomatic and extensible support for working with URIs and URLs in Scala."
  
  def dependencies = Seq(
    "core" -> "0.9.0"
  )
  
  def thirdPartyDependencies = Nil

  def imports = Seq(
    "rapture.core._",
    "rapture.test._"
  )
}
