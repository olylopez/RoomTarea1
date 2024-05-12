package edu.ucne.roomtarea1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import edu.ucne.roomtarea1.data.local.database.AporteDb
import edu.ucne.roomtarea1.data.local.entities.AporteEntity
import edu.ucne.roomtarea1.ui.theme.RoomTarea1Theme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import edu.ucne.roomtarea1.presentation.AporteListScreen
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : ComponentActivity() {
    private lateinit var aporteDb: AporteDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aporteDb = Room.databaseBuilder(
            this,
            AporteDb::class.java,
            "Aporte.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        enableEdgeToEdge()
        setContent {
            RoomTarea1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(8.dp)
                    ) {

                        val aportes: List<AporteEntity> by getAporte().collectAsStateWithLifecycle(
                            initialValue = emptyList()
                        )
                        var aporteId by remember { mutableStateOf("") }
                        var persona by remember { mutableStateOf("") }
                        var fecha by remember { mutableStateOf("") }
                        var monto by remember { mutableDoubleStateOf(0.0) }

                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {




                                OutlinedTextField(
                                    label = { Text(text = "Persona") },
                                    value = persona,
                                    onValueChange = { persona = it },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    label = { Text(text = "Fecha") },
                                    value = fecha,
                                    onValueChange = { fecha = it },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    label = { Text(text = "Monto") },
                                    value = monto.toString(),
                                    onValueChange = {newValue ->
                                        val newText = newValue.takeIf { it.matches(Regex("""^\d{0,5}(\.\d{0,2})?$""")) } ?: monto.toString()
                                        monto = newText.toDoubleOrNull() ?: 0.0
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.padding(2.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            aporteId = ""
                                            persona = ""
                                            fecha = ""
                                            monto = 0.0
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "new button"
                                        )
                                        Text(text = "Nuevo")
                                    }
                                    OutlinedButton(
                                        onClick = {
                                            saveAporte(
                                                AporteEntity(
                                                    aporteId = aporteId.toIntOrNull(),
                                                    persona = persona,
                                                    fecha = fecha,
                                                    monto = monto

                                                )
                                            )
                                            aporteId = ""
                                            persona = ""
                                            fecha = ""
                                            monto = 0.0
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "save button"
                                        )
                                        Text(text = "Guardar")
                                    }
                                }
                            }

                        }
                        Spacer(modifier = Modifier.padding(2.dp))


                        AporteListScreen(
                            aportes = aportes,
                            onVerAporte = { aporteSeleccionado ->
                                aporteId = aporteSeleccionado.aporteId.toString()
                                persona = aporteSeleccionado.persona
                                fecha = aporteSeleccionado.fecha
                                monto = aporteSeleccionado.monto.toDouble()
                            })
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun saveAporte(aporte: AporteEntity) {
        GlobalScope.launch {
            aporteDb.aporteDao().save(aporte)
        }
    }

    private fun getAporte(): Flow<List<AporteEntity>> {
        return aporteDb.aporteDao().getAll()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AporteScreen(
    persona: String, fecha: String, monto: Double, onSaveAporte: () -> Unit
) {

}

@Preview(showBackground = true)
@Composable
fun Preview()
{
    RoomTarea1Theme {

    }
}

