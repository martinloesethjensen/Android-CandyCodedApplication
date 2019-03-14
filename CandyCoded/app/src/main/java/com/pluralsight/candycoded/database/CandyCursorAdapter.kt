package com.pluralsight.candycoded.database

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

import com.pluralsight.candycoded.R


class CandyCursorAdapter(context: Context, c: Cursor) : CursorAdapter(context, c, false) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(
                R.layout.list_item_candy, parent, false)
    }

    override fun bindView(view: View, context: Context,
                          cursor: Cursor) {
        val textView = view.findViewById<View>(
                R.id.text_view_candy) as TextView
        val candyName = cursor.getString(
                cursor.getColumnIndexOrThrow("name"))
        textView.text = candyName
    }
}
