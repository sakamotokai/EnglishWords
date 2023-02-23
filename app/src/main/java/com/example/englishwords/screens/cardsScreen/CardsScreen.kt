package com.example.englishwords.screens.cardsScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import com.example.englishwords.viewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.compose.get
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

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

    Log.e("Log", "my error")

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
                    var changeCard by remember { mutableStateOf(false) }
                    Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
                    //CardsScreenMainCardFront(modeldb = it, height = 150, width = 350)
/*                    Button(onClick = { changeCard = !changeCard }) {

                    }*/
                    Log.e("Log", "Scaffold Log")
                    if (changeCard) {
                        CardsScreenMainCardBack(
                            mainViewModel = mainViewModel,
                            modeldb = it,
                            height = 150,
                            width = 350,
                            setOtherSide = { changeCard = it }
                        )
                    } else {
                        CardsScreenMainCardFront(
                            modeldb = it,
                            height = 150,
                            width = 350,
                            setOtherSide = { changeCard = it }
                        )
                    }
                    Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
                }
            }
        }
    }
}

@Composable
fun CardsScreenMainCardFront(
    modeldb: Modeldb,
    width: Int,
    height: Int,
    setOtherSide: (Boolean) -> Unit
) {
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
                        var localSimilarWordContainer by remember { mutableStateOf("") }
                        Text(
                            text = "Similar words",
                            fontSize = OwnTheme.typography.general.fontSize,
                            color = OwnTheme.colors.primaryText
                        )
                        modeldb.similar.forEach {
                            localSimilarWordContainer += it
                            localSimilarWordContainer += ", "
                        }
                        Text(
                            text = localSimilarWordContainer.dropLast(2),
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
                        setOtherSide(true)
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
    width: Int,
    height: Int,
    setOtherSide: (Boolean) -> Unit
) {
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
                        setOtherSide(false)
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