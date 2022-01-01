package `in`.rithikjain.spillthetea.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.rithikjain.spillthetea.R
import `in`.rithikjain.spillthetea.databinding.FragmentFeedBinding
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FeedFragment : Fragment() {

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

        val feedAdapter = FeedAdapter()

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
            viewModel.insertPost()
        }

        lifecycleScope.launchWhenStarted {
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
}