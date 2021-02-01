package com.example.visionguardian.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ExaminatioDao {
    @Insert
    fun patientExamInsertion(examin: ExaminationData?)

    @get:Query("Select * from examinationdata ORDER BY mExamId DESC")
    val patientExam: List<ExaminationData?>?

   /* @Query("Update Patient set mFirstName = :stuName where patientId = :patientID")
    fun updateStu(stuName: String?, patientID: Int)

    @Query("Delete from Patient where patientId = :patientID")
    fun deleteStu(patientID: Int)

    @Query("SELECT * FROM patient WHERE patientId=:id ")
    fun loadSingle(id: String): List<Patient>*/

    @Query("SELECT * FROM examinationdata WHERE mExamId=:id ")
    fun loadSingleExam(id: Int): List<ExaminationData>
}