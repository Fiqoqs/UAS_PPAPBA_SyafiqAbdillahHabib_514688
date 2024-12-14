package com.example.uas_ppapba_syafiqabdillahhabib_514688.model


import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("_id") val id: String? = null,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val imageUrl: String,
    val category: String,
    val userId: String? = null,
    val isAdmin: Boolean = false
)