package com.example.uas_ppapba_syafiqabdillahhabib_514688.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_ppapba_syafiqabdillahhabib_514688.R
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.BookmarkDao
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.BookmarkedRecipe
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.Recipe
import com.example.uas_ppapba_syafiqabdillahhabib_514688.model.RecipeAdapter
import com.example.uas_ppapba_syafiqabdillahhabib_514688.network.RecipeDatabase
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {
    private lateinit var bookmarkDao: BookmarkDao
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)

        bookmarkDao = RecipeDatabase.getDatabase(requireContext()).bookmarkDao()
        recyclerView = view.findViewById(R.id.bookmarks_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Observe bookmarked recipes
        viewLifecycleOwner.lifecycleScope.launch {
            bookmarkDao.getAllBookmarks().collect { bookmarks ->
                val bookmarkAdapter = RecipeAdapter(
                    bookmarks.map { bookmarked ->
                        Recipe(
                            id = bookmarked.id,
                            title = bookmarked.title,
                            description = bookmarked.description,
                            ingredients = listOf(),
                            instructions = listOf(),
                            imageUrl = bookmarked.imageUrl,
                            category = ""
                        )
                    },
                    onItemClick = { recipe ->
                        // Navigasi ke DetailFragment saat item di klik
                        navigateToDetailFragment(recipe.id ?: "")
                    },
                    onBookmarkClick = { bookmarkedRecipe ->
                        lifecycleScope.launch {
                            bookmarkDao.deleteBookmark(
                                BookmarkedRecipe(
                                    id = bookmarkedRecipe.id ?: "",
                                    title = bookmarkedRecipe.title,
                                    description = bookmarkedRecipe.description,
                                    imageUrl = bookmarkedRecipe.imageUrl
                                )
                            )
                        }
                    }
                )
                recyclerView.adapter = bookmarkAdapter
            }
        }

        return view
    }
    private fun navigateToDetailFragment(recipeId: String) {
        val bundle = Bundle().apply {
            putString("recipe_id", recipeId)
        }
        findNavController().navigate(R.id.action_bookmarkFragment_to_DetailFragment, bundle)
    }
}
