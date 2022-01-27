package meehan.matthew.waterwidget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import meehan.matthew.waterwidget.WaterWidget.Companion.MAX_GLASSES
import meehan.matthew.waterwidget.WaterWidget.Companion.WATER_WIDGET_PREFS_KEY

class AddWaterClickAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) {
            addWater(it)
        }
        WaterWidget().update(context, glanceId)
    }
}


fun addWater(it: Preferences): Preferences =
    it.toMutablePreferences()
        .apply {
            val glassesOfWater = this[intPreferencesKey(WATER_WIDGET_PREFS_KEY)] ?: 0
            if (glassesOfWater < MAX_GLASSES) {
                this[intPreferencesKey(WATER_WIDGET_PREFS_KEY)] = glassesOfWater + 1
            }
        }

class ClearWaterClickAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) {
            clearWater(it)
        }
        WaterWidget().update(context, glanceId)
    }

}


fun clearWater(it: Preferences): Preferences =
    it.toMutablePreferences()
        .apply {
            this[intPreferencesKey(WATER_WIDGET_PREFS_KEY)] = 0
        }