package com.boltuix.androidpreferences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.boltuix.androidpreferences.AppIntro.Companion.MAX_STEP
import com.boltuix.androidpreferences.databinding.IntroAppContentBinding
import com.boltuix.androidpreferences.databinding.IntroAppDesignBinding
import com.google.android.material.tabs.TabLayoutMediator

class AppIntro : Fragment() {
    private var _binding: IntroAppContentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.intro_app_content, container, false)
        _binding = IntroAppContentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //............................................................
        binding.viewPager2.adapter = AppIntroViewPager2Adapter()

        //............................................................
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
        }.attach()

        //............................................................
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if(position== MAX_STEP -1){
                    binding.btnNext.text                =   getString(R.string.intro_get_started)//"Get Started"
                    binding.btnNext.contentDescription  =   getString(R.string.intro_get_started)//"Get Started"
                }else{
                    binding.btnNext.text                  = getString(R.string.intro_next)//"Next"
                    binding.btnNext.contentDescription    = getString(R.string.intro_next)//"Next"
                }
            }
        })


        //............................................................
        binding.btnSkip.setOnClickListener {
            findNavController().navigateUp()
        }

        //............................................................
        binding.btnNext.setOnClickListener {
            if(binding.btnNext.text.toString()==getString(R.string.intro_get_started)){
                findNavController().navigateUp()
            }
            else{
                // to change current page - on click "Next BUTTON"
                val current = (binding.viewPager2.currentItem) + 1
                binding.viewPager2.currentItem = current

                // to update button text
                if(current>= MAX_STEP -1){
                    binding.btnNext.text                =   getString(R.string.intro_get_started)//"Get Started"
                    binding.btnNext.contentDescription  =   getString(R.string.intro_get_started)//"Get Started"

                }else{
                    binding.btnNext.text                =   getString(R.string.intro_next)//"Next"
                    binding.btnNext.contentDescription  =   getString(R.string.intro_next)//"Next"
                }
            }
        }
    }
    companion object {
        const val MAX_STEP = 3
    }
}
//...............................................................................


//................................................................................
class AppIntroViewPager2Adapter : RecyclerView.Adapter<PagerVH2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH2 {
        return PagerVH2(
            IntroAppDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //get the size of color array
    override fun getItemCount(): Int = MAX_STEP // Int.MAX_VALUE

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH2, position: Int) = holder.itemView.run {

        with(holder) {
            if (position == 0) {
                bindingDesign.introTitle.text = context.getString(R.string.intro_title_1)
                bindingDesign.introDescription.text = context.getString(R.string.intro_description_1)
                bindingDesign.introImage.setImageResource(R.drawable.intro_ic_a_day_at_the_park)
            }
            if (position == 1) {
                bindingDesign.introTitle.text = context.getString(R.string.intro_title_2)
                bindingDesign.introDescription.text = context.getString(R.string.intro_description_2)
                bindingDesign.introImage.setImageResource(R.drawable.intro_ic_directions)
            }
            if (position == 2) {
                bindingDesign.introTitle.text = context.getString(R.string.intro_title_3)
                bindingDesign.introDescription.text = context.getString(R.string.intro_description_3)
                bindingDesign.introImage.setImageResource(R.drawable.intro_ic_hang_out)
            }
        }
    }
}
class PagerVH2(val bindingDesign: IntroAppDesignBinding) : RecyclerView.ViewHolder(bindingDesign.root)

