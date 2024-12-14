package com.example.uas_ppapba_syafiqabdillahhabib_514688.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarked_recipes")
    fun getAllBookmarks(): Flow<List<BookmarkedRecipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(recipe: BookmarkedRecipe)

    @Delete
    suspend fun deleteBookmark(recipe: BookmarkedRecipe)

    @Query("SELECT * FROM bookmarked_recipes WHERE id = :recipeId")
    fun getBookmarkById(recipeId: String): BookmarkedRecipe?
}