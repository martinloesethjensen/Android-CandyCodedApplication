package com.pluralsight.candycoded

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

import java.lang.reflect.Method

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest(AppCompatActivity::class, Intent::class, DetailActivity::class, Method::class)
@RunWith(PowerMockRunner::class)
class _4_ShareACandyWithAnIntent {

    @Test
    @Throws(Exception::class)
    fun onOptionsItemSelected_Exists() {
        var myClass: Class<*>? = null

        try {
            myClass = DetailActivity::class.java
                    .getMethod("onOptionsItemSelected", MenuItem::class.java)
                    .declaringClass
        } catch (e: NoSuchMethodException) {
            //e.printStackTrace();
        }

        assertEquals("onOptionsItemSelected() method doesn't exist in DetailActivity class.",
                myClass, DetailActivity::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun onOptionsItemSelected_call_super() {
        onOptionsItemSelected_Exists()
        assertFalse("onOptionsItemSelected() does not return call to super.", onOptionsItemSelected_result)
    }

    @Test
    @Throws(Exception::class)
    fun share_intent_actionsend() {
        assertTrue("The Intent was not created correctly.", created_intent)
    }

    @Test
    @Throws(Exception::class)
    fun share_intent_settype() {
        assertTrue("The Intent's type needs to be set with setType().", set_type)
    }

    @Test
    @Throws(Exception::class)
    fun share_intent_putextra() {
        assertTrue("Send extra data with the Intent with putExtra().", called_put_extra)
    }

    @Test
    @Throws(Exception::class)
    fun share_intent_startactivity() {
        assertTrue("The method startActivity() was not called.", called_startActivity_correctly)
    }

    @Test
    @Throws(Exception::class)
    fun createShareIntent_Exists() {
        var myMethod: Method? = null

        try {
            myMethod = DetailActivity::class.java.getDeclaredMethod("createShareIntent")
        } catch (e: NoSuchMethodException) {
            //e.printStackTrace();
        }

        assertNotNull("reateShareIntent() method doesn't exist in DetailActivity class.", myMethod)
    }

    companion object {

        val SHARE_DESCRIPTION = "Look at this delicious candy from Candy Coded - "
        val HASHTAG_CANDYCODED = " #candycoded"
        val mCandyImageUrl = ""

        private var detailActivity: DetailActivity? = null
        private var onOptionsItemSelected_result = true
        private val called_createShareIntent = false
        private var created_intent = false
        private var set_type = false
        private var called_put_extra = false
        private var called_startActivity_correctly = false

        // Mockito setup
        @BeforeClass
        @Throws(Exception::class)
        fun setup() {
            // Spy on a MainActivity instance.
            detailActivity = PowerMockito.spy(DetailActivity())
            // Create a fake Bundle to pass in.
            val bundle = mock(Bundle::class.java)

            val intent = PowerMockito.spy(Intent(Intent.ACTION_SEND))

            try {
                // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
                PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity::class.java))

                PowerMockito.whenNew(Intent::class.java).withAnyArguments().thenReturn(intent)


                try {
                    detailActivity!!.onCreate(bundle)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                onOptionsItemSelected_result = detailActivity!!.onOptionsItemSelected(null)

                PowerMockito.verifyNew<Any>(Intent::class.java, Mockito.atLeastOnce()).withArguments(Mockito.eq(Intent.ACTION_SEND))
                created_intent = true

                verify(intent).type = "text/plain"
                set_type = true

                verify(intent).putExtra(Intent.EXTRA_TEXT, SHARE_DESCRIPTION + mCandyImageUrl + HASHTAG_CANDYCODED)
                called_put_extra = true

                verify<DetailActivity>(detailActivity).startActivity(Mockito.eq(intent))
                called_startActivity_correctly = true


            } catch (e: Throwable) {
                //e.printStackTrace();
            }

        }
    }
}

