package tech.alvarez.news.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_post.view.*
import tech.alvarez.news.R
import tech.alvarez.news.formatTimeDefaults
import tech.alvarez.news.inflate
import tech.alvarez.news.loadUrl
import tech.alvarez.news.models.Post

class ItemsAdapter(val items: MutableList<Post>) : RecyclerView.Adapter<ItemsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent.inflate(R.layout.item_post))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(items[position])

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post) = with(itemView) {
            photoImageView.loadUrl(post.image)
            titleTextView.text = post.title
            summaryTextView.text = post.summary
            dateTextView.text = post.date.formatTimeDefaults()
        }
    }

}
