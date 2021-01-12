package kr.co.jhjh550.mypodcast.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
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
                val myIntent = Intent(this@FileListActivity, PlayActivity::class.java)
                myIntent.putExtra("path", "$dirName/$fileName")
                startActivity(myIntent)
            }
            layoutManager = LinearLayoutManager(this@FileListActivity)
        }
    }
}