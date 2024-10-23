package com.example.pact24_var2_marinina

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment2.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment2 : Fragment() {
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var types: TextView
    private lateinit var moneys: EditText
    private lateinit var zams: EditText
    private var dateText: String = "null"
    private lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_2, container, false)
        zams = view.findViewById(R.id.zamm)
        types = view.findViewById(R.id.type2)
        moneys = view.findViewById(R.id.moneyy)

        sharedPreferences = requireActivity().getSharedPreferences("date_list", Context.MODE_PRIVATE)

        // Load previously saved data
        val money = sharedPreferences.getString("money", null)
        val type = sharedPreferences.getString("storetype", null)
        types.text = type ?: "Не выбран вид дохода"
        moneys.setText(money ?: "")

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView = view.findViewById(R.id.calendarView2)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateText = "$dayOfMonth.${month + 1}.$year"
        }

        navController = findNavController()

        // Set calendar min and max dates
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, -1)
        val minDate = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.add(Calendar.MONTH, +1)
        val maxDate = calendar.timeInMillis

        calendarView.minDate = minDate
        calendarView.maxDate = maxDate

        view.findViewById<Button>(R.id.button_f2).setOnClickListener {
            val zam = zams.text.toString()
            val money = moneys.text.toString()

            if (zam.isNotEmpty() && money.isNotEmpty()) {
                val his = DateItem(
                    types.text.toString(),
                    money,
                   zam,
                    dateText
                )
                Fragment3.add(requireContext(), his)
                save(zam, dateText)
                navController.navigate(R.id.fragment3)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Заполните все поля",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun save(zam: String, date: String) {
        val editor = sharedPreferences.edit()
        editor.putString("zam", zam)
        editor.putString("date", date)
        editor.apply()
    }
}