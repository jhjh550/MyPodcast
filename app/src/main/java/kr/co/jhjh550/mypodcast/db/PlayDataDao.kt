package kr.co.jhjh550.mypodcast.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: PlayData)

    @Delete
    suspend fun delete(data: PlayData)

    @Query("SELECT * FROM PlayData WHERE fileName=:fileName AND directoryName=:directoryName")
    fun getPlayData(fileName:String, directoryName:String): LiveData<PlayData>
}