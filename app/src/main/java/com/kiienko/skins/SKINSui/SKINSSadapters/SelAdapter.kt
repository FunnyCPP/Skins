package com.kiienko.skins.SKINSui.SKINSSadapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kiienko.skins.R
import com.kiienko.skins.SKINSdata.SKINSlocal.DB
import com.kiienko.skins.SKINSdata.SKINSmodels.Skin
import java.io.IOException
import java.io.InputStream

class SelAdapter(contextSkins: Context, skinsList: MutableList<Skin>, navControllerSkins: NavController): RecyclerView.Adapter<SelAdapter.FavoritesEnViewHolder>() {

    var contextSkins: Context = contextSkins
    var skinsList: MutableList<Skin> = skinsList
    val navControllerSkins= navControllerSkins
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesEnViewHolder {
        val view: View =
            LayoutInflater.from(contextSkins).inflate(R.layout.skin_cell_skin, parent, false)
        return FavoritesEnViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesEnViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        var position: Int=position
        position*=2

        var path: String="file:///android_asset/images/"+skinsList[position].imageName
        var currentSkin: Skin =skinsList[position]
        if(DB.checkSkin(currentSkin))
        {
            holder.btnAdd1.setImageResource(R.drawable.ic_star_selected)
        }
        try {
            val ims: InputStream = contextSkins.assets.open("images/"+skinsList[position].imageName)
            val d = Drawable.createFromStream(ims, null)
            holder.imgSkin1.setImageDrawable(d)
        } catch (ex: IOException) {

        }
        holder.rel1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val a: Int=skinsList[position].id-1
                val pair=Pair<String, Int>("position",a)
                navControllerSkins.navigate(R.id.action_selectedFragment_to_detailFragment, bundleOf(pair))
            }
        })
        holder.btnAdd1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if(DB.checkSkin(currentSkin))
                {
                    DB.removeSkin(skinsList[position])
                    removeItem(position)

                }
                else
                {
                    DB.addSkin(currentSkin)
                    holder.btnAdd1.setImageResource(R.drawable.ic_star_selected)
                }
            }
        })
        if(position+1<=skinsList.size-1) {
            path = "file:///android_asset/images/" + skinsList[position + 1].imageName
            currentSkin = skinsList[position + 1]
            if (DB.checkSkin(currentSkin)) {
                holder.btnAdd2.setImageResource(R.drawable.ic_star_selected)
            }
            try {
                val ims: InputStream = contextSkins.assets.open("images/"+skinsList[position+1].imageName)
                val d = Drawable.createFromStream(ims, null)
                holder.imgSkin2.setImageDrawable(d)
            } catch (ex: IOException) {

            }
            holder.rel2.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val a: Int=currentSkin.id-1
                    val pair=Pair<String, Int>("position",a)
                    navControllerSkins.navigate(R.id.action_selectedFragment_to_detailFragment, bundleOf(pair))
                }
            })
            holder.btnAdd2.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    if (DB.checkSkin(currentSkin)) {
                        DB.removeSkin(currentSkin)
                        holder.btnAdd2.setImageResource(R.drawable.ic_star)
                        removeItem(position + 1)

                    } else {
                        DB.addSkin(currentSkin)
                        holder.btnAdd2.setImageResource(R.drawable.ic_star_selected)
                    }
                }
            })
        }
        else {

            holder.imgSkin2.visibility= View.INVISIBLE
            holder.btnAdd2.visibility= View.INVISIBLE
            holder.layout2.visibility= View.INVISIBLE
            holder.imgSkin2.isClickable=false
            holder.btnAdd2.isClickable=false
        }
    }
    fun removeItem(position: Int) {
        skinsList.remove(skinsList[position])
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        if(skinsList.size%2==0)
        {
            return skinsList.size/2
        }
        else
        {
            return ((skinsList.size-1)/2)+1
        }
    }
    class FavoritesEnViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgSkin1: ImageView
        var btnAdd1: ImageButton
        var imgSkin2: ImageView
        var btnAdd2: ImageButton
        val layout2: RelativeLayout
        val rel1: RelativeLayout
        val rel2: RelativeLayout


        init {
            imgSkin1 = itemView.findViewById(R.id.imgSkin1_detail)
            btnAdd1  = itemView.findViewById(R.id.imgBtnAdd1_detail_skin)
            imgSkin2 = itemView.findViewById(R.id.imgSkin2)
            btnAdd2 = itemView.findViewById(R.id.imgBtnAdd2)
            layout2 = itemView.findViewById(R.id.rel_layout2)
            rel1 = itemView.findViewById(R.id.rel_layout1)
            rel2 = itemView.findViewById(R.id.rel_layout2)


        }
    }

}