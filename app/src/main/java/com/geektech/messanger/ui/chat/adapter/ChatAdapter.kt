package com.geektech.messanger.ui.chat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.messanger.databinding.ItemChatBinding
import com.geektech.messanger.model.User

class ChatAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {


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
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class ChatViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: User) {
            binding.tvName.text = userData.userName
            binding.tvPhoneNumber.text = userData.phone
            binding.tvUid.text = userData.uid
            if (userData.userSecondName != null) {
                binding.tvSurname.text = userData.userSecondName
            } else {
                binding.tvSurname.text = ""
            }
        }
    }
}