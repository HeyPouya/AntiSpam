package ir.apptune.antispam.features.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.apptune.antispam.R
import ir.apptune.antispam.databinding.FragmentAboutMeBinding

private const val GITHUB_URL = "https://github.com/SirLordPouya/AntiSpam"
private const val PERSONAL_WEBSITE_URL = "https://pouyaheydari.com"
private const val LINKED_IN_URL = "https://linkedin.com/in/pouyaheydari/"

/**
 * A fragment only to show some about me info
 */
class AboutMeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAboutMeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * sets up click listeners
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            imgGithub.setOnClickListener(this@AboutMeFragment)
            imgLinkedin.setOnClickListener(this@AboutMeFragment)
            imgWebsite.setOnClickListener(this@AboutMeFragment)
        }
    }

    /**
     * performs clicks on social buttons
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgGithub -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL)))
            R.id.imgLinkedin -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(LINKED_IN_URL)))
            R.id.imgWebsite -> startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(PERSONAL_WEBSITE_URL)
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
