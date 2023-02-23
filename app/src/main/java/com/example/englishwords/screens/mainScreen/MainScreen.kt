package com.example.englishwords.screens.mainScreen

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.englishwords.R
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.models.retrofitModels.CompletedResult
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import kotlinx.coroutines.*
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.util.*

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "CommitPrefEdits",
    "UnrememberedMutableState", "SimpleDateFormat"
)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    var buttonWasClicked by remember { mutableStateOf(false) }
    val mainScreenViewModel: MainScreenViewModel = koinViewModel()
    var clickOnButtonBoolean: Boolean by remember { mutableStateOf(false) }
    var moveButton: Boolean by remember { mainScreenViewModel.moveButton }
    val viewModel = getViewModel<MainViewModel>()
    var endPoint by remember { mainScreenViewModel.searchedWord }
    val scope = rememberCoroutineScope()
    val completedResult = viewModel.completedResult.collectAsState()
    var urlToListening: String? by remember { mutableStateOf("") }
    var errorClickOnButton by remember { mutableStateOf(mainScreenViewModel.errorCLickOnButton.value) }
    var animatedErrorButton: Boolean by remember { mutableStateOf(false) }
    //var popumSaveNoteState by remember { mutableStateOf(false) }
    val popUpViewNoteCreate = mainScreenViewModel.viewNoteCreateState.collectAsState()
    //val saveDataForNote = mainScreenViewModel.saveNote.collectAsState()
    Column(modifier = Modifier.background(color = OwnTheme.colors.primaryBackground)) {
        Spacer(modifier = Modifier.height(OwnTheme.dp.bigDp))
        SearchBar(
            endPoint = endPoint,
            returnEndPoint = { endPoint = it },
            moveButton = moveButton,
            returnMoveButton = { moveButton = it },
            mainViewModel = viewModel,
            buttonWasClicked = { buttonWasClicked = it },
            returnUrlToListening = { urlToListening = it },
            returnClickOnButtonBoolean = { clickOnButtonBoolean = it },
            clickOnButtonBoolean = clickOnButtonBoolean,
            returnErrorClickOnButton = { errorClickOnButton = it },
            animatedErrorButton = { animatedErrorButton = it },
            scope = scope
        )
        Spacer(Modifier.height(OwnTheme.dp.normalDp))
        AnimatedContent(targetState = true) {
            MainScreenSearchButtonRow(
                endPoint = endPoint,
                returnMoveButton = { moveButton = it },
                mainViewModel = viewModel,
                buttonWasClicked = { buttonWasClicked = it },
                returnUrlToListening = { urlToListening = it },
                returnClickOnButtonBoolean = { clickOnButtonBoolean = it },
                clickOnButtonBoolean = clickOnButtonBoolean,
                moveButton = moveButton,
                urlToListening = urlToListening,
                returnErrorClickOnButton = { errorClickOnButton = it },
                animatedErrorButton = animatedErrorButton,
                returnAnimatedErrorButton = { animatedErrorButton = it }
            )
        }
        ColumnOfContentSurface(
            viewModel = viewModel,
            completedResult = completedResult.value,
            buttonWasClicked = buttonWasClicked,
            returnUrl = { urlToListening = it },
            errorClickOnButton = errorClickOnButton
        )
    }

    AnimatedVisibility(
        visible = popUpViewNoteCreate.value,
        modifier = Modifier.fillMaxSize(),
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Log.e("Log","text own type")
        MainScreenAddNotePopup(
            mainViewModel = viewModel,
            mainScreenViewModel
        )
    }

    /*saveDataForNote.value?.let {
        viewModel.update(
            Modeldb(
                id = it.first.id,
                word = it.first.word,
                linkToSound = it.first.linkToSound,
                definitions = it.first.definitions,
                examples = it.first.examples,
                note = it.second,
                data = it.first.data
            )
        )
    }*/
    DisposableEffect(key1 = scope) {
        return@DisposableEffect onDispose {
            mainScreenViewModel.setSearchedWord(endPoint)
            mainScreenViewModel.setMoveButton(moveButton)
            mainScreenViewModel.setErrorClickOnButton(errorClickOnButton)
        }
    }
}

@Composable
fun MainScreenAddNotePopup(
    mainViewModel: MainViewModel,
    mainScreenViewModel: MainScreenViewModel
) {
    Popup(
        alignment = Alignment.Center,
        offset = IntOffset(0, 600),
        onDismissRequest = {
            mainScreenViewModel.setViewNoteCreateState(false)
        },
        properties = PopupProperties(focusable = true)
    ) {
        MainScreenAddNoteWindow(
            mainViewModel = mainViewModel,
            mainScreenViewModel = mainScreenViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAddNoteWindow(
    mainViewModel: MainViewModel,
    mainScreenViewModel: MainScreenViewModel
) {
    val pairModeldbWord = mainScreenViewModel.saveNote.collectAsState()
    val modeldb = mainViewModel.getSingleRoomData.collectAsState()
    val localScope = 0
    Column {
        mainViewModel.getRoomDataByWord(pairModeldbWord.value?.second?:"")
        var enableAutoSave by remember { mutableStateOf(true) }
        var localNote by remember { mutableStateOf("") }
        Box(
            modifier = Modifier
                .width(200.dp)
                .background(
                    OwnTheme.colors.secondaryBackground,
                    shape = OwnTheme.sizesShapes.mediumShape
                )
                .border(
                    width = 1.dp,
                    color = OwnTheme.colors.tintColor,
                    shape = OwnTheme.sizesShapes.mediumShape
                )
        ) {
            Column {
                Row(Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier
                        .background(
                            OwnTheme.colors.backgroundInBackground,
                            shape = OwnTheme.sizesShapes.mediumShape
                        )
                        .clickable {
                            mainScreenViewModel.setViewNoteCreateState(false)
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.check_1_save),
                            contentDescription = "null",
                            tint = OwnTheme.colors.green,
                            modifier = Modifier
                                .background(
                                    OwnTheme.colors.backgroundInBackground,
                                    shape = OwnTheme.sizesShapes.mediumShape
                                )
                        )
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Box(
                            modifier = Modifier
                                .background(
                                    OwnTheme.colors.backgroundInBackground,
                                    shape = OwnTheme.sizesShapes.mediumShape
                                )
                                .clickable {
                                    mainScreenViewModel.setViewNoteCreateState(false)
                                    enableAutoSave = false
                                }
                                .background(
                                    OwnTheme.colors.backgroundInBackground,
                                    shape = OwnTheme.sizesShapes.mediumShape
                                )
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.close_1),
                                contentDescription = "null",
                                tint = OwnTheme.colors.red,
                                modifier = Modifier
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = localNote,
                    onValueChange = { localNote = it },
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = OwnTheme.colors.tintColor,
                            shape = OwnTheme.sizesShapes.mediumShape
                        ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = OwnTheme.colors.tintColor,
                        containerColor = OwnTheme.colors.secondaryBackground,
                        focusedBorderColor = OwnTheme.colors.tintColor,
                        cursorColor = OwnTheme.colors.tintColor
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        color = OwnTheme.colors.primaryText,
                        fontSize = OwnTheme.typography.general.fontSize.value.sp
                    ),
                    maxLines = 3
                )
            }
        }
        DisposableEffect(key1 = localScope) {
            return@DisposableEffect onDispose {
                if(enableAutoSave)
                    modeldb.value?.let {
                        mainViewModel.update(Modeldb(
                            id = it.id,
                            word = it.word,
                            linkToSound = it.linkToSound,
                            definitions = it.definitions,
                            examples = it.examples,
                            note = localNote,
                            data = it.data,
                        similar = it.similar))
                    }
            }
        }
    }

    Spacer(modifier = Modifier.height(OwnTheme.dp.largeDp))
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenSearchButtonRow(
    endPoint: String,
    returnMoveButton: (Boolean) -> Unit,
    mainViewModel: MainViewModel,
    buttonWasClicked: (Boolean) -> Unit,
    returnUrlToListening: (String?) -> Unit,
    returnClickOnButtonBoolean: (Boolean) -> Unit,
    clickOnButtonBoolean: Boolean,
    moveButton: Boolean,
    urlToListening: String?,
    returnErrorClickOnButton: (Boolean) -> Unit,
    animatedErrorButton: Boolean,
    returnAnimatedErrorButton: (Boolean) -> Unit
) {

    val scale by animateFloatAsState(
        targetValue = if (animatedErrorButton) 1.1f else 1f,
        animationSpec = repeatable(
            iterations = 5,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = {
            returnAnimatedErrorButton(false)
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
            val scope = rememberCoroutineScope()
            Button(
                onClick = {
                    mainScreenDoSearchButton(
                        mainViewModel = mainViewModel,
                        endPoint = endPoint,
                        returnButtonWasClicked = { buttonWasClicked(it) },
                        returnUrlToListening = { returnUrlToListening(it) },
                        scope = scope,
                        returnClickOnButtonBoolean = { returnClickOnButtonBoolean(it) },
                        clickOnButtonBoolean = clickOnButtonBoolean,
                        moveButton = moveButton,
                        returnAnimatedErrorButton = { returnAnimatedErrorButton(it) },
                        returnErrorClickOnButton = { returnErrorClickOnButton(it) },
                        returnMoveButton = { returnMoveButton(it) })
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
                    .shadow(
                        10.dp,
                        shape = OwnTheme.sizesShapes.largeShape,
                        spotColor = OwnTheme.colors.secondaryBackground,
                        ambientColor = OwnTheme.colors.secondaryBackground,
                        clip = true
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = OwnTheme.colors.button)
            ) {
                Text(
                    text = "Search",
                    color = OwnTheme.colors.primaryText,
                    fontSize = OwnTheme.typography.general.fontSize.value.sp
                )
            }
        }
    }

    AnimatedVisibility(
        visible = moveButton,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            FloatingActionButton(
                onClick = {
                    val mediaPlayer = MediaPlayer()
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
                        .border(
                            animatedFloat.dp,
                            Color.Red,
                            shape = CircleShape
                        )
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

fun mainScreenDoSearchButton(
    mainViewModel: MainViewModel,
    endPoint: String,
    returnButtonWasClicked: (Boolean) -> Unit,
    returnUrlToListening: (String?) -> Unit,
    scope: CoroutineScope,
    returnClickOnButtonBoolean: (Boolean) -> Unit,
    clickOnButtonBoolean: Boolean,
    moveButton: Boolean,
    returnAnimatedErrorButton: (Boolean) -> Unit,
    returnErrorClickOnButton: (Boolean) -> Unit,
    returnMoveButton: (Boolean) -> Unit
) {
    mainViewModel.getCompletedResult(endPoint)
    returnButtonWasClicked(true)
    returnUrlToListening(null)
    scope.launch {
        returnClickOnButtonBoolean(!clickOnButtonBoolean)
        if (endPoint.isEmpty() && !moveButton) {
            returnAnimatedErrorButton(true)
            returnErrorClickOnButton(true)
        } else returnErrorClickOnButton(false)
        if (!endPoint.isEmpty()) {
            returnMoveButton(true)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ColumnOfContentSurface(
    viewModel: MainViewModel,
    completedResult: CompletedResult?,
    buttonWasClicked: Boolean,
    returnUrl: (result: String?) -> Unit,
    errorClickOnButton: Boolean
) {
    if (errorClickOnButton) return
    if (viewModel.loading.value) CircularProgressIndicator(Modifier.fillMaxSize()) else {
        if (completedResult != null) {
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
                text = "Error of server side. Please try later",
                color = OwnTheme.colors.primaryText,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    endPoint: String,
    returnEndPoint: (endPoint: String) -> Unit,
    moveButton: Boolean,
    mainViewModel: MainViewModel,
    buttonWasClicked: (Boolean) -> Unit,
    returnUrlToListening: (String?) -> Unit,
    scope: CoroutineScope,
    returnClickOnButtonBoolean: (Boolean) -> Unit,
    clickOnButtonBoolean: Boolean,
    animatedErrorButton: (Boolean) -> Unit,
    returnErrorClickOnButton: (Boolean) -> Unit,
    returnMoveButton: (Boolean) -> Unit
) {
    var localEndPoint by remember {
        mutableStateOf(endPoint)
    }
    OutlinedTextField(
        value = localEndPoint,
        onValueChange = {
            localEndPoint = it
            returnEndPoint(it)
            if (it.isEmpty()) returnMoveButton(false)
        },
        modifier = Modifier
            .border(3.dp, color = OwnTheme.colors.tintColor, shape = CircleShape)
            .height(35.dp + OwnTheme.typography.general.fontSize.value.dp)
            .fillMaxWidth()
            .shadow(5.dp, shape = CircleShape)
            .background(color = OwnTheme.colors.secondaryBackground),
        shape = CircleShape,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            color = OwnTheme.colors.primaryText,
            fontSize = OwnTheme.typography.general.fontSize.value.sp
        ),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Enter your word",
                style = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    color = OwnTheme.colors.secondaryText,
                    fontSize = OwnTheme.typography.general.fontSize.value.sp
                )
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {
            mainViewModel.getCompletedResult(endPoint)
            buttonWasClicked(true)
            returnUrlToListening(null)
            scope.launch {
                returnClickOnButtonBoolean(!clickOnButtonBoolean)
                if (endPoint.isEmpty() && !moveButton) {
                    animatedErrorButton(true)
                    returnErrorClickOnButton(true)
                } else returnErrorClickOnButton(false)
                if (!endPoint.isEmpty()) {
                    returnMoveButton(true)
                }
            }
        }
    )
}

@Composable
fun ShowCard(
    completedResult: CompletedResult?
) {
    LazyColumn(
        modifier = Modifier
            .background(
                color = OwnTheme.colors.secondaryBackground,
                shape = OwnTheme.shapes.cornersStyle
            )
    ) {
        if (completedResult != null)
            if (completedResult.isSuccess) {
                item {
                    MainCard(
                        item = "Word is ${completedResult.word}",
                        color = OwnTheme.colors.green,
                        centerText = true,
                        savable = true,
                        completedResult = completedResult,
                        wordCard = true
                    )
                }
                if (completedResult.definition != null)
                    items(completedResult.definition) { item ->
                        MainCard(
                            item = item,
                            color = OwnTheme.colors.definitionCard,
                            cardType = "Definition:"
                        )
                    }
                if (completedResult.instance != null)
                    items(completedResult.instance) { item ->
                        MainCard(
                            item = item,
                            color = OwnTheme.colors.exampleCard,
                            cardType = "Instance:"
                        )
                    }
            }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MainCard(
    item: String,
    color: Color,
    centerText: Boolean = false,
    cardType: String = "",
    savable: Boolean = false,
    completedResult: CompletedResult? = null,
    wordCard: Boolean = false
) {
    val mainViewModel: MainViewModel = getViewModel()
    val mainScreenViewModel: MainScreenViewModel = koinViewModel()
    var word: List<String> = listOf()
    var goalWord: String? by remember { mutableStateOf(null) }
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    if (wordCard) {
        LaunchedEffect(key1 = scope, block = {
            scope.launch(Dispatchers.Default) {
                word = item.split(" ")
                goalWord = mainScreenViewModel.checkGoalWord(word[2])
            }
        })
    }
    var sentence: String? by remember { mutableStateOf(null) }
    var showWindowAbove by remember { mutableStateOf(false) }
    AnimatedVisibility(
        visible = showWindowAbove,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        WindowAboveCard(
            word = sentence!!
                .replace(",", "")
                .replace(".", "")
                .replace("!", "")
                .replace(":", "")
                .replace(")", "")
                .replace(";", ""),
            closeThisWindow = { showWindowAbove = it },
            //popupSaveNoteState = {popupSaveNoteState(it)}
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(OwnTheme.colors.secondaryBackground, shape = CircleShape)
            .border(width = 3.dp, color = color, shape = CircleShape)
            .shadow(4.dp, CircleShape.copy(CornerSize(90)))
            .padding(start = 20.dp, end = 10.dp)
    ) {
        Column {
            if (cardType != "") {
                Text(
                    text = cardType,
                    color = OwnTheme.colors.primaryText,
                    fontSize = OwnTheme.typography.general.fontSize.value.sp
                )
            }
            var localText by remember { mutableStateOf("Save") }
            val text = AnnotatedString(item)
            Row(Modifier.fillMaxWidth()) {
                if (completedResult?.word != null)
                    if (savable && goalWord == "") Text(
                        text = localText,
                        color = OwnTheme.colors.primaryText,
                        modifier = Modifier
                            .clickable {
                                mainScreenViewModel.setViewNoteCreateState(true)
                                localText = ""
                                if (completedResult.word.isNotEmpty()) {
                                    scope.launch {
                                        val localDate = LocalDate.now()
                                        val localModeldb = Modeldb()
                                        localModeldb.definitions =
                                            completedResult.definition ?: listOf("No Data")
                                        localModeldb.examples =
                                            completedResult.instance ?: listOf("No Data")
                                        localModeldb.similar =
                                            completedResult.similar ?: listOf("No Similar")
                                        localModeldb.linkToSound =
                                            completedResult.urlToListening ?: ""
                                        localModeldb.word = completedResult.word
                                        localModeldb.data = listOf(
                                            localDate.dayOfMonth.toString(),
                                            localDate.month.value.toString(),
                                            localDate.year.toString()
                                        )
                                        mainViewModel.insert(localModeldb)
                                        mainScreenViewModel.setSaveNote(Pair(localModeldb,localModeldb.word))
                                    }
                                }
                            }, fontSize = OwnTheme.typography.general.fontSize.value.sp
                    )
                ClickableText(
                    text = text,
                    onClick = { offset ->
                        val words = text.split(" ")
                        var cursor = 0
                        for (i in words) {
                            cursor += i.length
                            if (offset <= cursor) {
                                sentence = i
                                showWindowAbove = true
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
                            color = OwnTheme.colors.primaryText,
                            fontSize = OwnTheme.typography.general.fontSize.value.sp
                        )
                    else
                        LocalTextStyle.current.copy(
                            color = OwnTheme.colors.primaryText,
                            fontSize = OwnTheme.typography.general.fontSize.value.sp
                        )
                )
            }

        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WindowAboveCard(
    word: String,
    mainScreenViewModel: MainScreenViewModel = koinViewModel(),
    closeThisWindow: (Boolean) -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    val viewModel = get<MainViewModel>()
    viewModel.getCompletedResult(word)
    val completedResult = viewModel.completedResult.collectAsState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = scope) {
        visible = true
    }
    AnimatedVisibility(visible = visible) {
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
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            closeThisWindow(false)
                            visible = false
                            mainScreenViewModel.aboveCardState.value = null
                        },
                    tint = OwnTheme.colors.tintColor
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
                        if (completedResult.value!!.word != null) {
                            MainCard(
                                item = "Word is ${completedResult.value!!.word}",
                                color = OwnTheme.colors.green,
                                centerText = true,
                                wordCard = true,
                                savable = true,
                                completedResult = completedResult.value
                            )
                        }
                        if (completedResult.value!!.definition != null)
                            completedResult.value!!.definition!!.forEach { item ->
                                MainCard(
                                    item = item,
                                    color = OwnTheme.colors.definitionCard,
                                    cardType = "Definition:"
                                )
                            }
                        if (completedResult.value!!.instance != null)
                            completedResult.value!!.instance!!.forEach { item ->
                                MainCard(
                                    item = item,
                                    color = OwnTheme.colors.exampleCard,
                                    cardType = "Instance:"
                                )
                            }
                    } else {
                        MainCard(item = "Error try later", color = OwnTheme.colors.error)
                    }
                } else {
                    MainCard(item = "Error try later", color = OwnTheme.colors.error)
                }
            }
        }
    }
}