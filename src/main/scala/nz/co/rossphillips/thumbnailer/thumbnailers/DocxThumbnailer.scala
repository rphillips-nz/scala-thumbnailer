package nz.co.rossphillips.thumbnailer.thumbnailers

import scala.collection.immutable.Set
import org.apache.poi.xwpf.converter.pdf.PdfOptions
import org.apache.poi.xwpf.converter.pdf.PdfConverter
import java.io.InputStream
import java.io.OutputStream
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream
import com.Ostermiller.util.CircularByteBuffer

class DocxThumbnailer extends Thumbnailer {

	private val pdfThumbnailer = new PDFThumbnailer

	override def generateThumbnail(input: InputStream, output: OutputStream) {
		val document = new XWPFDocument(input)
		val options = PdfOptions.create//.fontEncoding("windows-1250") // TODO test - This might be needed for some files?
		val buffer = new CircularByteBuffer(CircularByteBuffer.INFINITE_SIZE)

		PdfConverter.getInstance.convert(document, buffer.getOutputStream, options)
		buffer.getOutputStream.close

		pdfThumbnailer.generateThumbnail(buffer.getInputStream, output)
		buffer.getInputStream.close
	}

	override def supportedContentTypes = Set(
		"application/vnd.openxmlformats-officedocument.wordprocessingml.document"
	)

}
