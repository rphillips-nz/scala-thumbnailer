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
 * @constructor Construct a thumbnailer that delegates to thumbnailers
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

	private var delegateThumbnailers: Set[BaseThumbnailer] = Set.empty
	
	/**
	 * Adds a thumbnailer to the pool of available thumbanilers.
	 * Doesn't check for duplicates or multiple thumbnailers supporting the same
	 * content type.
	 * Writes the output in PNG format.
	 *
	 * @param thumbnailer the thumbnailer to add
	 */
	def addThumbnailer(thumbnailer: BaseThumbnailer) {
		delegateThumbnailers += thumbnailer
	}

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
			case None => throw new Exception(s"No supported thumbnailer found for $contentType")
		}
	}

	/**
	 * Returns a generated thumbnail image from input.
	 *
	 * @param input the stream of input data
	 * @param contentType the content type of input
	 *
	 * @return a byte array of the thumbnail data
	 */
	def generateThumbnail(input: InputStream, contentType: String) = {
		supportedThumbnailer(contentType) match {
			case Some(thumbnailer) => thumbnailer.generateThumbnail(input)
			case None => throw new Exception(s"No supported thumbnailer found for $contentType")
		}
	}

	private def supportedThumbnailer(contentType: String) = thumbnailers.find {
		_.supportedContentTypes.contains(contentType)
	}

}
