package ir.apptune.antispam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ir.apptune.antispam.features.service.ForegroundService
import ir.apptune.antispam.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ContextCompat.startForegroundService(this, Intent(this, ForegroundService::class.java))
        setUpBottomNav()
        setSupportActionBar(toolbar)
        setStatusBar()
    }

    private fun setUpBottomNav() {
        bottomNav.setupWithNavController(findNavController(R.id.navFragment))
        findNavController(R.id.navFragment).addOnDestinationChangedListener { _: NavController, navDestination: NavDestination, _: Bundle? ->
            when (navDestination.id) {
                R.id.splashFragment -> hideToolbarBottomNav()
                else -> showToolbarBottomNav()
            }
        }
    }

    private fun showToolbarBottomNav() {
        bottomNav.visibility = View.VISIBLE
        toolbar.visibility = View.VISIBLE
    }

    private fun hideToolbarBottomNav() {
        bottomNav.visibility = View.GONE
        toolbar.visibility = View.GONE
    }

    private fun setStatusBar() {
        val statusBarHeight = getStatusBarHeight(this)
        viewStatusBar.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
        viewStatusBar.background = ContextCompat.getDrawable(this, R.drawable.toolbar_shape)
    }
}