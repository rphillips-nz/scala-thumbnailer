package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream

trait Thumbnailer {
	var width = 120
	var height = 200

	def generateThumbnail(input: InputStream, output: OutputStream)
	def generateThumbnail(input: InputStream): Array[Byte]

	def setSize(width: Int, height: Int) {
		this.width = width
		this.height = height
	}

}
