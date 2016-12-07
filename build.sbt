// ------------------------------------------------------------------------- \\
// SBT Build File                                                            \\
// ------------------------------------------------------------------------- \\

organization := "nz.co.rossphillips"

name := "scala-thumbnailer"

// If the CI supplies a "build.version" environment variable, inject it as the rev part of the version number:
version := s"${sys.props.getOrElse("build.majorMinor", "0.5")}.${sys.props.getOrElse("build.version", "SNAPSHOT")}"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	"org.apache.pdfbox" % "pdfbox" % "2.0.3",
	"org.imgscalr" % "imgscalr-lib" % "4.2",
	"commons-io" % "commons-io" % "2.4",
	"org.ostermiller" % "utils" % "1.07.00",
	"fr.opensagres.xdocreport" % "org.apache.poi.xwpf.converter.pdf" % "1.0.2",
	"com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
)

// ------------------------------------------------------------------------- \\
// Publishing                                                                \\
// ------------------------------------------------------------------------- \\

publishArtifact in (Compile, packageDoc) := false

seq(bintraySettings:_*)

licenses += ("GPL-2.0", url("http://www.gnu.org/licenses/gpl-2.0.txt"))

homepage := Some(url("https://github.com/rphillips-nz/scala-thumbnailer"))

mappings in (Compile, packageBin) ~= { (ms: Seq[(File, String)]) =>
	ms filter { case (file, toPath) =>
		toPath != "nz/co/rossphillips/thumbnailer/Main.class" &&
		toPath != "nz/co/rossphillips/thumbnailer/Main$.class"
	}
}

