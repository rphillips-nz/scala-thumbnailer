package nz.co.rossphillips.thumbnailer

import nz.co.rossphillips.thumbnailer.thumbnailers._
import java.io.FileInputStream
import java.io.FileOutputStream


object Main {
	def main(args: Array[String]) {
		val thumbnailer = new ThumbnailerManager
		thumbnailer.addThumbnailer(new PDFThumbnailer)
		thumbnailer.addThumbnailer(new ImageThumbnailer)
		thumbnailer.addThumbnailer(new TextThumbnailer)

		val pdfInput = new FileInputStream("test.pdf")
		val pdfOutput = new FileOutputStream("pdf.png", false)
		thumbnailer.generateThumbnail(pdfInput, pdfOutput, "application/pdf")
		pdfOutput.close
		pdfInput.close

		val imageInput = new FileInputStream("test.png")
		val imageOutput = thumbnailer.generateThumbnail(imageInput, "image/png")
		val imageOutputStream = new FileOutputStream("png.png", false)
		imageOutputStream.write(imageOutput)
		imageOutputStream.close
		imageInput.close

		val textInput = new FileInputStream("test.txt")
		val textOutput = thumbnailer.generateThumbnail(textInput, "text/plain")
		val textOutputStream = new FileOutputStream("plain.png", false)
		textOutputStream.write(textOutput)
		textOutputStream.close
		textInput.close
	}

}
