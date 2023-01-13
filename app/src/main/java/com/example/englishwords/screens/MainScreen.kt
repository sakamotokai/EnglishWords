package com.example.englishwords.screens

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.englishwords.models.retrofitModels.CompletedResult
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CommitPrefEdits")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(isDarkmode: MutableState<Boolean>, ownStyle: MutableState<OwnTheme.OwnStyle>) {
    Log.e("Log","MainScreen")
    var buttonWasClicked by remember { mutableStateOf(false) }
    val mediaPlayer = MediaPlayer()
    var clickOnButtonBoolean: Boolean by remember { mutableStateOf(false) }
    var animatedErrorButton: Boolean by remember { mutableStateOf(false) }
    var moveButton: Boolean by remember { mutableStateOf(false) }
    val viewModel = getViewModel<MainViewModel>()
    var endPoint by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val completedResult = viewModel.completedResult.collectAsState()
    var urlToListening: String? by remember { mutableStateOf("") }
    Log.e("Log","Recomposing to based")
    val scale by animateFloatAsState(
        targetValue = if (animatedErrorButton) 1.1f else 1f,
        animationSpec = repeatable(
            iterations = 5,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = {
            animatedErrorButton = false
        }
    )
    val errorColor by animateColorAsState(
        targetValue = if (animatedErrorButton) OwnTheme.colors.error else Color(
            0,
            0,
            0,
            0
        ),
    )
    val animatedFloat by animateFloatAsState(
        targetValue = if (urlToListening == null) 3f else 0f
    )
    AnimatedContent(
        targetState = isDarkmode.value,
        transitionSpec = { fadeIn() with fadeOut() }
    ) {
        Column(modifier = Modifier.background(color = OwnTheme.colors.primaryBackground)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        OwnTheme.colors.secondaryBackground,
                        shape = OwnTheme.sizesShapes.mediumShape
                    ),
                horizontalArrangement = Arrangement.End
            ) {
                TopBar(isDarkmode = isDarkmode, ownStyle = ownStyle)
            }
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                endPoint = endPoint,
                returnEndPoint = { endPoint = it },
                moveButton = { moveButton = it })
            AnimatedContent(
                targetState = moveButton,
                transitionSpec = {
                    if (moveButton) {
                        slideInHorizontally(animationSpec = spring(2f)) with slideOutHorizontally()
                    } else {
                        slideInHorizontally(animationSpec = spring(2f)) with slideOutHorizontally()
                    }
                }
            ) {
                Row(
                    horizontalArrangement = if (it) Arrangement.Start else Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            viewModel.getCompletedResult(endPoint)
                            buttonWasClicked = true
                            urlToListening = null
                            scope.launch {
                                clickOnButtonBoolean = !clickOnButtonBoolean
                                if (endPoint.isEmpty() && !moveButton) {
                                    animatedErrorButton = true
                                }
                                if (!endPoint.isEmpty()) {
                                    moveButton = true
                                }
                            }
                        },
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = if (animatedErrorButton) scale else 1f
                                scaleY = if (animatedErrorButton) scale else 1f
                            }
                            .border(
                                width = 4.dp,
                                color = errorColor,
                                shape = RoundedCornerShape(100)
                            )
                            .shadow(5.dp, RoundedCornerShape(40)),
                        colors = ButtonDefaults.buttonColors(containerColor = OwnTheme.colors.purple)
                    ) {
                        Text(text = "Search", color = OwnTheme.colors.primaryText)
                    }

                }
                AnimatedVisibility(
                    visible = moveButton
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        FloatingActionButton(
                            onClick = {
                                try {
                                    mediaPlayer.setDataSource(urlToListening)
                                    mediaPlayer.prepare()
                                    mediaPlayer.start()
                                } catch (e: java.lang.Exception) {
                                    mediaPlayer.start()
                                }
                            },
                            shape = CircleShape,
                            containerColor = OwnTheme.colors.purple,
                            modifier = if (animatedFloat != 0f) {
                                Modifier
                                    .border(animatedFloat.dp, Color.Red, shape = CircleShape)
                                    .clickable(enabled = false) {}
                            } else Modifier
                        ) {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint = OwnTheme.colors.primaryText
                            )
                        }
                    }
                }
            }
            ColumnOfContent(
                viewModel = viewModel,
                completedResult = completedResult.value,
                buttonWasClicked = buttonWasClicked,
                returnUrl = {urlToListening = it}
            )
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ColumnOfContent(
    viewModel: MainViewModel,
    completedResult: CompletedResult?,
    buttonWasClicked: Boolean,
    returnUrl:(result:String?)->Unit
) {
    Log.e("Log","ColumnOnContent")
    if (viewModel.loading.value) CircularProgressIndicator(Modifier.fillMaxSize()) else {
        if (completedResult!=null) {
            if (completedResult.isSuccess) {
                returnUrl(completedResult.urlToListening)
                ShowCard(
                    completedResult = completedResult
                )
            } else {
                Text(
                    text = "Entered the wrong word",
                    color = OwnTheme.colors.primaryText,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        } else if (buttonWasClicked) {
            Text(
                text = "Entered the wrong word",
                color = OwnTheme.colors.primaryText,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun TopBar(isDarkmode: MutableState<Boolean>, ownStyle: MutableState<OwnTheme.OwnStyle>) {
    Log.e("Log","TopBar")
    FloatingActionButton(
        onClick = { ownStyle.value = OwnTheme.OwnStyle.Black },
        modifier = Modifier
            .border(1.dp, color = OwnTheme.colors.primaryText, shape = CircleShape)
            .size(40.dp),
        containerColor = OwnTheme.colors.black,
        shape = CircleShape,
    ) {}
    Spacer(modifier = Modifier.width(4.dp))
    FloatingActionButton(
        onClick = { ownStyle.value = OwnTheme.OwnStyle.Green },
        modifier = Modifier
            .border(1.dp, color = OwnTheme.colors.primaryText, shape = CircleShape)
            .size(40.dp),
        containerColor = OwnTheme.colors.green,
        shape = CircleShape
    ) {}
    Spacer(modifier = Modifier.width(4.dp))
    FloatingActionButton(
        onClick = { ownStyle.value = OwnTheme.OwnStyle.Blue },
        modifier = Modifier
            .border(1.dp, color = OwnTheme.colors.primaryText, shape = CircleShape)
            .size(40.dp),
        containerColor = OwnTheme.colors.blue,
        shape = CircleShape
    ) {}
    Spacer(modifier = Modifier.width(4.dp))
    FloatingActionButton(
        onClick = { ownStyle.value = OwnTheme.OwnStyle.Red },
        modifier = Modifier
            .border(1.dp, color = OwnTheme.colors.primaryText, shape = CircleShape)
            .size(40.dp),
        containerColor = OwnTheme.colors.red,
        shape = CircleShape
    ) {}
    Spacer(modifier = Modifier.width(4.dp))
    FloatingActionButton(
        onClick = { ownStyle.value = OwnTheme.OwnStyle.Purple },
        modifier = Modifier
            .border(1.dp, color = OwnTheme.colors.primaryText, shape = CircleShape)
            .size(40.dp),
        containerColor = OwnTheme.colors.purple,
        shape = CircleShape
    ) {}
    Spacer(modifier = Modifier.width(4.dp))
    IconButton(
        onClick = {
            isDarkmode.value = !isDarkmode.value
        },
        Modifier.height(40.dp)
    ) {
        Icon(
            if (isDarkmode.value) Icons.Filled.Build else Icons.Filled.Call,
            null,
            tint = OwnTheme.colors.primaryText
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    endPoint: String,
    returnEndPoint: (endPoint: String) -> Unit,
    moveButton: (result: Boolean) -> Unit
) {
    Log.e("Log","SearchBar")
    var endPoint by remember {
        mutableStateOf(endPoint)
    }
    OutlinedTextField(
        value = endPoint,
        onValueChange = {
            endPoint = it
            returnEndPoint(it)
            if (it.isEmpty()) moveButton(false)
        },
        modifier = Modifier
            .border(3.dp, color = OwnTheme.colors.tintColor, shape = CircleShape)
            .height(50.dp)
            .fillMaxSize()
            .shadow(5.dp, shape = CircleShape)
            .background(color = OwnTheme.colors.secondaryBackground),
        shape = CircleShape,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            color = OwnTheme.colors.primaryText
        ),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Enter your word",
                style = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    color = OwnTheme.colors.secondaryText
                )
            )
        }
    )
}

@Composable
fun ShowCard(
    completedResult: CompletedResult?
) {
    Log.e("Log","ShowCard")
    LazyColumn(
        modifier = Modifier
            .background(
                color = OwnTheme.colors.secondaryBackground,
                shape = OwnTheme.shapes.cornersStyle
            )
    ) {
        if(completedResult!=null)
            if(completedResult.isSuccess) {
                item {
                    Card(
                        item = "Word is ${completedResult.word}",
                        color = OwnTheme.colors.green,
                        centerText = true
                    )
                }
                if (completedResult.definition != null)
                    items(completedResult.definition) { item ->
                        Card(item = item, color = OwnTheme.colors.red, cardType = "Definition:")
                    }
                if (completedResult.instance != null)
                    items(completedResult.instance) { item ->
                        Card(item = item, color = OwnTheme.colors.blue, cardType = "Instance:")
                    }
            }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun Card(item: String, color: Color, centerText: Boolean = false, cardType: String = "") {
    Log.e("Log","Card")
    var sentence: String? by remember { mutableStateOf(null) }
    if (sentence != null)
        WindowAboveCard(
            word = sentence!!
                .replace(",", "")
                .replace(".", "")
                .replace("!", "")
                .replace(":", "")
                .replace(")", "")
                .replace(";", "")
        )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(OwnTheme.colors.secondaryBackground, shape = CircleShape)
            .border(width = 3.dp, color = color, shape = CircleShape)
            .shadow(4.dp, CircleShape.copy(CornerSize(90)))
            .padding(start = 20.dp, end = 10.dp)
    ) {
        Column {
            if (cardType != "")
                Text(text = cardType, color = OwnTheme.colors.primaryText)
            val text = AnnotatedString(item)
            ClickableText(
                text = text,
                onClick = { offset ->
                    val words = text.split(" ")
                    var cursor = 0
                    for (word in words) {
                        cursor += word.length
                        if (offset <= cursor) {
                            sentence = word
                            break
                        }
                        cursor++
                    }
                },
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WindowAboveCard(word: String?) {
    Log.e("Log","WindowAboveCard")
    var wordInstance: String? by remember { mutableStateOf(word) }
    var visible by remember { mutableStateOf(true) }
    val viewModel = get<MainViewModel>()
    if (word != wordInstance) {
        visible = true
        wordInstance = word
    }
    if (word != null) {
        viewModel.getCompletedResult(word)
        val completedResult = viewModel.completedResult.collectAsState()
        AnimatedVisibility(visible = visible){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        OwnTheme.colors.backgroundInBackground,
                        shape = OwnTheme.sizesShapes.smallShape
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        Icons.Filled.Close,
                        null,
                        modifier = Modifier
                            .clickable {
                                visible = false
                            }
                            .background(OwnTheme.colors.tintColor, shape = CircleShape)
                    )
                }
                if (viewModel.loading.value) {
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier)
                    }
                } else {
                    if (completedResult.value != null) {
                        if (completedResult.value!!.isSuccess) {
                            if(completedResult.value!!.word!=null){
                                Card(
                                    item = "Word is ${completedResult.value!!.word}",
                                    color = OwnTheme.colors.green,
                                    centerText = true
                                )
                            }
                            if (completedResult.value!!.definition != null)
                                completedResult.value!!.definition!!.forEach { item ->
                                    Card(
                                        item = item,
                                        color = OwnTheme.colors.red,
                                        cardType = "Definition:"
                                    )
                                }
                            if (completedResult.value!!.instance != null)
                                completedResult.value!!.instance!!.forEach { item ->
                                    Card(
                                        item = item,
                                        color = OwnTheme.colors.blue,
                                        cardType = "Instance:"
                                    )
                                }
                        } else {
                            Card(item = "Error try later", color = OwnTheme.colors.error)
                        }
                    } else {
                        Card(item = "Error try later", color = OwnTheme.colors.error)
                    }
                }
            }
        }
    }
}