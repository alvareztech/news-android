package tech.alvarez.news

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : Fragment() {

    private lateinit var behavior: BottomSheetBehavior<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        behavior = BottomSheetBehavior.from(filterView)
        // TODO: Get real bottom sheet

        collapseImageButton.setOnClickListener {
            behavior.state = if (behavior.skipCollapsed) STATE_HIDDEN else STATE_COLLAPSED
        }

        expandView.setOnClickListener {
            behavior.state = STATE_EXPANDED
        }
    }
}
