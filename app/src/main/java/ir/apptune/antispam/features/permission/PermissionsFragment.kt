package ir.apptune.antispam.features.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import ir.apptune.antispam.R
import ir.apptune.antispam.databinding.PermissionsFragmentBinding
import ir.apptune.antispam.utils.checkNeededPermissions

/**
 * This page shows a list of permissions that are required by the app, but user hasn't given the permission yet
 *
 */
class PermissionsFragment : Fragment() {

    private var _binding: PermissionsFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter: PermissionsAdapter by lazy {
        PermissionsAdapter {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(it),
                100
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PermissionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            checkPermissionsStatus()
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermissionsStatus()
    }

    private fun checkPermissionsStatus() {
        val list = checkNeededPermissions(requireContext())
        if (list.isEmpty())
            showEmptyState()
        else {
            hideEmptyState()
            adapter.submitList(list)
        }
    }

    private fun hideEmptyState() = with(binding) {
        recycler.visibility = View.VISIBLE
        layoutEmptyState.layoutCallLogEmpty.visibility = View.GONE
    }

    private fun showEmptyState() = with(binding) {
        recycler.visibility = View.GONE
        layoutEmptyState.layoutCallLogEmpty.visibility = View.VISIBLE
        layoutEmptyState.txtCallLogEmpty.text = getString(R.string.all_permissions_are_granted)
        layoutEmptyState.imgCallLogEmpty.setImageResource(R.drawable.ic_check)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}