package com.example.my_application

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel


class MainActivity : ComponentActivity() {
    private val squaresViewModel: ViewModel1 by viewModels<ViewModel1> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //squaresViewModel.squares = savedInstanceState?.getInt("SQUARE_COUNT")?:0
        setContent {
            MainScreen(squaresViewModel)
        }
    }

}

@Composable
fun MainScreen(savedSquareCount: ViewModel1) {
    /*var squaresSize by rememberSaveable { mutableIntStateOf(savedSquareCount.squares) }*/
    var screenColor by remember { mutableStateOf<Int>(0) }
    val scrollState = rememberLazyGridState()
    val columns = getGridColumns()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp,
                vertical = 20.dp)
    ) {
        if (screenColor != 0){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(if (screenColor % 2 == 0) Color.Blue else Color.Red)
                        .clickable {screenColor = 0} // Сброс нажатия по клику
                )

            }
        else {
            GridSquares(
                squares = savedSquareCount.squares,
                columns = columns,
                state = scrollState,
                Click = {color ->
                    if (color == Color.Blue) {
                        screenColor = 2
                    }
                    else screenColor = 1
        }
            )
        }
        AddSquareButton(
            onClick = {
                savedSquareCount.addSquares()
            }
        )
        if (savedSquareCount.squares > columns)
        LaunchedEffect(savedSquareCount.squares) {
                scrollState.animateScrollToItem(savedSquareCount.squares)
                     }
            }
}

@Composable
fun getGridColumns(): Int {
    val location = LocalConfiguration.current
    return if (location.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 3
}

@Composable
fun GridSquares(squares: Int, columns: Int, state: LazyGridState,Click: (Color) -> Unit ) {
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.padding(bottom = 80.dp)
    ) {
        items(squares, key = { it }) { index ->
            GridItem(index = index, onClick = Click)
        }
    }
}


@Composable
fun GridItem(index: Int, onClick: (Color) -> Unit) {
    var color = if (index % 2 == 0) Color.Blue else Color.Red
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .aspectRatio(1f)
            .background(color)
            .clickable {
                    onClick(color)
                },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${index + 1}",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
        )
    }
}

@Composable
fun AddSquareButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(bottom = 5.dp)
                .align(Alignment.BottomCenter)
        )
        { Text(stringResource(R.string.click_me), fontSize = 28.sp) }
    }
}