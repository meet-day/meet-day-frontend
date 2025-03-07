package com.konkuk.meetday.ui.theme.MeetDay

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.meetday.R
import com.konkuk.meetday.ui.theme.MeetDay.ViewModel.MeetDayViewModel


@Composable
fun ScheduleScreen(viewModel: MeetDayViewModel) {

//    var text by viewModel.scheduleName.observeAsState(null)
    var date = 0
    date = 12
    LazyColumn(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 10.dp)
    ) {
        item {
            TopBar(viewModel.scheduleName.value!!)// 이후 예외 처리할 예정
        }
        item {
            SeeSchedule(viewModel)
        }
    }

}
@Composable
fun SeeSchedule(viewModel: MeetDayViewModel){
    val times = (0..24).toList() // 5AM ~ 4AM (24시간 표시)
    Row(
        modifier = Modifier, horizontalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.padding(top = (42).dp)
        ) {
            // 시간 표시
            times.forEach { hour ->
                TimeText(hour)
            }
        }
//        DateHeader(startDay.takeLast(2), date)
        Box {
            ScheduleGrid(viewModel)
        }

        Column(
            modifier = Modifier.padding(top = 42.dp)
        ) {
            // 시간 표시
            times.forEach { hour ->
                TimeText(hour)
            }
        }

    }

}


@Composable
fun TopBar(text: String) {
    var userClicked by remember {
        mutableStateOf(false)
    }
    var editClicked by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.icon_trash_16),
                contentDescription = "삭제"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { editClicked = !editClicked }) {
                if (!editClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit_24),
                        contentDescription = "편집"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_x_24),
                        contentDescription = "취소"
                    )
                }

            }
            if (!editClicked) {
                Box {
                    IconButton(onClick = { userClicked = !userClicked }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_users_24),
                            tint = if (userClicked) Color.Blue else colorResource(R.color.black),
                            contentDescription = "사용자"
                        )
                    }

                    // 드롭다운 메뉴 (isClicked가 true일 때만 표시)
                    if (userClicked) {
                        UserDropdownMenu(onDismiss = { userClicked = false }) // 메뉴 외부 클릭 시 닫기
                    }

                }
            }
            else{
                IconButton(onClick = { editClicked = !editClicked}){
                    Icon(
                        painter = painterResource(id = R.drawable.icon_check_24),
                        tint = Color.Blue,
                        contentDescription = "확인"
                    )
                }
            }
        }
    }

}

@Composable
fun UserDropdownMenu(onDismiss: () -> Unit) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = onDismiss, // 외부 클릭 시 닫힘
        modifier = Modifier.width(130.dp)
    ) {
        val users = listOf("조율리", "사용자1", "사용자2", "사용자3", "사용자4", "사용자5", "사용자6", "사용자7")
            // 나머지를 다 햐안색으로
        users.forEach { name ->
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.LightGray, CircleShape) // 프로필 이미지 자리
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = name)
                    }
                },
                onClick = { onDismiss() } // 클릭 시 메뉴 닫기
            )
        }

        // 참가자 초대 버튼
        DropdownMenuItem(
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, contentDescription = "추가")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "참석자 초대")
                }
            },
            onClick = { }//초대 버튼
        )
    }

}

@Composable
fun ScheduleGrid(viewModel: MeetDayViewModel) {
    val scheduleDays by viewModel.scheduleDay.observeAsState(emptyList())
    val scheduleDate by viewModel.scheduleDate.observeAsState(emptyList())
    val scheduleTime by viewModel.scheduleTime.observeAsState(emptyList())
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    LazyRow(
        modifier = Modifier
            .widthIn(max = screenWidth - 40.dp)
    ) {
        items(scheduleDays.size) { i ->
            Column(
                modifier = Modifier.padding(top = 5.7.dp)
            ) {
                TimeOneDay(scheduleDays[i], scheduleDate[i], scheduleTime[i], i)
            }
        }

    }
}

@Composable
fun TimeOneDay(date: Int, day: String, counts: List<Int>, index: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = date.toString(),
            fontSize = 10.sp,
            color = if (day == "일") colorResource(R.color.red) else colorResource(R.color.gray_300)
        )
        Text(
            text = day,
            fontSize = 14.sp,
            color = if (day == "일") colorResource(R.color.red) else colorResource(R.color.gray_300)
        )
        var check = 0
        counts.chunked(2).take(24).map { (count1, count2) ->
            TimeBox(count1, count2, index, check++)

        }
    }

}

@Composable
fun TimeBox(count1: Int, count2: Int, index: Int, j: Int) {
    val context = LocalContext.current
    val colorTop: Color = getColorByCount(count1)
    val colorBottom: Color = getColorByCount(count2)
    val red = colorResource(R.color.red)
    Box(
        modifier = Modifier
            .height(29.22.dp)
            .width(50.dp)
            .drawBehind {
                val strokeWidth = 2.dp.toPx() // 선 두께

                // 위쪽 테두리 (파란색)
                drawLine(
                    color = if (j == 12) red else Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )

                // 왼쪽 테두리 (녹색)
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = strokeWidth
                )

                // 오른쪽 테두리 (노란색)
                drawLine(
                    color = Color.Gray,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .clickable {
                Toast
                    .makeText(context, "클릭한 값: $index, $j", Toast.LENGTH_SHORT)
                    .show()
            }
    )
    {
        Canvas(
            modifier = Modifier.fillMaxSize()

        ) {
            val halfHeight = size.height / 2
            val dashWidth = 10f  // 점선 한 개의 길이
            val dashGap = 5f  // 점선 간격
            val strokeWidth = 2f // 선 두께

            // 위쪽 절반 색칠
            drawRect(
                color = colorTop,
                topLeft = Offset(0f, 0f),
                size = Size(size.width, halfHeight)
            )

            // 아래쪽 절반 색칠
            drawRect(
                color = colorBottom,
                topLeft = Offset(0f, halfHeight),
                size = Size(size.width, halfHeight)
            )
            drawLine(
                color = Color.Gray,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(dashWidth, dashGap),
                    0f
                )
            )
        }
    }
}

@Composable
fun TimeText(time: Int) {
    Box(
        modifier = Modifier
            .height(29.22.dp)
            .width(17.72.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        var ti = time
        if (ti in 13..24) {
            ti -= 12
        } else if (ti >= 25) {
            ti -= 24
        }
        Text(
            text = ti.toString(),
            fontSize = 12.sp,
            color = if (ti == 12) colorResource(R.color.red) else colorResource(R.color.gray_300)
        )
    }
}

@Composable
fun getColorByCount(count: Int): Color {
    return when (count) {
        -1 -> colorResource(R.color.selected_green)
        0 -> colorResource(R.color.white)
        1 -> colorResource(R.color.blue_50)
        2 -> colorResource(R.color.blue_100)
        3 -> colorResource(R.color.blue_200)
        4 -> colorResource(R.color.blue_300)
        5 -> colorResource(R.color.blue_400)
        6 -> colorResource(R.color.blue_500)
        7 -> colorResource(R.color.blue_600)
        8 -> colorResource(R.color.main_color)
        9 -> colorResource(R.color.blue_800)
        10 -> colorResource(R.color.blue_900)
        else -> colorResource(R.color.blue_950)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSchedule() {
    val viewModel = MeetDayViewModel()
    ScheduleScreen(viewModel)
}
