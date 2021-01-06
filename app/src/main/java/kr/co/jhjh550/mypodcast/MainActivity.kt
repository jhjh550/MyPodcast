package kr.co.jhjh550.mypodcast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.jhjh550.mypodcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDir.apply {
            adapter = DirAdapter(""){ dirName ->
                val myIntent = Intent(this@MainActivity, FileListActivity::class.java)
                myIntent.putExtra("name", dirName)
                startActivity(myIntent)
            }
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}