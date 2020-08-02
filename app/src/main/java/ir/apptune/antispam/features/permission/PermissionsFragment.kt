package ir.apptune.antispam.features.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import ir.apptune.antispam.R
import ir.apptune.antispam.utils.checkNeededPermissions
import kotlinx.android.synthetic.main.empty_state_layout.*
import kotlinx.android.synthetic.main.permissions_fragment.*

class PermissionsFragment : Fragment() {

    private val adapter: PermissionsAdapter by lazy { PermissionsAdapter { ActivityCompat.requestPermissions(requireActivity(), arrayOf(it), 100) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.permissions_fragment, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler.adapter = adapter
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

    private fun hideEmptyState() {
        recycler.visibility = View.VISIBLE
        layoutCallLogEmpty.visibility = View.GONE
    }

    private fun showEmptyState() {
        recycler.visibility = View.GONE
        layoutCallLogEmpty.visibility = View.VISIBLE
        txtCallLogEmpty.text = getString(R.string.all_permissions_are_granted)
        imgCallLogEmpty.setImageResource(R.drawable.ic_check)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        checkPermissionsStatus()
    }
}