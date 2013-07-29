package nz.co.rossphillips.thumbnailer

import nz.co.rossphillips.thumbnailer.thumbnailers._
import java.io.FileInputStream
import java.io.FileOutputStream


object Main {

	val thumbnailer = new ThumbnailerManager

	def main(args: Array[String]) {
		thumbnailer.addThumbnailer(new PDFThumbnailer)
		thumbnailer.addThumbnailer(new ImageThumbnailer)
		thumbnailer.addThumbnailer(new TextThumbnailer)
		thumbnailer.addThumbnailer(new DocxThumbnailer)

		createThumbnail("test/resources/test.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
		createThumbnail("test/resources/test.pdf", "application/pdf")
		createThumbnail("test/resources/test.png", "image/png")
		createThumbnail("test/resources/test.txt", "text/plain")
	}

	def createThumbnail(inputFile: String, contentType: String) {
		val input = new FileInputStream(inputFile)
		val output = new FileOutputStream(s"output/$inputFile.png", false)
		thumbnailer.generateThumbnail(input, output, contentType)
		output.close
		input.close
	}

}
