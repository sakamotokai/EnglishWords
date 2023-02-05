package com.example.englishwords.screens.settingsScreen.notifications

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishwords.screens.ourUiElements.customClickable
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import org.koin.androidx.compose.get

@Composable
fun NotificationsScreen() {
    Column {
        val notificationViewModel: NotificationViewModel = get()
        Spacer(modifier = Modifier.height(OwnTheme.dp.largeDp))
        NotificationScreenColumn(notificationViewModel)
    }
}

@Composable
fun NotificationScreenColumn(notificationViewModel: NotificationViewModel) {
    Box(
        modifier = Modifier
            .background(
                OwnTheme.colors.secondaryBackground,
                shape = OwnTheme.sizesShapes.mediumShape
            )
    ) {
        Column {
            val getRemindedNotifications =
                notificationViewModel.isRemindedNotifications.collectAsState()
            val getWordNotifications = notificationViewModel.isWordNotifications.collectAsState()
            NotificationScreenElementCard(
                endElement = {
                    NotificationScreenToggleButton(
                        work = { notificationViewModel.changeRemindedNotificationsState() },
                        darkModeState = getRemindedNotifications.value
                    )
                },
                icon = Icons.Filled.Notifications,
                text = "Get reminded notifications"
            )
            NotificationScreenElementCard(
                endElement = {
                    NotificationScreenToggleButton(
                        work = { notificationViewModel.changeWordNotificationsState() },
                        darkModeState = getWordNotifications.value
                    )
                },
                icon = Icons.Filled.Notifications,
                text = "Get word notifications"
            )
            if (getWordNotifications.value)
                NotificationScreenElementCard(
                    endElement = {
                        NotificationScreenDropoutMenu(
                            menuList = listOf(
                                Pair("4 minutes", 240000),
                                Pair("15 minutes", 900000),
                                Pair("30 minutes", 1800000),
                                Pair("1 hour", 3600000),
                                Pair("2 hour", 7200000),
                                Pair("3 hour", 10800000)
                            ),
                            work = { time ->
                                Log.e("Log","first")
                                notificationViewModel.setNotificationTimeOut(time)
                            }
                        )
                    },
                    icon = Icons.Filled.Info,
                    text = "Select time between word notifications"
                )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NotificationScreenDropoutMenu(menuList: List<Pair<String, Long>>, work: (time: Long) -> Unit) {
    var dropMenuExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .customClickable { dropMenuExpanded = true },
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        Box {
            androidx.compose.material.Icon(
                imageVector = if (dropMenuExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = OwnTheme.colors.primaryBackground
            )
            DropdownMenu(
                expanded = dropMenuExpanded,
                onDismissRequest = { dropMenuExpanded = false },
                Modifier.background(OwnTheme.colors.primaryBackground)
            ) {
                AnimatedContent(targetState = dropMenuExpanded) {
                    Column {
                        for (pair in menuList) {
                            Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
                            Row(Modifier.fillMaxSize()) {
                                Spacer(modifier = Modifier.width(OwnTheme.dp.smallDp))
                                Text(
                                    pair.first,
                                    color = OwnTheme.colors.primaryText,
                                    modifier = Modifier.clickable {
                                        dropMenuExpanded = false
                                        work(pair.second)
                                    },
                                    fontSize = OwnTheme.typography.general.fontSize
                                )
                                Spacer(modifier = Modifier.width(OwnTheme.dp.smallDp))
                            }
                            Spacer(modifier = Modifier.height(OwnTheme.dp.smallDp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationScreenToggleButton(work: () -> Unit, darkModeState: Boolean) {
    Box(modifier = Modifier.height(10.dp)) {
        androidx.compose.material3.Switch(
            checked = darkModeState,
            onCheckedChange = {
                work()
            }
        )
    }
}

@Composable
fun NotificationScreenElementCard(
    endElement: @Composable () -> Unit,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        Modifier
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .height(OwnTheme.dp.bigDp)
            .padding(start = OwnTheme.dp.normalDp)
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OwnTheme.colors.tintColor
            )
            Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
            Text(
                text = text,
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            endElement()
        }
    }
}

enum class SharedPreferencesNotifications {
    RemindedNotifications, WordNotifications, WordNotificationsTimeOut
}