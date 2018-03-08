package com.kapouter.api.model

data class Yarn(val id: Int, val name: String, val first_photo: Photo, val grams: Int, val yardage: Int, val yarn_weight: YarnWeight, val yarn_company_name: String) {
}