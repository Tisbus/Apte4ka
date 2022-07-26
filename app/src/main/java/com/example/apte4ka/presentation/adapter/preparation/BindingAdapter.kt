package com.example.apte4ka.presentation.adapter.preparation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("setImagePreparation")
fun setImagePreparation(iv : ImageView, imgUrl : String){
    Picasso.get().load(imgUrl).rotate(90f).into(iv)
}