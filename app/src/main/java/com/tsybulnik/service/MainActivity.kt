package com.tsybulnik.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.tsybulnik.service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var id = 0

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.simpleService.setOnClickListener {
            stopService(MyForegroundService.newIntent(this))
            startService(MyService.newIntent(this,12))
            Log.d("Service_TAG","simpleService")
        }
        binding.foregroundService.setOnClickListener {
            Log.v("Service_TAG","foregroundService")
           ContextCompat.startForegroundService(this,MyForegroundService.newIntent(this))
        }
        binding.intentService.setOnClickListener {
            Log.v("Service_TAG","intentService")
            ContextCompat.startForegroundService(this,MyIntentService.newIntent(this))
        }
        binding.jobScheluder.setOnClickListener {
            Log.v("Service_TAG","MyJobService")
            val componentName = ComponentName(this,MyJobService::class.java)
            val jobInfo = JobInfo.Builder(MyJobService.JOBINFO_ID,componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .build()
            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }
    }


}