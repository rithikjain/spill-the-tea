package `in`.rithikjain.spillthetea.ui.posts

import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.databinding.ActivityPostsBinding
import `in`.rithikjain.spillthetea.ui.addeditpost.AddEditPostActivity
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.Serializable

@AndroidEntryPoint
class PostsActivity : AppCompatActivity(), PostsAdapter.OnItemClickListener {

    private lateinit var binding: ActivityPostsBinding
    private val viewModel: PostsViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")
        val value = intent.getStringExtra("value")
        if (type == "hashtag") {
            binding.appBarTitle.text = "#$value"
        } else if (type == "person") {
            binding.appBarTitle.text = "@$value"
        }

        val postsAdapter = PostsAdapter(this)

        binding.postsRecyclerView.apply {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(this@PostsActivity)
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (type == "hashtag") {
                    viewModel.getPostsByHashtag(value ?: "").collect {
                        postsAdapter.submitList(it[0].posts)
                    }
                } else if (type == "person") {
                    viewModel.getPostsByPerson(value ?: "").collect {
                        postsAdapter.submitList(it[0].posts)
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getName().collect {
                    postsAdapter.setName(it ?: "")
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUsername().collect {
                    postsAdapter.setUsername(it ?: "")
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getProfilePhotoPath().collect {
                    postsAdapter.setProfilePhotoPath(it)
                }
            }
        }
    }

    override fun onItemClick(post: Post) {
        val intent = Intent(this, AddEditPostActivity::class.java)
        intent.putExtra("post", post as Serializable)
        startActivity(intent)
    }

    override fun onHashtagClick(hashtag: String, post: Post) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra("type", "hashtag")
        intent.putExtra("value", hashtag)
        startActivity(intent)
    }

    override fun onPersonClick(person: String, post: Post) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra("type", "person")
        intent.putExtra("value", person)
        startActivity(intent)
    }
}