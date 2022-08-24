package ir.apptune.antispam.features.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.apptune.antispam.R
import org.koin.android.ext.android.inject

const val FIRST_LOGIN = "FirstLogin"

/**
 * Splash page
 *
 */
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val sharedPreferences: SharedPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (sharedPreferences.getBoolean(FIRST_LOGIN, true)) {
            findNavController().navigate(R.id.action_splashFragment_to_introFragment)
            sharedPreferences.edit().putBoolean(FIRST_LOGIN, false).apply()
        } else
            findNavController().navigate(R.id.action_splashFragment_to_callLogsFragment)
    }
}