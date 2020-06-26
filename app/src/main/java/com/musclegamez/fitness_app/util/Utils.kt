package com.musclegamez.fitness_app.util
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun calculateAge(birthdate: Date): Int {
        val birth = Calendar.getInstance()
        birth.setTime(birthdate)
        val today = Calendar.getInstance()
        var yearDifference = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--
        } else {
            if (today.get(Calendar.MONTH) === birth.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
                yearDifference--
            }
        }
        return yearDifference
    }

    fun parseStringToDate(date: String): Date {
        val df = SimpleDateFormat("dd-mm-yyyy")
        return df.parse(date)
    }

    fun millisecondsToDateTime(milliseconds: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        return formatter.format(calendar.getTime())
    }

//    fun isGooglePlayServicesAvailable(activity: Activity, requestCode: Int): Boolean {
//        val apiAvailability = GoogleApiAvailability.getInstance()
//        val resultCode = apiAvailability.isGooglePlayServicesAvailable(activity.application.applicationContext)
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(activity, resultCode, requestCode)
//                    .show()
//            } else {
//                Timber.i("Google play services are not available!")
//                AlertUtil.showToast(
//                    activity.application.applicationContext,
//                    activity.getString(R.string.fcm_not_supported_msg)
//                )
//                activity.finish()
//            }
//            return false
//        }
//        return true
//    }

    fun encodeToBase64(image: Bitmap, returnImage64: (image: String) -> Unit) {
        val immagex = Bitmap.createScaledBitmap(image, 350, 350, true)
        val baos = ByteArrayOutputStream()
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val imageEncoded = Base64.encodeToString(b, Base64.DEFAULT)
        Log.e("LOOK", imageEncoded)
        returnImage64("data:image/png;base64," + imageEncoded.replace(" ", "").replace("\n", "")
        )
    }
}