package com.geektech.messanger.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.messanger.databinding.ItemChatBinding
import com.geektech.messanger.model.User

class UsersAdapter(private val onClick: (uid: String) -> Unit) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private val userList = arrayListOf<User>()

    fun addUsers(newData: ArrayList<User>) {
        userList.clear()
        userList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UsersViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: User) {

            itemView.setOnClickListener {
                onClick(userData.uid.toString())
            }

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