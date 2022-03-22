package com.example.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UserListAdapter(private val context: Context, private val users: MutableList<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.user_name)
        val email: TextView = itemView.findViewById(R.id.user_email)
        val image: ImageView = itemView.findViewById(R.id.user_image)

        init {
            itemView.setOnLongClickListener {
                val name = users[adapterPosition].firstName + " " + users[adapterPosition].lastName
                val message = "Are you sure you want to delete $name?"
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.alert_dialog_title)
                    .setMessage(message)
                    .setPositiveButton(R.string.alert_dialog_positive_button) { dialog, _ ->
                        users.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        notifyItemRangeChanged(adapterPosition, users.size)
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.alert_dialog_negative_button) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val userListView = layoutInflater.inflate(R.layout.item_user, parent, false)
        return ViewHolder(userListView)
    }

    override fun onBindViewHolder(holder: UserListAdapter.ViewHolder, position: Int) {
        val user = users[position]
        val fullName = user.firstName + " " + user.lastName
        holder.name.text = fullName
        holder.email.text = user.email
        Glide.with(context).load(user.avatar).into(holder.image)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}