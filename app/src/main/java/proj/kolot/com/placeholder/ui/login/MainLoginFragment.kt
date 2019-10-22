package proj.kolot.com.placeholder.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.login_fragment.*
import org.json.JSONException
import proj.kolot.com.placeholder.PlaceholderApp

import proj.kolot.com.placeholder.R
import proj.kolot.com.placeholder.data.model.LoggedUser
import proj.kolot.com.placeholder.ui.item.UserFragment
import proj.kolot.com.placeholder.ui.item.UserViewModel

class MainLoginFragment : Fragment() {

    companion object {
        fun newInstance() = MainLoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private var callbackManager: CallbackManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = LoginViewModelFactory(
            (activity?.application as PlaceholderApp)
        )
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

        callbackManager = CallbackManager.Factory.create()
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = AccessToken.getCurrentAccessToken()
                val isLoggedIn = accessToken != null && !accessToken.isExpired
                if (isLoggedIn) useLoginInformation(accessToken)
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
    }

    private fun useLoginInformation(accessToken: AccessToken) {
        progressBar.visibility = View.VISIBLE

        val request = GraphRequest.newMeRequest(
            accessToken
        ) { `object`, response ->
            //OnCompleted is invoked once the GraphRequest is successful
            try {
                val name = `object`.getString("name")
                val email = `object`.getString("email")
                val image = `object`.getJSONObject("picture").getJSONObject("data").getString("url")
                viewModel.saveUser(LoggedUser(name, accessToken.token, email, image))
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


    class LoginViewModelFactory(var app: PlaceholderApp) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val loginViewModel: LoginViewModel =
                app.applicationComponent.loginViewModel()
            return loginViewModel as T
        }
    }
}
