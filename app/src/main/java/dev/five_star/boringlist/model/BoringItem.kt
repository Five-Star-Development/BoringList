package dev.five_star.boringlist.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "BoringList")
data class BoringItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val todo: String,
    val description: String = "")
