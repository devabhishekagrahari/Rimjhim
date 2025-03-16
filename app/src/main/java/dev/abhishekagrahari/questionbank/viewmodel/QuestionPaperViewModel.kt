package dev.abhishekagrahari.questionbank.viewmodel



import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import dev.abhishekagrahari.questionbank.model.QuestionPaper
import dev.abhishekagrahari.questionbank.repository.QuestionPaperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class QuestionPaperViewModel(
    private val repository: QuestionPaperRepository = QuestionPaperRepository()
) : ViewModel() {

    private val _papers = MutableStateFlow<List<QuestionPaper>>(emptyList())
    val papers: StateFlow<List<QuestionPaper>> = _papers
init {
    getCategory()
}
    /** Fetch all question papers */
    fun loadQuestionPapers() {
        viewModelScope.launch {
            _papers.value = repository.getQuestionPapers()
        }
    }

    /** Add a new question paper */
    fun addQuestionPaper(paper: QuestionPaper, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.addQuestionPaper(paper)
            onResult(success)
            if (success) loadQuestionPapers()
        }
    }
    fun generateQuestionPaper(category: String, difficulty: String, createdBy: String,  questionCount: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            val questions = repository.getQuestionsByCategoryAndDifficulty(category, difficulty)
            val selectedQuestions = questions.shuffled().take(questionCount)

            if (selectedQuestions.isNotEmpty()) {
                val paper = QuestionPaper(
                    id = System.currentTimeMillis().toString(),
                    title = "$category Paper ($difficulty - $questionCount Questions)",
                    questions = selectedQuestions,
                    createdBy = createdBy
                )

                repository.saveQuestionPaper(paper)
                onComplete()
            } else {
                onComplete()
            }
        }
    }
    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    fun getCategory() {
        viewModelScope.launch {
            val fetchedCategories = repository.getQuestionCategories()

            // Ensure UI updates even if the list is empty
            _categories.value = if (fetchedCategories.isNotEmpty()) fetchedCategories else listOf("General")
        }
    }

    @SuppressLint("NewApi")
    fun generateQuestionPaperPdf(context: Context, questionPaper: QuestionPaper, fileName: String) {
        val contentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, "$fileName.pdf")
            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS) // Saves in Downloads
        }

        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            try {
                contentResolver.openOutputStream(it)?.use { outputStream ->
                    val pdfWriter = PdfWriter(outputStream)
                    val pdfDocument = PdfDocument(pdfWriter)
                    val document = Document(pdfDocument)

                    // Title
                    document.add(Paragraph("📄 ${questionPaper.title}").setBold().setFontSize(18f))
                    document.add(Paragraph("👨‍🏫 Created by: ${questionPaper.createdBy}").setItalic())
                    document.add(Paragraph("📅 Date: ${java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").format(questionPaper.timestamp)}"))
                    document.add(Paragraph("📝 Total Marks: ${questionPaper.totalMarks}\n"))

                    // Difficulty Distribution
                    document.add(Paragraph("📊 Difficulty Distribution:"))
                    questionPaper.difficultyDistribution.forEach { (difficulty, count) ->
                        document.add(Paragraph("   - $difficulty: $count question(s)"))
                    }

                    document.add(Paragraph("\n📌 Questions:\n").setBold())

                    // Add Questions
                    questionPaper.questions.forEachIndexed { index, question ->
                        document.add(Paragraph("${index + 1}. ${question.question}"))
                        val optionList = com.itextpdf.layout.element.List()
                        question.options?.forEach { option ->
                            optionList.add(com.itextpdf.layout.element.ListItem(option))
                        }
                        document.add(optionList)
                        document.add(Paragraph("\n"))
                    }

                    document.close()
                    Toast.makeText(context, "✅ PDF saved to Downloads", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "❌ Error saving PDF", Toast.LENGTH_LONG).show()
            }
        } ?: run {
            Toast.makeText(context, "❌ Failed to create file", Toast.LENGTH_LONG).show()
        }
    }
    }




