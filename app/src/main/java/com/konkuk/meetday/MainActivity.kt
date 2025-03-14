package com.konkuk.meetday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.konkuk.meetday.ui.theme.MeetDay.ScheduleScreen
import com.konkuk.meetday.ui.theme.MeetDay.ViewModel.MeetDayViewModel
import com.konkuk.meetday.ui.theme.MeetDayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeetDayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val viewModel = MeetDayViewModel()
    ScheduleScreen(viewModel)
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeetDayTheme {
        Greeting("Android")
    }
}