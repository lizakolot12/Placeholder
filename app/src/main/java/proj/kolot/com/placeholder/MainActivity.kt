package proj.kolot.com.placeholder

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.navigation.NavigationView
import org.json.JSONException
import proj.kolot.com.placeholder.ui.list.ListFragment


class MainActivity : AppCompatActivity() {
    private var callbackManager: CallbackManager? = null
    private lateinit var toggle:ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callbackManager = CallbackManager.Factory.create()
        val loginButton = findViewById(R.id.login_button) as LoginButton
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = AccessToken.getCurrentAccessToken()
                val isLoggedIn = accessToken != null && !accessToken.isExpired
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })

        val dl: DrawerLayout =   findViewById(R.id.activity_main) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close)

        dl.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val nv = findViewById(R.id.navigationView) as NavigationView
        nv.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
             override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val id = item.getItemId()
                when (id) {
                    R.id.account -> Toast.makeText(
                        this@MainActivity,
                        "My Account",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> return true
                }


                return true

            }
        })

        supportFragmentManager.addOnBackStackChangedListener(object :
            FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                } else {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    toggle.syncState()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (toggle.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)

    }

    public override fun onStart() {
        super.onStart()
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            useLoginInformation(accessToken)
            openList()
        }
    }

    private fun openList() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ListFragment.newInstance())
            .commitNow()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun useLoginInformation(accessToken: AccessToken) {
        /**
         * Creating the GraphRequest to fetch user details
         * 1st Param - AccessToken
         * 2nd Param - Callback (which will be invoked once the request is successful)
         */
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { `object`, response ->
            //OnCompleted is invoked once the GraphRequest is successful
            try {
                val name = `object`.getString("name")
                val email = `object`.getString("email")
                val image = `object`.getJSONObject("picture").getJSONObject("data").getString("url")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        // We set parameters to the GraphRequest using a Bundle.
        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,picture.width(200)")
        request.parameters = parameters
        // Initiate the GraphRequest
        request.executeAsync()
    }
}
