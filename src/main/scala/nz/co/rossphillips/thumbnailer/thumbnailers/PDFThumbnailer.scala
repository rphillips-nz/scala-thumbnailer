package nz.co.rossphillips.thumbnailer.thumbnailers

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.ImagingOpException
import java.io.InputStream
import java.io.OutputStream
import org.apache.pdfbox.pdfviewer.PageDrawer
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import javax.imageio.ImageIO
import org.imgscalr.Scalr._
import nz.co.rossphillips.thumbnailer.Util
import scala.collection.JavaConversions._

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
