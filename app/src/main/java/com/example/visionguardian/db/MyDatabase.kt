package com.example.visionguardian.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Patient::class , ExaminationData::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun dao(): DAO?
    abstract fun examDao(): ExaminatioDao?
}