package com.perisic.luka.studentperformance.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.perisic.luka.studentperformance.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragmentNavHost)

        mainViewModel.tokenCount().observe(this, Observer {
            if (it == 0 && navController.currentDestination?.id != R.id.loginFragment) {
                navController.navigate(
                    R.id.loginFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph_home_xml, true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            }
        })

        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.addDetailsFragment,
                R.id.loginFragment,
                R.id.userListFragment
            ),
            drawerLayout = drawerLayoutMain
        )

        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )

        setupWithNavController(
            navigationViewMain,
            navController
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            drawerLayoutMain.setDrawerLockMode(
                if (destination.id == R.id.loginFragment) {
                    toolbarMain.visibility = View.GONE
                    DrawerLayout.LOCK_MODE_LOCKED_CLOSED
                } else {
                    toolbarMain.visibility = View.VISIBLE
                    DrawerLayout.LOCK_MODE_UNLOCKED
                }
            )
        }

        mainViewModel.fetchRole().observe(this, Observer {
            it?.let {
                val action = when (it) {
                    "admin", "teacher" -> R.id.userListFragment
                    else -> R.id.addDetailsFragment
                }
                navigationViewMain.menu.clear()
                if (it == "admin" || it == "teacher") {
                    navigationViewMain.inflateMenu(R.menu.drawer_menu_admin)
                } else {
                    navigationViewMain.inflateMenu(R.menu.drawer_menu)
                }
                navController.navigate(
                    action,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph_home_xml, true)
                        .setLaunchSingleTop(true)
                        .build()
                )
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentNavHost)
            .navigateUp(appBarConfiguration)
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.loginFragment) {
            return finish()
        }
        super.onBackPressed()
    }

}
