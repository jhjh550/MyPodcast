package kr.co.jhjh550.mypodcast

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import kr.co.jhjh550.mypodcast.databinding.ActivityPlayBinding
import java.io.File

class PlayActivity : AppCompatActivity() {
    private val rootPath = Environment.getExternalStorageDirectory().toString()+"/Download"
    private val rootDir = File(rootPath)

    lateinit var mp: MediaPlayer
    lateinit var binding: ActivityPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path = intent.getStringExtra("path") ?: ""
        val fullPath = "$rootPath/$path"
        mp = MediaPlayer()
        mp.setDataSource(fullPath)
        mp.prepare()
        mp.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        mp.stop()
        mp.release()
    }
}