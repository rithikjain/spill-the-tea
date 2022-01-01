package `in`.rithikjain.spillthetea.ui.feed

import `in`.rithikjain.spillthetea.data.repository.PostRepository
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

}