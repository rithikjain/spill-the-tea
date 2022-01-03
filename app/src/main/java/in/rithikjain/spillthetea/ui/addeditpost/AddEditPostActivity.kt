package `in`.rithikjain.spillthetea.ui.addeditpost

import `in`.rithikjain.spillthetea.databinding.ActivityAddEditPostBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditPostBinding

    private val viewModel: AddEditPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spillButton.setOnClickListener {
            val content = binding.contentTextField.editText!!.text.toString()
            viewModel.insertPost(content)
        }

        binding.closeButton.setOnClickListener {
            finish()
        }

        lifecycleScope.launchWhenStarted {
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
}