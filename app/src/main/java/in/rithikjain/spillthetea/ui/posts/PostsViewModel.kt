package `in`.rithikjain.spillthetea.ui.posts

import `in`.rithikjain.spillthetea.data.repository.AppRepository
import `in`.rithikjain.spillthetea.data.repository.DataStoreRepository
import `in`.rithikjain.spillthetea.utils.Constants
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: AppRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    fun getName() = dataStoreRepository.getString(Constants.PREF_NAME_KEY)
    fun getUsername() = dataStoreRepository.getString(Constants.PREF_USERNAME_KEY)
    fun getProfilePhotoPath() = dataStoreRepository.getString(Constants.PREF_IMAGE_KEY)

    fun getPostsByHashtag(hashtag: String) = repository.getPostsByHashtag(hashtag)
    fun getPostsByPerson(person: String) = repository.getPostsByPerson(person)
}