package meehan.matthew.waterwidget

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import meehan.matthew.waterwidget.WaterWidget.Companion.MAX_GLASSES
import meehan.matthew.waterwidget.WaterWidget.Companion.WATER_WIDGET_PREFS_KEY

class AddWaterClickAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        addWaterClickActionFun(context)
        WaterWidget().updateAll(context)
    }
}

fun addWaterClickActionFunAsync(context: Context) {
    GlobalScope.launch {
        addWaterClickActionFun(context)

    }
}

private suspend fun addWaterClickActionFun(context: Context) {
    context.dataStore.updateData {
        addWater(it)
    }
    WaterWidget().updateAll(context)
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
        clearWaterClickActionFun(context)
        WaterWidget().updateAll(context)
    }

}


fun clearWaterClickActionFunAsync(context: Context) {
    GlobalScope.launch {
        clearWaterClickActionFun(context)
    }
}

private suspend fun clearWaterClickActionFun(context: Context) {
    context.dataStore.updateData {
        clearWater(it)
    }
    WaterWidget().updateAll(context)
}

private fun clearWater(it: Preferences): Preferences =
    it.toMutablePreferences()
        .apply {
            this[intPreferencesKey(WATER_WIDGET_PREFS_KEY)] = 0
        }
