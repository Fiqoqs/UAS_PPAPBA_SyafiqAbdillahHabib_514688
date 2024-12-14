package com.example.uas_ppapba_syafiqabdillahhabib_514688.network

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.BookmarkDao
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.BookmarkedRecipe

@Database(entities = [BookmarkedRecipe::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: android.content.Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}