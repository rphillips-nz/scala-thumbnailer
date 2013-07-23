package nz.co.rossphillips.thumbnailer

import nz.co.rossphillips.thumbnailer.thumbnailers._
import java.io.FileInputStream
import java.io.FileOutputStream


object Main {
	def main(args: Array[String]) {
		println("Creating thumbnails...")

		val pdfThumbnailer = new PDFThumbnailer
		val imageThumbnailer = new ImageThumbnailer

		val pdfInput = new FileInputStream("test.pdf")
		val pdfOutput = pdfThumbnailer.generateThumbnail(pdfInput)
		val pdfOutputStream = new FileOutputStream("pdf.png", false)
		pdfOutputStream.write(pdfOutput)
		pdfOutputStream.close
		pdfInput.close
		
		
		val imageInput = new FileInputStream("test.png")
		val imageOutput = imageThumbnailer.generateThumbnail(imageInput)
		val imageOutputStream = new FileOutputStream("png.png", false)
		imageOutputStream.write(imageOutput)
		imageOutputStream.close
		imageInput.close
		
		
		println("Finished!\n")
	}

}
