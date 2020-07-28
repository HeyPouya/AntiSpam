package ir.apptune.antispam.features.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.apptune.antispam.R

class SearchPhoneFragment : Fragment() {

    companion object {
        fun newInstance() = SearchPhoneFragment()
    }

    private lateinit var viewModel: SearchPhoneViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_phone_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchPhoneViewModel::class.java)
        // TODO: Use the ViewModel
    }

}