package nz.co.rossphillips.thumbnailer

import nz.co.rossphillips.thumbnailer.thumbnailers._
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Date


object Main {

	val thumbnailer = new ThumbnailerManager

	def main(args: Array[String]) {
		thumbnailer.addThumbnailer(new PDFThumbnailer)
		thumbnailer.addThumbnailer(new ImageThumbnailer)
		thumbnailer.addThumbnailer(new TextThumbnailer)
		thumbnailer.addThumbnailer(new DOCXThumbnailer)

		createThumbnail("src/main/resources/test.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
		createThumbnail("src/main/resources/test.pdf", "application/pdf")
		createThumbnail("src/main/resources/test.png", "image/png")
		createThumbnail("src/main/resources/test.txt", "text/plain")
		createThumbnail("src/main/resources/test.jpg", "image/jpeg")
	}

	def createThumbnail(inputFile: String, contentType: String) {
		val start = new Date

		val input = new FileInputStream(inputFile)
		val outputFilename = inputFile.substring(inputFile.lastIndexOf('/') + 1)
		val output = new FileOutputStream(s"output/$outputFilename.png", false)
		thumbnailer.generateThumbnail(input, output, contentType)
		output.close
		input.close

		val time = ((new Date).getTime - start.getTime) / 1000.0
		println(s"$time seconds for $inputFile -> $outputFilename.png")
	}

}
