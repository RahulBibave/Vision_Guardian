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

    @Query("Update ExaminationData set status = :mStatus where mExamId = :id")
    fun updateExam(mStatus: String?, id: Int)

    /*  @Query("Delete from Patient where patientId = :patientID")
      fun deleteStu(patientID: Int)

      @Query("SELECT * FROM patient WHERE patientId=:id ")
      fun loadSingle(id: String): List<Patient>*/

    @Query("SELECT * FROM examinationdata WHERE mExamId=:id ")
    fun loadSingleExam(id: Int): List<ExaminationData>

    @Query("SELECT * FROM examinationdata WHERE mDateOfExam BETWEEN :startDate AND :endDate")
    fun collectItems(startDate: String?, endDate: String?): List<ExaminationData?>?
}