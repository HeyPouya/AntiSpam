package ir.apptune.antispam.features.callog

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ir.apptune.antispam.R
import ir.apptune.antispam.databinding.FragmentCallHistoryBinding
import ir.apptune.antispam.features.callog.adapter.CallLogsAdapter
import ir.apptune.antispam.pojos.LiveDataResource
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * This page shows all call logs of the user
 *
 */
class CallLogsFragment : Fragment() {

    private var _binding: FragmentCallHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CallDetailsViewModel by viewModel()
    private val adapter by lazy { CallLogsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCallHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter

        if (savedInstanceState == null)
            viewModel.getCallLog(
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_CALL_LOG
                ) == PackageManager.PERMISSION_GRANTED
            )

        viewModel.getLiveDataResponse().observe(viewLifecycleOwner) {
            when (it) {
                is LiveDataResource.Loading -> binding.prgLoading.visibility = View.VISIBLE
                is LiveDataResource.Success -> adapter.submitList(it.callModel)
                is LiveDataResource.Completed -> {
                    hideLoading()
                    adapter.submitList(it.callModel)
                    if (it.callModel.isEmpty())
                        showEmptyState()
                }
                is LiveDataResource.NoCallLogPermission -> showNoPermissionState()
            }

        }
    }

    private fun showNoPermissionState() = with(binding) {
        prgLoading.visibility = View.GONE
        recycler.visibility = View.GONE
        layoutEmptyState.layoutCallLogEmpty.visibility = View.VISIBLE
        layoutEmptyState.txtCallLogEmpty.text =
            getString(R.string.please_provide_call_log_permission_to_see_the_call_log)
    }

    private fun showEmptyState() = with(binding) {
        prgLoading.visibility = View.GONE
        recycler.visibility = View.GONE
        layoutEmptyState.layoutCallLogEmpty.visibility = View.VISIBLE
    }

    private fun hideLoading() = with(binding) {
        prgLoading.visibility = View.GONE
        layoutEmptyState.layoutCallLogEmpty.visibility = View.GONE
        recycler.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
