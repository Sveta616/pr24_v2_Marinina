package com.example.pact24_var2_marinina

import android.annotation.SuppressLint
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
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment3 : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var position1: Int = -1
    private lateinit var navController: NavController
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DateAdapter
    private var datelist: MutableList<DateItem> = mutableListOf()
    private lateinit var money3: EditText
    private lateinit var zam3: EditText
    private var dateText: String = "null"
    private lateinit var calendarView: CalendarView
    private lateinit var spinner: Spinner
    private var item: String = "null"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        spinner = view.findViewById(R.id.spinner2)
        val m = arrayOf("Работа", "Степендия", "Пенсия")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, m)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                item = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(requireContext(), "Не выбран вид должности", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        zam3 = view.findViewById(R.id.zamed)
        money3 = view.findViewById(R.id.moneyed)
        calendarView = view.findViewById(R.id.calendarView)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, -1)
        val minDate = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.add(Calendar.MONTH, +1)
        val maxDate = calendar.timeInMillis

        calendarView.minDate = minDate
        calendarView.maxDate = maxDate

        navController = findNavController()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = DateAdapter(datelist) { position ->
           position1 = position
            val selectedItem = datelist[position]
            money3.setText(selectedItem.money.dropLast(1))
            zam3.setText(selectedItem.zam)
        }
        recyclerView.adapter = adapter
        load()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateText = "$dayOfMonth.${month + 1}.$year"
        }

        view.findViewById<Button>(R.id.edit).setOnClickListener {
            if (position1 != -1) {
                if (dateText != "null") {
                    val new = DateItem(
                        item,
                        money3.text.toString(), // Correctly retrieve the text
                        zam3.text.toString(),
                        dateText
                    )
                    update(position1, new)
                } else {
                    Toast.makeText(requireContext(), "Выберите дату", Toast.LENGTH_SHORT).show()
                }
            }
        }

        view.findViewById<ImageView>(R.id.goback).setOnClickListener {
            navController.navigate(R.id.fragment1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun load() {
        sharedPreferences = requireActivity().getSharedPreferences("date_list", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("date_list", null)
        if (json != null) {
            val type = object : TypeToken<List<DateItem>>() {}.type
            val savedHis: List<DateItem> = Gson().fromJson(json, type)
            datelist.clear()
            datelist.addAll(savedHis)
            adapter.notifyDataSetChanged()
        }
    }

    fun update(position: Int, newItem: DateItem) {
        datelist[position] = newItem
        adapter.notifyItemChanged(position)
        save()
    }

    private fun save() {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(datelist)
        editor.putString("date_list", json)
        editor.apply()
    }

    companion object {
        fun add(context: Context, task: DateItem) {
            val sharedPreferences = context.getSharedPreferences("date_list", Context.MODE_PRIVATE)
            val json = sharedPreferences.getString("date_list", null)
            val taskList = if (json != null) {
                val type = object : TypeToken<MutableList<DateItem>>() {}.type
                val savedTasks: MutableList<DateItem> = Gson().fromJson(json, type)
                savedTasks
            } else {
                mutableListOf()
            }
            taskList.add(task)
            sharedPreferences.edit().putString("date_list", Gson().toJson(taskList)).apply()
        }

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}