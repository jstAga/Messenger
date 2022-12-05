package com.geektech.messanger.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.geektech.messanger.R
import com.geektech.messanger.databinding.FragmentAuthBinding
import com.geektech.messanger.utils.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private val auth = FirebaseAuth.getInstance()
    private var phoneNumber = ""
    private var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inAuth.ccpCountry.setOnCountryChangeListener {    //set country code into etPhoneNum
            binding.inAuth.etPhoneNumber.setText("+" + binding.inAuth.ccpCountry.selectedCountryCode)
        }

        binding.inAuth.civSendSms.setOnClickListener {
            sendCode()
        }

        binding.inAccept.civCheckCode.setOnClickListener {
            checkCode()
        }

        binding.inUsername.civSave.setOnClickListener {
            saveUserData()
        }
    }

    private fun checkCode() {
        val credential =
            PhoneAuthProvider.getCredential(userId, binding.inAccept.smsCodeView.enteredCode)
        signInWithPhoneAuthCredential(credential)
    }

    private fun sendCode() {
        initCallbacks()
        phoneNumber = binding.inAuth.etPhoneNumber.text.toString()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    binding.acceptContainer.visibility = View.GONE
                    binding.usernameContainer.visibility = View.VISIBLE
                } else {
                    showToast("Auth error")
                }
            }
    }


    private fun saveUserData() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val ref = FirebaseFirestore.getInstance().collection("Users").document(uid)

            val userData = hashMapOf<String, String>()
            userData["uid"] = uid
            userData["phone"] = phoneNumber

            if (binding.inUsername.etFirstName.text.toString().isNotEmpty()
            ) {
                userData["username"] = binding.inUsername.etFirstName.text.toString()
            } else {
                binding.inUsername.etFirstName.error = "Enter your name"
            }

            if (binding.inUsername.etSecondName.text.toString().isNotEmpty()) {
                userData["userSecondName"] = binding.inUsername.etSecondName.text.toString()
            }

            ref.set(userData).addOnCompleteListener {
                if (it.isSuccessful && binding.inUsername.etFirstName.text.isNotEmpty()) {
                    findNavController().navigate(R.id.chatFragment)
                } else {
                    it.exception?.message?.let { it1 -> showToast(it1) }
                }
            }
        }
    }


    private fun initCallbacks() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                showToast(credential.toString())
            }

            override fun onVerificationFailed(e: FirebaseException) {
                showToast(e.message.toString())
                if (e is FirebaseAuthInvalidCredentialsException) {
                    showToast(e.message.toString())
                } else if (e is FirebaseTooManyRequestsException) {
                    showToast(e.message.toString())
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                userId = verificationId

                binding.authContainer.visibility = View.GONE
                binding.acceptContainer.visibility = View.VISIBLE

                binding.inAccept.tvAcceptDesc.text = getString(R.string.accept_desc) + phoneNumber
            }
        }
    }
    }


