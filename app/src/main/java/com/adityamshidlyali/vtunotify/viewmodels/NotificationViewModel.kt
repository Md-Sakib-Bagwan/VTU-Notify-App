package com.adityamshidlyali.vtunotify.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.adityamshidlyali.vtunotify.model.NotificationItem
import com.adityamshidlyali.vtunotify.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(
    application: Application,
    private val repository: NotificationRepository
) : AndroidViewModel(application) {

    fun getNotifications(VTU_URL: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNotifications(VTU_URL!!)
        }
    }

    val notifications: LiveData<List<NotificationItem>>
        get() = repository.notifications
}