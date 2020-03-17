package com.coronago

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.coronago.utils.REQUEST_CODE_REQUEST_LOCATION_PERMISSION
import com.coronago.utils.hasLocationPermission
import com.coronago.utils.requestLocationPermission
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        requestPermissionButton.setOnClickListener {
            if(hasLocationPermission()) {
                complete()
            } else {
                requestLocationPermission()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_REQUEST_LOCATION_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    complete()
                } else {
                    Toast.makeText(this, R.string.permissionAlertDenied, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun complete() {
        HomeActivity.start(this)
        finish()
    }

    companion object {

        fun start(context: Context) {
            Intent(context, PermissionActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}
