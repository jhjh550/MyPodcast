package kr.co.jhjh550.mypodcast

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.view.View
import android.widget.RemoteViews

class MusicService : Service() {

    inner class MusicServiceBinder: Binder(){
        fun getService(): MusicService{
            return this@MusicService
        }
    }

    private val binder = MusicServiceBinder()
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private fun sendNotification(){
        val views = RemoteViews(packageName, R.layout.noti_layout)
        val notificationIntent = Intent(this, PlayActivity::class.java)
    }

    private var mp: MediaPlayer? = null

    fun play(path:String){
        mp = MediaPlayer().apply {
            setDataSource(path)
            prepare()
            start()
        }
    }
    fun stop(){
        mp?.stop()
        mp?.release()
        mp = null
    }

    fun pause() = mp?.pause()
    fun resume() = mp?.start()
    fun isPlaying() = mp?.isPlaying ?: false
}