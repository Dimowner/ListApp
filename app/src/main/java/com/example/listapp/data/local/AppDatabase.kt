package com.example.listapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.listapp.data.local.model.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostsDao
}
