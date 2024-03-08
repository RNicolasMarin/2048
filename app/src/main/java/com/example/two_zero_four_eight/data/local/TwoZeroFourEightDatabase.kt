package com.example.two_zero_four_eight.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.two_zero_four_eight.data.local.daos.RecordDao
import com.example.two_zero_four_eight.data.model.RecordValues

@Database(
    entities = [RecordValues::class],
    version = 1,
    exportSchema = false
)
abstract class TwoZeroFourEightDatabase: RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: TwoZeroFourEightDatabase? = null

        fun getInstance(context: Context): TwoZeroFourEightDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TwoZeroFourEightDatabase::class.java,
                "2048.db"
            ).build()
    }
}