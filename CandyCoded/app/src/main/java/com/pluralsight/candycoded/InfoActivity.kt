package com.pluralsight.candycoded

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView

import com.squareup.picasso.Picasso

class InfoActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val uri = Uri.parse("android.resource://com.codeschool.candycoded/" + R.drawable.store_front)
        val candyStoreImageView = findViewById<View>(R.id.image_view_candy_store) as ImageView
        Picasso.with(this).load(uri).into(candyStoreImageView)
    }

    fun createMapIntent(view: View) {
        val uri = Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) startActivity(mapIntent)

    }

    fun createPhoneIntent(view: View) {
        startActivity(Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:0123456789")))
    }

    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***
}
