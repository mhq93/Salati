package com.mhq.salati.locationpicker.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.mhq.salati.shared.ui.theme.SalatiTheme
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/** Users have no reason to zoom out past country/continent level to pick a prayer location. */
private const val MIN_ZOOM_LEVEL = 3.0

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

    if (LocalInspectionMode.current) {
        Box(
            modifier = modifier.background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("OSM Map Placeholder (Preview Mode)")
        }
        return
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance()
            .load(
                context,
                context.getSharedPreferences(
                    "osmdroid",
                    Context.MODE_PRIVATE
                )
            )

        Configuration.getInstance().userAgentValue =
            "Salati/1.0 (${context.packageName}; https://github.com/mhq93/salati)"
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                setHorizontalMapRepetitionEnabled(false)
                setVerticalMapRepetitionEnabled(false)
                setScrollableAreaLimitDouble(
                    BoundingBox(
                        MapView.getTileSystem().maxLatitude,
                        MapView.getTileSystem().maxLongitude,
                        MapView.getTileSystem().minLatitude,
                        MapView.getTileSystem().minLongitude
                    )
                )
                minZoomLevel = MIN_ZOOM_LEVEL

                controller.setZoom(initialZoom.toDouble())
                controller.setCenter(GeoPoint(initialLat, initialLng))

                val marker = Marker(this).apply {
                    position = GeoPoint(initialLat, initialLng)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                overlays.add(marker)

                val gestureDetector = GestureDetector(
                    ctx,
                    object : GestureDetector.SimpleOnGestureListener() {
                        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                            val geoPoint =
                                projection.fromPixels(e.x.toInt(), e.y.toInt()) as GeoPoint
                            marker.position = geoPoint
                            onPointSelected(geoPoint.latitude, geoPoint.longitude)
                            return true
                        }
                    }
                )
                setOnTouchListener { _, event ->
                    gestureDetector.onTouchEvent(event)
                    false
                }
            }
        },
        update = { mapView ->
            val targetLat = selectedLatitude ?: initialLat
            val targetLng = selectedLongitude ?: initialLng
            val targetPoint = GeoPoint(targetLat, targetLng)
            mapView.controller.animateTo(targetPoint)
            val marker = mapView.overlays.find { it is Marker } as? Marker
            marker?.position = targetPoint
        },
        onRelease = { mapView ->
            mapView.onDetach()
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun LocationPickerMapPreview() {
    SalatiTheme() {
        LocationPickerMap(
            initialLat = 21.4225,
            initialLng = 39.8262,
            selectedLatitude = null,
            selectedLongitude = null,
            onPointSelected = { _, _ -> }
        )
    }
}