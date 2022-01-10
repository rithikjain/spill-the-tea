package `in`.rithikjain.spillthetea.ui.profile

import `in`.rithikjain.spillthetea.databinding.FragmentProfileBinding
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            viewModel.setName(binding.nameTextInput.editText!!.text.toString())
            viewModel.setUsername(binding.usernameTextInput.editText!!.text.toString())
            Snackbar.make(view,"Saved",Snackbar.LENGTH_SHORT).show()
        }

        val startForProfileImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val fileUri = data?.data!!

                        viewModel.setProfilePhotoPath(fileUri.toString())
                    }
                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            ImagePicker.getError(data),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    else -> {
                        Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        binding.profileImageView.setOnClickListener {
            ImagePicker.with(this)
                .crop(1f, 1f)
                .createIntent {
                    startForProfileImageResult.launch(it)
                }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getName().collect {
                    binding.nameTextInput.editText!!.setText(it ?: "")
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUsername().collect {
                    binding.usernameTextInput.editText!!.setText(it ?: "")
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getProfilePhotoPath().collect {
                    if (it.isNullOrEmpty()) {
                        binding.profileImageView.setImageURI(null)
                    } else {
                        binding.profileImageView.setImageURI(Uri.parse(it))
                    }
                }
            }
        }
    }
}