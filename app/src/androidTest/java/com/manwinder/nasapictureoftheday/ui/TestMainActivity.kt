package com.manwinder.nasapictureoftheday.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.manwinder.nasapictureoftheday.R
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestMainActivity {

    @get:Rule
    var rule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun ensureImageViewIsPresent() {
        val activity = rule.activity

        val nasaImgView = activity.findViewById<ImageView>(R.id.nasa_img_iv)
        Assert.assertNotNull(nasaImgView)
        assertThat(nasaImgView, instanceOf(ImageView::class.java))
    }

    @Test
    fun ensureTextViewIsPresent() {
        val activity = rule.activity

        val imgTitleTextView= activity.findViewById<TextView>(R.id.img_title_tv)
        Assert.assertNotNull(imgTitleTextView)
        assertThat(imgTitleTextView, instanceOf(TextView::class.java))
    }

    @Test
    fun ensureTextViewTextCanChange() {
        val activity = rule.activity
        val imgTitleTextView= activity.findViewById<TextView>(R.id.img_title_tv)

        Assert.assertNotNull(imgTitleTextView)
        assertThat(imgTitleTextView, instanceOf(TextView::class.java))

        assertThat(imgTitleTextView.text.toString(), `is`(""))

        activity.setTextViewText("test")
        assertThat(imgTitleTextView.text.toString(), `is`("test"))
    }

    @Test
    fun ensureSnackbarChangesTextViewText() {
        val activity = rule.activity
        val imgTitleTextView= activity.findViewById<TextView>(R.id.img_title_tv)

        Assert.assertNotNull(imgTitleTextView)
        assertThat(imgTitleTextView, instanceOf(TextView::class.java))

        assertThat(imgTitleTextView.text.toString(), `is`(""))

        activity.displaySnackbar("test")
        assertThat(imgTitleTextView.text.toString(), `is`("test"))
    }

    @Test
    fun retrievePictureOfDayVerifyTextView() {
        val activity = rule.activity
        activity.retrievePictureOfTheDay {
            val imgTitleTextView= activity.findViewById<TextView>(R.id.img_title_tv)
            Assert.assertNotNull(imgTitleTextView)
            assertThat(imgTitleTextView, instanceOf(TextView::class.java))
            assertThat(imgTitleTextView.text.toString(), `is`(not("")))
        }
    }
}