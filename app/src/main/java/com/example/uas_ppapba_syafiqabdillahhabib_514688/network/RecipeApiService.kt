package com.example.uas_ppapba_syafiqabdillahhabib_514688.network


import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.Recipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RecipeApiService {
    @GET("recipes")
    suspend fun getAllRecipes(
        @Query("options") options: String? = null,
        @Query("projection") projection: String? = null
    ): List<Recipe>

    @GET("recipes/{id}")
    suspend fun getRecipeById(
        @Path("id") id: String
    ): Recipe

    @POST("recipes")
    suspend fun createRecipe(
        @Body recipe: Recipe
    ): Recipe

    @DELETE("recipes/{id}")
    suspend fun deleteRecipe(
        @Path("id") id: String
    )

    companion object {
        private const val BASE_URL = "https://ppbo-api.vercel.app/0Q19n/"

        fun create(): RecipeApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApiService::class.java)
        }
    }
}