package `in`.rithikjain.spillthetea.ui.addeditpost

import `in`.rithikjain.spillthetea.data.local.entity.*
import `in`.rithikjain.spillthetea.data.repository.DataStoreRepository
import `in`.rithikjain.spillthetea.data.repository.AppRepository
import `in`.rithikjain.spillthetea.utils.Constants
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AddEditPostViewModel @Inject constructor(
    private val repository: AppRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val addEditPostEventChannel = Channel<AddEditPostEvent>()
    val addEditPostEvent = addEditPostEventChannel.receiveAsFlow()

    private fun getAllWordsByChar(content: String, char: String): List<String> {
        val regexPattern = "($char\\w+)"
        val p = Pattern.compile(regexPattern)
        val m = p.matcher(content)

        val collections = mutableListOf<String>()

        while (m.find()) {
            if (m.group(1) != null) {
                val token = m.group(1) ?: ""
                collections.add(token.substring(1))
            }
        }

        return collections
    }

    fun insertPost(content: String) {
        if (content.trim().isNotEmpty()) {
            val hashtags = getAllWordsByChar(content.trim(), "#")
            val people = getAllWordsByChar(content.trim(), "@")

            viewModelScope.launch {
                val postId = repository.insertPost(
                    Post(content.trim(), Date())
                )

                for (hashtag in hashtags) {
                    repository.insertHashtag(Hashtag(hashtag))
                    repository.insertHashtagPost(PostHashtagCrossRef(postId, hashtag))
                }

                for (person in people) {
                    repository.insertPerson(Person(person))
                    repository.insertPersonPost(PostPersonCrossRef(postId, person))
                }

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