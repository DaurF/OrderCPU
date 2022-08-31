package mob.dau.ordercpu.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mob.dau.ordercpu.R
import mob.dau.ordercpu.databinding.FragmentCpuModelsBinding
import mob.dau.ordercpu.model.CpuViewModel
import java.text.NumberFormat

class ProcessorModelFragment : Fragment() {
    private val sharedViewModel: CpuViewModel by activityViewModels()

    private var _binding: FragmentCpuModelsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCpuModelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            nextButton.setOnClickListener { goToTheNextScreen() }
        }
            when (sharedViewModel.cpuManufacturer.value) {
                getString(R.string.amd_label) -> {
                    binding.amdModelOptions.visibility = View.VISIBLE
                }
                else -> {
                    binding.intelModelOptions.visibility = View.VISIBLE
                }
            }
        sharedViewModel.pricePerUnit.observe(viewLifecycleOwner) { newPrice ->
            val formattedPrice = NumberFormat.getCurrencyInstance().format(newPrice)
            binding.priceTextView.text = getString(R.string.price_label, formattedPrice)
        }

        binding.cancelButton.setOnClickListener { cancelOrder() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToTheNextScreen() {
        findNavController().navigate(R.id.action_processorModelFragment_to_amountFragment)
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_processorModelFragment_to_manufacturerFragment)
    }
}