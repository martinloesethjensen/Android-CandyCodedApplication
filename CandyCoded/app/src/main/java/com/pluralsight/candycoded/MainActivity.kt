package com.pluralsight.candycoded

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import com.pluralsight.candycoded.database.CandyContract
import com.pluralsight.candycoded.database.CandyCursorAdapter
import com.pluralsight.candycoded.database.CandyDbHelper
import cz.msebera.android.httpclient.Header


class MainActivity : AppCompatActivity() {
    private var candies: Array<Candy>? = null
    private val candyDbHelper = CandyDbHelper(this)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = candyDbHelper.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM candy", null)

        val adapter = CandyCursorAdapter(this, cursor)
        val listView = this.findViewById<View>(R.id.list_view_candy) as ListView

        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
            detailIntent.putExtra("position", i)
            startActivity(detailIntent)
        }

        val client = AsyncHttpClient()
        client.get("https://vast-brushlands-23089.herokuapp.com/main/api",
                object : TextHttpResponseHandler() {
                    override fun onFailure(statusCode: Int, headers: Array<Header>, response: String, throwable: Throwable) {
                        Log.e("AsyncHttpClient", "response = $response")
                    }

                    override fun onSuccess(statusCode: Int, headers: Array<Header>, response: String) {
                        Log.d("AsyncHttpClient", "response = $response")
                        val gson = GsonBuilder().create()
                        candies = gson.fromJson<Array<Candy>>(response, Array<Candy>::class.java)

                        addCandiesToDatabase(candies!!)

                        val database = candyDbHelper.writableDatabase
                        database.rawQuery("SELECT * FROM candy", null)
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val infoIntent = Intent(this, InfoActivity::class.java)
        startActivity(infoIntent)

        return super.onOptionsItemSelected(item)
    }

    private fun addCandiesToDatabase(candies: Array<Candy>) {
        val db = candyDbHelper.writableDatabase

        for (candy in candies) {
            val values = ContentValues()
            values.put(CandyContract.CandyEntry.COLUMN_NAME_NAME, candy.name)
            values.put(CandyContract.CandyEntry.COLUMN_NAME_PRICE, candy.price)
            values.put(CandyContract.CandyEntry.COLUMN_NAME_DESC, candy.description)
            values.put(CandyContract.CandyEntry.COLUMN_NAME_IMAGE, candy.image)

            db.insert(CandyContract.CandyEntry.TABLE_NAME, null, values)
        }
    }
}