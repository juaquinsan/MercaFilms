package com.joaquin.mercafilms.utils

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

/*
    Useful functions apply to android methods
 */

fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId,
    this, false)

fun ImageView.loadByUrl(url: String) = Picasso.get().load(url).into(this)

inline fun <reified T : Activity> Activity.goToActivity(noinline init: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}
