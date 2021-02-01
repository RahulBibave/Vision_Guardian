package com.example.visionguardian.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAO {
    @Insert
    fun patientInsertion(patient: Patient?)

    @get:Query("Select * from patient ORDER BY patientId DESC")
    val patient: List<Patient?>?

    @Query("Update Patient set mFirstName = :stuName where patientId = :patientID")
    fun updateStu(stuName: String?, patientID: Int)

    @Query("Delete from Patient where patientId = :patientID")
    fun deleteStu(patientID: Int)

    @Query("SELECT * FROM patient WHERE patientId=:id ")
    fun loadSingle(id: Int): List<Patient>

    @Query("SELECT * FROM patient WHERE mFirstName LIKE :search " +  "OR mMobile LIKE :search")
    fun findUserWithName(search: String): List<Patient>
}