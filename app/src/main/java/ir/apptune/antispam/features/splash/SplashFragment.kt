package ir.apptune.antispam.features.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.apptune.antispam.R
import ir.apptune.antispam.databinding.SearchPhoneFragmentBinding
import org.koin.android.ext.android.inject

const val FIRST_LOGIN = "FirstLogin"

/**
 * Splash page
 *
 */
class SplashFragment : Fragment() {

    private var _binding: SearchPhoneFragmentBinding? = null
    private val binding get() = _binding!!
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchPhoneFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (sharedPreferences.getBoolean(FIRST_LOGIN, true)) {
            findNavController().navigate(R.id.action_splashFragment_to_introFragment)
            sharedPreferences.edit().putBoolean(FIRST_LOGIN, false).apply()
        } else
            findNavController().navigate(R.id.action_splashFragment_to_callLogsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
