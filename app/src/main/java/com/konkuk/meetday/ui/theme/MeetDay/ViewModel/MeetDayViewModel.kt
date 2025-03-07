package com.konkuk.meetday.ui.theme.MeetDay.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MeetDayViewModel : ViewModel() {
    private val _scheduleName = MutableLiveData<String>()
    private val _scheduleTime = MutableLiveData<List<List<Int>>>()
    private val _scheduleDate = MutableLiveData<List<String>>()
    private val _scheduleDay = MutableLiveData<List<Int>>() // 데이터 보고결정


    val scheduleTime: LiveData<List<List<Int>>>get() = _scheduleTime
    val scheduleDate: LiveData<List<String>>get() = _scheduleDate
    val scheduleDay: LiveData<List<Int>>get() = _scheduleDay
    val scheduleName: LiveData<String>get() = _scheduleName

    init {
        initDummyData()
    }


    fun LoadSchedule(){
        // 스케쥴 로드 로직 api 연결
    }

    fun UpdateSchedule(){
        // 스케쥴 업데이트 로직 api 연결
    }


    private fun initDummyData(){
        _scheduleName.value="글자 수 최대 가능 수??"
        _scheduleDay.value=listOf(1,2,3,4,5,6,7,8,9,10,11)
        _scheduleDate.value=listOf("월","화","수","목","금","토","일","월","화","수","목")

        val dummyTime = List(11) { MutableList(48) { 0 } }
        for(i in 0..10) {
            for (j in 0..47) {
                dummyTime[i][j]=i+j
            }
        }
        _scheduleTime.value=dummyTime

    }

}