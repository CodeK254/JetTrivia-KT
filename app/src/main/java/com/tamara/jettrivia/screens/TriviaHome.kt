package com.tamara.jettrivia.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamara.jettrivia.component.QuestionWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaHome(
    modifier: Modifier = Modifier,
    viewModel: QuestionsViewModel = hiltViewModel()
) {
    val questions = viewModel.questions()
    val isLoading = viewModel.isLoading()

    TriviaHomeContent(
        isLoading = isLoading,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaHomeContent(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            "TRIVIA",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu Icon"
                    )
                },
                actions = {}
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = modifier.padding(
                paddingValues = paddingValues,
            ).padding(horizontal = 20.dp),
        ) {
            QuestionWidget(
                isLoading = isLoading,
                modifier = Modifier
            )
        }
    }
}
