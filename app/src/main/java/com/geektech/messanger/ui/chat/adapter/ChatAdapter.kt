package com.geektech.messanger.ui.chat.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.messanger.databinding.ItemChatBinding
import com.geektech.messanger.model.Message

class ChatAdapter(private val onClick: (uid: String) -> Unit) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val data = arrayListOf<Message>()

    fun addUsers(newData: ArrayList<Message>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ChatViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: Message) {

        }
    }
}