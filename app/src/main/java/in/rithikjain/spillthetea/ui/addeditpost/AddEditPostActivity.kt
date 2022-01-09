package `in`.rithikjain.spillthetea.ui.addeditpost

import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.databinding.ActivityAddEditPostBinding
import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.hendraanggrian.appcompat.socialview.Hashtag
import com.hendraanggrian.appcompat.socialview.Mention
import com.hendraanggrian.appcompat.widget.HashtagArrayAdapter
import com.hendraanggrian.appcompat.widget.MentionArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditPostBinding

    private val viewModel: AddEditPostViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = intent.getSerializableExtra("post") as Post?

        if (post != null) {
            binding.spillButton.text = "Update"
            binding.deleteButton.visibility = View.VISIBLE
            binding.contentTextField.setText(post.content)
        }

        binding.deleteButton.setOnClickListener {
            if (post != null) {
                viewModel.deletePost(post)
                finish()
            }
        }

        binding.spillButton.setOnClickListener {
            val content = binding.contentTextField.text.toString()
            if (post != null) {
                viewModel.updatePost(post, content)
            } else {
                viewModel.insertPost(content)
            }
        }

        binding.closeButton.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addEditPostEvent.collect { event ->
                    when (event) {
                        is AddEditPostViewModel.AddEditPostEvent.ShowErrorMessage -> {
                            Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                        }
                        is AddEditPostViewModel.AddEditPostEvent.PostInsertionSuccess -> {
                            finish()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getProfilePhotoPath().collect { path ->
                    binding.profilePhotoImageView.setImageURI(
                        if (path.isNullOrEmpty()) null else Uri.parse(path)
                    )
                }
            }
        }

        val hashtagAdapter = HashtagArrayAdapter<Hashtag>(this)
        val mentionAdapter = MentionArrayAdapter<Mention>(this)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hashtags.collect {
                    for (hashtag in it) {
                        hashtagAdapter.add(Hashtag(hashtag.hashtagName))
                    }
                    binding.contentTextField.hashtagAdapter = hashtagAdapter
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.people.collect {
                    for (person in it) {
                        mentionAdapter.add(Mention(person.personName))
                    }
                    binding.contentTextField.mentionAdapter = mentionAdapter
                }
            }
        }
    }
}