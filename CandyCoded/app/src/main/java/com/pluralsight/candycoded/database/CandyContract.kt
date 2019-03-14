package com.pluralsight.candycoded.database

import android.provider.BaseColumns

object CandyContract {
    val DB_NAME = "candycoded.db"
    val DB_VERSION = 1

    val SQL_CREATE_ENTRIES = "CREATE TABLE " + CandyEntry.TABLE_NAME + " (" +
            CandyEntry.ID + " INTEGER PRIMARY KEY," + // MAYBE DELETE
            CandyEntry.COLUMN_NAME_NAME + " TEXT," +
            CandyEntry.COLUMN_NAME_PRICE + " TEXT," +
            CandyEntry.COLUMN_NAME_DESC + " TEXT," +
            CandyEntry.COLUMN_NAME_IMAGE + " TEXT)"

    val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CandyEntry.TABLE_NAME

    class CandyEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "candy"
            val ID = "ID" // MAYBE DELETE
            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_PRICE = "price"
            val COLUMN_NAME_DESC = "description"
            val COLUMN_NAME_IMAGE = "image"
        }
    }
}
