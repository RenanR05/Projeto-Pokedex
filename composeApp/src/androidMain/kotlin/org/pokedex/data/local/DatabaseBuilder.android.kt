package org.pokedex.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

internal lateinit var appContext: Context

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = appContext.getDatabasePath("pokedex.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
