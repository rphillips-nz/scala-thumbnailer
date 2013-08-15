package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import psd._
import psd.model._
import psd.util._
import psd.parser.PsdInputStream

class PSDThumbnailer extends BaseThumbnailer {

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val psd = new PsdImage(input)
//		ImageIO.write(bufferedImage, "PNG", output)
	}

	override def supportedContentTypes = Set(
		"image/photoshop",
		"image/x-photoshop",
		"image/psd",
		"application/photoshop",
		"application/psd",
		"zz-application/zz-winassoc-psd"
	)
}