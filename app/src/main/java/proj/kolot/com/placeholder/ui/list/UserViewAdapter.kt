package proj.kolot.com.placeholder.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proj.kolot.com.placeholder.R
import proj.kolot.com.placeholder.data.model.User

class UserViewAdapter(ctx: Context, private var data: List<User>) :
    RecyclerView.Adapter<UserViewAdapter.UserViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(ctx)
    var onUserClickListener:OnUserClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = inflater.inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = data[position]
        holder.nameView.setText(user.name)
        holder.userNameView.text = user.userName
        holder.emailView.text = user.email
        holder.itemView.setOnClickListener(View.OnClickListener { onUserClickListener?.onClick(user) })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(data: List<User>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameView: TextView
        var userNameView: TextView
        var emailView: TextView

        init {
            nameView = itemView.findViewById(R.id.nameView) as TextView
            userNameView = itemView.findViewById(R.id.userNameView) as TextView
            emailView = itemView.findViewById(R.id.emailView) as TextView
        }
    }

    interface OnUserClickListener {
        fun onClick(user:User)
    }
}
