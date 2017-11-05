package com.thelegacycoder.pubapp.Activities

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thelegacycoder.pubapp.Fragments.PubListFragment
import com.thelegacycoder.pubapp.Interfaces.ApiCall
import com.thelegacycoder.pubapp.Models.Pub
import com.thelegacycoder.pubapp.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, PubListFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val fragmentManager: FragmentManager by lazy { supportFragmentManager }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        getPubs()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun getPubs() {

        val pubs = ArrayList<Pub>()
        val client = OkHttpClient.Builder()
        client.interceptors()?.add(FakeInterceptor())
        val apiCall: ApiCall = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .baseUrl(resources.getString(R.string.base_url))
                .build().create(ApiCall::class.java)

        apiCall.getPubs()
                .enqueue(object : Callback<List<Pub>> {
                    override fun onResponse(call: Call<List<Pub>>?, response: Response<List<Pub>>?) {
                        pubs.clear()
                        pubs.addAll(response?.body() ?: List(1, { Pub() }))
                        println("RECEIVED: ${pubs.size}")
                    }

                    override fun onFailure(call: Call<List<Pub>>?, t: Throwable?) {
                        println("FAILED")
                    }


                })

        pubs.forEach { println(it) }


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about_developer -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                changeFragment(PubListFragment.newInstance())
            }
            R.id.nav_about_us -> {

            }
            R.id.nav_contact_us -> {

            }
            R.id.nav_placeholder -> {

            }
            R.id.nav_placeholder1 -> {

            }
            R.id.nav_placeholder2 -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }


}

class FakeInterceptor : Interceptor {
    private val getPubResponse = "[{\n" +
            "    \"pubName\": \"Pub 1\",\n" +
            "    \"timings\": \"4PM to 3AM\",\n" +
            "    \"location\": {\n" +
            "        \"lat\": 1234,\n" +
            "        \"lon\": 5678\n" +
            "    },\n" +
            "    \"city\": \"Mumbai\",\n" +
            "    \"backgroundPic\": \"background_url\",\n" +
            "    \"displayPic\": \"display_url\"\n" +
            "},\n" +
            "{\n" +
            "    \"pubName\": \"Pub 2\",\n" +
            "    \"timings\": \"5PM to 4AM\",\n" +
            "    \"location\": {\n" +
            "        \"lat\": 9101,\n" +
            "        \"lon\": 1213\n" +
            "    },\n" +
            "    \"city\": \"Delhi\",\n" +
            "    \"backgroundPic\": \"background_url\",\n" +
            "    \"displayPic\": \"display_url\"\n" +
            "}]"

    override fun intercept(chain: Interceptor.Chain?): okhttp3.Response {
        return okhttp3.Response.Builder()
                .code(200)
                .message("Success")
                .body((ResponseBody.create(MediaType.parse("application/json"), getPubResponse)))
                .request(chain!!.request())
                .protocol(Protocol.HTTP_2)
                .addHeader("content-type", "application/json")
                .build()
    }

}
