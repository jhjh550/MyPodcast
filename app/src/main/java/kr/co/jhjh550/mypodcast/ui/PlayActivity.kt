package kr.co.jhjh550.mypodcast.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import kr.co.jhjh550.mypodcast.MusicService
import kr.co.jhjh550.mypodcast.R
import kr.co.jhjh550.mypodcast.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {
    private val rootPath = Environment.getExternalStorageDirectory().toString()+"/Download"

    private var musicService: MusicService? = null
    lateinit var binding: ActivityPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindMusicService()
        initUI()
    }

    private fun initUI(){
        binding.btnPlay.setOnClickListener {
            musicService?.apply {
                if(isPlaying()){
                    pause()
                    binding.btnPlay.setImageResource(R.drawable.ic_play)
                }else {
                    resume()
                    binding.btnPlay.setImageResource(R.drawable.ic_pause)
                }
            }
        }
    }

    private fun bindMusicService(){
        val serviceIntent = Intent(this, MusicService::class.java)
        bindService(serviceIntent, conn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
    }

    val conn = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            musicService = (binder as MusicService.MusicServiceBinder).getService()

            val path = intent.getStringExtra("path") ?: ""
            val fullPath = "$rootPath/$path"
            musicService?.play(fullPath)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
        }
    }
}