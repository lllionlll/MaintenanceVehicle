package io.maintenancevehicle.views.maintenance_vehicle_view.maintenance_vehicle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import io.maintenancevehicle.bases.BaseAdapter
import io.maintenancevehicle.data.model.VehicleDetail
import io.maintenancevehicle.databinding.ItemVehicleDetailBinding

class MaintenanceVehicleAdapter : BaseAdapter<VehicleDetail, ItemVehicleDetailBinding>() {

    inner class VehicleViewHolder(binding: ItemVehicleDetailBinding) : BaseViewHolder(binding) {

        init {
            binding.root.setOnClickListener {
                binding.moreDetail.isVisible = !binding.moreDetail.isVisible
            }
        }

        override fun setData(item: VehicleDetail) {
            binding.vehicleId.text = ""
            binding.vehicleId.text = item.id

            binding.status.text = ""
            binding.status.text = item.status

            binding.reason.text = ""
            binding.reason.text = item.reason

            binding.solution.text = ""
            binding.solution.text = item.solution

            binding.supervisor.text = ""
            binding.supervisor.text = item.supervisor

            binding.createdAt.text = ""
            binding.createdAt.text = item.createdAt

            binding.note.text = ""
            binding.note.text = item.note
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVehicleDetailBinding.inflate(inflater, parent, false)
        return VehicleViewHolder(binding)
    }

}