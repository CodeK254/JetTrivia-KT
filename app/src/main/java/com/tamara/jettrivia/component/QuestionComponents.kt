package com.tamara.jettrivia.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamara.jettrivia.model.Question
import com.tamara.jettrivia.model.QuestionModel
import com.tamara.jettrivia.screens.Function
import com.tamara.jettrivia.screens.QuestionsViewModel
import com.tamara.jettrivia.util.Constants
import com.tamara.jettrivia.util.logger

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionWidget(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    viewModel: QuestionsViewModel = hiltViewModel()
) {
    if (isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = modifier
            )
        }
    } else {
        val questions: List<QuestionModel> = viewModel.questions()
        val question: QuestionModel = questions[viewModel.getCurrentIndex()]
        Column(
            modifier = modifier,
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                CategoryWidget()
                TimerWidget(
                    viewModel.getCurrentIndex(),
                    viewModel.getCurrentIndex()
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            question.question ?: "",
                            fontSize = 25.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    LazyColumn (
                        modifier = Modifier.weight(2f)
                    ) {
                        val choices: List<String> = question.choices ?: emptyList()
                        items(choices.size) { index ->
                            val choice: String = choices[index]
                            ChoiceWidget(
                                index = index + 1,
                                choice = choice,
                                isCorrect = (choice == question.answer)
                                        && viewModel.currentAnswer().isNotEmpty(),
                                isWrong = (viewModel.currentAnswer() == choice)
                                        && (viewModel.currentAnswer() != question.answer),
                            ){
                                if(viewModel.currentAnswer().isEmpty()) {
                                    viewModel.setAnswer(choices[index])
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                ToggleButton(
                    modifier = Modifier.weight(1f),
                    label = "Previous",
                    onClick = {
                        viewModel.toggleQuestions(Function.BACK)
                    }
                )
                Spacer(modifier = Modifier.width(30.dp))
                ToggleButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.toggleQuestions(Function.NEXT)
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CategoryWidget(
    category: String = "Unfiltered"
) {
    Column (
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ){
        Text(
            "CATEGORY",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Card(
            modifier = Modifier.border(
                border = BorderStroke(
                    1.dp,
                    color = Color.Black
                ),
            ),
            colors = CardDefaults.cardColors().copy(
                containerColor = Color.White
            )
        ) {
            Text(
                category.uppercase(),
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun TimerWidget(
    score: Int,
    answered: Int,
) {
    Column (
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ){
        Text(
            "SCORE",
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            "$score/$answered",
            color = Color.Black,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
fun ChoiceWidget(
    index: Int,
    choice: String,
    isCorrect: Boolean,
    isWrong: Boolean,
    onClick: () -> Unit,
){
    return Card(
        border = BorderStroke(
            1.5.dp,
            Color.Black
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = if (isCorrect){
                Color.Green
            } else if(isWrong) {
                Color.Red
            } else {
                Color.White
            }
        ),
        shape = RectangleShape,
        modifier = Modifier.padding(top = 10.dp)
            .clickable{
                onClick.invoke()
            }
    ) {
        val label: String = if(isCorrect){
            "√"
        } else if(isWrong){
            "X"
        } else {
            "0$index"
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                choice,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            Text(
                label,
                fontSize = 20.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    label: String = "Next",
    onClick: () -> Unit,
){
    return Card(
        border = BorderStroke(
            1.5.dp,
            Color.Black
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        ),
        shape = RectangleShape,
        modifier = modifier.clickable{
            onClick.invoke()
        },
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                label,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

