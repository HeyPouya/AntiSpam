package ir.apptune.antispam.features.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.apptune.antispam.R
import org.koin.android.ext.android.inject

const val FIRST_LOGIN = "FirstLogin"

class SplashFragment : Fragment() {

    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (sharedPreferences.getBoolean(FIRST_LOGIN, false)) {

            //TODO: Make permission introduction pages
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_callLogsFragment)
        }
    }
}