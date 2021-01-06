package kr.co.jhjh550.mypodcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.jhjh550.mypodcast.databinding.ActivityMainBinding

class FileListActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDir.apply {
            val dirName = intent.getStringExtra("name") ?: ""
            adapter = DirAdapter(dirName){ fileName ->

            }
            layoutManager = LinearLayoutManager(this@FileListActivity)
        }
    }
}