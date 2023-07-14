package com.flaxstudio.wallpaperapp.utils

class DataClasses {



}

data class Users (val displayName : String = "", val imageUrl : String = "", val uid : String = "" ,val subscriber : Boolean = false , val startDate : Long = 0L , val endDate : Long = 0L)

enum class FragmentType{
    All,
    Specific
}