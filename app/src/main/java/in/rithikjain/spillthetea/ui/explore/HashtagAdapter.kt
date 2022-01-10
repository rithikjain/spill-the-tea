package `in`.rithikjain.spillthetea.ui.explore

import `in`.rithikjain.spillthetea.R
import `in`.rithikjain.spillthetea.data.local.entity.Hashtag
import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.databinding.FragmentExploreBinding
import `in`.rithikjain.spillthetea.databinding.HashtagItemBinding
import `in`.rithikjain.spillthetea.databinding.PostItemBinding
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class HashtagAdapter(private val listener:OnItemClickListener) : ListAdapter<Hashtag, HashtagAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: HashtagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val hashtag = getItem(position)
                        listener.onHashtagClick(hashtag)
                    }
                }
            }
        }

        fun bind(hashtag: Hashtag) {
            binding.apply {
                tvHashtagName.text = "#${hashtag.hashtagName}"
                //tvHashtagName.setTextColor(ContextCompat.getColor(binding.root.context,R.color.blue))

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HashtagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hashtag = getItem(position)
        holder.bind(hashtag)
    }

    class DiffCallback : DiffUtil.ItemCallback<Hashtag>() {
        override fun areItemsTheSame(oldItem: Hashtag, newItem: Hashtag) =
            oldItem.hashtagName == newItem.hashtagName

        override fun areContentsTheSame(oldItem: Hashtag, newItem: Hashtag) =
            oldItem.hashtagName == newItem.hashtagName
    }

    interface OnItemClickListener {
        fun onHashtagClick(hashtag: Hashtag)

    }
}