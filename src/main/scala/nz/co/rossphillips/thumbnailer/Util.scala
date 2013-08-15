package nz.co.rossphillips.thumbnailer

import java.awt.image.BufferedImage
import org.imgscalr.Scalr._
import java.awt.Color

object Util {

	def paddedResize(image: BufferedImage, width: Int, height: Int) = {
		val resized =
			if (image.getWidth > width || image.getHeight > height)
				resize(image, Method.ULTRA_QUALITY, width, height)
			else
				image

		val xOffset = (width - resized.getWidth) / 2
		val yOffset = (height - resized.getHeight) / 2

		val output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		val g = output.createGraphics

		g.setBackground(Color.WHITE)
		g.setPaint(Color.WHITE)
		g.fillRect(0, 0, width, height)
		g.drawImage(resized, null, xOffset, yOffset)
		g.dispose

		output
	}

}