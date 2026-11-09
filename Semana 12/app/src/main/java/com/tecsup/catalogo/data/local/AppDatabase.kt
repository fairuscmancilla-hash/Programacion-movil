package com.tecsup.catalogo.data.local
import androidx.room.*
@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() { abstract fun itemDao(): ItemDao }
