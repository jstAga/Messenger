package com.geektech.messanger.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.geektech.messanger.model.Message
import android.view.ViewGroup
import com.geektech.messanger.databinding.FragmentChatBinding
import com.geektech.messanger.utils.Keys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ChatFragment : Fragment() {
    private lateinit var ref: CollectionReference
    private lateinit var binding: FragmentChatBinding
    private lateinit var uid: String
    private var receiverUid = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        receiverUid = arguments?.getString(Keys.RECEIVER_UID).toString()
        ref = FirebaseFirestore.getInstance().collection("Messages")

        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        ref.addSnapshotListener { value, error ->
            if (value != null) {
                for (item in value.documents) {
                    if (item.data?.get("receiverUid").toString() == receiverUid &&
                        item.data?.get("senderUid").toString() == uid
                    ) {
                        val message = Message(
                            item.data?.get("senderUid").toString(),
                            item.data?.get("receiverUid").toString(),
                            item.data?.get("message").toString(),
                            item.data?.get("time") as Long
                        )
                        Log.e("aga",message.message)
                    }
                }
            }
        }
    }

    private fun sendMessage() {
        val data = Message(uid, receiverUid, binding.etMessage.text.toString())

        ref.document().set(data).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.e("aga", "successful")
            } else {
                Log.e("aga", "error")
            }
            clearText()
        }
    }

    private fun clearText() {
        binding.etMessage.setText("")
    }

}