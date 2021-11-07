package com.tsybulnik.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyIntentService2 : IntentService(NAME) {



    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.extras?.getInt(PAGE)
        for(i in 0 until 5){
            Thread.sleep(1000)
            log("Timer ${i} $page")
        }
    }

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message:String){
        Log.d("Service","MyForegroundService ${message}")
    }
    companion object{

        private const val NAME = "intent_service"
        private const val PAGE = "page"
        fun newIntent(context: Context,page:Int): Intent {
            return Intent(context,MyService::class.java).apply {
                putExtra(PAGE,page) }

        }
    }

}