package com.example.chatapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.R
import com.example.chatapp.adapter.MessageAdapter
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.models.Chat
import com.example.chatapp.network.SocketManager

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding: FragmentChatBinding
    private lateinit var socketManager: SocketManager
    private lateinit var messageAdapter: MessageAdapter

    private var userName = ""
    private val chatList = mutableListOf<Chat>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentChatBinding.inflate(inflater)
        socketManager = SocketManager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        userName = ChatFragmentArgs.fromBundle(requireArguments()).userName
        messageAdapter = MessageAdapter()

        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messageAdapter
        }
        binding.btnSend.setOnClickListener {
            val message = binding.etMsg.text.toString()
            if(message.isNotEmpty()){
                val chat = Chat(
                    userName = userName,
                    text = message
                )
                socketManager.emitChat(chat)
                binding.etMsg.setText("")
            }
            else{
                Toast.makeText(requireContext(),"Write something",Toast.LENGTH_SHORT).show()
            }
        }

        socketManager.onNewChat.observe(viewLifecycleOwner) {
            val chat = it.copy(isSender = it.userName==userName)
            chatList.add(chat)
            messageAdapter.submitChat(chatList)
            binding.rvChat.scrollToPosition(chatList.size-1)
        }
    }

    override fun onDestroy() {
        socketManager.disconnectSocket()
        super.onDestroy()
    }
}