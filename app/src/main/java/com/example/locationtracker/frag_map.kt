package com.example.locationtracker

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class frag_map : Fragment() {

    private lateinit var googleMap: GoogleMap
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private val markers: MutableList<Marker> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_frag_map, container, false)

        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.Map) as? SupportMapFragment
        supportMapFragment?.getMapAsync { map ->
            googleMap = map

            googleMap.setOnMapClickListener { latLng ->
                // Add a marker at the clicked location
                val markerOptions = MarkerOptions().position(latLng)
                // Use safe call operator to handle nullable marker
                val marker: Marker? = googleMap.addMarker(markerOptions)
                marker?.let {
                    markers.add(it)
                }
            }

            // Set a click listener for markers
            googleMap.setOnMarkerClickListener { marker ->
                // Find the clicked marker in the markers list
                val clickedMarker = markers.find { it == marker }
                // Remove the clicked marker from the map and from the markers list
                clickedMarker?.remove()
                markers.remove(clickedMarker)
                // Return true to indicate that the click event is handled
                true
            }
        }

        return view
    }
}
