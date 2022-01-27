package meehan.matthew.waterwidget

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val glassesOfWater by glassesOfWater().collectAsState(0)
            WaterActivityContent(modifier = Modifier, glassesOfWater)
        }
    }
}