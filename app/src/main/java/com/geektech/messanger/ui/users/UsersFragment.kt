package com.geektech.messanger.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.geektech.messanger.R
import com.geektech.messanger.databinding.FragmentUsersBinding
import com.geektech.messanger.model.User
import com.geektech.messanger.ui.users.adapter.UsersAdapter
import com.geektech.messanger.utils.Keys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class UsersFragment : Fragment() {
    private lateinit var binding: FragmentUsersBinding
    private lateinit var adapter: UsersAdapter
    private var userList = arrayListOf<User>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UsersAdapter { uid ->
            findNavController().navigate(R.id.chatFragment, bundleOf(Keys.RECEIVER_UID to uid))
        }
        loadData()
        binding.rvChat.adapter = adapter
    }

    private fun loadData() {
        val ref = FirebaseFirestore.getInstance().collection("Users")

        ref.get().addOnCompleteListener {
            if (it.isSuccessful) {
                userList.clear()
                for (item in it.result.documents) {
                    val user = item.toObject(User::class.java)
                    if (user != null && user.uid != FirebaseAuth.getInstance().currentUser?.uid) {
                        userList.add(user)
                    }
                }
                adapter.addUsers(userList)
            }
        }
    }

}