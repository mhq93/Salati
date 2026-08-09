package com.mhq.salati.locationpicker.presentation.screens

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun LocationPickerMap(
    selectedLatitude: Double?,
    selectedLongitude: Double?,
    onPointSelected: (Double, Double) -> Unit,
    onMapCreated: (MapView) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var marker by remember { mutableStateOf<Marker?>(null) }

    AndroidView(
        factory = {
            Configuration.getInstance().userAgentValue = context.packageName

            lateinit var map: MapView
            val gestureDetector = GestureDetector(
                context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                        val projection = map.projection
                        val geoPoint = projection.fromPixels(e.x.toInt(), e.y.toInt())
                        onPointSelected(geoPoint.latitude, geoPoint.longitude)
                        return true
                    }
                }
            )

            map = MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                isHorizontalMapRepetitionEnabled = false
                isVerticalMapRepetitionEnabled = false
                minZoomLevel = 3.0

                setScrollableAreaLimitDouble(
                    org.osmdroid.util.BoundingBox(
                        85.0,
                        180.0,
                        -85.0,
                        -180.0
                    )
                )

                controller.setZoom(5.0)
                controller.setCenter(GeoPoint(21.4225, 39.8262))

                @Suppress("ClickableViewAccessibility")
                setOnTouchListener { _, event ->
                    gestureDetector.onTouchEvent(event)
                    false
                }
            }
            mapView = map
            onMapCreated(map)
            map
        },
        update = { map ->
            val lat = selectedLatitude
            val lng = selectedLongitude

            if (lat != null && lng != null) {
                val point = GeoPoint(lat, lng)
                val existingMarker = marker

                if (existingMarker == null) {
                    val newMarker = Marker(map).apply { position = point }
                    map.overlays.add(newMarker)
                    marker = newMarker
                    map.controller.animateTo(point)
                } else if (existingMarker.position != point) {
                    existingMarker.position = point
                    map.controller.animateTo(point)
                }
                map.invalidate()
            }
        },
        modifier = modifier
    )

    DisposableEffect(Unit) {
        onDispose { mapView?.onDetach() }
    }
}