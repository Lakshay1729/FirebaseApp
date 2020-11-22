package com.example.ui.Model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserModel (var Email:String?="", var FirstName:String?="", var LastName:String?="", var Password:String?="", var PhoneNumber:String?="", var UID:String?="", var UserImage:String?="")