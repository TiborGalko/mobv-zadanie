package com.example.mobv_zadanie.ui

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.databinding.ActivityMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class MainActivity : AppCompatActivity() {

    private var alert: AlertDialog ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        Log.i("TAG_API", "main activity called")

        val locationManager: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }
    }

    // credits https://stackoverflow.com/questions/843675/how-do-i-find-out-if-the-gps-of-an-android-device-is-enabled
    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?" +
                " Android location services must be turned on to get right SSID.")
            .setCancelable(false)
            .setPositiveButton("Yes") {
                    _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
            .setNegativeButton("No") {
                    dialog, _ -> dialog.cancel()
            }
        alert = builder.create()
        alert?.show();
    }

    // Has to check if google play api is available because of Firebase
    override fun onResume() {
        super.onResume()

        val result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(applicationContext)
        if(result == ConnectionResult.SUCCESS) {
            Log.i("TAG_API", "Google play api is available. No problems.")
        } else if (result == ConnectionResult.SERVICE_MISSING
            || result == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
            || result == ConnectionResult.SERVICE_DISABLED) {

            Log.e("TAG_API", "Google play api error.")
            GoogleApiAvailability.getInstance().getErrorDialog(this, result, 0).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (alert != null) {
            alert?.dismiss()
        }
    }
}
