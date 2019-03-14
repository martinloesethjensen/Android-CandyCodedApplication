package com.pluralsight.candycoded

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@PrepareForTest(AppCompatActivity::class, MainActivity::class, Intent::class, InfoActivity::class)
@RunWith(PowerMockRunner::class)
class _1_StartTheStoreInformationActivity {

    @Test
    @Throws(Exception::class)
    fun mainactivity_onoptionitemselected_return_super() {
        override_mainactivity_onoptionitemselected()
        assertFalse("onOptionsItemSelected() does not return call to super.", onOptionsItemSelected_result)
    }

    @Test
    @Throws(Exception::class)
    fun create_intent_infoactivity() {
        override_mainactivity_onoptionitemselected()
        assertTrue("The Intent was not created.", called_Intent)
        assertTrue("The Intent was created but with the wrong parameters. @intent-infoactivity", called_Intent_correctly)
    }

    @Test
    @Throws(Exception::class)
    fun startactivity_infoactivity() {
        override_mainactivity_onoptionitemselected()
        assertTrue("The method startActivity() was not called.", called_startActivity)
    }

    @Test
    @Throws(Exception::class)
    fun override_mainactivity_onoptionitemselected() {
        // Determine if the method OnOptionsItemSelected() is implemented in MainActivity
        // or just in the Base class
        var myClass: Class<*>? = null

        try {
            myClass = MainActivity::class.java
                    .getMethod("onOptionsItemSelected", MenuItem::class.java)
                    .declaringClass
        } catch (e: NoSuchMethodException) {
            //e.printStackTrace();
        }

        assertEquals("onOptionsItemSelected() method doesn't exist in MainActivity class.",
                myClass, MainActivity::class.java)

        assertEquals("onOptionsItemSelected() method doesn't exist in MainActivity class.",
                myClass, MainActivity::class.java)
    }

    companion object {
        private var activity: MainActivity? = null

        private var onOptionsItemSelected_result = true
        private var called_Intent = false
        private var called_Intent_correctly = false
        private var called_startActivity = false

        // Mockito setup
        @BeforeClass
        @Throws(Exception::class)
        fun setup() {
            // Spy on a MainActivity instance.
            activity = PowerMockito.spy(MainActivity())
            // Create a fake Bundle to pass in.
            val bundle = Mockito.mock(Bundle::class.java)
            // Create a spy Intent to return from new Intent().
            val intent = PowerMockito.spy(Intent(activity, InfoActivity::class.java))

            try {
                // Do not allow super.onCreate() to be called, as it throws errors before the user's code.
                PowerMockito.suppress(PowerMockito.methodsDeclaredIn(AppCompatActivity::class.java))

                // Return a mocked Intent from the call to its constructor.
                PowerMockito.whenNew(Intent::class.java).withAnyArguments().thenReturn(intent)

                // We expect calling onCreate() to throw an Exception due to our mocking. Ignore it.
                try {
                    activity!!.onCreate(bundle)
                } catch (e: Exception) {
                    //e.printStackTrace();
                }

                try {
                    onOptionsItemSelected_result = activity!!.onOptionsItemSelected(null)
                } catch (e: Throwable) {
                    //e.printStackTrace();
                }

                // Check if new Intent() was called with any arguments.
                try {
                    PowerMockito.verifyNew<Any>(Intent::class.java, Mockito.atLeastOnce()).withNoArguments()
                    called_Intent = true
                } catch (e: Throwable) {
                    //e.printStackTrace();
                }

                try {
                    PowerMockito.verifyNew<Any>(Intent::class.java, Mockito.atLeastOnce()).withArguments(Mockito.any(MainActivity::class.java), Mockito.any(Class::class.java))
                    called_Intent = true
                } catch (e: Throwable) {
                    //e.printStackTrace();
                }

                // Check if new Intent() was called with the correct arguments.
                PowerMockito.verifyNew<Any>(Intent::class.java, Mockito.atLeastOnce()).withArguments(Mockito.eq<MainActivity>(activity), Mockito.eq(InfoActivity::class.java))
                called_Intent_correctly = true

                // Check if startActivity() was called with the correct argument.
                Mockito.verify<MainActivity>(activity).startActivity(Mockito.eq(intent))
                called_startActivity = true

            } catch (e: Throwable) {
                //e.printStackTrace();
            }

        }
    }
}
