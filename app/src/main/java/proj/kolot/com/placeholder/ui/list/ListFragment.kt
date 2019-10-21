package proj.kolot.com.placeholder.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_fragment.*
import proj.kolot.com.placeholder.PlaceholderApp
import proj.kolot.com.placeholder.R
import proj.kolot.com.placeholder.data.model.User
import proj.kolot.com.placeholder.ui.item.UserFragment

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ListViewModelFactory(
            (activity?.application as PlaceholderApp)
        )
        viewModel = ViewModelProviders.of(this, factory).get(ListViewModel::class.java)

        val adapter =
            UserViewAdapter(this.requireContext(), emptyList())
        adapter.onUserClickListener = object : UserViewAdapter.OnUserClickListener{
            override fun onClick(user: User) {

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, UserFragment.newInstance(user.id))
                    ?.commitNow()
            }

        }

        content.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        content.adapter = adapter

        viewModel.list.observe(this, Observer { t -> adapter.updateData(t) })

        viewModel.progress.observe(
            this,
            Observer { t -> progressBar?.visibility = if (t) View.VISIBLE else View.INVISIBLE })

        viewModel.error.observe(
            this,
            Observer { t ->
                if (t != null && t.isNotEmpty()) Toast.makeText(
                    context,
                    t,
                    Toast.LENGTH_LONG
                ).show()
            })
        viewModel.loadList()
    }


    class ListViewModelFactory(var app: PlaceholderApp) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val mainViewModel: ListViewModel =
                app.applicationComponent.listViewModel()
            return mainViewModel as T
        }
    }
}
