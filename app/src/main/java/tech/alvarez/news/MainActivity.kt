package tech.alvarez.news

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import tech.alvarez.news.adapters.ItemsAdapter
import tech.alvarez.news.models.Post


class MainActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()
    var adapter: ItemsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            bar.toggleFabAlignment()
        }

        getData()
    }

    private fun getData() {
        db.collection("posts")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val postsList = mutableListOf<Post>()
                        for (document in task.result) {
                            val post = document.toObject(Post::class.java)
                            postsList.add(post)
                        }
                        setData(postsList)

                    } else {
                        Log.w("NEWS", "Error getting documents.", task.exception)
                    }
                }
    }

    private fun setData(postsList: MutableList<Post>) {
        adapter = ItemsAdapter(postsList) {
            val url = it.url
            val builder = CustomTabsIntent.Builder()
            builder.setShowTitle(true)
            builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
            builder.setCloseButtonIcon(bitmapFromDrawable(R.drawable.ic_arrow_back))
            builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }
        recyclerView.adapter = adapter
    }

    private fun bitmapFromDrawable(drawableId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(applicationContext, drawableId)
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else if (drawable is VectorDrawable) {
            bitmapFromVectorDrawable((drawable as VectorDrawable?)!!)
        } else {
            throw IllegalArgumentException("Unable to convert to bitmap")
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun bitmapFromVectorDrawable(vectorDrawable: VectorDrawable): Bitmap {
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation, menu)
        return true
    }
}
