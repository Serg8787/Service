package com.tsybulnik.service

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {
    private val scope = CoroutineScope(context = Dispatchers.Main)


    override fun onStartJob(p0: JobParameters?): Boolean {
        log("OnStartCommand")
        scope.launch {
            for(i in 0 until 100){
                delay(1000)
                log("Timer ${i}")
            }
            jobFinished(p0,true)
        }

        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
       log("onStopJob")
        return true
    }

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()
    }

    private fun log(message:String){
        Log.d("Service_TAG","MyJobService ${message}")
    }
    companion object{
       const val JOBINFO_ID = 1
    }

}