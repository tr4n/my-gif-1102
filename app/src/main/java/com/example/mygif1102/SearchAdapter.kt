package com.example.mygif1102

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygif1102.SearchAdapter.SearchViewHolder
import com.example.mygif1102.gifview.GIFView
import com.example.mygif1102.model.GifImage
import com.example.mygif1102.utils.SCREEN_WIDTH
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(var items: List<GifImage>, private val onItemClick: (gifImage: GifImage?) -> Unit) :
    RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SearchViewHolder =
        SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search, parent, false), onItemClick
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: SearchViewHolder, position: Int) =
        viewHolder.onBindData(items[position])


    inner class SearchViewHolder(itemView: View, onItemClick: (gifImage: GifImage?) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private var gifImage: GifImage? = null
        init{
            itemView.setOnClickListener { onItemClick(gifImage) }
        }
        fun onBindData(item: GifImage) {
            gifImage = item
            val width = item.width
            val height = item.height
            val fixedWidth = SCREEN_WIDTH shr 1

           itemView.imageItem.setLayoutParams(width, height, fixedWidth, GIFView.WIDTH_SCALE)
          //  itemView.imageItem.imageUrl = item.url
           itemView.imageItem.gifStream = item.url
        }

        private fun displayImage(url: String) {
//            Glide.with(itemView.context)
//                .load(gifStream)
//                .placeholder(Constants.COLORS[Random.nextInt(Constants.COLORS.size)])
//                .into(itemView.imageItem)
        }
    }
}
