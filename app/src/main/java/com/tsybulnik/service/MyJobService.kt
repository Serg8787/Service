package com.tsybulnik.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {
    private val scope = CoroutineScope(context = Dispatchers.Main)


    override fun onStartJob(p0: JobParameters?): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            scope.launch {
                log("OnStartCommand")
                var workItem = p0?.dequeueWork()
                while (workItem != null) {
                    val page = workItem.intent?.getIntExtra(PAGE, 0)

                    for (i in 0 until 5) {
                        delay(1000)
                        log("Timer ${i} $page")
                    }

                    p0?.completeWork(workItem)
                    workItem = p0?.dequeueWork()

                }
                jobFinished(p0, false)
            }
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

    private fun log(message: String) {
        Log.d("Service_TAG", "MyJobService ${message}")
    }

    companion object {
        const val JOBINFO_ID = 1
        const val PAGE = "page"
        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }
    }

}