package com.Rapid.id.Konsumen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.Rapid.id.Model.UserLocation
import com.Rapid.id.R
import com.Rapid.id.util.Constants
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,LocationListener,GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var search: EditText
    lateinit var img_search : ImageView
    lateinit var btn_pilih_lokasi : Button

//    lateinit var imglokasi : ImageView

    private lateinit var locationCallback: LocationCallback
    private var locationUpdateState = false

    private lateinit var locationRequest: LocationRequest

    //tutorial search manual
    internal lateinit var mLastLocation: Location
    internal var mCurrLocationMarker: Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest

    private lateinit var mapResult: UserLocation

    var PICK_UP : Int  = 0
    var DEST_LOC : Int = 1
    var REQUEST_CODE : Int = 0

    companion object {
        private const val PLACE_PICKER_REQUEST = 3
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

   private var mapview: View? = null
   private var selectedMap: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        buildGoogleApiClient()

        createLocationRequest()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapview = mapFragment.view!!

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }


        btn_pilih_lokasi = findViewById(R.id.btnGunakanLokasi)

        btn_pilih_lokasi.setOnClickListener {
            val intent = Intent()
            intent.putExtra(RenovKonsumenActivity.KEY_MAP, mapResult)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        img_search = findViewById(R.id.imgSearch)

        img_search.setOnClickListener {
            lateinit var location: String
            location = search.text.toString()
            var addressList: List<Address>? = null

            if (location == null || location == "") {
                Toast.makeText(applicationContext,"provide location",Toast.LENGTH_SHORT).show()
            }
            else{
                val geoCoder = Geocoder(this)
                try {
                    addressList = geoCoder.getFromLocationName(location, 1)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val address = addressList!![0]
                val latLng = LatLng(address.latitude, address.longitude)
                mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                Toast.makeText(applicationContext, address.latitude.toString() + " " + address.longitude, Toast.LENGTH_LONG).show()
            }
        }

        search = findViewById(R.id.edtSearch)




    }

    override fun onMapReady(googleMap: GoogleMap) {
        var zoomlevel: Float = 16.0f

        mMap = googleMap
        mMap.isMyLocationEnabled = true



        if(mMap != null) {

            if (intent.hasExtra("lat")) {
                val address = intent.getStringExtra("address")
                val latLng = LatLng(
                    intent.getDoubleExtra("lat", 0.0),
                    intent.getDoubleExtra("lon", 0.0)
                )
                selectedMap = mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(address)
                )
            }

            mMap.setOnMapLongClickListener(object : GoogleMap.OnMapLongClickListener {
                override fun onMapLongClick(latLng: LatLng) {

                    var geocoder = Geocoder(this@MapsActivity)
                    var list: List<Address>? = null

                    try {
                        list = geocoder.getFromLocation(
                            latLng.latitude,
                            latLng.longitude,1
                        )

                    } catch (e: IOException) {
                        return
                    }
                    val address = list.get(0)
                    if (mCurrLocationMarker != null) {

                        mCurrLocationMarker!!.remove()

                    }
                    selectedMap?.remove()

                    val options = MarkerOptions()
                        .title(address.getAddressLine(0))
                        .position(
                            LatLng(
                                latLng.latitude,
                                latLng.longitude

                            )
                        )

                    // set data nya buat di kasih ke activity sebelumnya
                    mapResult = UserLocation(
                        mapOf(
                            "lat" to latLng.latitude,
                            "lon" to latLng.longitude
                        ),
                        options.title
                    )

                    mCurrLocationMarker = mMap.addMarker(options)
                    search.setText(options.title)
                }
            })

            mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
                override fun onMarkerClick(marker: Marker): Boolean {
                    return false

                }

            })

            mMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener{
                override fun onMapClick(latLng: LatLng) {
                    mCurrLocationMarker = null

                }

            })
        }


        val locationButton : View = (mapview?.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById<View>(Integer.parseInt("2"))
        val rlp=locationButton.layoutParams as (RelativeLayout.LayoutParams)
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE)
        rlp.setMargins(0,0,30,250)
        mMap.uiSettings.isZoomControlsEnabled = true


        setUpMap()

    }


    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        val markerOptions = MarkerOptions().position(location)
        // 2
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        AutocompleteFilter.Builder().setCountry("ID").build()
        try {
            startActivityForResult(builder.build(this@MapsActivity), PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
            Toast.makeText(this, "Layanan Play Services Tidak Tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startLocationUpdates() {

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Toast.makeText(this, "Membutuhkan Izin Lokasi", Toast.LENGTH_SHORT).show();
            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1
                )
            }
        } else {
            // Permission has already been granted
            Toast.makeText(this, "Izin Lokasi diberikan", Toast.LENGTH_SHORT).show();
        }


        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )

    }

    private fun setRootView(activity: Activity) {
        val rootView =
            (activity.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as? ViewGroup
        if (rootView != null) {
            rootView.fitsSystemWindows = true
            rootView.clipToPadding = true
        }
    }
    private fun setUpMap() {
//        mMap.isMyLocationEnabled = true


        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
//                placeMarkerOnMap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f))
            }
        }


    }

    private fun createLocationRequest() {
        // 1
        locationRequest = LocationRequest()
        // 2
        locationRequest.interval = 1000
        // 3
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        // 4
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        // 5
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        this@MapsActivity,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }

    // 2
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // 3
    public override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }

    }



    private fun showPlaceAutoComplete(typeLocation: Int){

        REQUEST_CODE = typeLocation

        val typeFilter : AutocompleteFilter = AutocompleteFilter.Builder().setCountry("ID").build()

        try {

            val mIntent: Intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                .setFilter(typeFilter)
                .build(this)
            startActivityForResult(mIntent, REQUEST_CODE)
        }catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
            Toast.makeText(this, "Layanan Play Services Tidak Tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient
            .Builder(this)
            .addApi(Places.GEO_DATA_API)
            .addApi(Places.PLACE_DETECTION_API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mGoogleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {

        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this)
        }
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onLocationChanged(location: Location) {

        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        //Place current location marker
        val latLng = LatLng(location.latitude, location.longitude)

        //move map camera
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.getFusedLocationProviderClient(this)
        }

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    fun searchLocation(view: View) {

    }


}
