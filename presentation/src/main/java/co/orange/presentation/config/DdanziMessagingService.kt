package co.orange.presentation.config

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import co.orange.presentation.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.presentation.R
import java.util.Random

@AndroidEntryPoint
class DdanziMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun handleIntent(intent: Intent?) {
        intent?.let {
            if (intent.getStringExtra(MSG_TITLE).isNullOrEmpty()) return
            sendNotification(
                mapOf(
                    MSG_TITLE to it.getStringExtra(MSG_TITLE).toString(),
                    MSG_BODY to it.getStringExtra(MSG_BODY).toString(),
                ),
            )
        }
    }

    private fun sendNotification(messageBody: Map<String, String>) {
        val notifyId = Random().nextInt()
        val intent = Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(
                this,
                notifyId,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE,
            )
        val channelId = getString(R.string.default_notification_channel_id)

        val notificationBuilder =
            NotificationCompat.Builder(this, channelId).apply {
                setSmallIcon(R.mipmap.ic_launcher)
                setContentTitle(messageBody[MSG_TITLE])
                setContentText(messageBody[MSG_BODY])
                setAutoCancel(true)
                setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                setContentIntent(pendingIntent)
            }

        getSystemService<NotificationManager>()?.run {
            createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_HIGH,
                ),
            )
            notify(notifyId, notificationBuilder.build())
        }
    }

    companion object {
        private const val MSG_TITLE = "MSG_TITLE"
        private const val MSG_BODY = "MSG_BODY"
    }
}
