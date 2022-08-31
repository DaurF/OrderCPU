package mob.dau.ordercpu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mob.dau.ordercpu.R
import mob.dau.ordercpu.databinding.FragmentAmountBinding
import mob.dau.ordercpu.model.CpuViewModel

class AmountFragment : Fragment() {
    private val sharedViewModel: CpuViewModel by activityViewModels()

    private var _binding: FragmentAmountBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAmountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = sharedViewModel

        binding.nextButton.setOnClickListener { checkTotalPrice() }
        binding.cancelButton.setOnClickListener { cancelOrder() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkTotalPrice() {
        findNavController().navigate(R.id.action_amountFragment_to_totalFragment)
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_amountFragment_to_manufacturerFragment)
    }
}