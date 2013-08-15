// ------------------------------------------------------------------------- \\
// SBT Build File                                                            \\
// ------------------------------------------------------------------------- \\

organization := "nz.co.rossphillips"

name := "scala-thumbnailer"

version := "0.2"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
	"org.apache.pdfbox" % "pdfbox" % "1.8.2",
	"org.imgscalr" % "imgscalr-lib" % "4.2",
	"commons-io" % "commons-io" % "2.4",
	"org.ostermiller" % "utils" % "1.07.00",
	"fr.opensagres.xdocreport" % "org.apache.poi.xwpf.converter.pdf" % "1.0.2",
	"com.typesafe" %% "scalalogging-slf4j" % "1.0.1"
)

// ------------------------------------------------------------------------- \\
// Publishing                                                                \\
// ------------------------------------------------------------------------- \\

publishMavenStyle := true

crossPaths := false

licenses := Seq("GPL v2.0" -> url("http://www.gnu.org/licenses/gpl-2.0.txt"))

homepage := Some(url("https://github.com/rphillips-nz/scala-thumbnailer"))

pomIncludeRepository := { _ => false }

pomExtra := (
	<scm>
		<url>git@github.com:rphillips-nz/scala-thumbnailer.git</url>
		<connection>scm:git@github.com:rphillips-nz/scala-thumbnailer.git</connection>
	</scm>
	<developers>
		<developer>
			<id>rphillips</id>
			<name>Ross Phillips</name>
			<url>http://rossphillips.co.nz</url>
		</developer>
	</developers>
)

publishTo <<= version { (v: String) =>
	val nexus = "https://oss.sonatype.org/"
	if (v.trim.endsWith("SNAPSHOT"))
		Some("snapshots" at nexus + "content/repositories/snapshots")
	else
		Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
