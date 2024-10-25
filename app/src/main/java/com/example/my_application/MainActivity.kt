package com.example.my_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(){
    var kvadrat by rememberSaveable { mutableStateOf(listOf<Int>()) }
    val scroll = rememberScrollState()
    val columns = location()


    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        GridKvadrat(kvadrat = kvadrat,scroll, columns = columns, 80.dp)
        AddkvadratButton(
            onClick = {
                kvadrat = kvadrat + kvadrat.size
                LaunchedEffect(kvadrat.size) {
                    scroll.animateScrollTo(scroll.maxValue)
                    }
            }
        )
    }
}

@Composable
fun location():Int{
    val c = LocalConfiguration.current
    return if( c.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT ){
        3
    } else {
        4
    }

}

@Composable
fun GridKvadrat(kvadrat: List<Int>,scroll:androidx.compose.foundation.ScrollState, columns: Int, paddingButton: Dp) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingButton)
            .verticalScroll(scroll)
    ) {
        kvadrat.forEach{ index ->
            GridItem(index = index)
        }
    }
}

@Composable
fun GridItem(index: Int){
    val color = if (index % 2 == 1){
        Color.Red
    }
    else Color.Blue
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp) // Отступ между квадратами
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        // Текст внутри квадрата
        Text(
            text = "${index + 1}",
            color = Color.White
        )
    }
}

@Composable
fun AddkvadratButton(onClick: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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