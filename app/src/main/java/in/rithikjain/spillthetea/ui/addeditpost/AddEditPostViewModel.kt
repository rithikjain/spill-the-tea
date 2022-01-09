package `in`.rithikjain.spillthetea.ui.addeditpost

import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.data.repository.DataStoreRepository
import `in`.rithikjain.spillthetea.data.repository.PostRepository
import `in`.rithikjain.spillthetea.utils.Constants
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditPostViewModel @Inject constructor(
    private val repository: PostRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val addEditPostEventChannel = Channel<AddEditPostEvent>()
    val addEditPostEvent = addEditPostEventChannel.receiveAsFlow()

    fun insertPost(content: String) {
        if (content.trim().isNotEmpty()) {
            viewModelScope.launch {
                repository.insertPost(
                    Post(content.trim(), Date())
                )
                addEditPostEventChannel.send(AddEditPostEvent.PostInsertionSuccess)
            }
        } else {
            viewModelScope.launch {
                addEditPostEventChannel.send(AddEditPostEvent.ShowErrorMessage("Content can't be empty '_'"))
            }
        }
    }

    fun updatePost(post: Post, content: String) {
        if (content.trim().isNotEmpty()) {
            val updatedPost = post.copy(content = content)
            viewModelScope.launch {
                repository.insertPost(updatedPost)
                addEditPostEventChannel.send(AddEditPostEvent.PostInsertionSuccess)
            }
        } else {
            viewModelScope.launch {
                addEditPostEventChannel.send(AddEditPostEvent.ShowErrorMessage("Content can't be empty '_'"))
            }
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
        }
    }

    fun getProfilePhotoPath(): Flow<String?> {
        return dataStoreRepository.getString(Constants.PREF_IMAGE_KEY)
    }

    sealed class AddEditPostEvent {
        data class ShowErrorMessage(val message: String) : AddEditPostEvent()
        object PostInsertionSuccess : AddEditPostEvent()
    }
}