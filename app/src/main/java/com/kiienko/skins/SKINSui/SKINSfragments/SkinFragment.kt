package com.kiienko.skins.SKINSui.SKINSfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiienko.skins.R
import com.kiienko.skins.SKINSdata.SKINSlocal.AssetDatabaseOpenHelper
import com.kiienko.skins.SKINSdata.SKINSmodels.Skin
import com.kiienko.skins.SKINSui.MainActivity
import com.kiienko.skins.SKINSui.SKINSSadapters.HomeAdapterEn
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



class SkinFragment : Fragment() {

    var items: MutableList<Skin> = LinkedList<Skin>()
    lateinit var recyclerViewSkins: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skin_skins, container, false)
    }
    override fun onPause() {
        MainActivity.skinPosition = recyclerViewSkins.computeVerticalScrollOffset()/500
        Log.d("Skin on pause", MainActivity.skinPosition.toString())
        super.onPause()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        items= AssetDatabaseOpenHelper.getList()
        recyclerViewSkins = requireView().findViewById(R.id.recyclerView_skin)
        setAdapter(items)
        recyclerViewSkins.scrollToPosition(MainActivity.skinPosition)
        super.onViewCreated(view, savedInstanceState)
        requireView().findViewById<TextView>(R.id.skin_txt_selected).setOnClickListener {
            findNavController().navigate(R.id.action_skinFragment_to_selectedFragment)
        }
    }
    fun setAdapter(skins: MutableList<Skin>)
    {
        var lang= Locale.getDefault().getLanguage()
        var adapterSkins = HomeAdapterEn(requireContext(), skins, findNavController())
        val layoutManagerSkins: RecyclerView.LayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewSkins.adapter = adapterSkins
        recyclerViewSkins.layoutManager = layoutManagerSkins
    }
}