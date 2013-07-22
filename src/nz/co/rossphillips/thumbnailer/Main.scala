package nz.co.rossphillips.thumbnailer

import nz.co.rossphillips.thumbnailer.thumbnailers.PDFBoxThumbnailer
import java.io.FileInputStream
import java.io.FileOutputStream

object Main {
	def main(args: Array[String]) {
		println("Creating thumbnail...")
		val thumbnailer = new PDFBoxThumbnailer

		val input = new FileInputStream("test.pdf")
		val output = thumbnailer.generateThumbnail(input)
		
		println(output)
		val outputStream = new FileOutputStream("test.png", false)
		outputStream.write(output)
		
		//outputStream.close
//		input.close
		
		println("Finished!\n")
	}

}
