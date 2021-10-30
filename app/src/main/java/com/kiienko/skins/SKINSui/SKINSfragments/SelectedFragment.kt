package com.kiienko.skins.SKINSui.SKINSfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kiienko.skins.R
import com.kiienko.skins.SKINSdata.SKINSlocal.DB
import com.kiienko.skins.SKINSdata.SKINSmodels.Skin
import com.kiienko.skins.SKINSui.MainActivity
import com.kiienko.skins.SKINSui.SKINSSadapters.SelAdapter
import java.util.*

class SelectedFragment : Fragment() {


    var skins: MutableList<Skin> = LinkedList<Skin>()
    lateinit var rView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_skins, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        skins= DB.getSkins()
        rView = requireView().findViewById(R.id.recyclerView_skin)
        setAdapterSkins(skins)
        rView.scrollToPosition(MainActivity.SKINselectedPosition)
        requireView().findViewById<TextView>(R.id.selected_txt_skin).setOnClickListener {
            findNavController().navigate(R.id.action_selectedFragment_to_skinFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onPause() {
        MainActivity.SKINselectedPosition = rView.computeVerticalScrollOffset()/600

        super.onPause()
    }
    fun setAdapterSkins(skins: MutableList<Skin>)
    {
        var rViewSkins: RecyclerView = requireView().findViewById(R.id.recyclerView_skin)
        var adapterSkin = SelAdapter(requireContext(), skins, findNavController())
        val layoutManagerSkin: RecyclerView.LayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rViewSkins.adapter = adapterSkin
        rViewSkins.layoutManager = layoutManagerSkin
    }
}