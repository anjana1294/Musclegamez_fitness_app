package com.musclegamez.fitness_app.ui.home

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStates
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.util.AlertUtil
import com.musclegamez.fitness_app.util.Constants
import com.musclegamez.fitness_app.util.Constants.Companion.LOCATION_SETTINGS_REQUEST
import com.musclegamez.fitness_app.util.RxUtils
import com.skyfishjy.library.RippleBackground
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import timber.log.Timber
import java.util.*

class HomeFragment : Fragment(), OnMapReadyCallback, OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    lateinit var locationUpdatesObservable: Observable<Location>
    var locationProvider = ReactiveLocationProvider(activity)
    lateinit var location: Location
    lateinit var marker: Marker
    lateinit var rippleBackground: RippleBackground
    lateinit var foundPeople: ImageView
    private var mDelayHandler: Handler? = null
    private val FoundDelay: Long = 3000

    private var disposable: Disposable? = null
    internal var fields: List<Place.Field> =
            Arrays.asList<Place.Field>(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.ADDRESS,
                    Place.Field.LAT_LNG
            )
    internal val mRunnable: Runnable = Runnable {
        foundPeople()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        rippleBackground = root.findViewById(R.id.content)
        foundPeople = root.findViewById(R.id.centerImage)

        setLocationObserverable()
        val mapFragment = childFragmentManager.findFragmentById((R.id.map)) as SupportMapFragment

        //  val mapFragment =supportFragmentManager
        // .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (!Places.isInitialized()) {
            activity?.let {
                Places.initialize(
                        it,
                        //  "AIzaSyAuj0rU6naaI2xPRwbFYr3_ZP_fkbpVjy0" LIve key of muscle

                        "AIzaSyCFxCbNrR6FqKcrYleDd5qE3slEpAnbf_c"
                )
            } // Create a new Places client instance. PlacesClient placesClient = Places.createClient(this);
        }
        foundPeople.setOnClickListener(clickListener)
        return root
    }


    val clickListener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.centerImage -> {
                rippleBackground.startRippleAnimation()
                mDelayHandler = Handler()
                mDelayHandler!!.postDelayed(mRunnable, FoundDelay)
            }
        }
    }

    private fun foundPeople() {
        val animatorSet = AnimatorSet()
        animatorSet.setDuration(400)
        animatorSet.setInterpolator(AccelerateDecelerateInterpolator())
        val animatorList: ArrayList<Animator> = ArrayList<Animator>()
        val scaleXAnimator: ObjectAnimator = ObjectAnimator.ofFloat(foundPeople, "ScaleX", 0f, 1.2f, 1f)
        animatorList.add(scaleXAnimator)
        val scaleYAnimator: ObjectAnimator = ObjectAnimator.ofFloat(foundPeople, "ScaleY", 0f, 1.2f, 1f)
        animatorList.add(scaleYAnimator)
        animatorSet.playTogether(animatorList)
        foundPeople.setVisibility(View.VISIBLE)
        animatorSet.start()
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        //mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.setOnMarkerClickListener(this)
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            activity,
                            R.raw.map_style
                    )
            )

            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
        //requestPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION)

    }


    @SuppressLint("MissingPermission")
    fun setLocationObserverable() {
        val locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5).setInterval(100)

        val locationSettings = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true).build()

        locationUpdatesObservable = locationProvider
                .checkLocationSettings(locationSettings)
                .doOnNext({ locationSettingsResult ->
                    val status = locationSettingsResult.getStatus()
                    if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            status.startResolutionForResult(activity, Constants.LOCATION_SETTINGS_REQUEST)
                        } catch (e: IntentSender.SendIntentException) {
                            Timber.e(e)
                            showGPSWarningAlert()
                        }
                    }
                })
                .flatMap({ locationSettingsResult -> locationProvider.getUpdatedLocation(locationRequest) })
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
    }

    @SuppressLint("MissingPermission")
    fun requestLocation() {
        if (disposable != null) {
            RxUtils.dispose(disposable)
        }
        disposable = locationUpdatesObservable.subscribe { newLocation ->
            getCurrentLocation(newLocation)
            Log.d("Found location: %s", newLocation.latitude.toString())
            RxUtils.dispose(disposable)
        };
    }


    fun showGPSWarningAlert() {
        activity?.let {
            AlertUtil.showActionNotCancelableAlertDialog(it,
                    "Location error", "Sorry device location couldn't be accessed, you can't proceed further!",
                    "Retry", DialogInterface.OnClickListener { dialog, which ->
                requestPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION)

            })
        }
    }

    fun requestPermissions(permission: String) {
        when (permission) {
            android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                activity?.let {
                    RxPermissions(it)
                            .request(android.Manifest.permission.ACCESS_FINE_LOCATION)
                            .subscribe { granted ->
                                if (granted!!) {
                                    if (locationUpdatesObservable != null)
                                        requestLocation()
                                } else {
                                    showGPSWarningAlert()
                                }
                            }
                }
            }
        }
    }

    fun getCurrentLocation(location: Location) {
        Log.d(" location: %s", location.latitude.toString())
        this.location = location
        if (mMap != null) {
            marker = mMap.addMarker(MarkerOptions().position(LatLng(location.latitude, location.longitude)).flat(true))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 15.0f))
            mMap.getUiSettings().setZoomControlsEnabled(false)
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            when (requestCode) {
                LOCATION_SETTINGS_REQUEST ->
                    when (resultCode) {
                        RESULT_OK -> {
                            Timber.d("User enabled location")
                            val states = LocationSettingsStates.fromIntent(data)
                            if (states.isGpsPresent && states.isGpsUsable) {
                                requestLocation()
                            } else {
                                showGPSWarningAlert()
                            }
                        }
                        RESULT_CANCELED -> {
                            Timber.d("User cancelled enabling location.")
                            showGPSWarningAlert()
                        }
                    }
            }
        }


    }

    override fun onMarkerClick(p0: Marker?) = false

    //    private fun setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = (getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment).getMap()
//            mMap.isMyLocationEnabled = true
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                mMap.setOnMyLocationChangeListener { arg0 -> mMap.addMarker(MarkerOptions().position(LatLng(arg0.latitude, arg0.longitude)).title("It's Me!")) }
//            }
//        }
//    }
    fun showDialog() {
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_send_request)
        val requestBtn = dialog?.findViewById(R.id.btn_send_request) as Button
        requestBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}
