package proj.kolot.com.placeholder

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.navigation.NavigationView
import org.json.JSONException






class MainActivity : AppCompatActivity() {
    private var callbackManager: CallbackManager? = null
    private lateinit var toogle:ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callbackManager = CallbackManager.Factory.create()
        var test = findViewById<TextView>(R.id.textView)
        test.setOnClickListener(View.OnClickListener {
          //  val intent = Intent(this, GeneralActivity::class.java)
          //  startActivity(intent)
        })
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
        toogle = ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close)

        dl.addDrawerListener(toogle)
        toogle.syncState()

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (toogle.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)

    }

    public override fun onStart() {
        super.onStart()
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            useLoginInformation(accessToken)
        }
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
