package io.maintenancevehicle.views.customer.customer_management

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.maintenancevehicle.bases.BaseAdapter
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.databinding.ItemCustomerBinding

class CustomerManagementAdapter(
    val onClickCustomerDetail: (Customer) -> Unit
): BaseAdapter<Customer, ItemCustomerBinding>() {

    inner class CustomerViewHolder(itemCustomerBinding: ItemCustomerBinding) : BaseViewHolder(itemCustomerBinding) {

        init {
            binding.customerDetail.setOnClickListener {
                onClickCustomerDetail.invoke(itemList[layoutPosition])
            }
        }

        override fun setData(item: Customer) {
            binding.customerName.text = ""
            binding.customerName.text = item.name

            binding.phoneNumber.text = ""
            binding.phoneNumber.text = item.phoneNumber

            Glide.with(binding.imgCustomer.context)
                .load(item.imageUrl)
                .into(binding.imgCustomer)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCustomerBinding.inflate(inflater, parent, false)
        return CustomerViewHolder(binding)
    }

}