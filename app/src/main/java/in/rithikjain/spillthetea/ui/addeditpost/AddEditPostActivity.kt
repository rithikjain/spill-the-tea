package `in`.rithikjain.spillthetea.ui.addeditpost

import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.databinding.ActivityAddEditPostBinding
import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
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
            binding.contentTextField.editText!!.setText(post.content)
        }

        binding.deleteButton.setOnClickListener {
            if (post != null) {
                viewModel.deletePost(post)
                finish()
            }
        }

        binding.spillButton.setOnClickListener {
            val content = binding.contentTextField.editText!!.text.toString()
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
    }
}