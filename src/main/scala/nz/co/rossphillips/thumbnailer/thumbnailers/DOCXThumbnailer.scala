package nz.co.rossphillips.thumbnailer.thumbnailers

import java.io.InputStream
import java.io.OutputStream
import scala.collection.immutable.Set
import org.apache.poi.xwpf.converter.pdf.PdfConverter
import org.apache.poi.xwpf.converter.pdf.PdfOptions
import org.apache.poi.xwpf.usermodel.XWPFDocument
import com.Ostermiller.util.CircularByteBuffer

/**
 * Creates thumbnails from Microsoft Office Open XML Format Documents (2007).
 *
 * @author Ross Phillips
 */
class DOCXThumbnailer extends BaseThumbnailer {

	private val pdfThumbnailer = new PDFThumbnailer

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val document = new XWPFDocument(input)

		def generate(options: PdfOptions) = {
			val buffer = new CircularByteBuffer(CircularByteBuffer.INFINITE_SIZE)

			PdfConverter.getInstance.convert(document, buffer.getOutputStream, options)
			buffer.getOutputStream.close

			pdfThumbnailer.generateThumbnail(buffer.getInputStream, output)
			buffer.getInputStream.close
		}

		try {
			generate(PdfOptions.create)
		} catch { case e: Exception =>
			generate(PdfOptions.create.fontEncoding("windows-1250"))
		}
	}

	override val supportedContentTypes = Set(
		"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
	)

	override def setSize(width: Int, height: Int) {
		super.setSize(width, height)
		pdfThumbnailer.setSize(width, height)
	}

	override def setShouldPadThumbnail(shouldPadThumbnail: Boolean) {
		super.setShouldPadThumbnail(shouldPadThumbnail)
		pdfThumbnailer.setShouldPadThumbnail(shouldPadThumbnail)
	}

}
