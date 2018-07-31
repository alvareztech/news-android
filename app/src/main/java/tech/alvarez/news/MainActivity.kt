package tech.alvarez.news

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import tech.alvarez.news.adapters.ItemsAdapter
import tech.alvarez.news.models.Post

class MainActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()
    var adapter: ItemsAdapter? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                recyclerView.smoothScrollToPosition(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

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
            builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }
        recyclerView.adapter = adapter
    }
}
