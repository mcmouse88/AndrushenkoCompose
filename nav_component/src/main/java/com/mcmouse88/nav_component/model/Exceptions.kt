package com.mcmouse88.nav_component.model

class LoadDataException : Exception()

class DuplicateException(
    val duplicatedValue: String
) : Exception("The list can't contain duplicated items")