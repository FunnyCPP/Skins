package com.kiienko.skins.SKINSui.SKINSfragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kiienko.skins.R
import com.kiienko.skins.SKINSdata.SKINSlocal.AssetDatabaseOpenHelper
import com.kiienko.skins.SKINSdata.SKINSlocal.DB
import com.kiienko.skins.SKINSdata.SKINSlocal.GeneratingZIP
import com.kiienko.skins.SKINSdata.SKINSmodels.Skin
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val POSITION_SKINS = "position"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var SKINposition: Int = 0
    var positionList: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            SKINposition = it.getInt(POSITION_SKINS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_skins, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        positionList=SKINposition
        var skins: MutableList<Skin> = LinkedList<Skin>()
        skins= AssetDatabaseOpenHelper.getList()
        var qimgSkin1: ImageView = requireView().findViewById(R.id.imgSkin1_detail)
        var qimgBack: ImageView = requireView().findViewById(R.id.imgBtnBack_skins)
        var qbtnAdd1: ImageView = requireView().findViewById(R.id.imgBtnAdd1_detail_skin)
        var qbtnExport: Button =requireView().findViewById(R.id.btnExport_skin)
        var qbtnGallery: Button =requireView().findViewById(R.id.btnExportToGallery)
        var qpath ="file:///android_asset/images/"+skins[positionList].imageName
        var qcurrentSkin =skins[positionList]
        Glide.with(this)
            .load(Uri.parse(qpath))
            .into(qimgSkin1)
        if(DB.checkSkin(qcurrentSkin))
        {
            qbtnAdd1.setImageResource(R.drawable.ic_star_selected)
        }
        qbtnAdd1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (DB.checkSkin(qcurrentSkin)) {
                    DB.removeSkin(qcurrentSkin)
                    qbtnAdd1.setImageResource(R.drawable.ic_star)
                } else {
                    DB.addSkin(qcurrentSkin)
                    qbtnAdd1.setImageResource(R.drawable.ic_star_selected)
                }
            }
        })
        qimgBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                findNavController().navigateUp()
            }
        })
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE), 1)
        }

        qbtnExport.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                var qskinFile = GeneratingZIP.createZip(qcurrentSkin, requireContext())
                var qfileb = File(requireContext().getExternalFilesDir(""), "skin_res_" + qcurrentSkin.id + ".mcpack")
                if(!qfileb.exists())
                {
                    qskinFile.copyTo(qfileb)
                }
                qfileb.setReadable(true,false)
                val fileUriSkins: Uri? = try {
                    FileProvider.getUriForFile(
                            requireContext(),
                            "com.kiienko.skins.fileprovider",
                            qfileb)
                } catch (e: IllegalArgumentException) {
                    Log.e("File Selector",
                            e.toString())
                    null
                }
                var intentSkins: Intent = Intent(Intent.ACTION_VIEW)
                intentSkins.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
                intentSkins.`package`  = "com.mojang.minecraftpe"
                intentSkins.data = (fileUriSkins)
                Log.d("path","minecraft://?=import=$fileUriSkins")
                try {
                    startActivity(intentSkins)
                } catch (e: Exception) {
                    Log.e("EXPORt",e.toString())
                    val url = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
        })
        qbtnGallery.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                try {
                    var qbFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "minecraft_"+qcurrentSkin.resName)
                    var qinStream: InputStream? = null
                    var qoutStream: OutputStream? = null

                    qinStream = requireContext().assets.open("skins/" + qcurrentSkin.resName)
                    qoutStream = FileOutputStream(qbFile)

                    qinStream.copyTo(qoutStream)
                    qinStream.close()
                    qoutStream.close()
                }
                catch (e: java.lang.Exception)
                {
                    Log.e(e.toString(), e.message!!)
                }
                Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        @JvmStatic
        fun newInstance(position: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(POSITION_SKINS, position)
                }
            }
    }
}