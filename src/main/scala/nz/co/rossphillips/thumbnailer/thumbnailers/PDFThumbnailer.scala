package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import scala.collection.JavaConversions.asScalaBuffer
import nz.co.rossphillips.thumbnailer.Util

/**
 * Create thumbnails from Portable Document Format data.
 *
 * @author Ross Phillips
 */
class PDFThumbnailer extends BaseThumbnailer {

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val document = PDDocument.load(input)
		val page = document.getDocumentCatalog.getAllPages.head.asInstanceOf[PDPage]
		val bufferedImage = Util.paddedResize(page.convertToImage, width, height)
		document.close

		ImageIO.write(bufferedImage, "PNG", output)
	}

	override def supportedContentTypes = Set("application/pdf")

}
