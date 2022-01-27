package meehan.matthew.waterwidget

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import meehan.matthew.waterwidget.WaterWidget.Companion.MAX_GLASSES
import meehan.matthew.waterwidget.WaterWidget.Companion.WATER_WIDGET_PREFS_KEY

class AddWaterClickAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        addWaterClickActionFun(context.dataStore)
        WaterWidget().update(context, glanceId)
    }
}

fun addWaterClickActionFunAsync(dataStore: DataStore<Preferences>) {
    GlobalScope.launch {
        addWaterClickActionFun(dataStore)
    }
}

private suspend fun addWaterClickActionFun(dataStore: DataStore<Preferences>) {
    dataStore.updateData {
        addWater(it)
    }
}

private fun addWater(it: Preferences): Preferences =
    it.toMutablePreferences()
        .apply {
            val glassesOfWater = this[intPreferencesKey(WATER_WIDGET_PREFS_KEY)] ?: 0
            if (glassesOfWater < MAX_GLASSES) {
                this[intPreferencesKey(WATER_WIDGET_PREFS_KEY)] = glassesOfWater + 1
            }
        }

class ClearWaterClickAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        clearWaterClickActionFun(context.dataStore)
        WaterWidget().update(context, glanceId)
    }

}


fun clearWaterClickActionFunAsync(dataStore: DataStore<Preferences>) {
    GlobalScope.launch {
        clearWaterClickActionFun(dataStore)
    }
}

private suspend fun clearWaterClickActionFun(dataStore: DataStore<Preferences>) {
    dataStore.updateData {
        clearWater(it)
    }
}

private fun clearWater(it: Preferences): Preferences =
    it.toMutablePreferences()
        .apply {
            this[intPreferencesKey(WATER_WIDGET_PREFS_KEY)] = 0
        }
