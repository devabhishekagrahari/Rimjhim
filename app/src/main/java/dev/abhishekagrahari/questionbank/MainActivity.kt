package dev.abhishekagrahari.questionbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.abhishekagrahari.questionbank.View.AboutUsScreen
import dev.abhishekagrahari.questionbank.View.AddQuestionScreen
import dev.abhishekagrahari.questionbank.View.BaseLayout
import dev.abhishekagrahari.questionbank.View.ContactUsScreen
import dev.abhishekagrahari.questionbank.View.FullPaperViewScreen
import dev.abhishekagrahari.questionbank.View.GeneratePaperScreen
import dev.abhishekagrahari.questionbank.View.HomeScreen
import dev.abhishekagrahari.questionbank.View.QuestionListScreen
import dev.abhishekagrahari.questionbank.View.ViewPaperScreen
import dev.abhishekagrahari.questionbank.model.Question
import dev.abhishekagrahari.questionbank.ui.theme.QuestionBankTheme
import dev.abhishekagrahari.questionbank.viewmodel.parseDifficultyDistribution
import dev.abhishekagrahari.questionbank.viewmodel.parseQuestions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuestionBankTheme {
                 QuestionBankApp()
            }
        }
    }
}

@Composable
fun QuestionBankApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            BaseLayout(navController = navController ){ HomeScreen(navController)} }
composable(Screen.AboutUs.route){
    BaseLayout(navController = navController , title = " About us") {
        AboutUsScreen()
    }
}
        composable(Screen.ContactUs.route){
            BaseLayout(navController = navController , title ="Contact Developer") {
                ContactUsScreen()
            }
        }
      /*  composable(
            "fullQuestionPaper/{title}/{createdBy}/{totalMarks}/{difficultyDistribution}/{questions}/{timeStamp}"
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            val createdBy = backStackEntry.arguments?.getString("createdBy")
            val totalMarks = backStackEntry.arguments?.getString("totalMarks")?.toIntOrNull()
            val timeStamp=backStackEntry.arguments?.getString("timeStamp")?.toLongOrNull()
            val difficultyDistribution = parseDifficultyDistribution(
                backStackEntry.arguments?.getString("difficultyDistribution")
            )
            val questions = parseQuestions(
                backStackEntry.arguments?.getString("questions")
            )


            BaseLayout(navController = navController, title = "Full Paper") {
                if (title != null && createdBy != null && totalMarks != null && difficultyDistribution != null && questions != null &&timeStamp!=null) {
                    FullPaperViewScreen(title = title, createdBy = createdBy, totalMarks = totalMarks, timestamp = timeStamp,difficultyDistribution = difficultyDistribution, questions = questions)
                } else {
                    // Handle missing arguments gracefully (e.g., show a loading state or error message)
                    Text("Error: Missing required data")
                }
            }
        }

        */


        
        composable(Screen.AddQuestion.route) { BaseLayout(navController = navController , title= "Let's Add Question to the Question Bank"){AddQuestionScreen { navController.popBackStack() }} }
        composable(Screen.QuestionList.route) { BaseLayout(navController = navController, title = "List of Added Questions"){QuestionListScreen() }}
        composable(Screen.GeneratePaper.route) { BaseLayout(navController = navController, title= "Let's create a new paper"){GeneratePaperScreen ( context= LocalContext.current, onPaperGenerated =  {navController.popBackStack()}) }}
        composable(Screen.ViewPaper.route) { BaseLayout(navController = navController, title = "Your Created Paper "){
            ViewPaperScreen(
            context = LocalContext.current , navController = navController
        )
        }
        }
    }
}
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddQuestion : Screen("add_question")
    object QuestionList : Screen("question_list")
    object GeneratePaper : Screen("generate_paper")
    object ViewPaper : Screen("view_paper")
    object ContactUs: Screen("contact_us")
    object AboutUs: Screen("about_us")
    object FullPaper: Screen("full_paper_screen")
}
