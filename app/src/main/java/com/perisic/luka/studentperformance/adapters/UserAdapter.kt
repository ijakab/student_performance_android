package com.perisic.luka.studentperformance.adapters

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.perisic.luka.data.remote.model.helpers.User
import com.perisic.luka.studentperformance.base.BasePagedAdapter
import com.perisic.luka.studentperformance.base.BaseViewHolder
import com.perisic.luka.studentperformance.base.inflate
import com.perisic.luka.studentperformance.databinding.ItemUserBinding
import com.perisic.luka.studentperformance.ui.userList.UserListFragmentDirections

class UserAdapter(
    val onItemClick: (View, User) -> Unit
) : BasePagedAdapter<User>(
    User.DIFF_UTIL
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<User> {
        return UserViewHolder(
            parent.inflate(ItemUserBinding::inflate)
        )
    }

    inner class UserViewHolder(
        binding: ItemUserBinding
    ) : BaseViewHolder<User>(
        binding,
        {
            binding.user = it
            binding.buttonOptions.setOnClickListener { view ->
                onItemClick(view, it)
            }
            binding.root.setOnClickListener { _ ->
                binding.root.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(
                        userId = it.id
                    )
                )
            }
        }
    )

}