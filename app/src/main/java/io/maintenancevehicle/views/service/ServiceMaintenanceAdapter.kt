package io.maintenancevehicle.views.service

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.maintenancevehicle.R
import io.maintenancevehicle.bases.BaseAdapter
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.databinding.ItemServiceBinding

class ServiceMaintenanceAdapter(
    val onClickServiceDetail: (Service) -> Unit
) : BaseAdapter<Service, ItemServiceBinding>() {

    inner class ServiceViewHolder(itemServiceBinding: ItemServiceBinding) : BaseViewHolder(itemServiceBinding) {

        init {
            binding.root.setOnClickListener {
                onClickServiceDetail.invoke(itemList[layoutPosition])
            }
        }

        override fun setData(item: Service) {
            binding.serviceName.text = ""
            binding.serviceName.text = item.name

            Glide.with(binding.serviceImage.context)
                .load(R.mipmap.ic_launcher)
                .into(binding.serviceImage)

            Glide.with(binding.serviceImage.context)
                .load(item.image)
                .into(binding.serviceImage)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemServiceBinding.inflate(inflater, parent, false)
        return ServiceViewHolder(binding)
    }

}