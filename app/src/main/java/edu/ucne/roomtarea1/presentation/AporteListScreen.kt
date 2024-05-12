package edu.ucne.roomtarea1.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.roomtarea1.data.local.entities.AporteEntity
import org.w3c.dom.Text

@Composable
fun AporteListScreen(
    aportes: List<AporteEntity>,
    onVerAporte: (AporteEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(aportes) { aporte ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onVerAporte(aporte) }
                        .padding(16.dp)
                ) {
                    Text(text = aporte.aporteId.toString(), modifier = Modifier.weight(0.10f))
                    Text(text = aporte.persona, modifier = Modifier.weight(0.400f))
                    Text(text = aporte.fecha, modifier = Modifier.weight(0.40f))
                    Text(text = "RD" + aporte.monto.toString(), modifier = Modifier.weight(0.40f))
                }
            }
        }
    }
}


