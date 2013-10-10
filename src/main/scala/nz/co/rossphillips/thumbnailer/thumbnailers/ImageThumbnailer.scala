package nz.co.rossphillips.thumbnailer.thumbnailers

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO
import nz.co.rossphillips.thumbnailer.Util

/**
 * Creates thumbnails from most image files.
 * Will fit entire image in width and height, padding with white to keep ratio.
 * TIFF not supported.
 *
 * @author Ross Phillips
 */
class ImageThumbnailer extends BaseThumbnailer {

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val image = ImageIO.read(input)

		ImageIO.write(Util.resize(image, width, height, shouldPadThumbnail), "PNG", output)
	}

	override val supportedContentTypes = ImageIO.getReaderMIMETypes.toSet

}
