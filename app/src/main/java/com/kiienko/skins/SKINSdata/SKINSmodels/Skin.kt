package com.kiienko.skins.SKINSdata.SKINSmodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skins")
data class Skin(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "image_name") val imageName: String?,
    @ColumnInfo(name = "res_name") val resName: String?,
    @ColumnInfo(name = "viewed") val viewed: Int?,
    @ColumnInfo(name = "liked") val liked: Int?,
    @ColumnInfo(name = "downloaded") val downloaded: Int?,
    @ColumnInfo(name = "favorite") val favorite: Int?
)
