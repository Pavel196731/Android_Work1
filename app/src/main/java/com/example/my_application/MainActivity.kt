package com.example.my_application

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
import androidx.compose.ui.unit.Dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen() // Главный экран
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreen() {
    var squares by rememberSaveable { mutableStateOf(listOf<Int>()) } // Сохранение списка квадратиков
    val gridState = rememberLazyGridState() // Запоминаем состояние для управления прокруткой

    // Получаем количество колонок через отдельную функцию
    val columns = getGridColumns()

    // Основной контейнер
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Отображаем сетку квадратов с отступом снизу
        GridSquares(squares = squares, columns = columns, paddingBottom = 80.dp, state = gridState)
        // Кнопка для добавления квадратов
        AddSquareButton(
            onClick = {
                squares = squares + (squares.size + 1) // Добавляем новый квадрат
            }
        )
    }
}

// Вынесенная функция для определения количества колонок в зависимости от ориентации
@Composable
fun getGridColumns(): Int {
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
        4 // Ландшафтная ориентация
    } else {
        3 // Портретная ориентация
    }
}

@Composable
fun GridSquares(squares: List<Int>, columns: Int, paddingBottom: Dp, state: LazyGridState) {
    LazyVerticalGrid(
        state = state, // Передаем состояние для управления скроллом
        columns = GridCells.Fixed(columns), // Устанавливаем количество колонок
        modifier = Modifier.run {
            fillMaxSize()
                .padding(bottom = paddingBottom)
        }, // Добавляем отступ снизу
        contentPadding = PaddingValues(16.dp)
    ) {
        // Отображаем каждый квадрат
        items(squares.size) { index ->
            GridItem(index = index)
        }
    }
}

@Composable
fun GridItem(index: Int) {
    // Условие для смены цвета: чётные (красные), нечётные (синие)
    val color = if (index % 2 == 0) Color.Red else Color.Blue

    Box(
        modifier = Modifier
            .padding(4.dp) // Отступ между квадратами
            .aspectRatio(1f) // Пропорция 1:1 для квадратов
            .background(color), // Устанавливаем цвет в зависимости от индекса
        contentAlignment = Alignment.Center
    ) {
        // Текст внутри квадрата
        Text(text = "${index + 1}", color = Color.White)
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
                .padding(16.dp) // Кнопка будет внизу справа
        ) {
            Text("Click Me")
        }
    }
}