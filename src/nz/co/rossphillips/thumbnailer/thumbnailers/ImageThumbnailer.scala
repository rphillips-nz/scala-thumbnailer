package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import org.imgscalr.Scalr._
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream

class ImageThumbnailer extends Thumbnailer {

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val image = ImageIO.read(input)
		val resized = resize(image, Method.ULTRA_QUALITY, width, height)

		ImageIO.write(resized, "PNG", output)
	}

	override def supportedContentTypes = Set(
		"image/png",
		"image/jpeg",
		"image/gif",
		"image/tiff",
		"image/pjpeg",
		"image/svg+xml"
	)

}
