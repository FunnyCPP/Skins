package com.kiienko.skins.SKINSdata.SKINSlocal

import com.kiienko.skins.SKINSdata.SKINSmodels.Skin
import java.util.*

class AssetDatabaseOpenHelper() {
    companion object {
        fun getList(): MutableList<Skin> {
            var skin: Skin
            val skins: MutableList<Skin> = LinkedList<Skin>()
            for (i in 1..100) {
                skin = Skin(i, "skin_image_$i.png", "skin_res_$i.png", 0, 0, 0,0)
                skins.add(skin)
            }
            return skins
        }
    }
}