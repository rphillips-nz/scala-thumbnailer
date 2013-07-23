package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import java.io.ByteArrayOutputStream

trait Thumbnailer {
	var width = 120
	var height = 170

	def generateThumbnail(input: InputStream, output: OutputStream)

	def generateThumbnail(input: InputStream): Array[Byte] = {
		val output = new ByteArrayOutputStream
		generateThumbnail(input, output)

		val bytes = output.toByteArray
		output.close
		bytes
	}

	def supportedContentTypes: Set[String]

	def setSize(width: Int, height: Int) {
		this.width = width
		this.height = height
	}

}
