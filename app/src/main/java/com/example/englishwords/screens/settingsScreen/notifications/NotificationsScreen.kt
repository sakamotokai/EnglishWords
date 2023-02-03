package com.example.englishwords.screens.settingsScreen.notifications

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.englishwords.navigation.SettingsScreen
import com.example.englishwords.screens.ourUiElements.customClickable
import com.example.englishwords.screens.settingsScreen.manageTheme.OwnToggleButton
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import org.koin.androidx.compose.get

@Composable
fun NotificationsScreen(){
    Column {
        val notificationViewModel:NotificationViewModel = get()
        Spacer(modifier = Modifier.height(OwnTheme.dp.largeDp))
        NotificationScreenColumn(notificationViewModel)
    }
}

@Composable
fun NotificationScreenColumn(notificationViewModel: NotificationViewModel){
    Box(
        modifier = Modifier
            .background(OwnTheme.colors.secondaryBackground,shape = OwnTheme.sizesShapes.mediumShape)
    ){
        Column {
            val getNotifications = notificationViewModel.isNotifications.collectAsState()
            NotificationScreenDoElementCard(
                endElement = { OwnToggleButton(
                    work = { notificationViewModel.changeNotificationsState() },
                    darkModeState = getNotifications.value
                ) },
                icon = Icons.Filled.Notifications,
                text = "Get notifications"
            )
        }
    }
}

@Composable
fun NotificationScreenDoElementCard(endElement: @Composable () -> Unit, icon: ImageVector, text: String) {
    Row(
        Modifier
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
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(OwnTheme.dp.normalDp))
            androidx.compose.material3.Text(
                text = text,
                color = OwnTheme.colors.primaryText,
                fontSize = OwnTheme.typography.general.fontSize.value.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            endElement()
        }
    }
}

enum class SharedPreferencesNotifications {
    SharedPreferenceNotifications
}