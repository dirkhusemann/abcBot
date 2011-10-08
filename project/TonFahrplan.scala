// simple build tool configuration 

// based on the full sbt configuration example available at
// https://github.com/harrah/xsbt/wiki/Full-Configuration-Example

import sbt._
import Keys._

object BuildSettings {
  val buildOrganization = "d2h.net"
  val buildVersion      = "1.0.0"
  val buildScalaVersion = "2.9.0-1"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion,
    shellPrompt  := ShellPrompt.buildShellPrompt
  )
}

// Shell prompt which show the current project, 
// git branch and build version
object ShellPrompt {
  object devnull extends ProcessLogger {
    def info (s: => String) {}
    def error (s: => String) { }
    def buffer[T] (f: => T): T = f
  }
  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
  )

  val buildShellPrompt = { 
    (state: State) => {
      val currProject = Project.extract (state).currentProject.id
      "%s:%s:%s> ".format (
        currProject, currBranch, BuildSettings.buildVersion
      )
    }
  }
}

object Resolvers {
}

object Dependencies {
  val logbackVer = "0.9.16"

  val logbackcore    = "ch.qos.logback" % "logback-core"     % logbackVer
  val logbackclassic = "ch.qos.logback" % "logback-classic"  % logbackVer

  val apachenet   = "commons-net"   % "commons-net"   % "2.0"
  val apachecodec = "commons-codec" % "commons-codec" % "1.4"

  val scalatest = "org.scalatest" % "scalatest_2.9.0" % "1.4.1" % "test"
}

object TonFahrplanBuild extends Build {
  import Resolvers._
  import Dependencies._
  import BuildSettings._

  // Sub-project specific dependencies
  val commonDeps = Seq (
    logbackcore,
    logbackclassic,
    jacksonjson,
    scalatest
  )

  val serverDeps = Seq (
    grizzlyframwork,
    grizzlyhttp,
    grizzlyrcm,
    grizzlyutils,
    grizzlyportunif,
    sleepycat,
    scalatest
  )

  val pricingDeps = Seq (apachenet, apachecodec, scalatest)
  
  lazy val cdap2 = Project (
    "cdap2",
    file ("."),
    settings = buildSettings
  ) aggregate (common, server, compact, pricing, pricing_service)

  lazy val common = Project (
    "common",
    file ("cdap2-common"),
    settings = buildSettings ++ Seq (libraryDependencies ++= commonDeps)
  )
                             
  lazy val server = Project (
    "server",
    file ("cdap2-server"),
    settings = buildSettings ++ Seq (resolvers := oracleResolvers, 
                                     libraryDependencies ++= serverDeps)
  ) dependsOn (common)

  lazy val pricing = Project (
    "pricing",
    file ("cdap2-pricing"),
    settings = buildSettings ++ Seq (libraryDependencies ++= pricingDeps)
  ) dependsOn (common, compact, server)

  lazy val pricing_service = Project (
    "pricing-service",
    file ("cdap2-pricing-service"),
    settings = buildSettings
  ) dependsOn (pricing, server)

  lazy val compact = Project (
    "compact",
    file ("compact-hashmap"),
    settings = buildSettings
  )
}
