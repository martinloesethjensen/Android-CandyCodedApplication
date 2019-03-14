package com.pluralsight.candycoded

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

import java.io.IOException
import java.util.ArrayList

/**
 * Created by sarah on 9/28/17.
 */

object XMLTestHelpers {

    class ViewContainer(var id: String?, var onClick: String?, var clickable: String?) {

        override fun equals(obj: Any?): Boolean {
            if (obj == null) {
                return false
            }
            if (!ViewContainer::class.java.isAssignableFrom(obj.javaClass)) {
                return false
            }
            val other = obj as ViewContainer?
            if (if (this.id == null) other!!.id != null else this.id != other!!.id) {
                return false
            }
            if (if (this.onClick == null) other.onClick != null else this.onClick != other.onClick) {
                return false
            }
            return if (if (this.clickable == null) other.clickable != null else this.clickable != other.clickable) {
                false
            } else true
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun readFeed(parser: XmlPullParser): ArrayList<ViewContainer> {

        val viewContainers = ArrayList<ViewContainer>()

        parser.require(XmlPullParser.START_TAG, null, "android.support.constraint.ConstraintLayout")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            val name = parser.name
            // Starts by looking for the entry tag
            if (name == "TextView") {
                // TO DO
                val idProperty = parser.getAttributeValue(null, "android:id")
                val onClickProperty = parser.getAttributeValue(null, "android:onClick")
                val clickableProperty = parser.getAttributeValue(null, "android:clickable")

                val viewContainer = ViewContainer(idProperty, onClickProperty, clickableProperty)
                viewContainers.add(viewContainer)
                // This will go to the end of the TextView tag
                parser.next()
            } else {
                skip(parser)
            }
        }

        return viewContainers
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
