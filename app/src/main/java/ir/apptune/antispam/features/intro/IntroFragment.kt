package ir.apptune.antispam.features.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.apptune.antispam.R
import kotlinx.android.synthetic.main.fragment_intro.*

/**
 * A page that asks the user to give the application needed permissions
 *
 */
class IntroFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txtSkip.setOnClickListener { findNavController().navigate(R.id.action_introFragment_to_callLogsFragment) }
    }
}