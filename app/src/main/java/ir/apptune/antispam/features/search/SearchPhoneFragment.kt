package ir.apptune.antispam.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import ir.apptune.antispam.databinding.SearchPhoneFragmentBinding
import ir.apptune.antispam.utils.getWarningList
import org.koin.android.ext.android.inject

/**
 * A page to search a phone number to find its address
 *
 */
class SearchPhoneFragment : Fragment() {

    private var _binding: SearchPhoneFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchPhoneViewModel by inject()

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
        setupWarningList()

        viewModel.getPhoneNumberDetailsLiveData().observe(viewLifecycleOwner) {
            binding.txtPhoneNumberDetails.text = it
        }

        binding.btnSearch.setOnClickListener {
            viewModel.searchLocation(binding.edtSearch.text.toString())
        }
        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                viewModel.searchLocation(binding.edtSearch.text.toString())
            true
        }
    }

    private fun setupWarningList() {
        val adapter = WarningsAdapter(getWarningList(requireContext()))
        binding.recycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}