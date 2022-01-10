package `in`.rithikjain.spillthetea.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.rithikjain.spillthetea.R
import `in`.rithikjain.spillthetea.data.local.entity.Hashtag
import `in`.rithikjain.spillthetea.data.local.entity.Person
import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.databinding.FragmentExploreBinding
import `in`.rithikjain.spillthetea.ui.addeditpost.AddEditPostActivity
import `in`.rithikjain.spillthetea.ui.posts.PostsActivity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import java.io.Serializable

@AndroidEntryPoint
class ExploreFragment : Fragment(), HashtagAdapter.OnItemClickListener,PeopleAdapter.OnItemClickListener {
    private val viewModel: ExploreViewModel by viewModels()

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hashtagAdapter = HashtagAdapter(this)
        val personAdapter=PeopleAdapter(this)
        binding.rvHashtags.apply {
            adapter = hashtagAdapter
            layoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.HORIZONTAL)
        }

        binding.rvPeople.apply {
            adapter = personAdapter
            layoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.HORIZONTAL)
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hashtags.collect {
                    if (it.isNotEmpty()) {
                        hashtagAdapter.submitList(it)
                    }

                }
            }
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.people.collect {
                    if (it.isNotEmpty()) {
                        personAdapter.submitList(it)
                    }

                }
            }
        }
    }

    override fun onHashtagClick(hashtag: Hashtag) {
        Log.d("ExploreFragment","Hashtag clicked")
        val intent = Intent(requireContext(), PostsActivity::class.java)

        intent.putExtra("type", "hashtag")
        intent.putExtra("value", hashtag.hashtagName)
        startActivity(intent)
    }

    override fun onPersonClick(person: Person) {
        val intent = Intent(requireContext(), PostsActivity::class.java)

        intent.putExtra("type", "person")
        intent.putExtra("value", person.personName)
        startActivity(intent)
    }
}