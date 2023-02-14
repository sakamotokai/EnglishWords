package com.example.englishwords.screens.cardsScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import org.koin.androidx.compose.get

@Composable
fun CardsScreen() {
    val mainViewModel: MainViewModel = get()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = scope) {
        mainViewModel.getAllFromRoom()
    }
    Column {
        getListByDataSort(mainViewModel)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun getListByDataSort(mainViewModel: MainViewModel) {
    val localData: MutableList<String> = mutableListOf()
    val listOfModeldb = mainViewModel.getAllRoomData.collectAsState()
    var localCounter = -1
    val listModeldb = mutableListOf(mutableListOf<Modeldb>())
    listOfModeldb.value?.forEach {
        if (it.data == localData) {
            listModeldb[localCounter].add(it)
        } else {
            localCounter++
            localData.clear()
            localData.addAll(it.data)
            if (localCounter != 0) listModeldb.add(mutableListOf(it))
            else listModeldb[localCounter].add(it)
        }
    }
    LazyColumn {
        items(listModeldb.reversed()) {
            Spacer(modifier = Modifier.height(OwnTheme.dp.largeDp))
            Text(
                text = try {
                    it[0].data[0] + "." + it[0].data[1] + "." + it[0].data[2]
                } catch (e: Exception) {
                    "No data"
                },
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize,
                modifier = Modifier
                    .padding(start = OwnTheme.dp.normalDp)
            )
            LazyRow {
                items(it.reversed()) {
                    Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
                    CardsScreenMainCardFront(modeldb = it, height = 150, width = 350)
                    Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
                }
            }
        }
    }
}

@Composable
fun CardsScreenMainCardFront(modeldb: Modeldb, width: Int, height: Int) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .border(
                width = 2.dp,
                color = OwnTheme.colors.tintColor,
                shape = OwnTheme.sizesShapes.mediumShape
            )
            .background(OwnTheme.colors.secondaryBackground, OwnTheme.sizesShapes.mediumShape)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Column {
                Box(
                    modifier = Modifier
                        .width(width = (width / 2).dp)
                        .height((height / 2).dp)
                        .border(width = 1.dp, color = OwnTheme.colors.tintColor)
                        .background(OwnTheme.colors.secondaryBackground)
                ) {
                    Text(
                        text = modeldb.word,
                        fontSize = OwnTheme.typography.general.fontSize,
                        color = OwnTheme.colors.primaryText,
                        fontStyle = OwnTheme.typography.heading.fontStyle
                    )
                }
                Box(
                    modifier = Modifier
                        .width(width = (width / 2).dp)
                        .height((height / 2).dp)
                        .border(width = 1.dp, color = OwnTheme.colors.tintColor)
                        .background(OwnTheme.colors.secondaryBackground)
                ) {
                    Column {
                        var localSimilarWordContainer by remember { mutableStateOf("") }
                        Text(
                            text = "Similar words",
                            fontSize = OwnTheme.typography.general.fontSize,
                            color = OwnTheme.colors.primaryText
                        )
                        modeldb.similar.forEach {
                            Text(
                                text = it,
                                fontSize = OwnTheme.typography.general.fontSize,
                                color = OwnTheme.colors.primaryText
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .width(width = (width / 2).dp)
                    .fillMaxHeight()
                    .border(width = 1.dp, color = OwnTheme.colors.tintColor)
                    .background(OwnTheme.colors.secondaryBackground)
            ) {
                Column {

                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = OwnTheme.dp.normalDp, bottom = OwnTheme.dp.normalDp)
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .border(2.dp, OwnTheme.colors.tintColor, shape = newShape)
            )
        }
    }
}

@Composable
fun CardsScreenMainCardBack() {

}

private val newShape = GenericShape { size, _ ->
    moveTo(size.width, size.height)
    lineTo(size.width, 0f)
    lineTo(0f, size.height)
}

class CustomShape(private val count: Float) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                reset()
                arcTo(
                    Rect(
                        left = -count,
                        right = count,
                        top = -count,
                        bottom = count
                    ),
                    startAngleDegrees = 90.0f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
                lineTo(x = size.width, y = 0f)
                close()
            }
        )
    }
}
