package com.pluralsight.candycoded

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

    // ***
    // TODO - Task 2 - Launch the Google Maps Activity
    // ***

    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***
}
