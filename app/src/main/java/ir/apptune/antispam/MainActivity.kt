package ir.apptune.antispam

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import ir.apptune.antispam.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, ShakerService::class.java))
        setSupportActionBar(toolbar)
        setStatusBar()

    }

    private fun setStatusBar() {
        val statusBarHeight = getStatusBarHeight(this)
        viewStatusBar.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
        viewStatusBar.background = ContextCompat.getDrawable(this, R.drawable.toolbar_shape)
    }
}