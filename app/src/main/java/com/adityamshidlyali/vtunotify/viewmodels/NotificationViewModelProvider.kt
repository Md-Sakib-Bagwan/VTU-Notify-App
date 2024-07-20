package com.adityamshidlyali.vtunotify.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adityamshidlyali.vtunotify.repository.NotificationRepository

class NotificationViewModelProvider(
    private val application: Application,
    private val repository: NotificationRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationViewModel(application, repository) as T
    }
}