package dev.five_star.boringlist.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BoringDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBoringItem(item: BoringItem)

    @Query("Select * FROM BoringList ORDER BY id ASC")
    suspend fun getAll(): List<BoringItem>
}