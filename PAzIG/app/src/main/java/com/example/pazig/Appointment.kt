package com.example.pazig

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Appointment(val appointmentID: String? = null, val appointmentdate: String? = null, val appointmentdescription: String? = null, val humor: String? = null, val medications: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}