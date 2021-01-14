package kr.co.jhjh550.mypodcast.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlayData::class], version = 1)
abstract class PlayDataBase: RoomDatabase() {
    abstract fun playDataDao(): PlayDataDao

    companion object{
        @Volatile private var instance: PlayDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            PlayDataBase::class.java, "playdatabase.db").build()
    }
}