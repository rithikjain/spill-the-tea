package `in`.rithikjain.spillthetea.ui.feed

import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.databinding.PostItemBinding
import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FeedAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Post, FeedAdapter.ViewHolder>(DiffCallback()) {

    private var name: String = ""
    private var username: String = ""
    private var profilePhotoPath: String? = null

    fun setName(name: String) {
        this.name = name
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setProfilePhotoPath(profilePhotoPath: String?) {
        this.profilePhotoPath = profilePhotoPath
    }

    inner class ViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                contentTextView.setOnHashtagClickListener { _, text ->
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val post = getItem(position)
                        listener.onHashtagClick(text.toString(), post)
                    }
                }
                contentTextView.setOnMentionClickListener { _, text ->
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val post = getItem(position)
                        listener.onPersonClick(text.toString(), post)
                    }
                }
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val post = getItem(position)
                        listener.onItemClick(post)
                    }
                }
            }
        }

        var dateFormat: DateFormat = SimpleDateFormat("HH:mm • dd MMM yy", Locale.ENGLISH)

        @SuppressLint("SetTextI18n")
        fun bind(post: Post) {
            binding.apply {
                contentTextView.text = post.content
                dateTimeTextView.text = dateFormat.format(post.timestamp)
                nameTextView.text = name
                usernameTextView.text = "@$username"
                profilePhotoImageView.setImageURI(
                    if (profilePhotoPath.isNullOrEmpty()) null else Uri.parse(profilePhotoPath)
                )
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PostItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        viewHolder.bind(currentItem)
    }

    interface OnItemClickListener {
        fun onItemClick(post: Post)
        fun onHashtagClick(hashtag: String, post: Post)
        fun onPersonClick(person: String, post: Post)
    }

    class DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}