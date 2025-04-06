package dev.abhishekagrahari.questionbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import dev.abhishekagrahari.questionbank.View.About.AboutUsScreen
import dev.abhishekagrahari.questionbank.View.Home.AddQuestionScreen
import dev.abhishekagrahari.questionbank.View.BaseLayout
import dev.abhishekagrahari.questionbank.View.Contact.ContactUsScreen
import dev.abhishekagrahari.questionbank.View.Home.GeneratePaperScreen
import dev.abhishekagrahari.questionbank.View.Home.HomeScreen
import dev.abhishekagrahari.questionbank.View.Home.QuestionListScreen
//import dev.abhishekagrahari.questionbank.View.QuizScreen
import dev.abhishekagrahari.questionbank.View.Home.ViewPaperScreen
import dev.abhishekagrahari.questionbank.ui.theme.QuestionBankTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by rememberSaveable { mutableStateOf(false) }

            QuestionBankTheme(darkTheme = darkTheme) {
                 QuestionBankApp(
                     darkTheme = darkTheme,
                     onThemeChange = { darkTheme = it }
                 )
            }
        }
    }
}

@Composable
fun QuestionBankApp(darkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            BaseLayout(navController = navController , darkTheme = darkTheme, onThemeChange = onThemeChange){
                HomeScreen(navController)
            }
        }
        /*
        composable(Screen.quiz.route) {
            BaseLayout(navController = navController ){ /*QuizScreen(navController = navController)*/ } }

         */
        composable(Screen.AboutUs.route){
            BaseLayout(navController = navController , title = "About us", darkTheme = darkTheme, onThemeChange = onThemeChange) {
                AboutUsScreen()
            }
        }
        composable(Screen.ContactUs.route){
            BaseLayout(navController = navController , title ="Contact Developer", darkTheme = darkTheme, onThemeChange = onThemeChange) {
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
        composable(Screen.AddQuestion.route) {
            BaseLayout(navController = navController, title = "Let's Add Question", darkTheme = darkTheme, onThemeChange = onThemeChange) {
                AddQuestionScreen { navController.popBackStack() }
            }
        }
        composable(Screen.QuestionList.route) {
            BaseLayout(navController = navController, title = "List of Added Questions", darkTheme = darkTheme, onThemeChange = onThemeChange) {
                QuestionListScreen()
            }
        }
        composable(Screen.GeneratePaper.route) {
            BaseLayout(navController = navController, title = "Create a New Paper", darkTheme = darkTheme, onThemeChange = onThemeChange) {
                GeneratePaperScreen(context = LocalContext.current, onPaperGenerated = { navController.popBackStack() })
            }
        }
        composable(Screen.ViewPaper.route) {
            BaseLayout(navController = navController, title = "Your Created Paper", darkTheme = darkTheme, onThemeChange = onThemeChange) {
                ViewPaperScreen(context = LocalContext.current, navController = navController)
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
    object quiz:Screen("quiz")
}
