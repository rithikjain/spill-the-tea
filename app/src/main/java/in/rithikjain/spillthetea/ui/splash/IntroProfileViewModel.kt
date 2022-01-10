package `in`.rithikjain.spillthetea.ui.splash

import `in`.rithikjain.spillthetea.data.repository.DataStoreRepository
import `in`.rithikjain.spillthetea.utils.Constants
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    fun getName(): Flow<String?> {
        return dataStoreRepository.getString(Constants.PREF_NAME_KEY)
    }

    fun setName(name: String) {
        viewModelScope.launch {
            dataStoreRepository.putString(Constants.PREF_NAME_KEY, name)
        }
    }

    fun getUsername(): Flow<String?> {
        return dataStoreRepository.getString(Constants.PREF_USERNAME_KEY)
    }

    fun setUsername(username: String) {
        viewModelScope.launch {
            dataStoreRepository.putString(Constants.PREF_USERNAME_KEY, username)
        }
    }

    fun getProfilePhotoPath(): Flow<String?> {
        return dataStoreRepository.getString(Constants.PREF_IMAGE_KEY)
    }

    fun setProfilePhotoPath(path: String) {
        viewModelScope.launch {
            dataStoreRepository.putString(Constants.PREF_IMAGE_KEY, path)
        }
    }
}