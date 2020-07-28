package ir.apptune.antispam.features.callog

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ir.apptune.antispam.R
import ir.apptune.antispam.features.callog.adapter.CallHistoryAdapter
import ir.apptune.antispam.pojos.LiveDataResource
import kotlinx.android.synthetic.main.empty_state_layout.*
import kotlinx.android.synthetic.main.fragment_call_history.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class CallDetailsFragment : Fragment() {

    @ExperimentalCoroutinesApi
    private val viewModel: CallDetailsViewModel by viewModel()
    private val adapter by lazy { CallHistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_call_history, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler.adapter = adapter

        if (savedInstanceState == null)
            viewModel.getCallLog(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED)

        viewModel.getLiveDataResponse().observe(viewLifecycleOwner, Observer {
            when (it) {
                is LiveDataResource.Loading -> prgLoading.visibility = View.VISIBLE
                is LiveDataResource.Success -> adapter.submitList(it.callModel)
                is LiveDataResource.Completed -> {
                    hideLoading()
                    adapter.submitList(it.callModel)
                    if (it.callModel.isEmpty())
                        showEmptyState()
                }
                is LiveDataResource.NoCallLogPermission -> showNoPermissionState()
            }

        })
    }

    private fun showNoPermissionState() {
        prgLoading.visibility = View.GONE
        recycler.visibility = View.GONE
        layoutCallLogEmpty.visibility = View.VISIBLE
        txtCallLogEmpty.text = getString(R.string.please_provide_call_log_permission_to_see_the_call_log)
    }

    private fun showEmptyState() {
        prgLoading.visibility = View.GONE
        recycler.visibility = View.GONE
        layoutCallLogEmpty.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        prgLoading.visibility = View.GONE
        layoutCallLogEmpty.visibility = View.GONE
        recycler.visibility = View.VISIBLE
    }
}