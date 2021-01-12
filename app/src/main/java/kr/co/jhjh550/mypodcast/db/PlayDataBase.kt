package kr.co.jhjh550.mypodcast.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayData::class], version = 1)
abstract class PlayDataBase: RoomDatabase() {
    abstract fun playDataDao(): PlayDataDao
}