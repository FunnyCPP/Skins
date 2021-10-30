package com.kiienko.skins.SKINSdata.SKINSlocal

import android.content.Context
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.kiienko.skins.SKINSdata.SKINSmodels.*
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class GeneratingZIP() {
    companion object {
        var formatVersion = 1

        var headerUUID: UUID = UUID.randomUUID()
        var modulUUID: UUID = UUID.randomUUID()
        var version: Array<Int> = arrayOf(1, 0, 0)
        var type = "skin_pack"
        var geometry = "skinpacks/skins.json"
        var localizationName = "current"
        var skinsGeometry = "geometry.humanoid.custom"



        fun createManifestJSON(skin: Skin, context: Context): File {
            var skinsType = "free"
            var title = "skin_res_" + skin.id
            var texture = skin.resName!!
            var name: String = title
            var skinsLocalizationName = title
            var serializeName = title
            var langEnLine1 = "skin." + title + localizationName + "=" + title
            var langEnLine2 = "skinpack." + title + "=" + "Skins"
            var langRuLine1 = "skin." + title + localizationName + "=" + title
            var langRuLine2 = "skinpack." + title + "=" + "Скины"
            var header: SkinPackManifestHeader = SkinPackManifestHeader(name, UUID.randomUUID(), version)
            var modul: SkinPackManifestModul = SkinPackManifestModul(type, UUID.randomUUID(), version)
            val listModul: List<SkinPackManifestModul> = listOf(modul)
            var skinPackManifest: SkinPackManifest = SkinPackManifest(formatVersion, header, listModul)
            val gson: Gson = Gson()
            var json: String = gson.toJson(skinPackManifest)
            var fileName = "manifest.json"
            var file: File = File(context.getExternalFilesDir(null),fileName)
            file.writeText(json)
            return file
        }

        fun createSkinsJSON(skin: Skin, context: Context): File {
            var skinsType = "free"
            var title = "skin_res_" + skin.id
            var texture = skin.resName!!
            var name: String = skin.resName!!
            var skinsLocalizationName = title
            var serializeName = title
            var langEnLine1 = "skin." + title + localizationName + "=" + title
            var langEnLine2 = "skinpack." + title + "=" + "Skins"
            var langRuLine1 = "skin." + title + localizationName + "=" + title
            var langRuLine2 = "skinpack." + title + "=" + "Скины"
            var skin: SkinPackSkinsSkin = SkinPackSkinsSkin(localizationName, skinsGeometry, texture, "free")
            var skinsList: MutableList<SkinPackSkinsSkin> = mutableListOf(skin)
            var skins: SkinPackSkins = SkinPackSkins(geometry, skinsList, serializeName, skinsLocalizationName)
            var fileName = "skins.json"
            val gson: Gson = Gson()
            var json: String = gson.toJson(skins)
            var file: File = File(context.getExternalFilesDir(null),fileName)
            file.writeText(json)
            return file
        }

        fun createLangEn(skin: Skin, context: Context): File {
            var skinsType = "free"
            var title = "skin_res_" + skin.id
            var texture = skin.resName!!
            var name: String = skin.resName!!
            var skinsLocalizationName = title
            var serializeName = title
            var langEnLine1 = "skin." + title + localizationName + "=" + title
            var langEnLine2 = "skinpack." + title + "=" + title
            var langRuLine1 = "skin." + title + localizationName + "=" + title
            var langRuLine2 = "skinpack." + title + "=" + title
            var txt = langEnLine1 + "\n" + langEnLine2
            var fileName = "texts/en_US.lang"
            var mfile: File = File(context.getExternalFilesDir(null),"texts")
            if (mfile.exists()) {
                Log.d("folder", "exists")
            } else {
                Log.d("folder", "not exists")
                mfile.mkdirs()
            }
            var file: File = File(context.getExternalFilesDir(null),fileName)
            file.writeText(txt)
            return file

        }

         fun createZip(skin: Skin, context: Context): File {
            var skinsType = "free"
            var title = "skin_res_" + skin.id
            var texture = skin.resName!!
            var name: String = skin.resName!!
            var skinsLocalizationName = title
            var serializeName = title
            var langEnLine1 = "skin." + title + localizationName + "=" + title
            var langEnLine2 = "skinpack." + title + "=" + "Skins"
            var langRuLine1 = "skin." + title + localizationName + "=" + title
            var langRuLine2 = "skinpack." + title + "=" + "Скины"
            val f = File("/texts")
            var pathb: String= Environment.getExternalStorageDirectory().toString()+  skin.resName
            val bfile = File(context.getExternalFilesDir(null),skin.resName )
            var inStream: InputStream? = null
            var outStream: OutputStream? = null

            inStream = context.assets.open("skins/"+skin.resName)
            outStream = FileOutputStream(bfile)

            val buffer = ByteArray(1024)
            var length = inStream.read(buffer)
            while (length    > 0 )

            {
                outStream.write(buffer, 0, length)
                length = inStream.read(buffer)
            }

            inStream.close()
            outStream.close()

            var files: List<File> = listOf(createSkinsJSON(skin,context), createManifestJSON(skin,context), bfile, createLangEn(skin, context))

            var skinFile = File(context.getExternalFilesDir(null),title + ".mcpack")
            val myFile= File(context.getExternalFilesDir(null),title+".zip")
            ZipOutputStream(BufferedOutputStream(FileOutputStream(myFile))).use { zfile ->
                for (file in files) {

                    FileInputStream(file).use { fi ->
                        BufferedInputStream(fi).use { origin ->
                            val entry = ZipEntry(file.name)
                            zfile.putNextEntry(entry)
                            origin.copyTo(zfile, 1024)
                        }
                    }
                }
            }
            if(!skinFile.exists())
                myFile.copyTo(skinFile)

            return skinFile
        }

    }
}