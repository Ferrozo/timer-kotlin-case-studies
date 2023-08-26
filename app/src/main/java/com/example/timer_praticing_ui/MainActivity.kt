package com.example.timer_praticing_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                color = Color(0xFF212121),
                modifier = Modifier.fillMaxSize(),
            ){
            Box(
                contentAlignment = Alignment.Center
            ){
                Timer(
                    seconds = 100L * 1000L,
                    activeColor= Color.Magenta ,
                    bgColor = Color.Gray,
                    modifier = Modifier.size(200.dp)
                    )
            }
            }
        }
    }
}

@Composable
fun Timer(
    seconds: Long,
    activeColor: Color,
    bgColor: Color,
    modifier: Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 10.dp,

    ){

    val size = remember {
        mutableStateOf(IntSize.Zero)
    }
    val value = remember {
        mutableStateOf(initialValue)
    }
    val currentTime = remember {
        mutableStateOf(seconds)
    }

    val isTimerRunning = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = currentTime.value, key2 = isTimerRunning.value){
        if(currentTime.value > 0 && isTimerRunning.value){
            delay(1000L)
            currentTime.value -= 1000L
            value.value = currentTime.value / seconds.toFloat()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.onSizeChanged {
                size.value = it
            }
        ){
            Canvas(modifier = modifier ){
                drawArc(
                    color = bgColor,
                    startAngle = 90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    size = Size( size.value.width.toFloat() , size.value.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap =  StrokeCap.Round),
                )
                drawArc(
                    color = activeColor,
                    startAngle = 90f,
                    sweepAngle = 360f * value.value,
                    useCenter = false,
                    size = Size( size.value.width.toFloat() , size.value.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap =  StrokeCap.Round),
                )


            }

            Column{
                Text(
                    text = "seconds",
                    fontSize = 10.sp,
                    color = Color.White,
                )
                Text(
                    text = (currentTime.value / 1000L).toString(),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

        }
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedButton(
            modifier =Modifier
                .height(60.dp)
                .width(170.dp)
            ,
            onClick = {
                if( currentTime.value <= 0L){
                    currentTime.value = seconds
                    isTimerRunning.value = true
                }else{
                    isTimerRunning.value = !isTimerRunning.value
                }
            },
            border = BorderStroke(2.dp, Color.Magenta)


        ) {
            Text(text =
            if(isTimerRunning.value && currentTime.value >= 0L){
                "STOP"
            } else
                if(!isTimerRunning.value || currentTime.value >= 0L){
                    "START"
                }else {"RESTART"},
                color =Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }

}
