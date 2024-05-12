package edu.ucne.roomtarea1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.roomtarea1.data.local.dao.AporteDao
import edu.ucne.roomtarea1.data.local.entities.AporteEntity

@Database(
entities = [
        AporteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AporteDb : RoomDatabase(){
    abstract fun aporteDao(): AporteDao
}
