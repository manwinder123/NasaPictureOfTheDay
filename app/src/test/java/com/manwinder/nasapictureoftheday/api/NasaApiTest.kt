package com.manwinder.nasapictureoftheday.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.manwinder.nasapictureoftheday.utils.BaseWebServerTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class NasaApiTest : BaseWebServerTest() {

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testNewsItemsRetrieval() {
        enqueueResponse("nasa_response.json")

        mockWebServer.start()

        val nasaApi = NasaApi.getNasaApi(mockWebServer.url(""))
        val response = nasaApi.getPictureOfTheDay().execute()

        Assert.assertNotNull(response)
        Assert.assertNotNull(response?.message())

        val nasaResponse = response?.body()

        Assert.assertNotNull(nasaResponse)

        MatcherAssert.assertThat(nasaResponse?.copyright, CoreMatchers.`is`("John Kraus"))
        MatcherAssert.assertThat(nasaResponse?.date, CoreMatchers.`is`("2018-08-15"))

        MatcherAssert.assertThat(nasaResponse?.explanation, CoreMatchers.`is`("When is the best time to launch a probe to the Sun?  The now historic answer -- which is not a joke because this really happened this past weekend -- was at night. Night, not only because NASA's Parker Solar Probe's (PSP) launch window to its planned orbit occurred, in part, at night, but also because most PSP instruments will operate in the shadow of its shield -- in effect creating its own perpetual night near the Sun. Before then, years will pass as the PSP sheds enough orbital energy to approach the Sun, swinging past Venus seven times. Eventually, the PSP is scheduled to pass dangerously close to the Sun, within 9 solar radii, the closest ever. This close, the temperature will be 1,400 degrees Celsius on the day side of the PSP's Sun shield -- hot enough to melt many forms of glass.  On the night side, though, it will be near room temperature.  A major goal of the PSP's mission to the Sun is to increase humanity's understanding of the Sun's explosions that impact Earth's satellites and power grids.  Pictured is the night launch of the PSP aboard the United Launch Alliances' Delta IV Heavy rocket early Sunday morning.   Gallery: Best Submitted Images of PSP Launch"))

        MatcherAssert.assertThat(nasaResponse?.hdurl, CoreMatchers.`is`("https://apod.nasa.gov/apod/image/1808/SolarProbeLaunch_Kraus_2048.jpg"))
        MatcherAssert.assertThat(nasaResponse?.media_type, CoreMatchers.`is`("image"))
        MatcherAssert.assertThat(nasaResponse?.service_version, CoreMatchers.`is`("v1"))
        MatcherAssert.assertThat(nasaResponse?.title, CoreMatchers.`is`("Launch of the Parker Solar Probe"))
        MatcherAssert.assertThat(nasaResponse?.url, CoreMatchers.`is`("https://apod.nasa.gov/apod/image/1808/SolarProbeLaunch_Kraus_960.jpg"))
    }

}