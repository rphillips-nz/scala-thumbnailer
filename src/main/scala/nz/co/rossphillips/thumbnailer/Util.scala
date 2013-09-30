package nz.co.rossphillips.thumbnailer

import java.awt.image.BufferedImage
import org.imgscalr.Scalr
import org.imgscalr.Scalr.Method._
import java.awt.Color

object Util {

	def resize(image: BufferedImage, width: Int, height: Int, padImage: Boolean = true) = {
		val resized =
			if (image.getWidth > width || image.getHeight > height)
				Scalr.resize(image, ULTRA_QUALITY, width, height)
			else
				image

		if (padImage) {
			val xOffset = (width - resized.getWidth) / 2
			val yOffset = (height - resized.getHeight) / 2

			val padded = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
			val g = padded.createGraphics

			g.setBackground(Color.WHITE)
			g.setPaint(Color.WHITE)
			g.fillRect(0, 0, width, height)
			g.drawImage(resized, null, xOffset, yOffset)
			g.dispose

			padded
		} else {
			resized
		}
	}

}
