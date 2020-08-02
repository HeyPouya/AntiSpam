package ir.apptune.antispam.features.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.apptune.antispam.R
import ir.apptune.antispam.utils.checkNeededPermissions
import kotlinx.android.synthetic.main.permissions_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PermissionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.permissions_fragment, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = PermissionsAdapter(checkNeededPermissions(requireContext()))
        recycler.adapter = adapter

    }
}