package `in`.rithikjain.spillthetea.ui.feed

import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.data.repository.DataStoreRepository
import `in`.rithikjain.spillthetea.data.repository.PostRepository
import `in`.rithikjain.spillthetea.utils.Constants
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: PostRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var name: String = ""
    private var username: String = ""

    init {
        viewModelScope.launch {
            name = dataStoreRepository.getString(Constants.PREF_NAME_KEY).first() ?: ""
            username = dataStoreRepository.getString(Constants.PREF_USERNAME_KEY).first() ?: ""
        }
    }

    val posts = repository.getPosts().map {
        it.map { post ->
            post.name = name
            post.username = username
            post
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
        }
    }

}