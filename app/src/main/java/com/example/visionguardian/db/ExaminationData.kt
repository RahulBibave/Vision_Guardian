package com.example.visionguardian.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ExaminationData(var mHistoryQ1: String,
                      var mHistoryQ2: String,
                      var mHistoryQ3: String,
                      var mHistoryQ4: String,
                      var mHistoryQ5: String,
                      var mHistoryQ6: String,
                      var mHistoryQ6_1: String,
                      var mHistoryQ7: String,
                      var mHistoryQ8: String,
                      var mHistoryQ9: String,
                      var mHistoryQ10: String,
                      var mHistoryQ11: String,
                      var mHistoryQ12: String,
                      var mPatientID: Int,
                      var mdistanceVisionWithoutL:String,
                      var mdistanceVisionWithoutR:String,
                      var mdistanceGlassL:String,
                      var mdistanceGlassR:String,
                      var mNearWithGL:String,
                      var mNearWithGR:String,
                      var mNearWithOutGR:String,
                      var mNearWithOutGL:String,
                      var mCateVI:String,
                      var mSphereL:String,
                      var mSphereR:String,
                      var mCylinderL:String,
                      var mCylinderR:String,
                      var mAxisR:String,
                      var mAxisL:String,
                      var mDevL:String,
                      var mDeviation:String,
                      var mSameSize:String,
                      var mEyelidsR:String,
                      var mEyelidsL:String,
                      var mEyelashL:String,
                      var mEyelashR:String,
                      var mWhiteR:String,
                      var mWhiteL:String,
                      var mBlackL:String,
                      var mBlackR:String,
                      var mPupilR:String,
                      var mPupilL:String,
                      var mExInjuL:String,
                      var mExInjuR:String,
                      var mForeignR:String,
                      var mForeignL:String,
                      var mDateOfExam:String




                  ) {
    @PrimaryKey(autoGenerate = true)
    var mExamId = 0
}