package com.moose.foodies.features.feature_recipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.moose.foodies.databinding.RecipeItemBinding
import com.moose.foodies.features.feature_home.domain.Item
import com.moose.foodies.features.feature_recipe.adapters.ItemListAdapter.ItemViewHolder

class ItemListAdapter(private val items: List<Item>, private val type: String): RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ItemViewHolder(private val binding: RecipeItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            val imageUrl = "https://spoonacular.com/cdn/${type}_100x100/${item.image}"

            with(binding){
                itemName.text = item.name
                itemImage.load(imageUrl)
            }
        }

    }
}