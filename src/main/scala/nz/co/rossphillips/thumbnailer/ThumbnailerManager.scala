package nz.co.rossphillips.thumbnailer

import java.io.InputStream
import java.io.OutputStream
import nz.co.rossphillips.thumbnailer.thumbnailers._

class ThumbnailerManager {

	private var thumbnailers: Set[Thumbnailer] = Set.empty
	def addThumbnailer(thumbnailer: Thumbnailer) = thumbnailers = thumbnailers + thumbnailer

	def supportedThumbnailer(contentType: String) = thumbnailers.find {
		_.supportedContentTypes.contains(contentType)
	}

	def generateThumbnail(input: InputStream, output: OutputStream, contentType: String) {
		supportedThumbnailer(contentType) match {
			case Some(thumbnailer) => thumbnailer.generateThumbnail(input, output)
			case None => throw new Exception(s"No supported thumbnailer found for $contentType")
		}
	}

	def generateThumbnail(input: InputStream, contentType: String) = {
		supportedThumbnailer(contentType) match {
			case Some(thumbnailer) => thumbnailer.generateThumbnail(input)
			case None => throw new Exception(s"No supported thumbnailer found for $contentType")
		}
	}

}
