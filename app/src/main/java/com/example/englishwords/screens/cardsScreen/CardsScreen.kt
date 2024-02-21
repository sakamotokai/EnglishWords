package com.example.englishwords.screens.cardsScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.englishwords.R
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.screens.ourUiElements.CustomOutlinedTextField
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.get
import java.io.ByteArrayOutputStream
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CardsScreen() {
    val mainViewModel: MainViewModel = get()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = scope) {
        mainViewModel.getAllFromRoom()
        delay(500)
        mainViewModel.getCardList()
    }
    val cardScreenVM: CardScreenVM = get()
    var isChoseScreenOpen by remember { mutableStateOf(false) }
    val allRoomData = mainViewModel.getAllRoomData.collectAsState()
    val cardList = mainViewModel.cardList.collectAsState()
    Log.e("localError", "-----------------")

    if(!isChoseScreenOpen)
    CardList(
        mainViewModel = mainViewModel,
        cardScreenVM = cardScreenVM
    )

    if (isChoseScreenOpen)
        Box(
            Modifier
                .fillMaxSize()
        ) {
            ChoseWordScreen(
                mainViewModel,
                isChoseScreenOpen = { isChoseScreenOpen = it },
                value = isChoseScreenOpen
            )
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {
        AddCardButton(isChoseScreenOpen = { isChoseScreenOpen = it }, value = isChoseScreenOpen)
    }

    Log.e("localError", "ERRORRRR")
}


@ExperimentalMaterialApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyUI(
    list:List<Modeldb>,
    mainViewModel: MainViewModel,
    returnCardList: (List<Modeldb>) -> Unit,
    allRoomData: List<Modeldb>?
) {
    // list for the lazy column
    val peopleList = remember { mutableStateListOf<Modeldb>() }
    peopleList.clear()
    peopleList.addAll(list)

    val contextForToast = LocalContext.current.applicationContext

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp) // margin
    ) {
        items(
            items = peopleList,
            key = { people ->
                people.id
            }
        ) { people ->
            val dismissState = androidx.compose.material3.rememberDismissState()

            // check if the user swiped
            if (dismissState.isDismissed(direction = DismissDirection.EndToStart)) {
                Toast.makeText(contextForToast, "Delete", Toast.LENGTH_SHORT).show()
                //mainViewModel.update(people.apply { isUsingInCard = false })
                peopleList.remove(people)
                returnCardList(peopleList)
            }

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.EndToStart
                ),
                background = {
                    // this background is visible when we swipe.
                    // it contains the icon

                    // background color
                    val backgroundColor by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.8f)
                            else -> Color.White
                        }
                    )

                    // icon size
                    val iconScale by animateFloatAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.DismissedToStart) 1.3f else 0.5f
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color = backgroundColor)
                            .padding(end = 16.dp), // inner padding
                        contentAlignment = Alignment.CenterEnd // place the icon at the end (left)
                    ) {
                        Icon(
                            modifier = Modifier.scale(iconScale),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                },
                dismissContent = {
                    // list item
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                    ) {
                        Text(
                            text = people.word,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp, bottom = 16.dp, start = 12.dp)
                        )
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardList(
    mainViewModel: MainViewModel,
    cardScreenVM: CardScreenVM
) {
    val scope = rememberCoroutineScope()
    var parentRoomData = remember{ mutableStateListOf<Modeldb>() }
        LaunchedEffect(scope){
        mainViewModel.getAllRoomData.collect{
            parentRoomData.clear()
            it?.let { parentRoomData.addAll(it) }
        }
    }

    var activeCardList = remember { mutableListOf<Modeldb>() }
    activeCardList.clear()
    Log.e("localError", "Inside")

    if(parentRoomData.isNotEmpty()) {
        activeCardList.clear()
        parentRoomData.toList().forEach {
            Log.e("localError", "8")
            if (it.isUsingInCard) {
                activeCardList.add(it)
            }
        }
    }
    SwipeToDeleteCardList(
        activeCardList = activeCardList,
        mainViewModel = mainViewModel,
        cardScreenVM = cardScreenVM
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteCardList(
    activeCardList: MutableList<Modeldb>,
    mainViewModel: MainViewModel,
    cardScreenVM: CardScreenVM
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        items(
            activeCardList,
            key = { model ->
                model.id
            }
        ) {
            Log.e("localError", "7")
            val dismissState = androidx.compose.material3.rememberDismissState()
            if (dismissState.isDismissed(direction = DismissDirection.EndToStart)) {
                mainViewModel.update(it.apply { isUsingInCard = false })
                //roomData.remove(it)
                //removedModels.add(it)
            }
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.EndToStart
                ),
                background = {
                    // this background is visible when we swipe.
                    // it contains the icon

                    // background color
                    val backgroundColor by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.8f)
                            else -> Color.White
                        }
                    )

                    // icon size
                    val iconScale by animateFloatAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.DismissedToStart) 1.3f else 0.5f
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color = backgroundColor)
                            .padding(end = 16.dp), // inner padding
                        contentAlignment = Alignment.CenterEnd // place the icon at the end (left)
                    ) {
                        Icon(
                            modifier = Modifier.scale(iconScale),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                },
                dismissContent = {
                    Log.e("localError", "6")
                    // list item
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CertainCard(
                            modeldb = it,
                            mainViewModel = mainViewModel,
                            cardScreenVM = cardScreenVM
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            )

        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ChoseWordScreen(
    mainViewModel: MainViewModel,
    isChoseScreenOpen: (Boolean) -> Unit,
    value: Boolean
) {
    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = OwnTheme.colors.secondaryBackground.copy(alpha = 0.3f),
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .blur(3.dp))
        val list = remember{mutableStateListOf<Modeldb>()}
        val roomData = mainViewModel.getAllRoomData.collectAsState()
        roomData.value?.let {listModeldb->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(OwnTheme.colors.secondaryBackground.copy(alpha = 0.3f))
                    .border(
                        2.dp,
                        color = OwnTheme.colors.tintColor,
                        shape = OwnTheme.shapes.cornersStyle
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(listModeldb) {model->
                    Button(
                        shape = OwnTheme.shapes.cornersStyle,
                        colors = ButtonDefaults.buttonColors(backgroundColor = OwnTheme.colors.button),
                        onClick = {
                            model.apply {
                                mainViewModel.update(
                                    Modeldb(
                                        id = id,
                                        word = word,
                                        linkToSound = linkToSound,
                                        definitions = definitions,
                                        examples = examples,
                                        note = note,
                                        data = data,
                                        similar = similar,
                                        urlToImage = urlToImage,
                                        isUsingInCard = true
                                    )
                                )
                            }
                        }) {
                        Text(
                            text = model.word,
                            color = OwnTheme.colors.primaryText,
                            fontSize = OwnTheme.typography.general.fontSize.value.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun SwipeToDismiss(
    state: DismissState,
    background: @Composable RowScope.() -> Unit,
    dismissContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    directions: Set<DismissDirection> = setOf(
        DismissDirection.EndToStart,
        DismissDirection.StartToEnd
    ),
) {
}

@Composable
private fun AddCardButton(
    isChoseScreenOpen: (Boolean) -> Unit,
    value: Boolean
) {
    FloatingActionButton(
        onClick = {
            isChoseScreenOpen(!value)
        }) {
        Icon(ImageVector.vectorResource(R.drawable.add_card_1), null)
    }
}

//TODO("Исправить эту срань")
@Composable
fun CertainCard(modeldb: Modeldb, mainViewModel: MainViewModel, cardScreenVM: CardScreenVM) {
    var isFrontSide by remember { mutableStateOf(true) }
    if (isFrontSide) {
        Log.e("localError", "4")
        CardsScreenMainCardFront(
            modeldb = modeldb,
            setOtherSide = { isFrontSide = it },
            cardScreenVM = cardScreenVM
        )
    } else {
        Log.e("localError", "5")
        CardsScreenMainCardBack(
            mainViewModel = mainViewModel,
            modeldb = modeldb,
            setOtherSide = { isFrontSide = it }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CardsScreenMainCardFront(
    modeldb: Modeldb,
    width: Int = 350,
    height: Int = 150,
    setOtherSide: (Boolean) -> Unit,
    cardScreenVM: CardScreenVM
) {
    //Log.e("localError", "8")
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .background(OwnTheme.colors.secondaryBackground, OwnTheme.sizesShapes.mediumShape)
            .border(
                width = 2.dp,
                color = OwnTheme.colors.tintColor,
                shape = OwnTheme.sizesShapes.mediumShape
            )
    ) {
        Row(Modifier.fillMaxWidth()) {
            Column {
                Box(
                    modifier = Modifier
                        .width(width = (width / 2).dp)
                        .height((height / 2).dp)
                        .padding(start = OwnTheme.dp.smallDp)
                ) {
                    Text(
                        text = modeldb.word,
                        fontSize = OwnTheme.typography.general.fontSize,
                        color = OwnTheme.colors.primaryText,
                        fontStyle = OwnTheme.typography.heading.fontStyle
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width((width / 2).dp)
                        .height(2.dp)
                        .border(width = 1.dp, color = OwnTheme.colors.tintColor)
                )
                Box(
                    modifier = Modifier
                        .width(width = (width / 2).dp)
                        .height((height / 2).dp)
                        .padding(start = OwnTheme.dp.smallDp)
                ) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Text(
                            text = "Similar words",
                            fontSize = OwnTheme.typography.general.fontSize,
                            color = OwnTheme.colors.primaryText
                        )
                        Text(
                            text = cardScreenVM.getSimilarWords(modeldb).dropLast(2),
                            fontSize = OwnTheme.typography.general.fontSize,
                            color = OwnTheme.colors.primaryText
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .width(2.dp)
                    .height((height).dp)
                    .border(width = 2.dp, color = OwnTheme.colors.tintColor)
            )
            Box(
                modifier = Modifier
                    .width(width = (width / 2).dp)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    modeldb.definitions.forEach {
                        Text(
                            text = it,
                            fontSize = OwnTheme.typography.general.fontSize,
                            color = OwnTheme.colors.primaryText
                        )
                    }
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
                    .border(2.dp, OwnTheme.colors.tintColor, newShape)
                    .clickable {
                        setOtherSide(false)
                    }
            )
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun CardsScreenMainCardBack(
    mainViewModel: MainViewModel,
    modeldb: Modeldb,
    width: Int = 350,
    height: Int = 150,
    setOtherSide: (Boolean) -> Unit
) {
    Log.e("localError", "9")
    val scope = CoroutineScope(SupervisorJob())
    val context = LocalContext.current
    Box {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(
                    start = OwnTheme.dp.normalDp,
                    top = OwnTheme.dp.normalDp
                )
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .border(2.dp, OwnTheme.colors.tintColor, otherwiseNewShape)
                    .clickable {
                        setOtherSide(true)
                    }
            )
        }
        Row(
            modifier = Modifier
                .height(height.dp)
                .width(width.dp)
                .border(
                    width = 2.dp,
                    color = OwnTheme.colors.tintColor,
                    shape = OwnTheme.sizesShapes.mediumShape
                )
        ) {
            Column(
                Modifier
                    .height(height.dp)
                    .width((width / 2).dp)
                    .padding(
                        start = OwnTheme.dp.normalDp,
                        top = OwnTheme.dp.normalDp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                modeldb.examples.forEach {
                    Text(
                        text = it,
                        fontSize = OwnTheme.typography.general.fontSize,
                        color = OwnTheme.colors.primaryText

                    )
                }
            }
            Column(
                Modifier
                    .height(height.dp)
                    .width((width / 2).dp)
                    .padding(top = OwnTheme.dp.smallDp, bottom = OwnTheme.dp.smallDp)
            ) {
                var imageUri by remember {
                    mutableStateOf<Uri?>(null)
                }

                val bitmap = remember {
                    mutableStateOf<Bitmap?>(
                        if (modeldb.urlToImage.isEmpty()) null else convertStringToBitmap(
                            modeldb.urlToImage
                        )
                    )
                }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) {}

                val launcher = rememberLauncherForActivityResult(
                    contract =
                    ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    imageUri = uri
                }


                if (bitmap.value == null) {
                    FloatingActionButton(onClick = {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) -> {
                                launcher.launch("image/*")
                            }

                            else -> {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }

                    imageUri?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            bitmap.value = MediaStore.Images
                                .Media.getBitmap(context.contentResolver, it)
                        } else {
                            val source = ImageDecoder
                                .createSource(context.contentResolver, it)
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }
                        bitmap.value?.let { btm ->
                            Image(
                                bitmap = btm.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.size(400.dp)
                            )
                            modeldb.apply {
                                mainViewModel.update(
                                    Modeldb(
                                        id = id,
                                        word = word,
                                        linkToSound = linkToSound,
                                        definitions = definitions,
                                        examples = examples,
                                        note = note,
                                        data = data,
                                        similar = similar,
                                        urlToImage = BitMapToString(bitmap.value!!)//it.toString()
                                    )
                                )
                            }
                        }
                    }
                } else {
                    Image(
                        bitmap = bitmap.value!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(400.dp)
                    )
                }
            }
        }
    }
}

private fun getImageUriFromBitmap(context: Context?, bitmap: Bitmap): Uri {
    try {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context!!.contentResolver, bitmap, "File", null)
        return Uri.parse(path.toString())
    } catch (e: java.lang.Exception) {
        return Uri.EMPTY
    }
}

fun BitMapToString(bitmap: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
}

private fun convertStringToBitmap(base64String: String): Bitmap {
    val decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

private val newShape = GenericShape { size, _ ->
    moveTo(size.width, 0f)
    lineTo(x = size.width, y = size.height)
    lineTo(0f, size.height)
    close()
}

private val otherwiseNewShape = GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(x = size.width, y = 0f)
    lineTo(0f, size.height)
    close()
}