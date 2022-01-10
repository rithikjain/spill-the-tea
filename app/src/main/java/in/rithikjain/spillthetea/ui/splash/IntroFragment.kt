package `in`.rithikjain.spillthetea.ui.splash

import `in`.rithikjain.spillthetea.R
import `in`.rithikjain.spillthetea.databinding.FragmentIntroBinding
import `in`.rithikjain.spillthetea.databinding.FragmentProfileBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController


class IntroFragment : Fragment() {
    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnIntroStart.setOnClickListener{
            findNavController().navigate(R.id.action_introFragment_to_introProfileFragment)
        }
    }


}