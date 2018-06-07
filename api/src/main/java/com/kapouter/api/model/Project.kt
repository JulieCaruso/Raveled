package com.kapouter.api.model

data class Project(val id: Int,
                   val name: String,
                   val pattern_name: String?,
                   val first_photo: Photo?,
                   val favorites_count: Int,
                   val progress: Int)