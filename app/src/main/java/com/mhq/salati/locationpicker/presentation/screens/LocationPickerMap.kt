package com.mhq.salati.locationpicker.presentation.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

//NOMINATIM…
@SuppressLint("ClickableViewAccessibility")
@Composable
fun LocationPickerMap(
    initialLat: Double,
    initialLng: Double,
    selectedLatitude: Double?,
    selectedLongitude: Double?,
    initialZoom: Int = 15,
    onPointSelected: (Double, Double) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // CRITICAL: Configure OSMDroid with a valid User-Agent to avoid 403 blocks
    LaunchedEffect(Unit) {
        Configuration.getInstance()
            .load(
                context,
                context.getSharedPreferences(
                    "osmdroid",
                    Context.MODE_PRIVATE
                )
            )

        Configuration.getInstance().userAgentValue = context.packageName
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(initialZoom.toDouble())
                controller.setCenter(GeoPoint(initialLat, initialLng))

                // Add a marker at the initial location
                val marker = Marker(this).apply {
                    position = GeoPoint(initialLat, initialLng)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                overlays.add(marker)

                // Handle taps on the map
                setOnTouchListener { _, event ->
                    if (event.action == android.view.MotionEvent.ACTION_UP) {
                        val projection = projection
                        val geoPoint =
                            projection.fromPixels(event.x.toInt(), event.y.toInt()) as GeoPoint

                        // Update marker position
                        marker.position = geoPoint

                        // Callback to Compose
                        onPointSelected(geoPoint.latitude, geoPoint.longitude)
                    }
                    false // Let the map handle other touch events (panning/zooming)
                }
            }
        },
        update = { mapView ->
            // ✅ FIX 1: Prioritize selected coordinates (from search) over initial coordinates
            val targetLat = selectedLatitude ?: initialLat
            val targetLng = selectedLongitude ?: initialLng
            val targetPoint = GeoPoint(targetLat, targetLng)

            // Animate smoothly to the new location
            mapView.controller.animateTo(targetPoint)

            // ✅ FIX 2: Update the marker to the new location
            val marker = mapView.overlays.find { it is Marker } as? Marker
            marker?.position = targetPoint
        },
        // ✅ FIX 3: Prevent memory leaks when the Composable is removed from the tree
        onRelease = { mapView ->
            mapView.onDetach()
        },
        modifier = modifier
    )
}

//import android.view.GestureDetector
//import android.view.MotionEvent
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleEventObserver
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import org.osmdroid.config.Configuration
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory
//import org.osmdroid.util.GeoPoint
//import org.osmdroid.views.MapView
//import org.osmdroid.views.overlay.Marker
//
//@Composable
//fun LocationPickerMap(
//    selectedLatitude: Double?,
//    selectedLongitude: Double?,
//    onPointSelected: (Double, Double) -> Unit,
//    onMapCreated: (MapView) -> Unit = {},
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    var mapView by remember { mutableStateOf<MapView?>(null) }
//    var marker by remember { mutableStateOf<Marker?>(null) }
//
//    AndroidView(
//        factory = {
//            Configuration.getInstance().userAgentValue = context.packageName
//
//            lateinit var map: MapView
//            val gestureDetector = GestureDetector(
//                context,
//                object : GestureDetector.SimpleOnGestureListener() {
//                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
//                        val projection = map.projection
//                        val geoPoint = projection.fromPixels(e.x.toInt(), e.y.toInt())
//                        onPointSelected(geoPoint.latitude, geoPoint.longitude)
//                        return true
//                    }
//                }
//            )
//
//            map = MapView(context).apply {
//                setTileSource(TileSourceFactory.MAPNIK)
//                setMultiTouchControls(true)
//
//                isHorizontalMapRepetitionEnabled = false
//                isVerticalMapRepetitionEnabled = false
//                minZoomLevel = 3.0
//
//                setScrollableAreaLimitDouble(
//                    org.osmdroid.util.BoundingBox(
//                        85.0,
//                        180.0,
//                        -85.0,
//                        -180.0
//                    )
//                )
//
//                controller.setZoom(5.0)
//                controller.setCenter(GeoPoint(21.4225, 39.8262))
//
//                @Suppress("ClickableViewAccessibility")
//                setOnTouchListener { _, event ->
//                    gestureDetector.onTouchEvent(event)
//                    false
//                }
//            }
//            mapView = map
//            onMapCreated(map)
//            map
//        },
//        update = { map ->
//            val lat = selectedLatitude
//            val lng = selectedLongitude
//
//            if (lat != null && lng != null) {
//                val point = GeoPoint(lat, lng)
//                val existingMarker = marker
//
//                if (existingMarker == null) {
//                    val newMarker = Marker(map).apply { position = point }
//                    map.overlays.add(newMarker)
//                    marker = newMarker
//                    map.controller.animateTo(point)
//                } else if (existingMarker.position != point) {
//                    existingMarker.position = point
//                    map.controller.animateTo(point)
//                }
//                map.invalidate()
//            }
//        },
//        modifier = modifier
//    )
//
//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            when (event) {
//                Lifecycle.Event.ON_RESUME -> mapView?.onResume()
//                Lifecycle.Event.ON_PAUSE -> mapView?.onPause()
//                else -> Unit
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//            mapView?.onDetach()
//        }
//    }
//}