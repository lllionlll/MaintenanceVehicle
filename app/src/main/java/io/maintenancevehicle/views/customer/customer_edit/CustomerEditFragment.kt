package io.maintenancevehicle.views.customer.customer_edit

import android.R
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.databinding.FragmentCustomerEditBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_add.CustomerAddRoute

@AndroidEntryPoint
class CustomerEditFragment : BaseFragment<FragmentCustomerEditBinding>(
    FragmentCustomerEditBinding::inflate
) {
    private val customerEditViewModel by viewModels<CustomerEditViewModel>()
    private lateinit var idCustomer: String
    private lateinit var customer: Customer
    private val genderList = mutableListOf("Nam", "Nữ", "Khác")
    override fun initData() {
        super.initData()
        idCustomer = arguments?.getString("id").toString()
        customerEditViewModel.getCustomerDetail(idCustomer)
        binding.gender.adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, genderList)
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerEditRoute.backScreen(this)
        }

        binding.birthday.setOnClickListener {

            DateFunction.showDatePicker(requireContext()) { date ->
                binding.birthday.setText(
                    DateFunction.formatDate(
                        date,
                        "dd/MM/yyyy"
                    )
                )
            }
        }
        binding.dayCreate.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.dayCreate.setText(
                    DateFunction.formatDate(
                        date,
                        "dd/MM/yyyy"
                    )
                )
            }
        }
        binding.dayUpdate.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.dayUpdate.setText(
                    DateFunction.formatDate(
                        date,
                        "dd/MM/yyyy"
                    )
                )
            }
        }
        binding.btnSave.setOnClickListener {
            customer.birthday = binding.birthday.text.toString()
            customer.name = binding.name.text.toString()
            customer.createdAt = binding.dayCreate.text.toString()
            customer.updatedAt = binding.dayUpdate.text.toString()
            customer.gender = genderList[binding.gender.selectedItemPosition]
            customer.homeTown = binding.address.text.toString()
            customer.phoneNumber = binding.phone.text.toString()
            customer.note = binding.note.text.toString()
            customerEditViewModel.editCustomer(customer)
        }
    }

    override fun observerData() {
        super.observerData()
        customerEditViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }
        customerEditViewModel.isEdit.observe(viewLifecycleOwner) { isEdit->
            CustomerAddRoute.backScreen(this)
        }
        customerEditViewModel.customer.observe(viewLifecycleOwner) { customer ->
            this.customer = customer
            binding.name.setText(customer.name)
            for (i in 1..genderList.size) {
                if (genderList[i] == customer.gender) {
                    binding.gender.setSelection(i)
                    break
                }
            }
            binding.phone.setText(customer.phoneNumber)
            binding.address.setText(customer.homeTown)
            binding.birthday.setText(customer.birthday)
            binding.dayCreate.setText(customer.createdAt)
            binding.dayUpdate.setText(customer.updatedAt)
            binding.note.setText(customer.note)
            Glide.with(binding.avatar.context)
                .load(customer.imageUrl)
                .into(binding.avatar)
        }
    }

}