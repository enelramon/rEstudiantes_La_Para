package com.tutorial.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tutorial.myapplication.data.dao.StudentDao
import com.tutorial.myapplication.model.Student

@Database(entities = [Student::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "TutorialDb"
        @Volatile private var instance: AppDataBase? = null

        private fun buildDataBase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                DATABASE_NAME
            ).build()
        }

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }
    }
    abstract fun studentDao(): StudentDao
}