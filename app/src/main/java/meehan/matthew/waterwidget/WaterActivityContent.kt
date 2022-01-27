package meehan.matthew.waterwidget

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import meehan.matthew.waterwidget.WaterWidget.Companion.RECOMMENDED_DAILY_GLASSES
import meehan.matthew.waterwidget.WaterWidget.Companion.WATER_WIDGET_PREFS_KEY

@Composable
fun WaterActivityContent(
    modifier: Modifier,
    glassesOfWater: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        WaterActivityCounter(
            context = context,
            glassesOfWater = glassesOfWater,
            modifier = Modifier
                .fillMaxWidth()
        )
        WaterActivityGoal(
            context = context,
            glassesOfWater = glassesOfWater,
            modifier = Modifier
                .fillMaxWidth()
        )
        WaterActivityButtonLayout(
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun WaterActivityCounter(
    context: Context,
    glassesOfWater: Int,
    modifier: Modifier
) {
    Text(
        text = context.getString(
            R.string.glasses_of_water_format,
            glassesOfWater
        ),
        modifier = modifier,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        ),
    )
}

@Composable
fun WaterActivityGoal(
    context: Context,
    glassesOfWater: Int,
    modifier: Modifier
) {
    Text(
        text =
        when {
            glassesOfWater >= RECOMMENDED_DAILY_GLASSES -> context.getString(
                R.string.goal_met
            )
            else -> context.getString(
                R.string.water_goal,
                RECOMMENDED_DAILY_GLASSES
            )
        },
        modifier = modifier,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        ),
    )
}

@Composable
fun WaterActivityButtonLayout(
    modifier: Modifier
) {
    val dataStore = LocalContext.current.dataStore
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_baseline_delete_outline_24
            ),
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    onClick = { clearWaterClickActionFun(dataStore) }
                )

        )
        Image(
            painter = painterResource(
                id = R.drawable.ic_baseline_add_24
            ),
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    onClick = { addWaterClickActionFun(dataStore) }
                )
        )
    }
}

fun clearWaterClickActionFun(dataStore: DataStore<Preferences>) {
    GlobalScope.launch {
        dataStore.updateData {
            clearWater(it)
        }
    }
}

fun addWaterClickActionFun(dataStore: DataStore<Preferences>) {
    GlobalScope.launch {
        dataStore.updateData {
            addWater(it)
        }
    }
}

internal const val FILENAME = "main"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FILENAME,
)

fun Context.glassesOfWater(): Flow<Int> = this.dataStore.data.map {
    it[intPreferencesKey(WATER_WIDGET_PREFS_KEY)] ?: 0
}
