package nz.co.rossphillips.thumbnailer

import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Date

/**
 * Creates a few test thumbnails.
 *
 * @author Ross Phillips
 */
object Main {

	val thumbnailer = new Thumbnailer

	/**
	 * Creates the test thumbnails.
	 *
	 * @param args command line arguments
	 */
	def main(args: Array[String]) {
		createThumbnail("src/test/resources/test.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
		createThumbnail("src/test/resources/test.pdf", "application/pdf")
		createThumbnail("src/test/resources/test.png", "image/png")
		createThumbnail("src/test/resources/test.txt", "text/plain")
		createThumbnail("src/test/resources/test.jpg", "image/jpeg")
	}

	/**
	 * Opens a test file and writes to a test file.
	 *
	 * @param inputFile the file to create a thumbnail from
	 * @param contentType the content type of inputFile
	 */
	def createThumbnail(inputFile: String, contentType: String) {
		val start = new Date

		val input = new FileInputStream(inputFile)
		val outputFilename = inputFile.substring(inputFile.lastIndexOf('/') + 1)
		val output = new FileOutputStream(s"output/$outputFilename.png", false)
		thumbnailer.generateThumbnail(input, output, contentType)
		output.close
		input.close

		val time = ((new Date).getTime - start.getTime) / 1000.0
		println(s"$time seconds for $inputFile -> $outputFilename.png")
	}

}
