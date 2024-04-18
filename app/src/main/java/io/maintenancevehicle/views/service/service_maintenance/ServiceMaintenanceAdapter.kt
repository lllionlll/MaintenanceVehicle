package io.maintenancevehicle.views.service.service_maintenance

import android.view.LayoutInflater
import android.view.ViewGroup
import io.maintenancevehicle.bases.BaseAdapter
import io.maintenancevehicle.data.model.Service
import io.maintenancevehicle.databinding.ItemServiceBinding

class ServiceMaintenanceAdapter : BaseAdapter<Service, ItemServiceBinding>() {

    inner class ServiceViewHolder(itemServiceBinding: ItemServiceBinding) : BaseViewHolder(itemServiceBinding) {
        override fun setData(item: Service) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemServiceBinding.inflate(inflater, parent, false)
        return ServiceViewHolder(binding)
    }

}