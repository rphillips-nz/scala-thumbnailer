package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO

import org.apache.pdfbox.pdmodel.PDDocument
import nz.co.rossphillips.thumbnailer.Util

import org.apache.pdfbox.rendering.{PDFRenderer}

/**
 * Create thumbnails from Portable Document Format data.
 *
 * @author Ross Phillips
 */
class PDFThumbnailer extends BaseThumbnailer {


	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val document = PDDocument.load(input)
		val pdfRenderer = new PDFRenderer(document)
		val image = pdfRenderer.renderImage(0)
		val resized = Util.resize(image, width, height, shouldPadThumbnail)
		document.close

		ImageIO.write(resized, "PNG", output)
	}

	override val supportedContentTypes = Set("application/pdf")

}
