package com.mhq.salati.locationpicker.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
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

        // OSM tile servers' usage policy expects a real identifying User-Agent, not a bare
        // package name — some servers 403 on the latter.
        // TODO: swap in your actual repo/contact if this changes.
        Configuration.getInstance().userAgentValue =
            "Salati/1.0 (${context.packageName}; https://github.com/mhq93/salati)"
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                // FIX: without these, zooming out past ~continent level tiles the
                // world map repeatedly in both directions (the striped-globe artifact).
                // Repetition off + a hard scroll limit at the real world bounds fixes it;
                // the min zoom just avoids ever reaching a view with nothing useful on it.
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

                // Add a marker at the initial location
                val marker = Marker(this).apply {
                    position = GeoPoint(initialLat, initialLng)
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                overlays.add(marker)

                // Distinguish a real tap from a pan/fling release. GestureDetector's
                // onSingleTapConfirmed only fires for an actual tap (accounts for touch-slop
                // and double-tap timing internally), so panning/zooming no longer moves the
                // marker or triggers a reverse-geocode call. Returning false from the
                // listener still lets osmdroid's own gesture handling (pan/pinch/double-tap
                // zoom) receive every event untouched.
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