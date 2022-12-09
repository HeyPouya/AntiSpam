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
import ir.apptune.antispam.databinding.ActivityMainBinding
import ir.apptune.antispam.features.service.ForegroundService
import ir.apptune.antispam.utils.getStatusBarHeight

/**
 * We use SingleActivity approach
 *
 */
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ContextCompat.startForegroundService(this, Intent(this, ForegroundService::class.java))
        setUpBottomNav()
        setSupportActionBar(binding.toolbar)
        setStatusBar()
    }

    private fun setUpBottomNav() {
        binding.bottomNav.setupWithNavController(findNavController(R.id.navFragment))
        findNavController(R.id.navFragment).addOnDestinationChangedListener { _: NavController, navDestination: NavDestination, _: Bundle? ->
            when (navDestination.id) {
                R.id.splashFragment -> hideToolbarBottomNav()
                R.id.introFragment -> hideBottomNav()
                else -> showToolbarBottomNav()
            }
        }
    }

    private fun hideBottomNav() = with(binding) {
        bottomNav.visibility = View.GONE
        toolbar.visibility = View.VISIBLE
    }

    private fun showToolbarBottomNav() = with(binding) {
        bottomNav.visibility = View.VISIBLE
        toolbar.visibility = View.VISIBLE
    }

    private fun hideToolbarBottomNav() = with(binding) {
        bottomNav.visibility = View.GONE
        toolbar.visibility = View.GONE
    }

    private fun setStatusBar() = with(binding) {
        val statusBarHeight = getStatusBarHeight(this@MainActivity)
        viewStatusBar.layoutParams =
            ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
        viewStatusBar.background =
            ContextCompat.getDrawable(this@MainActivity, R.drawable.toolbar_shape)
    }
}
