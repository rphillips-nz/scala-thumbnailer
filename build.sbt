// ------------------------------------------------------------------------- \\
// SBT Build File                                                            \\
// ------------------------------------------------------------------------- \\


organization := "ch.wavein"

name := "scala-thumbnailer"

scalaVersion := "2.12.5"

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


licenses += ("GPL-2.0", url("http://www.gnu.org/licenses/gpl-2.0.txt"))

homepage := Some(url("https://github.com/rphillips-nz/scala-thumbnailer"))

mappings in (Compile, packageBin) ~= { (ms: Seq[(File, String)]) =>
	ms filter { case (file, toPath) =>
		toPath != "nz/co/rossphillips/thumbnailer/Main.class" &&
		toPath != "nz/co/rossphillips/thumbnailer/Main$.class"
	}
}

