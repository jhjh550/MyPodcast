package kr.co.jhjh550.mypodcast

import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kr.co.jhjh550.mypodcast.databinding.ItemFileBinding
import java.io.File

class DirAdapter: RecyclerView.Adapter<DirAdapter.MyViewHolder>() {
    private val rootPath = Environment.getExternalStorageDirectory().toString()+"/Download"
    private val rootDir = File(rootPath)
    private val items: ArrayList<File> = ArrayList()

    init {
        rootDir.listFiles()?.let {
            for(file in it){
                if(file.isDirectory)
                    items.add(file)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val file = items[position]
        holder.bind(file)
        holder.itemView.setOnClickListener {
            if(file.isFile){
                Toast.makeText(holder.itemView.context, "file: ${file.name}", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(holder.itemView.context, "directory: ${file.name}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    class MyViewHolder(private val binding: ItemFileBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(file:File){
            binding.tvTitle.text = file.name
        }

    }
}
