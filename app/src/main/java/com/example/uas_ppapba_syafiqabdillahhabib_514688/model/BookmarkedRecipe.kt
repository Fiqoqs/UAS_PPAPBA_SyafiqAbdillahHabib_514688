package com.example.uas_ppapba_syafiqabdillahhabib_514688.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_recipes")
data class BookmarkedRecipe(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val imageUrl: String
)