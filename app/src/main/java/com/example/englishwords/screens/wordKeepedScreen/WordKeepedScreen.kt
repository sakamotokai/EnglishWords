package com.example.englishwords.screens.wordKeepedScreen

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.models.retrofitModels.CompletedResult
import com.example.englishwords.navigation.Screen
import com.example.englishwords.screens.MainCard
import com.example.englishwords.screens.WindowAboveCard
import com.example.englishwords.screens.mainScreen.MainScreenViewModel
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import kotlinx.coroutines.*
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WordKeepedScreen() {
    val mainViewModel: MainViewModel = get()
    mainViewModel.deleteEmptyWordFromRoom()
    mainViewModel.getAllFromRoom()
    var allRoomWords = mainViewModel.getAllRoomData.collectAsState()
    val rangeForHandling = 20//range must be no huge or i think logic can crash
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        val scope = CoroutineScope(SupervisorJob())
        if (allRoomWords.value != null) {
            var startList = 0
            var endList = rangeForHandling - 1
            repeat(allRoomWords.value!!.size / rangeForHandling) {
                scope.launch(Dispatchers.Default) {
                    for (i in startList..endList) {
                        item {
                            val item = allRoomWords.value!![i]
                            WordKeepedCard(
                                item = item.word,
                                color = OwnTheme.colors.blue,
                                roomData = item
                            )
                        }
                    }
                    startList += rangeForHandling
                    endList += rangeForHandling
                }
            }
            scope.launch {
                startList =
                    if (allRoomWords.value!!.size > rangeForHandling/* - 1*/) allRoomWords.value!!.size - endList + rangeForHandling else 0
                endList = allRoomWords.value!!.size - 1
                if (endList - startList >= 0 && endList < allRoomWords.value!!.size)
                    scope.launch(Dispatchers.Default) {
                        for (i in startList..endList) {
                            item {
                                val item = allRoomWords.value!![i]
                                WordKeepedCard(
                                    item = item.word,
                                    color = OwnTheme.colors.savedCard,
                                    roomData = item
                                )
                            }
                        }
                    }
            }
        }
    }
}

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation",
    "UnrememberedMutableState"
)
@Composable
fun WordKeepedCard(
    item: String,
    color: Color,
    roomData: Modeldb
) {
    val scope = CoroutineScope(SupervisorJob())
    val mainViewModel: MainViewModel = getViewModel()
    var animationValue by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(OwnTheme.colors.secondaryBackground, shape = OwnTheme.sizesShapes.bigShape)
            .border(width = 3.dp, color = color, shape = OwnTheme.sizesShapes.bigShape)
            .padding(start = 20.dp, end = 10.dp, top = OwnTheme.dp.normalDp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = "delete",
                color = OwnTheme.colors.primaryText,
                modifier = Modifier
                    .clickable {
                        mainViewModel.deleteByName(item)
                    },
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
            Text(
                text = item,
                textAlign = TextAlign.Center,
                color = OwnTheme.colors.primaryText,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        animationValue = !animationValue
                    },
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
        }
        AnimatedVisibility(
            visible = animationValue,
        ) {
            WordKeepedCardContent(roomData = roomData, mainViewModel)
        }
        Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
    }
    Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
}

@Composable
fun WordKeepedFloatingActionButton(linkToSound: String) {
    val mediaPlayer = MediaPlayer()
    FloatingActionButton(
        onClick = {
            try {
                mediaPlayer.setDataSource(linkToSound)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: java.lang.Exception) {
                mediaPlayer.start()
            }
        },
        shape = CircleShape,
        containerColor = OwnTheme.colors.blue
    ) {
        Icon(
            imageVector = Icons.Filled.Call,
            contentDescription = null,
            tint = OwnTheme.colors.primaryBackground
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordKeepedCardContent(roomData: Modeldb, mainViewModel: MainViewModel) {
    Column {
        val scope = rememberCoroutineScope()
        var userText by remember { mutableStateOf(roomData.note ?: "") }
        Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        Row(Modifier.fillMaxWidth()) {
            WordKeepedFloatingActionButton(roomData.linkToSound)
            TextField(//I don't know how do that trick with coroutine and delay in couple seconds
                value = userText,
                onValueChange = {
                    userText = it
                    //scope.launch {
                        roomData.apply {
                            mainViewModel.update(
                                Modeldb(
                                    id = id,
                                    word = word,
                                    linkToSound = linkToSound,
                                    definitions = definitions,
                                    examples = examples,
                                    note = it
                                )
                            )
                        //}
                    }
                },
                Modifier.background(OwnTheme.colors.secondaryBackground),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = OwnTheme.colors.primaryText,
                    containerColor = OwnTheme.colors.secondaryBackground,
                    focusedIndicatorColor = OwnTheme.colors.secondaryBackground,
                    unfocusedIndicatorColor = OwnTheme.colors.secondaryBackground
                ),
                textStyle = TextStyle(fontSize = OwnTheme.typography.general.fontSize.value.sp),
                placeholder = {
                    Text(
                        text = "You can add note by taping to here",
                        color = OwnTheme.colors.secondaryText,
                        fontSize = OwnTheme.typography.general.fontSize.value.sp
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        Text(
            text = "Definitions:",
            color = OwnTheme.colors.primaryText,
            fontSize = OwnTheme.typography.general.fontSize.value.sp
        )
        roomData.definitions.forEach { definition ->
            Text(
                text = definition,
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        }
        Text(
            text = "Examples:",
            color = OwnTheme.colors.primaryText,
            fontSize = OwnTheme.typography.general.fontSize.value.sp
        )
        roomData.examples.forEach { example ->
            Text(
                text = example,
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
            Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        }
    }
}