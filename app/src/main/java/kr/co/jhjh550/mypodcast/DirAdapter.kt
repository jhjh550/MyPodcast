package kr.co.jhjh550.mypodcast

import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kr.co.jhjh550.mypodcast.databinding.ItemFileBinding
import java.io.File

// todo : storage permission

class DirAdapter(private val dirName:String,
                    private val callback:(name:String)->Unit
): RecyclerView.Adapter<DirAdapter.MyViewHolder>() {
    private val rootPath = Environment.getExternalStorageDirectory().toString()+"/Download"
    private val rootDir = File(rootPath)
    private val items: ArrayList<File> = ArrayList()

    init {
        if(dirName.isEmpty()){
            getDirNames()
        }else{
            getFileNames()
        }

    }

    private fun getDirNames(){
        /***
         * Download 폴더에 audio 파일이 있을 경우에만 디렉토리 추가
         */
        rootDir.listFiles()?.let {
            for(dir in it){
                if(dir.isDirectory) {
                    dir.listFiles()?.let{ childs ->
                        for(f in childs) {
                            if(isAudioFile(f)){
                                items.add(dir)
                                return@let
                            }
                        }
                    }
                }
            }
        }
    }
    private fun getFileNames(){
        val dir = File(rootDir, dirName)
        dir.listFiles()?.let{
            for(f in it){
                if(isAudioFile(f)){
                    items.add(f)
                }
            }
        }
    }

    private fun isAudioFile(f: File): Boolean{
        if(f.isFile){
            val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(f.extension)
            type?.apply {
                if(startsWith("audio"))
                    return true
            }
        }

        return false
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val file = items[position]
        holder.bind(file)
        holder.itemView.setOnClickListener { callback(file.name) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(private val binding: ItemFileBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(file:File){
            binding.tvTitle.text = file.name
        }

    }
}
