package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(private val emails: List<Email>) :
    RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emails[position]
        holder.bind(email)
    }

    override fun getItemCount(): Int = emails.size

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarTextView: TextView = itemView.findViewById(R.id.tvAvatar)
        private val senderNameTextView: TextView = itemView.findViewById(R.id.tvSenderName)
        private val subjectTextView: TextView = itemView.findViewById(R.id.tvSubject)
        private val previewTextView: TextView = itemView.findViewById(R.id.tvPreview)
        private val timeTextView: TextView = itemView.findViewById(R.id.tvTime)

        fun bind(email: Email) {
            avatarTextView.text = email.senderName[0].toString() // First letter of sender name
            senderNameTextView.text = email.senderName
            subjectTextView.text = email.subject
            previewTextView.text = email.preview
            timeTextView.text = email.time
        }
    }
}
