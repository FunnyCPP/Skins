package com.kiienko.skins.SKINSdata.SKINSmodels

data class SkinPackSkins(
    var geometry: String,
    var skins: MutableList<SkinPackSkinsSkin>,
    var serialize_name: String,
    var localization_name: String
)