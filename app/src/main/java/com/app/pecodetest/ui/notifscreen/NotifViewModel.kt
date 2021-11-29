package com.app.pecodetest.ui.notifscreen

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import com.app.pecodetest.R
import com.app.pecodetest.ui.notifactivity.MainActivity
import com.app.pecodetest.utils.Constants

class NotifViewModel(private val app: Application) : AndroidViewModel(app) {

    var fragmentId: Int = 0
    private var notifManager: NotificationManagerCompat? = null

    private fun buildNotification(): NotificationCompat.Builder {
        createNotificationChannel()

        val notifTitle = app.getString(R.string.notif_title)
        val notifDescription = app.getString(R.string.notif_description, fragmentId)
        val uri = "${app.getString(R.string.base_deep_link)}${fragmentId}".toUri()

        return NotificationCompat.Builder(app, CHANNEL_ID).apply {
            setContentTitle(notifTitle)
            setContentText(notifDescription)
            setSmallIcon(R.drawable.pecode_logo)
            priority = NotificationCompat.PRIORITY_LOW
            setOnlyAlertOnce(true)
            setContentIntent(
                PendingIntent.getActivity(
                    app,
                    REQUEST_CONTENT,
                    Intent(app, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(uri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            setAutoCancel(true)
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                val name = app.getString(R.string.notif_channel_name)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance)
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun createNotification() {
        if (notifManager == null) {
            notifManager = NotificationManagerCompat.from(app)
        }
        notifManager?.notify(
            fragmentId,
            buildNotification().build()
        )
    }

    fun clearNotification() {
        notifManager?.cancel(fragmentId)
    }

    companion object {
        private const val CHANNEL_ID = Constants.NOTIF_CHANNEL_ID
        private const val REQUEST_CONTENT = 1
    }

}