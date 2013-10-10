package nz.co.rossphillips.thumbnailer

import java.io.InputStream
import java.io.OutputStream
import nz.co.rossphillips.thumbnailer.thumbnailers.BaseThumbnailer
import nz.co.rossphillips.thumbnailer.thumbnailers.DOCXThumbnailer
import nz.co.rossphillips.thumbnailer.thumbnailers.ImageThumbnailer
import nz.co.rossphillips.thumbnailer.thumbnailers.PDFThumbnailer
import nz.co.rossphillips.thumbnailer.thumbnailers.TextThumbnailer

/**
 * Generates thumbnails from various file types based on content type.
 *
 * @author Ross Phillips
 *
 * @constructor Construct a thumbnailer that delegates to other thumbnailers
 */
class Thumbnailer(thumbnailers: BaseThumbnailer *) {

	/**
	 * Construct a thumbnailer with all supported content types
	 */
	def this() = this(
		new PDFThumbnailer,
		new ImageThumbnailer,
		new TextThumbnailer,
		new DOCXThumbnailer
	)

	/**
	 * Generates a thumbnail image from input and writes it to output.
	 * Writes the output in PNG format.
	 *
	 * @param input the stream of input data
	 * @param output the stream to write the thumbnail to
	 * @param contentType the content type of input
	 */
	def generateThumbnail(input: InputStream, output: OutputStream, contentType: String) {
		supportedThumbnailer(contentType) match {
			case Some(thumbnailer) => thumbnailer.generateThumbnail(input, output)
			case None => throw new UnsupportedOperationException(s"No supported thumbnailer found for $contentType")
		}
	}

	/**
	 * Returns a generated thumbnail image from input.
	 * Returns the output in PNG format.
	 *
	 * @param input the stream of input data
	 * @param contentType the content type of input
	 *
	 * @return a byte array of the thumbnail data
	 */
	def generateThumbnail(input: InputStream, contentType: String) = {
		supportedThumbnailer(contentType) match {
			case Some(thumbnailer) => thumbnailer.generateThumbnail(input)
			case None => throw new UnsupportedOperationException(s"No supported thumbnailer found for $contentType")
		}
	}

	/**
	 * Sets the size of the generated thumbnail.
	 *
	 * @param width the width of the thumbnail
	 * @param height the height of the thumbnail
	 */
	def setSize(width: Int, height: Int) = thumbnailers.foreach {
		_.setSize(width, height)
	}

	/**
	 * Sets whether or not the generated thumbnail should be padded to the
	 * thumbnail size.
	 *
	 * @param shouldPadThumbnail whether or not the thumbnail should be padded
	 */
	def setShouldPadThumbnail(shouldPadThumbnail: Boolean) = thumbnailers.foreach {
		_.setShouldPadThumbnail(shouldPadThumbnail)
	}

	private def supportedThumbnailer(contentType: String) = thumbnailers.find {
		_.supportedContentTypes.contains(contentType)
	}

}
