package com.example.myapplicationthree

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class DetailActivity : AppCompatActivity()  {

    private lateinit var tvAction: TextView
    private lateinit var tvId: TextView
    private lateinit var etTitleDetail: EditText
    private lateinit var etDescriptionDetail: EditText
    private lateinit var spinnerPriorityDetail: Spinner
    private lateinit var rgTypeDetail: RadioGroup
    private lateinit var rbtnType1Detail: RadioButton
    private lateinit var rbtnType2Detail: RadioButton
    private lateinit var etCountDetail: EditText
    private lateinit var etPeriodDetail: EditText
    private lateinit var btnSaveDetail: Button
    private lateinit var arrayAdapter: ArrayAdapter<CharSequence>
    var select: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initializationViews()
        initializationSpinnerAdapter()

        val bundleArguments = intent.extras
        if (bundleArguments != null) {

            tvAction.text = bundleArguments.getString("Action")
            tvId.text = bundleArguments.getInt("Id").toString()

            etTitleDetail.setText(bundleArguments.getString("Title"))
            etDescriptionDetail.setText(bundleArguments.getString("Description"))

            spinnerPriorityDetail.setSelection(arrayAdapter.getPosition(bundleArguments.getInt("Priority").toString()))

            when (bundleArguments.getString("Type")) {
                "type 1" -> {
                    // option one was clicked
                    rbtnType1Detail.setChecked(true)
                }
                "type 2" -> {
                    // option two was clicked
                    rbtnType2Detail.setChecked(true)
                }
            }

            etCountDetail.setText(bundleArguments.getInt("Count").toString())
            etPeriodDetail.setText(bundleArguments.getInt("Period").toString())
        }
    }


    private fun initializationViews() {
        tvAction = findViewById(R.id.tvAction)
        tvId = findViewById(R.id.tvId)
        etTitleDetail = findViewById(R.id.etTitle)
        etDescriptionDetail = findViewById(R.id.etDescription)
        spinnerPriorityDetail = findViewById(R.id.spinnerPriority)
        rgTypeDetail = findViewById(R.id.rgType)
        rbtnType1Detail = findViewById(R.id.rbtnTypeOne)
        rbtnType2Detail = findViewById(R.id.rbtnTypeTwo)
        etCountDetail = findViewById(R.id.etCount)
        etPeriodDetail = findViewById(R.id.etPeriod)
        btnSaveDetail = findViewById(R.id.btnSave)

        // check which button was pressed
        rgTypeDetail.setOnCheckedChangeListener { group, checkedId ->
            // on below line we are getting radio button from our group.
            val checkedRbtn = group.findViewById<RadioButton>(checkedId) as RadioButton
            select = checkedRbtn.text.toString()
        }
    }


    private fun initializationSpinnerAdapter(){
        // Настраиваем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        arrayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.priorities,
            android.R.layout.simple_spinner_item)
        // Определяем разметку для использования при выборе элемента
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Применяем адаптер к элементу spinner
        spinnerPriorityDetail.adapter = arrayAdapter
    }


    fun onButtonClicked(view: View){
        val intent = Intent()
        // Passing the data to the MainActivity
        intent.putExtra("Action", tvAction.text.toString())
        intent.putExtra("Id", tvId.text.toString().toInt())
        intent.putExtra("Title", etTitleDetail.text.toString())
        intent.putExtra("Description", etDescriptionDetail.text.toString())
        intent.putExtra("Priority", spinnerPriorityDetail.selectedItem.toString().toInt())
        intent.putExtra("Type", select)
        intent.putExtra("Count", etCountDetail.text.toString().toInt())
        intent.putExtra("Period", etPeriodDetail.text.toString().toInt())
        setResult(RESULT_OK, intent);
        finish();
    }

}
