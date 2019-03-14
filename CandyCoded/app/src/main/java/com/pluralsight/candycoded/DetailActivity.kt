package com.pluralsight.candycoded

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.pluralsight.candycoded.database.CandyContract
import com.pluralsight.candycoded.database.CandyContract.CandyEntry
import com.pluralsight.candycoded.database.CandyDbHelper
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    internal var mCandyImageUrl = ""

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = this@DetailActivity.intent

        if (intent != null && intent.hasExtra("position")) {
            val position = intent.getIntExtra("position", 0)

            val dbHelper = CandyDbHelper(this)
            val db = dbHelper.writableDatabase
            val cursor = db.rawQuery("SELECT * FROM candy", null)
            cursor.moveToPosition(position)

            val candyName = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyContract.CandyEntry.COLUMN_NAME_NAME))
            val candyPrice = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_PRICE))
            mCandyImageUrl = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_IMAGE))
            val candyDesc = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_DESC))


            val textView = this.findViewById<View>(R.id.text_view_name) as TextView
            textView.text = candyName

            val textViewPrice = this.findViewById<View>(R.id.text_view_price) as TextView
            textViewPrice.text = candyPrice

            val textViewDesc = this.findViewById<View>(R.id.text_view_desc) as TextView
            textViewDesc.text = candyDesc

            val imageView = this.findViewById<View>(
                    R.id.image_view_candy) as ImageView
            Picasso.with(this).load(mCandyImageUrl).into(imageView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail, menu)
        return true
    }

    companion object {

        val SHARE_DESCRIPTION = "Look at this delicious candy from Candy Coded - "
        val HASHTAG_CANDYCODED = " #candycoded"
    }

    // ***
    // TODO - Task 4 - Share the Current Candy with an Intent
    // ***
}
