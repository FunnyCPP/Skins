package com.kiienko.skins.SKINSui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import com.kiienko.skins.R
import io.paperdb.Paper
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_skins)
        Paper.init(this)
        try {
        }
        catch (e: Exception)
        {
            Log.d("Navigate Error", e.localizedMessage, e.cause)
        }
    }
    override fun onBackPressed() {
        val SKINnavController = Navigation.findNavController(this,R.id.nav_host_skin)
        if(SKINnavController.currentDestination!!.label.toString() == "fragment_skin"  || SKINnavController.currentDestination!!.label.toString() =="fragment_selected")
            finishAffinity()
        super.onBackPressed()
    }
    companion object{
        var skinPosition = 0
        var SKINselectedPosition = 0
    }

}