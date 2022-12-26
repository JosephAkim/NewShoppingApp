package com.kim.newshoppingapp.util

import android.util.Patterns

fun validateEmail(email: String): RegisterValidation{
    if(email.isEmpty())
        return RegisterValidation.Failed("Email is empty")
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong Email Format")
    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation{
    if (password.isEmpty())
        return RegisterValidation.Failed("Password is Empty")
    if (password.length < 6)
        return RegisterValidation.Failed("Password should contain 6 or more characters")
    return RegisterValidation.Success
}