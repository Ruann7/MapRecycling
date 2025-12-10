package com.example.maprecycling.ui.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DicasScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // 🔝 TOPO DA TELA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2E7D32))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                text = "Dicas Sustentáveis 🌱",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        // 📜 LISTA DE DICAS
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                DicaCard(
                    icon = Icons.Default.Recycling,
                    title = "Separe seu lixo",
                    description = "Plástico, papel, vidro e metal devem ser separados corretamente para facilitar a reciclagem."
                )
            }

            item {
                DicaCard(
                    icon = Icons.Default.WaterDrop,
                    title = "Economize água",
                    description = "Feche a torneira ao escovar os dentes e reaproveite água sempre que possível."
                )
            }

            item {
                DicaCard(
                    icon = Icons.Default.Eco,
                    title = "Reduza o consumo",
                    description = "Evite descartáveis, prefira produtos reutilizáveis e compre apenas o necessário."
                )
            }

            item {
                DicaCard(
                    icon = Icons.Default.Recycling,
                    title = "Descarte eletrônico",
                    description = "Pilhas, baterias e eletrônicos devem ser descartados em pontos específicos."
                )
            }
        }
    }
}
@Composable
fun DicaCard(
    icon: ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = description,
                    color = Color.DarkGray
                )
            }
        }
    }
}