package ir.apptune.antispam.features.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import ir.apptune.antispam.R
import ir.apptune.antispam.utils.getWarningList
import kotlinx.android.synthetic.main.search_phone_fragment.*
import org.koin.android.ext.android.inject

/**
 * A page to search a phone number to find its address
 *
 */
class SearchPhoneFragment : Fragment(R.layout.search_phone_fragment) {

    private val viewModel: SearchPhoneViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWarningList()

        viewModel.getPhoneNumberDetailsLiveData().observe(viewLifecycleOwner) {
            txtPhoneNumberDetails.text = it
        }

        btnSearch.setOnClickListener {
            viewModel.searchLocation(edtSearch.text.toString())
        }
        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                viewModel.searchLocation(edtSearch.text.toString())
            true
        }
    }

    private fun setupWarningList() {
        val adapter = WarningsAdapter(getWarningList(requireContext()))
        recycler.adapter = adapter
    }


}