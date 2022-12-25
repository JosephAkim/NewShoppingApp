package com.kim.newshoppingapp.data

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    //image path is empty cause user will not upload image on registration, it will be empty
    var imagePath: String = ""
){
    //this constructor is needed when working with firebase
    constructor(): this("", "", "", "")
}
