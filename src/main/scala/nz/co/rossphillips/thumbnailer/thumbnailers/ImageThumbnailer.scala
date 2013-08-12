package nz.co.rossphillips.thumbnailer.thumbnailers

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.InputStream
import java.io.OutputStream
import org.imgscalr.Scalr.Method
import org.imgscalr.Scalr.resize
import javax.imageio.ImageIO

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
		val resized =
			if (image.getWidth > width && image.getHeight > height)
				resize(image, Method.ULTRA_QUALITY, width, height)
			else
				image

		ImageIO.write(padImage(resized), "PNG", output)
	}

	override def supportedContentTypes = ImageIO.getReaderMIMETypes.toSet

	private def padImage(image: BufferedImage) = {
		val xOffset = (width - image.getWidth) / 2
		val yOffset = (height - image.getHeight) / 2

		val output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		val g = output.createGraphics

		g.setBackground(Color.WHITE)
		g.setPaint(Color.WHITE)
		g.fillRect(0, 0, width, height)
		g.drawImage(image, null, xOffset, yOffset)
		g.dispose

		output
	}

}
