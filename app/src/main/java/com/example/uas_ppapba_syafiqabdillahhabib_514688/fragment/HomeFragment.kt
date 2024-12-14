package com.example.uas_ppapba_syafiqabdillahhabib_514688.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uas_ppapba_syafiqabdillahhabib_514688.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_ppapba_syafiqabdillahhabib_514688.AuthManager
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.BookmarkDao
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.BookmarkedRecipe
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.Recipe
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.RecipeAdapter
import com.example.uas_ppapba_syafiqabdillahhabib_514688.network.RecipeApiService
import com.example.uas_ppapba_syafiqabdillahhabib_514688.network.RecipeDatabase
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var recipeApiService: RecipeApiService
    private lateinit var authManager: AuthManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var bookmarkDao: BookmarkDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize dependencies
        recipeApiService = RecipeApiService.create()
        authManager = AuthManager(requireContext())
        bookmarkDao = RecipeDatabase.getDatabase(requireContext()).bookmarkDao()

        recyclerView = view.findViewById(R.id.recipes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchRecipes()

        if (authManager.isAdmin) {
            setupAdminFeatures(view)
        }

        return view
    }
    private fun navigateToDetailFragment(recipeId: String) {
        val bundle = Bundle().apply {
            putString("recipe_id", recipeId) // Tambahkan data yang ingin dikirim
        }

        view?.let { view ->
            androidx.navigation.Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_DetailFragment, bundle)
        }
    }

    private fun setupAdminFeatures(view: View) {
        val addRecipeButton: Button = view.findViewById(R.id.add_recipe_button)
        val titleInput: EditText = view.findViewById(R.id.recipe_title_input)
        val descriptionInput: EditText = view.findViewById(R.id.recipe_description_input)
        val ingredientsInput: EditText = view.findViewById(R.id.recipe_ingredients_input)
        val instructionsInput: EditText = view.findViewById(R.id.recipe_instructions_input)

        addRecipeButton.visibility = View.VISIBLE
        titleInput.visibility = View.VISIBLE
        descriptionInput.visibility = View.VISIBLE
        ingredientsInput.visibility = View.VISIBLE
        instructionsInput.visibility = View.VISIBLE

        addRecipeButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val ingredients = ingredientsInput.text.toString().split("-").map { it.trim() }
            val instructions = instructionsInput.text.toString().split("-").map { it.trim() }

            if (title.isEmpty() || description.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newRecipe = Recipe(
                title = title,
                description = description,
                ingredients = ingredients,
                instructions = instructions,
                imageUrl = "",
                category = "General",
                userId = authManager.userId,
                isAdmin = true
            )

            lifecycleScope.launch {
                try {
                    recipeApiService.createRecipe(newRecipe)
                    fetchRecipes()
                } catch (e: Exception) {
                    Toast.makeText(context, "Failed to add recipe", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchRecipes() {
        lifecycleScope.launch {
            try {
                val recipes = recipeApiService.getAllRecipes()
                recipeAdapter = RecipeAdapter(
                    recipes,
                    onItemClick = { recipe ->
                        recipe.id?.let { recipeId ->
                            navigateToDetailFragment(recipeId)
                        } ?: Toast.makeText(context, "Recipe ID not found", Toast.LENGTH_SHORT).show()
                    },
                    onBookmarkClick = { recipe ->
                        lifecycleScope.launch {
                            try {
                                val bookmarkedRecipe = BookmarkedRecipe(
                                    id = recipe.id ?: "",
                                    title = recipe.title,
                                    description = recipe.description,
                                    imageUrl = recipe.imageUrl
                                )
                                bookmarkDao.insertBookmark(bookmarkedRecipe)
                                Toast.makeText(context, "Recipe bookmarked!", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Failed to bookmark recipe", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onDeleteClick = if (authManager.isAdmin) { recipe ->
                        lifecycleScope.launch {
                            try {
                                recipe.id?.let {
                                    recipeApiService.deleteRecipe(it)
                                    fetchRecipes()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Failed to delete recipe", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else null
                )
                recyclerView.adapter = recipeAdapter
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to fetch recipes", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
