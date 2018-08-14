package tech.alvarez.news.adapters

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import tech.alvarez.news.*
import tech.alvarez.news.models.Post

class ItemsAdapter(private val listener: PostListener) : RecyclerView.Adapter<ItemsAdapter.PostViewHolder>() {

    var items: MutableList<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent.inflate(R.layout.item_post))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(items[position], listener)

    fun setData(data: List<Post>) {
        items = data.toMutableList();
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post, listener: PostListener) {
            with(itemView) {
                if (TextUtils.isEmpty(post.image)) {
                    photoCardView.gone()
                } else {
                    photoCardView.visible()
                    photoImageView.loadUrl(post.image)
                }
                titleTextView.text = post.title
                summaryTextView.text = post.summary
                dateTextView.text = post.date.formatTimeDefaults()

                when (post.source) {
                    Sources.LOS_TIEMPOS.value -> sourceImageView.setImageResource(Sources.LOS_TIEMPOS.logo)
                    Sources.LA_RAZON.value -> sourceImageView.setImageResource(Sources.LA_RAZON.logo)
                    Sources.PAGINA_SIETE.value -> sourceImageView.setImageResource(Sources.PAGINA_SIETE.logo)
                    Sources.EL_DEBER.value -> sourceImageView.setImageResource(Sources.EL_DEBER.logo)
                }
            }
            itemView.setOnClickListener {
                listener.onSelected(post)
            }
        }
    }
}

interface PostListener {
    fun onSelected(post: Post)
}
