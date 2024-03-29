// simple build tool configuration 

// based on the full sbt configuration example available at
// https://github.com/harrah/xsbt/wiki/Full-Configuration-Example

import sbt._
import Keys._

object BuildSettings {
    val buildOrganization = "d2h.net"
    val buildVersion      = "1.0.0"
    val buildScalaVersion = "2.9.1"

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
        ("git branch" lines_! devnull 
         filter(_.startsWith("* ")) headOption)
        getOrElse "-" stripPrefix "* "
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

    val logbackCore    = "ch.qos.logback" % "logback-core"     % logbackVer
    val logbackClassic = "ch.qos.logback" % "logback-classic"  % logbackVer

    val apacheNet   = "commons-net"   % "commons-net"   % "2.0"
    val apacheCodec = "commons-codec" % "commons-codec" % "1.4"

    val jSoup = "org.jsoup" % "jsoup" % "1.6.1"

    val liftVersion = "2.4-M4"
    val liftWebkit = "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default"
    val liftMapper = "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default"
    val liftWizard = "net.liftweb" %% "lift-wizard" % liftVersion % "compile->default"

    // val jetty6       = "org.mortbay.jetty" % "jetty"        % "6.1.22"          % "jetty,test"
    val jetty7       = "org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "container"
    val javaxServlet = "javax.servlet"     % "servlet-api"  % "2.5"             % "provided->default"

    val dbH2 = "com.h2database" % "h2" % "1.2.138"

    val scalatest = "org.scalatest" % "scalatest_2.9.0" % "1.4.1" % "test"

    val specs2       = "org.specs2" %% "specs2"             % "1.6.1"
    val specs2Scalaz = "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test"
    val pegdown      = "org.pegdown" % "pegdown" % "1.0.2"
}

object ABCBotBuild extends Build {
    import Resolvers._
    import Dependencies._
    import BuildSettings._
    import com.github.siasia.WebPlugin.webSettings

    val commonDeps = Seq(logbackCore, logbackClassic, jSoup, liftWebkit, liftMapper, jetty7, javaxServlet, dbH2, specs2, specs2Scalaz, pegdown)
    
    lazy val abcBot = Project(
        "abcBot",
        file ("."),
        settings = buildSettings ++ webSettings ++ Seq(
            // need ScalaToolsSnapshots resolver for specs2 stuff
            resolvers += ScalaToolsSnapshots,
            libraryDependencies ++= commonDeps,

            // flag deprecated stuff
            scalacOptions += "-deprecation",

            // keep only specifications ending with Spec or Unit
            testOptions := Seq(Tests.Filter(s => Seq("Spec", "Unit").exists(s.endsWith(_)))),
            testOptions in Test += Tests.Argument("html", "console")
        )
    ) 
}
