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
		val image = document.getDocumentCatalog.getAllPages.head.asInstanceOf[PDPage].convertToImage
		val resized = Util.resize(image, width, height, shouldPadThumbnail)
		document.close

		ImageIO.write(resized, "PNG", output)
	}

	override val supportedContentTypes = Set("application/pdf")

}
