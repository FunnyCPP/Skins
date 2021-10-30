package com.kiienko.skins.SKINSdata.SKINSmodels

data class SkinPackManifest(
    var format_version: Int,
    var header: SkinPackManifestHeader,
    var modules: List<SkinPackManifestModul>
)