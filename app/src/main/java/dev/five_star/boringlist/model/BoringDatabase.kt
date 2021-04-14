package dev.five_star.boringlist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BoringItem::class], version = 1, exportSchema = false)
abstract class BoringDatabase : RoomDatabase() {

    abstract fun boringDao(): BoringDao

    companion object {
        @Volatile
        private var INSTANCE: BoringDatabase? = null

        fun getDatabase(context: Context): BoringDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BoringDatabase::class.java,
                    "boring_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}