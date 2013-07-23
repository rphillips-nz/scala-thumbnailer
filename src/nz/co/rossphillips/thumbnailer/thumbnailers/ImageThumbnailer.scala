package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import org.imgscalr.Scalr
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream

class ImageThumbnailer extends Thumbnailer {

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val image = ImageIO.read(input)
		val resized = Scalr.resize(image, width, height)

		ImageIO.write(resized, "PNG", output)
	}

	override def generateThumbnail(input: InputStream): Array[Byte] = {
		val output = new ByteArrayOutputStream
		generateThumbnail(input, output)
		
		val bytes = output.toByteArray
		output.close
		bytes
	}

}
