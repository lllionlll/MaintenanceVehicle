package io.maintenancevehicle.views.vehicle

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.maintenancevehicle.R
import io.maintenancevehicle.bases.BaseAdapter
import io.maintenancevehicle.data.model.Vehicle
import io.maintenancevehicle.databinding.ItemVehicleBinding

class VehicleAdapter(
    val onClickVehicleDetail: (Vehicle) -> Unit
) : BaseAdapter<Vehicle, ItemVehicleBinding>() {

    inner class VehicleViewHolder(itemVehicleBinding: ItemVehicleBinding) :
        BaseViewHolder(itemVehicleBinding) {

        init {
            binding.root.setOnClickListener {
                onClickVehicleDetail.invoke(itemList[layoutPosition])
            }
        }

        override fun setData(item: Vehicle) {
            binding.name.text = ""
            binding.name.text = item.name

            Glide.with(binding.image.context)
                .load(R.mipmap.ic_launcher)
                .into(binding.image)

            Glide.with(binding.image.context)
                .load(item.image)
                .into(binding.image)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVehicleBinding.inflate(inflater, parent, false)
        return VehicleViewHolder(binding)
    }

}