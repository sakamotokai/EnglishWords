package com.example.englishwords.screens.settingsScreen.manageTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import com.example.englishwords.ui.theme.ownTheme.OwnColors
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import org.koin.androidx.compose.get

@Composable
fun ManageTheme(){
    val settingsSharedPreferences = get<SettingsSharedPreferences>()
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = OwnTheme.colors.secondaryBackground),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = ,
                contentDescription = null
            )
        }
        LazyRow{

        }
    }
}
interface ManageThemeColorsListInterface{
    var colorsList:List<OwnColors>
}

class ManageThemeColorsList :ManageThemeColorsListInterface {
    override var colorsList: List<OwnColors> = listOf(

    )
}