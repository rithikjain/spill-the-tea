package `in`.rithikjain.spillthetea.ui.addeditpost

import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.data.repository.PostRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditPostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    fun insertPost(content: String) {
        if (content.trim().isNotEmpty()) {
            viewModelScope.launch {
                repository.insertPost(
                    Post(content.trim(), Date())
                )
            }
        }
    }
}