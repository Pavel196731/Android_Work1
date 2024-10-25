package com.example.my_application

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreen() {
    var kvadrat by rememberSaveable { mutableStateOf(listOf<Int>()) } // список квадратиков
    val state = rememberLazyGridState() // состояния для скролла
    val columns = getGridColumns()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GridKvadrat(kvadrat = kvadrat, columns = columns, paddingBottom = 80.dp, state = state)
        AddSquareButton(
            onClick = {
                kvadrat = kvadrat + (kvadrat.size + 1)
            }
        )
        if (kvadrat.isNotEmpty() && kvadrat.size>columns) {
            LaunchedEffect(kvadrat.size) {
                state.scrollToItem(kvadrat.size - (kvadrat.size % columns))
            }
        }
    }
}

@Composable
fun getGridColumns(): Int {
    val location = LocalConfiguration.current
    return if (location.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
        4
    } else {
        3
    }
}

@Composable
fun GridKvadrat(kvadrat: List<Int>, columns: Int, paddingBottom: Dp, state: LazyGridState) {
    LazyVerticalGrid(
        state = state, // скролл
        columns = GridCells.Fixed(columns), // количество колонок
        modifier = Modifier.run {
            fillMaxSize()
                .padding(bottom = paddingBottom)
        },
        contentPadding = PaddingValues(16.dp)
    ) {
        items(kvadrat.size) { index ->
            GridItem(index = index)
        }
    }
}

@Composable
fun GridItem(index: Int) {
    val color = if (index % 2 == 0) Color.Red else Color.Blue

    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${index + 1}",
            fontSize = 25.sp,
           // fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Medium,
            color = Color.White)
    }
}

@Composable
fun AddSquareButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Click Me")
        }
    }
}