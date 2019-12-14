package com.example.mobv_zadanie.data.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobv_zadanie.data.webapi.CallAPI
import com.example.mobv_zadanie.data.webapi.model.UserFidRequest
import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber
import java.net.ConnectException
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MessagingService"

        private val _token = MutableLiveData<String>()

        val token
            get() = _token
    }



    override fun onNewToken(token: String) {
        Timber.d("%s Refreshed token: $token", TAG)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        GlobalScope.launch { _token.postValue(token) }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.d("%s From: %s", TAG, remoteMessage.from!!)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("%s Message data payload: %s", TAG, remoteMessage.data)

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob()
            } else {
                // Handle message within 10 seconds
                //handleNow()
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Timber.d( "%s Message Notification Body: %s", TAG, remoteMessage.notification!!.body!!)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
