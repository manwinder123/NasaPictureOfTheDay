package com.manwinder.nasapictureoftheday.ui

import android.os.Bundle
import android.view.Menu
import android.view.animation.AnimationUtils
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.manwinder.nasapictureoftheday.R
import com.manwinder.nasapictureoftheday.api.NasaApi
import com.manwinder.nasapictureoftheday.api.NasaResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


open class MainActivity : AppCompatActivity() {

    private var shouldScaleUp = true
    private var nasaApi = NasaApi.getNasaApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nasaImageViewOnClickSetup()

        retrievePictureOfTheDay()
    }

    private fun nasaImageViewOnClickSetup() {
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up_and_rotate)
        val scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down_and_rotate)
        val fadeIn= AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeOut= AnimationUtils.loadAnimation(this, R.anim.fade_out)

        nasa_img_iv.setOnClickListener {
            if (shouldScaleUp) {
                shouldScaleUp = false
                it.startAnimation(scaleUp)
                img_title_tv.startAnimation(fadeOut)
                supportActionBar?.hide()
            } else {
                shouldScaleUp = true
                it.startAnimation(scaleDown)
                img_title_tv.startAnimation(fadeIn)
                supportActionBar?.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_items, menu)

        menu?.findItem(R.id.refresh)?.setOnMenuItemClickListener {
            retrievePictureOfTheDay()
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun retrievePictureOfTheDay(done: () -> Unit = {}) {
        val callback = object: Callback<NasaResponse> {
            override fun onFailure(call: Call<NasaResponse>?, t: Throwable?) {
                displaySnackbar("Error")
                done()
            }

            override fun onResponse(call: Call<NasaResponse>?, response: Response<NasaResponse>?) {
                response?.let { response ->
                    if (response.code() == 200 || response.code() == 202){
                        response.body()?.let {nasaResponse ->
                            if (URLUtil.isValidUrl(nasaResponse.url)) {
                                loadNasaImage(nasaResponse.url)
                            }
                            setTextViewText(nasaResponse.title)
                        } ?: run {
                            displaySnackbar(response.message())
                        }
                    } else {
                        displaySnackbar(response.message())
                    }
                } ?: run {
                    displaySnackbar("Error")
                }
                done()
            }
        }

        nasaApi.getPictureOfTheDay().enqueue(callback)
    }

    fun loadNasaImage(url: String): com.squareup.picasso.Callback {
        val callback = object: com.squareup.picasso.Callback {
            override fun onSuccess() { }

            override fun onError(e: Exception?) {
                displaySnackbar("Error while retrieving picture")
            }
        }

        Picasso.get()
                .load(url)
                .into(nasa_img_iv, callback)

        return callback
    }

    fun displaySnackbar(text : String) {
        val snackbar = Snackbar.make(nasa_img_iv, text, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        snackbar.setText("Error")
        snackbar.show()
        setTextViewText(text)
    }

    fun setTextViewText(text: String) {
        img_title_tv.text = text
    }
}
