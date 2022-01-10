package `in`.rithikjain.spillthetea.ui.explore

import `in`.rithikjain.spillthetea.data.repository.AppRepository
import `in`.rithikjain.spillthetea.data.repository.DataStoreRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    val hashtags = repository.getAllHashtags()
    val people = repository.getAllPeople()
}