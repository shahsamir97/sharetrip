package net.sharetrip.flight.history.view.imagepreview

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.sharetrip.flight.shared.utils.setNavigationResult
import net.sharetrip.flight.shared.view.BaseFragment
import com.sharetrip.base.viewmodel.BaseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.sharetrip.flight.R
import net.sharetrip.flight.databinding.FragmentImagePreviewInFlightBinding
import net.sharetrip.flight.history.FlightHistoryActivity
import net.sharetrip.flight.utils.ARG_FOR_CONFIRMATION
import net.sharetrip.flight.utils.ARG_IMAGE_DATA
import net.sharetrip.flight.utils.ARG_IMAGE_TAG
import net.sharetrip.flight.utils.ARG_IMAGE_URL

class ImagePreviewFragment : BaseFragment<FragmentImagePreviewInFlightBinding>() {

    val zoomView = ZoomInOutView(null)

    override fun layoutId(): Int = R.layout.fragment_image_preview_in_flight

    override fun getViewModel(): BaseViewModel? = null

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun initOnCreateView() {
        val imageUrl = requireArguments().getBundle(ARG_IMAGE_DATA)?.getString(ARG_IMAGE_URL)
        val title = requireArguments().getBundle(ARG_IMAGE_DATA)?.getString(ARG_IMAGE_TAG)!!
        val isConfirmation = requireArguments().getBundle(ARG_IMAGE_DATA)?.getBoolean(
            ARG_FOR_CONFIRMATION
        )

        if (isConfirmation == true) {
            setTitle(title)
            if (imageUrl!!.contains("pdf")) {
                bindingView.webView.visibility = VISIBLE
                bindingView.imageView.visibility = GONE
                bindingView.webView.settings.javaScriptEnabled = true
                bindingView.webView.loadUrl("https://docs.google.com/viewer?embedded%20=%20true&url=$imageUrl")
                bindingView.progressBar.visibility = GONE
            } else {
                Glide.with(requireContext()).load(imageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            bindingView.progressBar.visibility = GONE
                            zoomView.setOriginalValue()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            bindingView.progressBar.visibility = GONE
                            return false
                        }
                    })
                    .into(bindingView.imageView)
                zoomView.setView(bindingView.imageView)
            }
        }
        else
        {
            setTitle(title)
            bindingView.confirmationLayout.visibility = GONE
            if (imageUrl!!.contains("pdf")) {
                bindingView.webView.visibility = VISIBLE
                bindingView.imageView.visibility = GONE
                bindingView.webView.settings.javaScriptEnabled = true
                bindingView.webView.loadUrl("https://docs.google.com/viewer?embedded%20=%20true&url=$imageUrl")
                bindingView.progressBar.visibility = GONE
            } else {
                Glide.with(requireContext()).load(imageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            bindingView.progressBar.visibility = GONE
                            zoomView.setOriginalValue()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            bindingView.progressBar.visibility = GONE
                            return false
                        }
                    })
                    .into(bindingView.imageView)
                zoomView.setView(bindingView.imageView)
            }
        }

        bindingView.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

        bindingView.cancelButton.setOnClickListener {
            findNavController().navigateUp()
            setNavigationResult(true, "confirmation")
        }
    }

    @DelicateCoroutinesApi
    override fun onStop() {
        super.onStop()
        GlobalScope.launch {
            Glide.get(requireContext()).clearDiskCache()
        }
    }

    private fun setTitle(title: String) {
        (activity as FlightHistoryActivity).supportActionBar?.title = title
    }

    /*companion object {
        const val ARG_IMAGE_URL = "ARG_IMAGE_URL"
        const val ARG_IMAGE_TAG = "ARG_IMAGE_TAG"
        const val ARG_FOR_CONFIRMATION = "ARG_FOR_CONFIRMATION"
    }*/
}
