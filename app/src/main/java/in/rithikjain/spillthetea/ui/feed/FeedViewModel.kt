package `in`.rithikjain.spillthetea.ui.feed

import `in`.rithikjain.spillthetea.data.repository.DataStoreRepository
import `in`.rithikjain.spillthetea.data.repository.AppRepository
import `in`.rithikjain.spillthetea.utils.Constants
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: AppRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val posts = repository.getPosts().asLiveData()

    fun getName() = dataStoreRepository.getString(Constants.PREF_NAME_KEY)
    fun getUsername() = dataStoreRepository.getString(Constants.PREF_USERNAME_KEY)
    fun getProfilePhotoPath() = dataStoreRepository.getString(Constants.PREF_IMAGE_KEY)
}