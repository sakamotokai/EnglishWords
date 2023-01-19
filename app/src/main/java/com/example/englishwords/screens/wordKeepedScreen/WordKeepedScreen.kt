package com.example.englishwords.screens.wordKeepedScreen

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.screens.Card
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WordKeepedScreen(){
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
        val mainViewModel: MainViewModel = koinViewModel()
        mainViewModel.deleteEmptyWordFromRoom()
        mainViewModel.getAllFromRoom()
        val allRoomWords = mainViewModel.getAllRoomData.collectAsState()
        allRoomWords.value?.forEach {item->
            Card(item = item.word, color = OwnTheme.colors.blue, centerText = true)
        }
        allRoomWords.value?.forEach {item->
            Card(item = item.word, color = OwnTheme.colors.blue)
        }
        allRoomWords.value?.forEach {item->
            Card(item = item.word, color = OwnTheme.colors.blue)
        }
        allRoomWords.value?.forEach {item->
            Card(item = item.word, color = OwnTheme.colors.blue)
        }
    }
}

@Composable
fun WordKeepedCard(
    item: String,
    color: androidx.compose.ui.graphics.Color,
    centerText: Boolean = false,
    cardType: String = "",
){
    var extentCardSize by remember{ mutableStateOf(false) }
    val dunamicPadding = animateIntAsState(targetValue = if(extentCardSize) 90 else 30)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(OwnTheme.colors.secondaryBackground, shape = CircleShape)
            .border(width = 3.dp, color = color, shape = CircleShape)
            .shadow(4.dp, CircleShape.copy(CornerSize(90)))
            .padding(start = 20.dp, end = 10.dp)
            .clickable {

            }
            .height(dunamicPadding.value.dp)
    ) {
        Column {
            if (cardType != "")
                Text(text = cardType, color = OwnTheme.colors.primaryText)
            val text = AnnotatedString(item)
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                style =
                if (centerText)
                    LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        color = OwnTheme.colors.primaryText
                    )
                else
                    LocalTextStyle.current.copy(color = OwnTheme.colors.primaryText)
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
