# [scala-thumbnailer](http://rphillips-nz.github.io/scala-thumbnailer/)

A Scala library for generating thumbnails.

The API uses streams rather than files. Supports the following file types:

- PDF
- PNG, JPEG, GIF, BMP
- DOCX
- Plain text files

GPL v2.0


## Usage

**Create**

	val defaultThumbnailer = new Thumbnailer
	val thumbnailer = new Thumbnailer(new PDFThumbnailer, new TextThumbnailer)

**Output to stream**

	val thumbnailer = new Thumbnailer
	val input = new FileInputStream("input.pdf")
	val output = new FileOutputStream("output.png", false)

	thumbnailer.generateThumbnail(input, output, "application/pdf")

	output.close
	input.close

**Output to byte array**

	val thumbnailer = new Thumbnailer
	val input = new FileInputStream("input.pdf")

	val bytes: Array[Byte] = thumbnailer.generateThumbnail(input, "application/pdf")

	input.close

**Options**

	val thumbnailer = new Thumbnailer
	thumbnailer.setSize(100, 200)
	thumbnailer.setShouldPadThumbnail(false)


## Dependency Management

**SBT**

	libraryDependencies += "nz.co.rossphillips" % "scala-thumbnailer" % "0.4.0"

**Maven**

	<dependency>
		<groupId>nz.co.rossphillips</groupId>
		<artifactId>scala-thumbnailer</artifactId>
		<version>0.4.0</version>
	</dependency>

- - -

[Homepage](http://rphillips-nz.github.io/scala-thumbnailer/)

Originally inspired by [java-thumbnailer](https://github.com/benjaminpick/java-thumbnailer).
