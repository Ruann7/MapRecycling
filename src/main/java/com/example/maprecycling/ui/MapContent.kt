package com.example.maprecycling.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.SymbolLayer
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.android.utils.BitmapUtils
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection
import org.maplibre.geojson.Point

@Composable
fun MapContent(
    navController: NavHostController,
    recyclingPoints: List<RecyclingPoint>
) {
    var selectedPoint by remember { mutableStateOf<RecyclingPoint?>(null) }
    val points = if (recyclingPoints.isEmpty()) {
        listOf(
            RecyclingPoint(-8.685582, -35.590403, "Ponto 1 - Centro", "Coleta de plástico, vidro e papel.", ""),
            RecyclingPoint(-8.697530, -35.604506, "Ponto 2 - Quilombo 2", "Aceita eletrônicos e metais.", ""),
            RecyclingPoint(-8.664169, -35.573025, "Ponto 3 - Nilton Carneiro", "Materiais orgânicos e recicláveis.", "")
        )
    } else recyclingPoints

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                val mapView = MapView(ctx)
                mapView.getMapAsync { map ->
                    map.setStyle(Style.Builder().fromUri("https://tiles.stadiamaps.com/styles/osm_bright.json?api_key=0276a28c-4721-4ec7-b9c8-4222caead531")) { style ->
                        val centerLat = points.firstOrNull()?.lat ?: -8.6833
                        val centerLng = points.firstOrNull()?.lng ?: -35.5917
                        map.cameraPosition = CameraPosition.Builder()
                            .target(LatLng(centerLat, centerLng))
                            .zoom(13.0)
                            .build()

                        val features = points.map { p ->
                            Feature.fromGeometry(Point.fromLngLat(p.lng, p.lat)).apply {
                                addStringProperty("title", p.title)
                                addStringProperty("description", p.description)
                            }
                        }

                        val source = GeoJsonSource("recycling-source", FeatureCollection.fromFeatures(features))
                        style.addSource(source)

                        val icon = ctx.getDrawable(android.R.drawable.ic_menu_mylocation)!!
                        style.addImage("marker-icon", BitmapUtils.getBitmapFromDrawable(icon)!!)

                        val layer = SymbolLayer("recycling-layer", "recycling-source")
                            .withProperties(
                                PropertyFactory.iconImage("marker-icon"),
                                PropertyFactory.iconAllowOverlap(true),
                                PropertyFactory.iconSize(1.2f)
                            )
                        style.addLayer(layer)

                        map.addOnMapClickListener { mapPoint ->
                            val screenPoint = map.projection.toScreenLocation(mapPoint)
                            val clicked = map.queryRenderedFeatures(screenPoint, "recycling-layer")
                            if (clicked.isNotEmpty()) {
                                val f = clicked[0]
                                selectedPoint = RecyclingPoint(
                                    lat = f.getNumberProperty("geometry")?.toDouble() ?: 0.0,
                                    lng = 0.0,
                                    title = f.getStringProperty("title"),
                                    description = f.getStringProperty("description"),
                                    imageUrl = ""
                                )
                                true
                            } else false
                        }
                    }
                }
                mapView
            }
        )

        FloatingActionButton(
            onClick = { navController.navigate("home") },
            containerColor = Color(0xFF2E7D32),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
        }

        selectedPoint?.let { p ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Recycling, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(48.dp))
                        Spacer(Modifier.height(12.dp))
                        Text(p.title, style = MaterialTheme.typography.titleLarge, color = Color(0xFF1B5E20))
                        Spacer(Modifier.height(8.dp))
                        Text(p.description, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { selectedPoint = null }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))) {
                            Text("Fechar", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
