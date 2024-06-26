package io.maintenancevehicle.views.customer.customer_detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.databinding.FragmentCustomerDetailBinding
import io.maintenancevehicle.utils.ConfirmDialog
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_management.CustomerManagementRoute

@AndroidEntryPoint
class CustomerDetailFragment : BaseFragment<FragmentCustomerDetailBinding>(
    FragmentCustomerDetailBinding::inflate
) {

    private val customerDetailViewModel by viewModels<CustomerDetailViewModel>()
    private val safeVarargs by navArgs<CustomerDetailFragmentArgs>()

    private var customer = Customer()

    override fun initView() {
        super.initView()

        customerDetailViewModel.getCustomerDetail(
            customerId = safeVarargs.customerId
        )
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerManagementRoute.backScreen(this)
        }

        binding.customerDelete.setOnClickListener {
            ConfirmDialog.show(
                requireContext(),
                onConfirm = {
                    customerDetailViewModel.deleteCustomer(
                        requireContext(),
                        customerId = customer.customerId
                    )
                }
            )
        }

        binding.customerEdit.setOnClickListener {
            CustomerDetailRoute.goToCustomerEdit(this, customer.customerId)
        }

        binding.avatar.setOnClickListener {
            CustomerDetailRoute.goToImagePreview(
                this,
                customerId = customer.customerId
            )
        }
    }

    override fun observerData() {
        super.observerData()

        customerDetailViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        customerDetailViewModel.customer.observe(viewLifecycleOwner) { customer ->
            this.customer = customer
            binding.txtId.text = customer.customerId
            binding.name.text = customer.name
            binding.gender.text = customer.gender
            binding.phoneNumber.text = customer.phoneNumber
            binding.homeTown.text = customer.homeTown
            binding.birthday.text = customer.birthday
            binding.createdAt.text = customer.createdAt
            binding.updatedAt.text = customer.updatedAt
            binding.note.text = customer.note
            Glide.with(requireContext())
                .load(customer.imageUrl)
                .into(binding.avatar)
            binding.scrollView.visibility = View.VISIBLE
        }

        customerDetailViewModel.isDeleteSuccess.observe(viewLifecycleOwner) {
            if (it) {
                CustomerDetailRoute.backScreen(this)
            }
        }
    }
}