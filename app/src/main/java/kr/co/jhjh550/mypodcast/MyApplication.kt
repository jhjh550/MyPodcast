package kr.co.jhjh550.mypodcast

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class MyApplication: Application() {
    companion object{
        lateinit var channelId: String
    }

    override fun onCreate() {
        super.onCreate()
        initNotiChannel()
    }

    fun initNotiChannel() {
        channelId = packageName
        val appName = applicationInfo.loadLabel(packageManager).toString()
        val channel = NotificationChannel(channelId, appName, NotificationManager.IMPORTANCE_LOW)
        val mgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mgr.createNotificationChannel(channel)
    }
}