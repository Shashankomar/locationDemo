package com.locationdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

private const val PERMISSION_REQUEST_LOCATION: Int = 1

@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setClickListener()
    }

    private fun setClickListener() {
        btn_get_location.setOnClickListener {
            requestLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            //start location Access
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start Location preview Activity.
                Snackbar.make(
                    cl, R.string.location_permission_granted,
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            } else if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Snackbar.make(
                    cl, R.string.location_permission_denied,
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            } else {
                Snackbar.make(
                    cl, R.string.location_access_required,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(R.string.ok) {
                    // Request the permission
                    requestLocation()
                }.show()
            }
        }
    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //do location preview
            Toast.makeText(this, R.string.location_permission_available, Toast.LENGTH_SHORT).show()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            Snackbar.make(
                cl, R.string.location_access_required,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.ok) { // Request the permission
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_LOCATION
                )
            }.show()
        } else {
            Toast.makeText(this, R.string.location_permission_not_available, Toast.LENGTH_SHORT)
                .show()

            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_LOCATION
            )
        }
    }
}