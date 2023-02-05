package com.example.englishwords.screens.wordKeepedScreen

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.screens.ourUiElements.CustomOutlinedTextField
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import kotlinx.coroutines.*
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun WordKeepedScreen() {
    val mainViewModel: MainViewModel = get()
    mainViewModel.deleteEmptyWordFromRoom()
    mainViewModel.getAllFromRoom()
    val allRoomWordsCollection = mainViewModel.getAllRoomData.collectAsState()
    var searchedWord by remember { mutableStateOf("") }
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Spacer(modifier = Modifier.height(OwnTheme.dp.bigDp))
            WordKeepedSearchBar { searchedWord = it }
            Spacer(modifier = Modifier.height(OwnTheme.dp.mediumDp))
        }
        val allRoomWordsReverb = allRoomWordsCollection.value?.reversed()
        if (allRoomWordsReverb != null && searchedWord == "") {
            for (i in allRoomWordsReverb.indices) {
                item {
                    val item = allRoomWordsReverb[i]
                    WordKeepedCard(
                        item = item.word,
                        color = OwnTheme.colors.savedCard,
                        roomData = item
                    )
                }
            }
        } else {
            allRoomWordsReverb ?: return@LazyColumn
            for (modeldb in allRoomWordsReverb) {
                try {//I'm really apologize for this use try catch
                    if (modeldb.word.substring(searchedWord.indices) == searchedWord) {
                        item {
                            WordKeepedCard(
                                item = modeldb.word,
                                color = OwnTheme.colors.savedCard,
                                roomData = modeldb
                            )
                        }
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordKeepedSearchBar(returnWord: (String) -> Unit) {
    var searchedWord by remember { mutableStateOf("") }
    CustomOutlinedTextField(
        value = searchedWord,
        onValueChange = {
            searchedWord = it
            returnWord(it)
        },
        placeholder = {
            Text(
                text = "Search",
                fontSize = OwnTheme.typography.general.fontSize,
                color = OwnTheme.colors.secondaryText
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = OwnTheme.colors.secondaryBackground),
        modifier = Modifier
            .height(OwnTheme.dp.bigDp)
            .fillMaxWidth()
            .padding(start = OwnTheme.dp.mediumDp, end = OwnTheme.dp.mediumDp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = OwnTheme.colors.secondaryText
            )
        },
        contentPaddingValues = PaddingValues(top = OwnTheme.dp.bigDp / 5),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {

        })
    )
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
        DisposableEffect(key1 = scope) {
            return@DisposableEffect onDispose {
                roomData.apply {
                    mainViewModel.update(
                        Modeldb(
                            id = id,
                            word = word,
                            linkToSound = linkToSound,
                            definitions = definitions,
                            examples = examples,
                            note = userText
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(OwnTheme.dp.normalDp))
        Row(Modifier.fillMaxWidth()) {
            WordKeepedFloatingActionButton(roomData.linkToSound)
            TextField(
                value = userText,
                onValueChange = {
                    scope.launch {
                        userText = it
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