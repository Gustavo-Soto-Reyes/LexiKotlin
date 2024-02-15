package com.example.dictionary

import MeaningCard
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DefinitionPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DefinitionPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_definition_page, container, false)
        val fragPos: TextView = v.findViewById(R.id.frag_pos)
        val fragTitle: TextView = v.findViewById(R.id.frag_title)
        fragPos.text = param1
        fragTitle.text = param2
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DefinitionPage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(meaningCard: MeaningCard): DefinitionPageFragment {
            return DefinitionPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, meaningCard.title)
                    putString(ARG_PARAM2, meaningCard.pos)
                }
            }
        }
    }
}