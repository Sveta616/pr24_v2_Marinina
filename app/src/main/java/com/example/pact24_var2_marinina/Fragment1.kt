package com.example.pact24_var2_marinina

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1 : Fragment() {
    private lateinit var navController: NavController
    private lateinit var spinner: Spinner
    private lateinit var money: EditText
    private var item: String = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1, container, false)
        spinner = view.findViewById(R.id.spinner)
        money= view.findViewById(R.id.money)

        // Set up the spinner
        val m = arrayOf("Работа", "Степендия", "Пенсия")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, m)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                item = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(requireContext(), "Не выбран доход", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the button click listener
        view.findViewById<Button>(R.id.button1).setOnClickListener {
            val money = money.text.toString()

            if (money.isNotEmpty()) {
                if (money != "0") {
                    save(item, money,)
                    navController.navigate(R.id.fragment2) // Navigate to fragment2
                } else {
                    Toast.makeText(requireContext(), "Введите значение больше 0", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    private fun save(type: String, money: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("date_list", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("money", money)
        editor.putString("storetype", type)
        editor.apply()
    }
}