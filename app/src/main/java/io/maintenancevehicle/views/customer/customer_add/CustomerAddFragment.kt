package io.maintenancevehicle.views.customer.customer_add

import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.databinding.FragmentCustomerAddBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog

private const val TAG = "CustomerAddFragment"

@AndroidEntryPoint
class CustomerAddFragment : BaseFragment<FragmentCustomerAddBinding>(
    FragmentCustomerAddBinding::inflate
) {

    private val customerAddViewModel by viewModels<CustomerAddViewModel>()
    private var customer = Customer()
    private val genderList = mutableListOf("Nam", "Nữ", "Khác")
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try {
            customer.imageUrl = galleryUri.toString()
            binding.avatar.setImageURI(galleryUri)
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}: ")
        }
    }

    override fun initData() {
        super.initData()
        binding.gender.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderList)
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerAddRoute.backScreen(this)
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
            customerAddViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    LoadingDialog.showLoading(requireContext())
                } else {
                    LoadingDialog.hide()
                    CustomerAddRoute.backScreen(this)
                }
            }
            customer.birthday = binding.birthday.text.toString()
            customer.name = binding.name.text.toString()
            customer.createdAt = binding.dayCreate.text.toString()
            customer.updatedAt = binding.dayUpdate.text.toString()
            customer.gender = genderList[binding.gender.selectedItemPosition]
            customer.homeTown = binding.address.text.toString()
            customer.phoneNumber = binding.phone.text.toString()
            customer.note = binding.note.text.toString()
            customerAddViewModel.addCustomer(customer)

        }
        binding.avatar.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        fun check(): Boolean {
            var checkField = true;
            binding.apply {
                if (name.text.isEmpty()) {
                    name.error = "Vui lòng nhập tên!"
                    checkField = false
                }
                if (address.text.isEmpty()) {
                    address.error = "Vui lòng nhập địa chỉ!"
                    checkField = false
                }
                if (phone.text.isEmpty()) {
                    phone.error = "Vui lòng điền số điện thoại!"
                    checkField = false
                }
                if (birthday.text.isEmpty()) {
                    birthday.error = "Vui lòng chọn ngày sinh!"
                    return false
                }
                if (dayCreate.text.isEmpty()) {
                    dayCreate.error = "Vui lòng chọn ngày tạo!"
                    return false
                }
                if (dayUpdate.text.isEmpty()) {
                    dayUpdate.error = "Vui lòng chọn ngày cập nhật!"
                    return false
                }
            }
            return checkField
        }
    }
}