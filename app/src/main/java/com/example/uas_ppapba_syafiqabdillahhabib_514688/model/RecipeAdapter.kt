package com.example.uas_ppapba_syafiqabdillahhabib_514688.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uas_ppapba_syafiqabdillahhabib_514688.R

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit,
    private val onBookmarkClick: ((Recipe) -> Unit)? = null,
    private val onDeleteClick: ((Recipe) -> Unit)? = null
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.recipe_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.recipe_description)
        val recipeImageView: ImageView = itemView.findViewById(R.id.recipe_image)
        val bookmarkIcon: ImageView? = itemView.findViewById(R.id.bookmark_icon)
        val deleteIcon: ImageView? = itemView.findViewById(R.id.delete_icon)

        fun bind(recipe: Recipe) {
            titleTextView.text = recipe.title
            descriptionTextView.text = recipe.description

            Glide.with(itemView.context)
                .load(recipe.imageUrl)
                .placeholder(R.drawable.img)
                .into(recipeImageView)

            itemView.setOnClickListener { onItemClick(recipe) }

            bookmarkIcon?.setOnClickListener { onBookmarkClick?.invoke(recipe) }
            deleteIcon?.setOnClickListener { onDeleteClick?.invoke(recipe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount() = recipes.size
}