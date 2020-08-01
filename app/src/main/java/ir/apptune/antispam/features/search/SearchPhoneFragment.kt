package ir.apptune.antispam.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ir.apptune.antispam.R
import ir.apptune.antispam.features.about.WarningsAdapter
import ir.apptune.antispam.utils.getWarningList
import kotlinx.android.synthetic.main.search_phone_fragment.*
import org.koin.android.ext.android.inject

class SearchPhoneFragment : Fragment() {

    private val viewModel: SearchPhoneViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_phone_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupWarningList()

        viewModel.getPhoneNumberDetailsLiveData().observe(viewLifecycleOwner, Observer {
            txtPhoneNumberDetails.text = it
        })

        btnSearch.setOnClickListener {
            viewModel.searchLocation(edtSearch.text.toString())
        }
    }

    private fun setupWarningList() {
        val adapter = WarningsAdapter(getWarningList(requireContext()))
        recycler.adapter = adapter
    }


}