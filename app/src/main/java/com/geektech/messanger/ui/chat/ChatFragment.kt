package com.geektech.messanger.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geektech.messanger.databinding.FragmentChatBinding
import com.geektech.messanger.model.User
import com.geektech.messanger.ui.chat.adapter.ChatAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot


class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val userDataFields = arrayOf("uid", "phone", "username", "userSecondName")
    private var userList = arrayListOf<User>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
    }

    private fun loadData() {
        FirebaseFirestore.getInstance().collection("Users")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("aga", "Listen failed.", e)
                    return@addSnapshotListener
                }
                for (doc in value!!) {
                    userList.add(getUserData(doc))
                    binding.rvChat.adapter = ChatAdapter(userList)
                }
            }
    }

    private fun getUserData(doc: QueryDocumentSnapshot): User {
        val userData = hashMapOf<String, String>()
        for (field in userDataFields) {
            doc.getString(field)?.let {
                userData[field] = it
            }
        }
        return User(
            userData["uid"],
            userData["phone"],
            userData["username"],
            userData["userSecondName"]
        )
    }
}