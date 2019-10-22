package proj.kolot.com.placeholder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.facebook.login.LoginManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import proj.kolot.com.placeholder.data.model.LoggedUser
import proj.kolot.com.placeholder.ui.list.ListFragment
import proj.kolot.com.placeholder.ui.login.MainLoginFragment
import android.widget.TextView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ImageView
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val factory = MainViewModelFactory(
            (application as PlaceholderApp)
        )
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        if (viewModel.loggedUser().token.isEmpty()) {
            showUnlogedState(savedInstanceState)
        } else {
            showLoggedState(savedInstanceState)
        }


        viewModel.currentUser().observe(this, Observer { t ->
            //   if (t == null ) return@Observer;
            Log.e("TEST", "current token " + t.token)
            if (t == null || t.token.isEmpty()) {
                showUnlogedState(savedInstanceState)
            } else {
                showLoggedState(savedInstanceState)
            }
        })
        val dl: DrawerLayout = findViewById(R.id.activity_main) as DrawerLayout
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
                    R.id.users -> showLoggedState(savedInstanceState)

                    R.id.logout -> logout()

                    else -> return true
                }

                dl.closeDrawers()
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

    private fun logout() {
        LoginManager.getInstance().logOut()
        viewModel.logout()
    }

    private fun showUnlogedState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainLoginFragment.newInstance())
                .commitNow()
        }
    }

    private fun showLoggedState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance())
                .commitNow()
        }

        showUserInfo(viewModel.loggedUser())
    }

    private fun showUserInfo(loggedUser: LoggedUser) {
        val navigationView = findViewById(R.id.navigationView) as NavigationView
        val hView = navigationView.getHeaderView(0)
        val nav_user = hView.findViewById(R.id.info) as TextView
        nav_user.setText(loggedUser.login + " \n " + loggedUser.email)
        val imageView = hView.findViewById<ImageView>(R.id.photo)
        Glide.with(this).load(loggedUser.photoPath).placeholder(android.R.drawable.ic_menu_gallery).into(imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (toggle.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)

    }

    public override fun onStart() {
        super.onStart()
        /* val accessToken = AccessToken.getCurrentAccessToken()
         if (accessToken != null) {
             useLoginInformation(accessToken)
             openList()
         }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /* val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
         if ( currentFragment is MainLoginFragment) {}
         callbackManager?.onActivityResult(requestCode, resultCode, data)*/
        super.onActivityResult(requestCode, resultCode, data)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        currentFragment?.onActivityResult(requestCode, resultCode, data)
    }


    class MainViewModelFactory(var app: PlaceholderApp) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val mainViewModel: MainViewModel =
                app.applicationComponent.mainViewModel()
            return mainViewModel as T
        }
    }

}
