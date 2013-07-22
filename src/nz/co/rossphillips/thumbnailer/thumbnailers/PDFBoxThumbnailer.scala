package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import org.apache.pdfbox.pdmodel.PDDocument
import java.awt.image.BufferedImage
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.awt.Dimension
import java.awt.Graphics2D
import org.apache.pdfbox.pdfviewer.PageDrawer
import java.awt.image.ImagingOpException
import java.awt.Transparency
import java.awt.Color
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream

class PDFBoxThumbnailer extends Thumbnailer {
	
	private val TRANSPARENT_WHITE = new Color(255, 255, 255, 0)
	private val width = 120
	private val height = 200
	
	override def generateThumbnail(input: InputStream, output: OutputStream) {

	}

	override def generateThumbnail(input: InputStream): Array[Byte] = {
		val document = PDDocument.load(input)
		val bufferedImage = writeImageFirstPage(document, BufferedImage.TYPE_INT_RGB)
		val output = new ByteArrayOutputStream
		
		if (bufferedImage.getWidth == width) {
			ImageIO.write(bufferedImage, "PNG", output)
		} else {
			println("BRO NEED TO IMPLEMENT THIS IN PDFBoxThumbnailer")
			throw new Exception("BRO NEED TO IMPLEMENT THIS IN PDFBoxThumbnailer")
		}
//			val resizer = new ResizeImage(width, height)
			
//		}

					//			if (tmpImage.getWidth() == thumbWidth) {
					//				ImageIO.write(tmpImage, "PNG", output);
					//			} else {
					//				ResizeImage resizer = new ResizeImage(thumbWidth, thumbHeight);
					//				resizer.resizeMethod = ResizeImage.RESIZE_FIT_ONE_DIMENSION;
					//				resizer.setInputImage(input);
					//				resizer.writeOutput(output);
					//			}
					//		} finally {
					//			if (document != null) {
					//				try {
					//					document.close();
					//				} catch (IOException e) {
					//					throw new ThumbnailerException("Could not close PDF File", e);
					//				}
					//			}
					//		}
		val bytes = output.toByteArray
		output.close
		bytes
	}

	override def setSize(width: Int, height: Int) {

	}

	private def writeImageFirstPage(document: PDDocument, imageType: Int): BufferedImage = {//} throws IOException {
		val pages = document.getDocumentCatalog.getAllPages
		val page = pages.get(0).asInstanceOf[PDPage]

		val image = convertToImage(page, imageType, width, height)
		return image
	}

	private def convertToImage(page: PDPage, imageType: Int, thumbWidth: Int, thumbHeight: Int): BufferedImage = {// throws IOException
		val mBox = page.findMediaBox.asInstanceOf[PDRectangle]

		val widthPt = mBox.getWidth
		val heightPt = mBox.getHeight

		val widthPx = thumbWidth             // Math.round(widthPt * scaling);
		val heightPx = thumbHeight           // Math.round(heightPt * scaling);
		val scaling = thumbWidth / widthPt   // resolution / 72.0F;

		val pageDimension = new Dimension(widthPt.toInt, heightPt.toInt)

		val retval = new BufferedImage(widthPx, heightPx, imageType)
		val graphics = retval.getGraphics.asInstanceOf[Graphics2D]
		graphics.setBackground(TRANSPARENT_WHITE)
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
			//log.warn("Unable to rotate page image", e);
		} 
		retval
	}

}
