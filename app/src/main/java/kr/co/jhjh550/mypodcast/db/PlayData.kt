package kr.co.jhjh550.mypodcast.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayData(
    val directoryName:String,
    val fileName:String,
    val currentPosition:Long
){
    @PrimaryKey(autoGenerate = true) var uid:Int? = null
}