package com.example.uas_ppapba_syafiqabdillahhabib_514688.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.uas_ppapba_syafiqabdillahhabib_514688.R
import com.example.uas_ppapba_syafiqabdillahhabib_514688.network.RecipeApiService
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private lateinit var recipeApiService: RecipeApiService
    private var recipeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getString(ARG_RECIPE_ID)
        }
        recipeApiService = RecipeApiService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        recipeId?.let { id ->
            lifecycleScope.launch {
                try {
                    val recipe = recipeApiService.getRecipeById(id)
                    view.apply {
                        findViewById<TextView>(R.id.detail_title).text = recipe.title
                        findViewById<TextView>(R.id.detail_description).text = recipe.description
                        findViewById<TextView>(R.id.detail_ingredients).text =
                            recipe.ingredients.joinToString("\n") { "• $it" }
                        findViewById<TextView>(R.id.detail_instructions).text =
                            recipe.instructions.joinToString("\n\n") { "${recipe.instructions.indexOf(it) + 1}. $it" }
                    }
                } catch (e: Exception) {
                    // Handle error
                    Toast.makeText(context, "Error loading recipe details", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    companion object {
        private const val ARG_RECIPE_ID = "recipe_id"

        fun newInstance(recipeId: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_RECIPE_ID, recipeId)
                }
            }
    }
}