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
import com.typesafe.scalalogging.slf4j.Logging

/**
 * Create thumbnails from Portable Document Format data.
 *
 * @author Ross Phillips
 */
class PDFThumbnailer extends Thumbnailer with Logging {

	private val transparentWhite = new Color(255, 255, 255, 0)

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val document = PDDocument.load(input)
		val bufferedImage = writeImageFirstPage(document, BufferedImage.TYPE_INT_RGB)
		document.close

		ImageIO.write(bufferedImage, "PNG", output)
	}

	override def supportedContentTypes = Set("application/pdf")
	
	private def writeImageFirstPage(document: PDDocument, imageType: Int) = {
		val pages = document.getDocumentCatalog.getAllPages
		val page = pages.get(0).asInstanceOf[PDPage]

		convertToImage(page, imageType, width, height)
	}

	private def convertToImage(page: PDPage, imageType: Int, thumbWidth: Int, thumbHeight: Int) = {
		val mBox = page.findMediaBox.asInstanceOf[PDRectangle]

		val widthPt = mBox.getWidth
		val heightPt = mBox.getHeight

		val widthPx = thumbWidth
		val heightPx = thumbHeight
		val scaling = thumbWidth / widthPt

		val pageDimension = new Dimension(widthPt.toInt, heightPt.toInt)

		val retval = new BufferedImage(widthPx, heightPx, imageType)
		val graphics = retval.getGraphics.asInstanceOf[Graphics2D]
		graphics.setBackground(transparentWhite)
		graphics.clearRect(0, 0, retval.getWidth, retval.getHeight)
		graphics.scale(scaling, scaling)

		val drawer = new PageDrawer
		drawer.drawPage(graphics, page, pageDimension)

		try {
			val rotation = page.findRotation

			if (rotation == 90 || rotation == 270) {
				val w = retval.getWidth
				val h = retval.getHeight
				val rotatedImg = new BufferedImage(w, h, retval.getType)
				val g = rotatedImg.createGraphics

				g.rotate(Math.toRadians(rotation), w / 2, h / 2)
				g.drawImage(retval, null, 0, 0)
			}
		} catch { case e: ImagingOpException =>
			logger.warn("Unable to rotate page image", e)
		}

		retval
	}

}
