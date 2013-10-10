package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * Base class for thumbnailers.
 *
 * @author Ross Phillips
 */
trait BaseThumbnailer {
	protected var width = 160
	protected var height = 200
	protected var shouldPadThumbnail = true

	/**
	 * Generates a thumbnail image from input and writes it to output.
	 *
	 * @param input the stream of input data
	 * @param output the stream to write the thumbnail to
	 */
	def generateThumbnail(input: InputStream, output: OutputStream)

	/**
	 * Returns a generated thumbnail image from input.
	 *
	 * @param input the stream of input data
	 * @param output the stream to write the thumbnail to
	 *
	 * @return the thumbnail byte array
	 */
	def generateThumbnail(input: InputStream): Array[Byte] = {
		val output = new ByteArrayOutputStream
		generateThumbnail(input, output)

		val bytes = output.toByteArray
		output.close
		bytes
	}

	/**
	 * Returns a set of the input content types supported by this thumbnailer.
	 *
	 * @return the set of supported content types
	 */
	def supportedContentTypes: Set[String]

	/**
	 * Sets the size of the generated thumbnail.
	 *
	 * @param width the width of the thumbnail
	 * @param height the height of the thumbnail
	 */
	def setSize(width: Int, height: Int) {
		this.width = width
		this.height = height
	}

	/**
	 * Sets whether or not the generated thumbnail should be padded to the
	 * thumbnail size.
	 *
	 * @param shouldPadThumbnail whether or not the thumbnail should be padded
	 */
	def setShouldPadThumbnail(shouldPadThumbnail: Boolean) {
		this.shouldPadThumbnail = shouldPadThumbnail
	}

}
