package com.example.android_project_onwe.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.android_project_onwe.data.NotificationRepository

class NotificationViewModel(context: Context) : ViewModel() {
    private val repository = NotificationRepository(context)

    fun triggerNotification(title: String, message: String) {
        repository.sendNotification(title, message)
    }
}