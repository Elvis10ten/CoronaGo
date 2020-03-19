package com.coronago

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_alert.*


private const val KEY_ALERT = "KEY_ALERT"

class AlertActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)
        showAlert()
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    private fun showAlert() {
        val alert = intent.getParcelableExtra<Alert>(KEY_ALERT)!!
        mediaPlayer = MediaPlayer.create(this, alert.soundId).apply { start() }

        alertImage.setImageResource(alert.imageId)
        alertTitle.setText(alert.titleId)
        alertSubtitle.setText(alert.subtitleId)

        alertButton.setText(alert.buttonTextId)
        alertButton.setOnClickListener {
            startActivity(alert.activityIntent)
            finish()
        }
    }

    companion object {

        fun start(context: Context, alert: Alert) {
            Intent(context, AlertActivity::class.java).apply {
                putExtra(KEY_ALERT, alert)
                context.startActivity(this)
            }
        }
    }
}

@Parcelize
data class Alert(
    @DrawableRes
    val imageId: Int,
    @StringRes
    val titleId: Int,
    @StringRes
    val subtitleId: Int,
    @RawRes
    val soundId: Int,
    @StringRes
    val buttonTextId: Int,
    val activityIntent: Intent
): Parcelable