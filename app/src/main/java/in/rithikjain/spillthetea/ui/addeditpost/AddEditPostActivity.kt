package `in`.rithikjain.spillthetea.ui.addeditpost

import `in`.rithikjain.spillthetea.databinding.ActivityAddEditPostBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

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
            finish()
        }

        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}