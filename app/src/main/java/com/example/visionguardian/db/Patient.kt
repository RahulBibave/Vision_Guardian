package com.example.visionguardian.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Patient(
    var mFirstName: String,
    var mLastName: String,
    var mMiddle: String,
    var mDoVisit: String,
    var mDob: String,
    var mAge: String,
    var mGender: String,
    var mMobile: String,
    var mEmail: String,
    var mAddress: String
) {
    @PrimaryKey(autoGenerate = true)
    var patientId = 0
}