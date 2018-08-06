package tech.alvarez.news.adapters

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import tech.alvarez.news.*
import tech.alvarez.news.models.Post

class ItemsAdapter(val items: MutableList<Post>, val listener: (Post) -> Unit) : RecyclerView.Adapter<ItemsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent.inflate(R.layout.item_post))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(items[position], listener)

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post, listener: (Post) -> Unit) = with(itemView) {
            if (TextUtils.isEmpty(post.image)) {
                photoCardView.gone()
            } else {
                photoCardView.visible()
                photoImageView.loadUrl(post.image)
            }
            titleTextView.text = post.title
            summaryTextView.text = post.summary
            dateTextView.text = post.date.formatTimeDefaults()
            if (post.source == "lostiempos.com") {
                sourceImageView.setImageResource(R.drawable.lostiemposcom)
            } else if (post.source == "la-razon.com") {
                sourceImageView.setImageResource(R.drawable.larazoncom)
            } else {
                sourceImageView.setImageResource(R.drawable.paginasietebo)
            }
            setOnClickListener { listener(post) }
        }
    }

}
