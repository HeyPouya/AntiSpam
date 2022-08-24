package ir.apptune.antispam.features.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ir.apptune.antispam.R
import ir.apptune.antispam.utils.GITHUB_URL
import ir.apptune.antispam.utils.LINKED_IN_URL
import ir.apptune.antispam.utils.PERSONAL_WEBSITE_URL
import kotlinx.android.synthetic.main.fragment_about_me.*

/**
 * A fragment only to show some about me info
 */
class AboutMeFragment : Fragment(R.layout.fragment_about_me), View.OnClickListener {

    /**
     * sets up click listeners
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgGithub.setOnClickListener(this)
        imgLinkedin.setOnClickListener(this)
        imgWebsite.setOnClickListener(this)
    }

    /**
     * performs clicks on social buttons
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgGithub -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL)))
            R.id.imgLinkedin -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(LINKED_IN_URL)))
            R.id.imgWebsite -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PERSONAL_WEBSITE_URL)))
        }
    }
}
