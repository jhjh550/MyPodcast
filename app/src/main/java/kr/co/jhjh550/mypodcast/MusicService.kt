package kr.co.jhjh550.mypodcast

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import android.widget.Toast
import java.io.File

class MusicService : Service() {
    companion object{
        const val PACKAGE = "kr.co.jhjh550.mypodcast.MusicService"
        const val ACTION_PLAYER = "$PACKAGE.player"
        const val ACTION_PREV_30 = "$PACKAGE.prev30"
        const val ACTION_FILE_PLAY = "$PACKAGE.file.play"
        const val ACTION_NEXT_30 = "$PACKAGE.next30"
        const val ACTION_FILE_CLOSE = "$PACKAGE.file.close"
    }

    inner class MusicServiceBinder: Binder(){
        fun getService(): MusicService{
            return this@MusicService
        }
    }

    private val binder = MusicServiceBinder()
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            when(it){
                ACTION_FILE_CLOSE ->{
                    Toast.makeText(this, "close", Toast.LENGTH_LONG).show()
                }
                ACTION_FILE_PLAY->{
                    Toast.makeText(this, "play", Toast.LENGTH_LONG).show()
                }
                ACTION_PREV_30->{
                    Toast.makeText(this, "prev 30", Toast.LENGTH_LONG).show()
                }
                ACTION_NEXT_30->{
                    Toast.makeText(this, "next 30", Toast.LENGTH_LONG).show()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun sendNotification(){
        val notiIntent = Intent(this, PlayActivity::class.java)
        notiIntent.action = MusicService.ACTION_PLAYER
        notiIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val prevIntent = Intent(this, MusicService::class.java)
        prevIntent.action = ACTION_PREV_30
        val pprevIntent = PendingIntent.getService(this, 0, prevIntent, 0)

        val playIntent = Intent(this, MusicService::class.java)
        playIntent.action = ACTION_FILE_PLAY
        val pplayIntent = PendingIntent.getService(this, 0, playIntent, 0)

        val nextIntent = Intent(this, MusicService::class.java)
        nextIntent.action = ACTION_NEXT_30
        val pnextIntent = PendingIntent.getService(this, 0, nextIntent, 0)

        val closeIntent = Intent(this, MusicService::class.java)
        closeIntent.action = ACTION_FILE_CLOSE
        val pcloseIntent = PendingIntent.getService(this, 0, closeIntent, 0)

        val views = RemoteViews(packageName, R.layout.noti_layout).apply {
            setOnClickPendingIntent(R.id.notibar_play, pplayIntent)
            setOnClickPendingIntent(R.id.notibar_prev30, pprevIntent)
            setOnClickPendingIntent(R.id.notibar_next30, pnextIntent)
            setOnClickPendingIntent(R.id.notibar_close, pcloseIntent)
            val playResource = if(isPlaying()) R.drawable.ic_pause else R.drawable.ic_play
            setImageViewResource(R.id.notibar_play, playResource)
            file?.let {
                setTextViewText(R.id.notibar_tvFileName, it.name)
                setTextViewText(R.id.notibar_tvDirName, it.parentFile.name)
            }
        }

        val builder = Notification.Builder(this, MyApplication.channelId).apply {
            setContent(views)
            setAutoCancel(true)
            setSmallIcon(R.drawable.ic_play)
            setContentIntent(pendingIntent)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_play))
        }

        val noti = builder.build()
        startForeground(1234, noti)
    }

    private var mp: MediaPlayer? = null
    private var file: File? = null

    fun play(path:String){
        file = File(path)
        mp = MediaPlayer().apply {
            setDataSource(path)
            prepare()
            start()
        }
        sendNotification()
    }
    fun stop(){
        mp?.stop()
        mp?.release()
        mp = null
        sendNotification()
    }

    fun pause(){
        mp?.pause()
        sendNotification()
    }
    fun resume(){
        mp?.start()
        sendNotification()
    }
    fun isPlaying() = mp?.isPlaying ?: false
}