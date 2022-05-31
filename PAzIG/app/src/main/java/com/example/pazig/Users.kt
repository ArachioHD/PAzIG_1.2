package com.example.pazig

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(val username: String? = null, val userlastname: String? = null, val userage: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}