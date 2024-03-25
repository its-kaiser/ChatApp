package com.example.chatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.databinding.ItemReceiverMessageBinding
import com.example.chatapp.databinding.ItemSenderMessageBinding
import com.example.chatapp.models.Chat
import com.example.chatapp.utils.Constants.ITEM_RECEIVER
import com.example.chatapp.utils.Constants.ITEM_SENDER

class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class SenderMessageItemViewHolder(private val binding: ItemSenderMessageBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(message:Chat){
                binding.apply {
                    name.text = "You"
                    msg.text = message.text
                }
            }
        }

    inner class ReceiverMessageItemViewHolder(private val binding: ItemReceiverMessageBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(message:Chat){
                binding.apply {
                    name.text = message.userName
                    msg.text = message.text
                }
            }
        }

    private val diffCallback = object : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitChat(chat: List<Chat>) {
        differ.submitList(chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType== ITEM_SENDER){
            val binding = ItemSenderMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            SenderMessageItemViewHolder(binding)
        }else{
            val binding = ItemReceiverMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            ReceiverMessageItemViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = differ.currentList[position]
        if(message.isSender){
            (holder as SenderMessageItemViewHolder).bind(message)
        }else{
            (holder as ReceiverMessageItemViewHolder).bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chat = differ.currentList[position]
        return if (chat.isSender) ITEM_SENDER else ITEM_RECEIVER
    }
}