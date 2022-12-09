package ir.apptune.antispam.features.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.apptune.antispam.R
import ir.apptune.antispam.databinding.FragmentIntroBinding

/**
 * A page that asks the user to give the application needed permissions
 *
 */
class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSkip.setOnClickListener { findNavController().navigate(R.id.action_introFragment_to_callLogsFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}