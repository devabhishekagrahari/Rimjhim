package dev.abhishekagrahari.questionbank.viewmodel


import android.content.Context
import android.os.Environment
import dev.abhishekagrahari.questionbank.model.QuestionPaper
import org.apache.pdfbox.pdmodel.*
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.io.File
import java.io.FileOutputStream

object PdfGenerator {
    fun generatePdf(context: Context, questionPaper: QuestionPaper): File? {
        return try {
            val fileName = "${questionPaper.title.replace(" ", "_")}.pdf"
            val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "QuestionPapers")
            if (!dir.exists()) dir.mkdirs()

            val file = File(dir, fileName)
            val document = PDDocument()
            val page = PDPage()
            document.addPage(page)

            val contentStream = PDPageContentStream(document, page)
            contentStream.beginText()
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18f)
            contentStream.newLineAtOffset(100f, 700f)
            contentStream.showText(questionPaper.title)
            contentStream.endText()

            var yPosition = 670f
            for ((index, question) in questionPaper.questions.withIndex()) {
                contentStream.beginText()
                contentStream.setFont(PDType1Font.HELVETICA, 14f)
                contentStream.newLineAtOffset(50f, yPosition)
                contentStream.showText("${index + 1}. question")// change
                contentStream.endText()
                yPosition -= 30f
            }

            contentStream.close()
            document.save(FileOutputStream(file))
            document.close()
            file
        } catch (e: Exception) {
            null
        }
    }
}
