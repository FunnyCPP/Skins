package com.kiienko.skins.SKINSui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.kiienko.skins.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_skins)
        var i=0
        val SKINHandler: Handler = Handler()
        val Skintxt: TextView = findViewById(R.id.start_txt_skin)
        Thread(Runnable {

            while (i < 100) {
                i++

                SKINHandler.post {
                    if(i%10==0)
                    {
                        Skintxt.text ="."
                    }
                    if(i%15==0)
                    {
                        Skintxt.text =".."
                    }
                    if(i%20==0)
                    {
                        Skintxt.text ="..."
                    }
                    if(i==100)
                    {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                try {
                    Thread.sleep(30)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
    }
}