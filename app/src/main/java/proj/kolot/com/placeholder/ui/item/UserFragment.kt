package proj.kolot.com.placeholder.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.android.synthetic.main.user_fragment.*
import proj.kolot.com.placeholder.PlaceholderApp
import proj.kolot.com.placeholder.R
import proj.kolot.com.placeholder.data.model.User

class UserFragment : Fragment() , OnMapReadyCallback  {

    companion object {
        fun newInstance(id:Int):UserFragment {
            val fragment = UserFragment()
            fragment.arguments = bundleOf("id" to id)
            return fragment
        }
    }

    private lateinit var viewModel: UserViewModel
    private  var mMap: GoogleMap? = null
    private var user:User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = UserViewModelFactory(
            (activity?.application as PlaceholderApp)
        )
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)



        viewModel.progress.observe(
            this,
            Observer { t -> progressBar?.visibility = if (t) View.VISIBLE else View.INVISIBLE })


        viewModel.loadUser(arguments?.getInt("id")?:0)
        viewModel.user.observe(this, Observer {  t ->  if (t != null) showUser(t)})
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

       showUserPlace()
    }

    private fun showUserPlace(){
        if (user == null  || mMap == null) return
        val place = LatLng(user?.address?.geo?.lat?.toDouble()?:.0, user?.address?.geo?.lng?.toDouble()?:.0)
        mMap?.addMarker(MarkerOptions().position(place).title(user?.name))
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(place))
    }

    private fun showUser(user: User) {
        this.user = user
        nameView.text = user.name
        userNameView.text = user.userName
        emailView.text = user.email
        phoneView.text = user.phone
        websiteView.text = user.website
        showUserPlace()

    }


    class UserViewModelFactory(var app: PlaceholderApp) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val mainViewModel: UserViewModel =
                app.applicationComponent.userViewModel()
            return mainViewModel as T
        }
    }
}