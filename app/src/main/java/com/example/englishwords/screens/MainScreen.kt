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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.englishwords.models.ResponseData
import com.example.englishwords.models.retrofitModels.ResponseHandler
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CommitPrefEdits")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(isDarkmode: MutableState<Boolean>, ownStyle: MutableState<OwnTheme.OwnStyle>) {
    var buttonWasClicked by remember { mutableStateOf(false) }
    val mediaPlayer = MediaPlayer()
    var urlToListening: String? by remember { mutableStateOf("") }
    var clickOnButtonBoolean: Boolean by remember { mutableStateOf(false) }
    var animatedErrorButton: Boolean by remember { mutableStateOf(false) }
    var moveButton: Boolean by remember { mutableStateOf(false) }
    val viewModel = getViewModel<MainViewModel>()
    var endPoint by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val flowData = viewModel.wordDefinition.collectAsState()
    val wordDefinition: ResponseHandler? =
        if (flowData.value != null) {
            ResponseHandler(
                completed = flowData.value!!.body()!!,
                status = flowData.value!!.isSuccessful
            )
        } else null
    var oldEndPoint by remember { mutableStateOf(endPoint) }
    var oldWordDefinition by remember { mutableStateOf(wordDefinition) }
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
                            viewModel.getWordDefinition(endPoint)
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
                                Icons.Filled.Call,
                                contentDescription = null,
                                tint = OwnTheme.colors.primaryText
                            )
                        }
                    }
                }
            }
            AnimatedContent(
                targetState = true,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300, delayMillis = 90)) +
                            scaleIn(
                                initialScale = 0.92f,
                                animationSpec = tween(220, delayMillis = 90)
                            ) with
                            fadeOut(animationSpec = tween(90))
                }
            ) {
                ColumnOfContent(
                    viewModel = viewModel,
                    wordDefinition = wordDefinition,
                    returnUrlToListening = { urlToListening = it },
                    buttonWasClicked = buttonWasClicked,
                )
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ColumnOfContent(
    viewModel: MainViewModel,
    wordDefinition: ResponseHandler?,
    returnUrlToListening: (result: String?) -> Unit,
    buttonWasClicked: Boolean,
) {
    if (viewModel.loading.value) CircularProgressIndicator(Modifier.fillMaxSize()) else {
        if (wordDefinition != null) {
            if (viewModel.requestComplete.value) {
                ShowCard(
                    definition = wordDefinition,
                    urlToListening = { link ->
                        returnUrlToListening(link)
                    }
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
    definition: ResponseHandler,
    urlToListening: (link: String?) -> Unit = {},
) {
    var responseData: ResponseData by remember {
        mutableStateOf(ResponseData("", mutableListOf(), mutableListOf()))
    }
    var url by remember { mutableStateOf("") }
    LazyColumn(
        modifier = Modifier
            .background(
                color = OwnTheme.colors.secondaryBackground,
                shape = OwnTheme.shapes.cornersStyle
            )
    ) {
        if (definition.status) {
            definition.completed.forEach { model ->
                responseData.word = model.word
                model.phonetics.forEach {
                    if (it.audio != null)
                        url = it.audio
                    else null
                }
                urlToListening(url)
                model.meanings.forEach { meaning ->
                    meaning.definitions.forEach { definit ->
                        responseData.descriptions!!.add(definit.definition)
                        if (definit.example != null)
                            responseData.instances!!.add(definit.example)
                    }
                }
            }
        }
        if (responseData.word != null)
            item {
                //Text(text = responseData!!.word!!)
                Card(
                    item = "Word is ${responseData.word!!}",
                    color = OwnTheme.colors.green,
                    centerText = true
                )
            }
        if (responseData.descriptions != null)
            items(responseData.descriptions!!) { item ->
                Card(item = item, color = OwnTheme.colors.red, cardType = "Definition:")
            }
        if (responseData.instances != null)
            items(responseData.instances!!) { item ->
                Card(item = item, color = OwnTheme.colors.blue, cardType = "Instance:")
            }
    }
    responseData = ResponseData("", mutableListOf(), mutableListOf())
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun Card(item: String, color: Color, centerText: Boolean = false, cardType: String = "") {
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
    var wordInstance: String? by remember { mutableStateOf(word) }
    var visible by remember { mutableStateOf(true) }
    val viewModel = get<MainViewModel>()
    if (word != wordInstance) {
        visible = true
        wordInstance = word
    }
    var list: MutableList<String> = mutableListOf()
    if (word != null) {
        viewModel.getWordDefinition(word)
        val observData = viewModel.wordDefinition.collectAsState()
        AnimatedVisibility(visible = visible) {
            if (viewModel.loading.value) {
                Row(//in future
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier)
                }
            }
            list.clear()
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        OwnTheme.colors.backgroundInBackground,
                        shape = OwnTheme.sizesShapes.smallShape
                    )
            ) {
                if (observData.value != null)
                    if (observData.value!!.body() != null)
                        observData.value!!.body()!!.forEach { mainModel ->
                            mainModel.meanings.forEach { meaning ->
                                meaning.definitions.forEach { definition ->
                                    list.add(definition.definition)
                                }
                            }
                        }
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
                list.forEach { item ->
                    Card(item = item, color = OwnTheme.colors.blue)
                }
            }
        }
    }
}