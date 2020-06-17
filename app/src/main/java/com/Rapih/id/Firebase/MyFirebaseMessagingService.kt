package com.Rapih.id.Firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.Rapih.id.Konsumen.BottomNav.FragmentNavPesananRenov
import com.Rapih.id.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = String::class.java.simpleName


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(p0)

        Log.d(TAG, "From: ${remoteMessage?.from}")

        // Check if message contains a data payload.
        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            // Compose and show notification
            if (!remoteMessage.data.isNullOrEmpty()) {
                val msg: String = remoteMessage.data.get("message").toString()
                sendNotification(msg)
            }

        }

        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            sendNotification(remoteMessage.notification?.body)
        }
    }

    private fun sendNotification(messageBody: String?) {

        val intent = Intent(this, FragmentNavPesananRenov::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.iconjasaac)
            .setContentTitle(getString(R.string.fcm_message))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        // https://developer.android.com/training/notify-user/build-notification#Priority
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.default_notification_channel_id)
            val descriptionText = getString(R.string.default_notification_channel_id)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {

            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

}

//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//    val name = getString(R.string.default_notification_channel_id)
//    val descriptionText = getString(R.string.default_notification_channel_id)
//    val importance = NotificationManager.IMPORTANCE_DEFAULT
//    val channel = NotificationChannel("default", name, importance).apply {
//        description = descriptionText
//    }
//    // Register the channel with the system
//    val notificationManager: NotificationManager =
//        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager.createNotificationChannel(channel)
//}
//val intent = Intent(this, FragmentNavPesananRenov::class.java)
//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//val channelId = getString(R.string.default_notification_channel_id)
//val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//val notificationBuilder = NotificationCompat.Builder(this, channelId)
//    .setSmallIcon(R.drawable.logorapihbiru)
//    .setContentTitle(getString(R.string.fcm_message))
//    .setContentText(messageBody)
//    .setAutoCancel(true)
//    .setSound(defaultSoundUri)
//    .setContentIntent(pendingIntent)
//val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//// Since android Oreo notification channel is needed.
////        // https://developer.android.com/training/notify-user/build-notification#Priority
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH)
////            notificationManager.createNotificationChannel(channel)
////        }
//notificationManager.notify(0, notificationBuilder.build())
//}
