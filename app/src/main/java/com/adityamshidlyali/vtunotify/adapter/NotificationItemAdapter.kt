package com.adityamshidlyali.vtunotify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityamshidlyali.vtunotify.databinding.NotificationItemBinding
import com.adityamshidlyali.vtunotify.model.NotificationItem

class NotificationItemAdapter(
    private val notifications: List<NotificationItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<NotificationItemAdapter.NotificationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        return NotificationItemViewHolder(
            NotificationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {

        val date: String = notifications[position].date
        val month: String = notifications[position].monthYear.split(" ")[0]
        val year: String = notifications[position].monthYear.split(" ")[1]

        holder.binding.notificationDate.text = "$date $month\n$year"

        holder.binding.notificationText.text = notifications[position].text
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    inner class NotificationItemViewHolder(val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.cardViewNotificationItem.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            listener.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int);
    }
}