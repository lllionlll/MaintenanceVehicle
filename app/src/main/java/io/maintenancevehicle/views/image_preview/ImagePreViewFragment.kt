package io.maintenancevehicle.views.image_preview

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentImagePreviewBinding

@AndroidEntryPoint
class ImagePreViewFragment : BaseFragment<FragmentImagePreviewBinding>(
    FragmentImagePreviewBinding::inflate
) {

    override fun handleEvent() {
        super.handleEvent()

        binding.close.setOnClickListener {
            ImagePreViewRoute.backScreen(this)
        }
    }

}