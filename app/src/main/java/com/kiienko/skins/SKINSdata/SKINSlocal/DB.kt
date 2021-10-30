package com.kiienko.skins.SKINSdata.SKINSlocal

import com.kiienko.skins.SKINSdata.SKINSmodels.Skin
import io.paperdb.Paper

class DB {
    companion object {

        fun addSkin(skin: Skin) {
            val skins = DB.getSkins()
            skins.add(skin)
            DB.saveSkins(skins)

        }

        fun removeSkin(skin: Skin) {

            val skins = DB.getSkins()
            skins.remove(skin)
            DB.saveSkins(skins)

        }

        fun saveSkins(skins: MutableList<Skin>) {
            Paper.book().write("skin2", skins)
        }

        fun getSkins(): MutableList<Skin> {
            return Paper.book().read("skin2", mutableListOf())
        }
        fun checkSkin(skin: Skin): Boolean{
            val skins = DB.getSkins()
            return skins.contains(skin)
        }
    }
}