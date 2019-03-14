package com.pluralsight.candycoded

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import junit.framework.Assert
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Method
import java.util.*


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest(AppCompatActivity::class, Intent::class, Uri::class, InfoActivity::class)
@RunWith(PowerMockRunner::class)
class _2_StartTheGoogleMapsActivity {

    @Test
    @Throws(Exception::class)
    fun make_uri_address() {
        createMapIntent_Exists()
        assertTrue("The Uri for the map location wasn't created.", called_uri_parse)
    }

    @Test
    @Throws(Exception::class)
    fun create_actionview_map_intent() {
        createMapIntent_Exists()
        assertTrue("The Intent was not created correctly.", created_intent)
    }

    @Test
    @Throws(Exception::class)
    fun map_intent_set_package() {
        createMapIntent_Exists()
        assertTrue("The package was not set for the Intent.", set_package)
    }

    @Test
    @Throws(Exception::class)
    fun map_intent_handler_exists() {
        createMapIntent_Exists()
        assertTrue("The method resolveActivity() needs to be called.", resolve_activity)
    }

    @Test
    @Throws(Exception::class)
    fun map_intent_start_activity() {
        createMapIntent_Exists()
        assertTrue("The method startActivity() was not called.", called_startActivity_correctly)
    }

    @Test
    @Throws(Exception::class)
    fun createMapIntent_Exists() {
        var myMethod: Method? = null

        try {
            myMethod = InfoActivity::class.java
                    .getMethod("createMapIntent", View::class.java)
        } catch (e: NoSuchMethodException) {
            //e.printStackTrace();
        }

        assertNotNull("createMapIntent() method doesn't exist in InfoActivity class.", myMethod)
    }

    @Test
    @Throws(Exception::class)
    fun test_xml() {
        val viewContainers = readLayoutXML(LAYOUT_XML_FILE)
        val addressView = XMLTestHelpers.ViewContainer("@+id/text_view_address", "createMapIntent", "true")
        val address_set_correct = viewContainers.contains(addressView)

        Assert.assertTrue("In activity_info.xml, the TextView text_view_address does not have " + "the clickable and onClick properties set.",
                address_set_correct)
    }

    fun readLayoutXML(layoutFileName: String): ArrayList<XMLTestHelpers.ViewContainer> {
        var inputStream: InputStream? = null

        var viewContainers = ArrayList<XMLTestHelpers.ViewContainer>()

        try {
            inputStream = this.javaClass.classLoader!!.getResourceAsStream(layoutFileName)
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = false
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            parser.nextTag()
            viewContainers = XMLTestHelpers.readFeed(parser)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return viewContainers
    }

    companion object {

        val LAYOUT_XML_FILE = "res/layout/activity_info.xml"
        private var called_uri_parse = false
        private var created_intent = false
        private val created_intent_correctly = false
        private var set_package = false
        private var resolve_activity = false
        private var called_startActivity_correctly = false

        // Mockito setup
        @BeforeClass
        @Throws(Exception::class)
        fun setup() {
            // Spy on a MainActivity instance.
            val infoActivity = PowerMockito.spy(InfoActivity())
            // Create a fake Bundle to pass in.
            val bundle = mock(Bundle::class.java)
            val mockUri = mock(Uri::class.java)

            val mockPackageManager = mock(PackageManager::class.java)
            val mockComponentName = mock(ComponentName::class.java)
            val actualIntent = Intent(Intent.ACTION_VIEW, mockUri)
            val intent = PowerMockito.spy(actualIntent)

            try {
                // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
                PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity::class.java))


                // PROBLEM - this is not helping to make the mapIntent not null in createMapIntent()
                PowerMockito.whenNew(Intent::class.java).withAnyArguments().thenReturn(intent)

                try {
                    infoActivity.onCreate(bundle)
                } catch (e: Exception) {
                    //e.printStackTrace();
                }

                PowerMockito.doReturn(mockPackageManager).`when`(infoActivity, "getPackageManager")
                PowerMockito.doReturn(mockComponentName).`when`(intent, "resolveActivity", mockPackageManager)

                PowerMockito.mockStatic(Uri::class.java)
                PowerMockito.`when`<Any>(Uri::class.java, "parse", "geo:0,0?q=618 E South St Orlando, FL 32801").thenReturn(mockUri)

                try {
                    //infoActivity.createMapIntent(null);
                    val myMethod = InfoActivity::class.java
                            .getMethod("createMapIntent", View::class.java)
                    val param = arrayOf<Any>(Any())
                    myMethod.invoke(infoActivity, *param)
                } catch (e: Throwable) {
                    //e.printStackTrace();
                }

                PowerMockito.verifyStatic(Uri::class.java)
                Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801") // This has to come on the line after mockStatic
                called_uri_parse = true

                PowerMockito.verifyNew<Any>(Intent::class.java, Mockito.atLeastOnce()).withArguments(Mockito.eq(Intent.ACTION_VIEW), Mockito.eq(mockUri))
                created_intent = true


                verify(intent).setPackage("com.google.android.apps.maps")
                set_package = true

                verify(intent).resolveActivity(mockPackageManager)
                resolve_activity = true

                Mockito.verify(infoActivity).startActivity(Mockito.eq(intent))
                called_startActivity_correctly = true


            } catch (e: Throwable) {
                //e.printStackTrace();
            }

        }
    }
}

