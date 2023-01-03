package com.example.maplocation

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.maplocation.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    var currentmarker:Marker?=null
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(22.479444899999997, 88.359021)
        drawMarker(sydney)


        mMap.setOnMarkerDragListener(object :GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if (currentmarker!=null)
                currentmarker?.remove()
                val newLatLong=LatLng(p0.position.latitude,p0.position.longitude)
                drawMarker(newLatLong)

            }

            override fun onMarkerDragStart(p0: Marker) {

            }

        })
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun drawMarker(latLng: LatLng){
      val markerOption=  MarkerOptions().position(latLng).title("Marker in Sydney").draggable(true).snippet(getLocation(latLng.latitude,latLng.longitude))
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
       currentmarker=mMap.addMarker(markerOption)
        currentmarker?.showInfoWindow()
    }
    private fun getLocation(lat:Double,long:Double):String{
        val geoCoder=Geocoder(this, Locale.getDefault())
        val address=geoCoder.getFromLocation(lat,long,1)
        return address[0].getAddressLine(0).toString()
    }
}