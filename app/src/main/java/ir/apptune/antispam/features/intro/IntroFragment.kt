package ir.apptune.antispam.features.intro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.apptune.antispam.R
import kotlinx.android.synthetic.main.fragment_intro.*

/**
 * A page that asks the user to give the application needed permissions
 *
 */
class IntroFragment : Fragment(R.layout.fragment_intro) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtSkip.setOnClickListener { findNavController().navigate(R.id.action_introFragment_to_callLogsFragment) }
    }
}