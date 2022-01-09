package `in`.rithikjain.spillthetea.ui.feed

import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.databinding.FragmentFeedBinding
import `in`.rithikjain.spillthetea.ui.addeditpost.AddEditPostActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.Serializable

@AndroidEntryPoint
class FeedFragment : Fragment(), FeedAdapter.OnItemClickListener {

    private val viewModel: FeedViewModel by viewModels()

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val feedAdapter = FeedAdapter(this)

        binding.postsRecyclerView.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        binding.addFab.setOnClickListener {
            val intent = Intent(requireContext(), AddEditPostActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.posts.collect {
                    if (it.isNotEmpty()) {
                        binding.noTeaMessageLayout.visibility = View.GONE
                        binding.postsRecyclerView.visibility = View.VISIBLE
                    } else {
                        binding.noTeaMessageLayout.visibility = View.VISIBLE
                        binding.postsRecyclerView.visibility = View.GONE
                    }
                    feedAdapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getName().collect {
                    feedAdapter.setName(it ?: "")
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUsername().collect {
                    feedAdapter.setUsername(it ?: "")
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getProfilePhotoPath().collect {
                    feedAdapter.setProfilePhotoPath(it)
                }
            }
        }
    }

    override fun onItemClick(post: Post) {
        val intent = Intent(requireContext(), AddEditPostActivity::class.java)
        intent.putExtra("post", post as Serializable)
        startActivity(intent)
    }

    override fun onHashtagClick(hashtag: String, post: Post) {
        Log.d("esh", hashtag)
    }

    override fun onPersonClick(person: String, post: Post) {
        Log.d("esh", person)
    }
}