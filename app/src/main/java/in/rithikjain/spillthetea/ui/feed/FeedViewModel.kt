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

    val posts = repository.getPosts()

    fun getName() = dataStoreRepository.getString(Constants.PREF_NAME_KEY)
    fun getUsername() = dataStoreRepository.getString(Constants.PREF_USERNAME_KEY)
    fun getProfilePhotoPath() = dataStoreRepository.getString(Constants.PREF_IMAGE_KEY)

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
        }
    }
}