package com.perisic.luka.studentperformance.ui.userList


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.perisic.luka.data.remote.model.helpers.User
import com.perisic.luka.studentperformance.R
import com.perisic.luka.studentperformance.adapters.UserAdapter
import com.perisic.luka.studentperformance.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_list.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class UserListFragment : BaseFragment(
    R.layout.fragment_user_list
) {

    private val viewModel: UserListViewModel by viewModel()
    private val userAdapter: UserAdapter = UserAdapter(this::onItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.filterUsers("", refresh = true)
        setHasOptionsMenu(true)
        fabCreateUser.setOnClickListener { findNavController().navigate(R.id.createUserFragment) }
        recyclerViewUsers.adapter = userAdapter
        viewModel.userList.observe(this, Observer {
            userAdapter.submitList(it)
        })
        viewModel.deletedUser.observe(
            success = { userAdapter.currentList?.dataSource?.invalidate() },
            error = {}
        )
        viewModel.status.observeStatus()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user_list, menu)
        menu.findItem(R.id.action_search)?.let {
            val actionView = it.actionView
            if (actionView is SearchView) {
                actionView.setOnQueryTextListener(
                    object : SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            viewModel.filterUsers(query ?: "")
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            if (newText != null && newText.isEmpty())
                                viewModel.filterUsers("")
                            return false
                        }
                    }
                )
            }
        }
    }

    private fun onItemClick(view: View, user: User) {
        val popup = PopupMenu(requireContext(), view)
        val inflater = popup.menuInflater
        inflater.inflate(
            R.menu.menu_user_item,
            popup.menu
        )
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete_user -> {
                    onDeleteUserClick(user.id)
                }
                R.id.action_edit_user -> {
                    findNavController()
                        .navigate(
                            UserListFragmentDirections.actionUserListFragmentToUpdateUserFragment(
                                userId = user.id
                            )
                        )
                }
            }
            true
        }
        popup.show()
    }

    private fun onDeleteUserClick(userId: Int) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(R.string.delete_user)
            setMessage(R.string.confirm_user_delete_message)
            setPositiveButton(R.string.agree) { _, _ ->
                viewModel.deleteUser(userId)
            }
            setNegativeButton(R.string.dismiss) { _, _ -> }
        }.show()
    }
}