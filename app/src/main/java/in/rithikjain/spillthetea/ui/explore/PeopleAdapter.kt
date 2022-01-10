package `in`.rithikjain.spillthetea.ui.explore

import `in`.rithikjain.spillthetea.data.local.entity.Person
import `in`.rithikjain.spillthetea.data.local.entity.Post
import `in`.rithikjain.spillthetea.databinding.FragmentExploreBinding
import `in`.rithikjain.spillthetea.databinding.HashtagItemBinding
import `in`.rithikjain.spillthetea.databinding.PostItemBinding
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PeopleAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Person, PeopleAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: HashtagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val person = getItem(position)
                        listener.onPersonClick(person)
                    }
                }
            }
        }

        fun bind(person: Person) {
            binding.apply {
                tvHashtagName.text = person.personName

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HashtagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = getItem(position)
        holder.bind(person)
    }

    class DiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) =
            oldItem.personName == newItem.personName

        override fun areContentsTheSame(oldItem: Person, newItem: Person) =
            oldItem.personName == newItem.personName
    }

    interface OnItemClickListener {
        fun onPersonClick(person: Person)

    }
}