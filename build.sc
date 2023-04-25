import $ivy.`com.wiwiwa::mill-spring-boot:1.26`, com.wiwiwa.mill.ScalaLibraryModule
import mill._
import mill.scalalib._

object ezscala extends ScalaLibraryModule {
  override def scalaVersion = "3.2.1"
  override def organization = "com.wiwiwa"

  object test extends Tests with TestModule.Utest {
    override def ivyDeps = T{ super.ivyDeps() ++ Agg(
      ivy"com.lihaoyi::utest:0.8.1"
    )}
  }
}
