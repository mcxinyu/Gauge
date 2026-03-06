/*
 *     Gauge/Gauge.app.main
 *     MainActivity.kt Copyrighted by Yamin Siahmargooei at 2023/10/24
 *     MainActivity.kt Last modified copyright at 2023/10/24
 *     This file is part of Gauge/Gauge.app.main.
 *     Copyright (C) 2023  Yamin Siahmargooei
 *
 *     Gauge/Gauge.app.main is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Gauge/Gauge.app.main is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Gauge.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.yamin8000.gauge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.github.yamin8000.gauge.main.Gauge
import com.github.yamin8000.gauge.main.GaugeNumerics
import com.github.yamin8000.gauge.ui.color.GaugeArcColors
import com.github.yamin8000.gauge.ui.color.GaugeColors
import com.github.yamin8000.gauge.ui.color.GaugeTicksColors
import com.github.yamin8000.gauge.ui.style.GaugeArcStyle
import com.github.yamin8000.gauge.ui.style.GaugeNeedleStyle
import com.github.yamin8000.gauge.ui.style.GaugeStyle
import com.github.yamin8000.gauge.ui.theme.GaugeTheme
import kotlin.Boolean

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GaugeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                                .background(Color.White)
                        ) {
                            val configuration = LocalConfiguration.current
                            val screenWidth = configuration.screenWidthDp.dp
                            var value by remember { mutableFloatStateOf(66.66f) }
                            var totalSize by remember { mutableStateOf(350.dp) }
                            var strokeWidth by remember { mutableFloatStateOf(35f) }
                            val valueRange = 0f..100f
                            Box() {
                                Gauge(
                                    modifier = Modifier.size(totalSize),
                                    value = value,
                                    numerics = GaugeNumerics(
                                        startAngle = 135,
                                        sweepAngle = 270,
                                        valueRange = valueRange,
                                        bigTicksStep = 10f,
                                        smallTicksStep = 5f,
                                    ),
                                    style = GaugeStyle(
                                        hasBorder = false,
                                        hasValueText = true,
                                        borderWidth = 0f,
                                        needleStyle = GaugeNeedleStyle(
                                            hasNeedle = true,
                                            tipHasCircle = false,
                                            tipHasLine = true,
                                            hasRing = false,
                                            ringWidth = 0f,
                                        ),
                                        arcStyle = GaugeArcStyle(
                                            hasArcs = true,
                                            hasProgressive = false,
                                            bigTicksHasLabels = false,
                                            cap = StrokeCap.Butt,
                                            gap = 1f,
                                        ),
                                    ),
                                    ticksColorProvider = { list ->
                                        list.map { pair ->
                                            if (pair.first % 33 == 0)
                                                pair.first to Color(0xFF2962FF)
                                            else pair
                                        }
                                    },
                                    arcColorsProvider = { colors, gaugeValue, range ->
                                        when (gaugeValue) {
                                            in range.start..range.endInclusive / 3f ->
                                                GaugeArcColors(colors.off, Color.Red)

                                            in range.endInclusive / 3f..range.endInclusive / 3 * 2 ->
                                                GaugeArcColors(colors.off, Color.Yellow)

                                            else -> GaugeArcColors(colors.off, Color.Green)
                                        }
                                    },
                                )

                                // Box(
                                //     modifier = Modifier
                                //         .align(Alignment.Center)
                                //         .size(250.dp)
                                //         .clip(CircleShape)
                                //         .background(Color.White),
                                // ) {
                                //
                                // }
                            }
                            Text("width: $screenWidth")
                            Text("Value: $value")
                            Slider(
                                value = value,
                                valueRange = valueRange,
                                onValueChange = {
                                    value = it
                                },
                                modifier = Modifier.padding(horizontal = 20.dp),
                            )
                            Text("Total Size: $totalSize")
                            Slider(
                                value = totalSize.value,
                                valueRange = 0f..500f,
                                onValueChange = {
                                    totalSize = it.dp
                                },
                                modifier = Modifier.padding(horizontal = 20.dp),
                            )

                            Text("Arc Stroke Width: $strokeWidth")
                            Slider(
                                value = strokeWidth,
                                valueRange = 10f..60f,
                                onValueChange = {
                                    strokeWidth = it
                                },
                                modifier = Modifier.padding(horizontal = 20.dp),
                            )
                        }
                    }
                )
            }
        }
    }
}