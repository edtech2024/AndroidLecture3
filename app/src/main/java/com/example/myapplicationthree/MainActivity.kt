package com.example.myapplicationthree

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json


class MainActivity : AppCompatActivity() {

    private lateinit var addFab: FloatingActionButton
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView
    var itemsList: MutableList<Item> = mutableListOf<Item>()
    var count: Int = itemsList.size
    private val countVariableKey = "COUNT_VARIABLE"
    private val itemsListKey = "ITEMS_LIST"

    // сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {

        outState.putInt(countVariableKey, this.itemAdapter.itemCount)

        var jsonString: String? = Json.encodeToString(ListSerializer(Item.serializer()), this.itemAdapter.itemsList )
        outState.putString(itemsListKey, jsonString)

        super.onSaveInstanceState(outState)
    }

    // получение ранее сохраненного состояния
    @SuppressLint("NotifyDataSetChanged")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        this.count = savedInstanceState.getInt(countVariableKey)

        var jsonString: String? = savedInstanceState.getString(itemsListKey)

        this.itemsList.addAll( jsonString?.let {
            Json.decodeFromString(ListSerializer(Item.serializer()),
                it)
        } as MutableList<Item>)

        this.runOnUiThread (
            java.lang.Runnable {
                this.itemAdapter.itemsList = this.itemsList
                this.itemAdapter.notifyDataSetChanged()
            })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializationRVAdapter()
        initializationFAB()
    }


    private fun initializationFAB(){
        // Register the FABs with ID
        addFab = findViewById(R.id.fab)

        addFab.setOnClickListener {
            // do something when the button is clicked
            val intentAdd = Intent(this@MainActivity, DetailActivity::class.java)
            // Passing the data to the DetailActivity
            intentAdd.putExtra("Action", "Create")
            intentAdd.putExtra("Id", this.count)
            startActivityForResult(intentAdd, 1)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (data != null) {
                    val bundleArguments = data.extras
                    if (bundleArguments != null) {
                        if ((bundleArguments.getString("Action")) == "Create") {
                            createItem(bundleArguments)
                        }
                        else{
                            val valueOfElement: Item = readItem(bundleArguments)
                            updateItem(valueOfElement, bundleArguments)
                        }
                    }
                }
            }
        }
    }


    //порядок функций для адаптера
    private fun initializationRVAdapter() {
        this.recyclerView = findViewById(R.id.rvList)
        // Assign itemList to ItemAdapter
        this.itemAdapter = ItemAdapter(itemsList){ item -> onItemListClick(item) }
        // Set the LayoutManager that this RecyclerView will use.
        recyclerView.layoutManager = LinearLayoutManager(this)
        // adapter instance is set to the recyclerview to inflate the items.
        recyclerView.adapter = itemAdapter
        //когда recyclerView не планирует изменять размеры своих дочерних элементов динамически
        recyclerView.setHasFixedSize(true)
    }


    private fun onItemListClick(item: Item) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("Action", "Edit")
        intent.putExtra("Id", item.id)
        intent.putExtra("Title", item.title)
        intent.putExtra("Description", item.description)
        intent.putExtra("Priority", item.priority)
        intent.putExtra("Type", item.type)
        intent.putExtra("Count", item.count)
        intent.putExtra("Period", item.period)

        startActivityForResult(intent, 1)
    }


    private fun createItem(bundleArguments: Bundle?) {
        val item: Item = Item(bundleArguments!!.getInt("Id"),
            bundleArguments!!.getString("Title"),
            bundleArguments!!.getString("Description"),
            bundleArguments!!.getInt("Priority"),
            bundleArguments!!.getString("Type"),
            bundleArguments!!.getInt("Count"),
            bundleArguments!!.getInt("Period")
        )

        this.runOnUiThread (
            java.lang.Runnable {
                this.itemAdapter.addItem(item)
            })

        this.count = this.recyclerView.adapter!!.itemCount
    }


    private fun updateItem(valueOfElement: Item, bundleArguments: Bundle) {
        valueOfElement.id = bundleArguments.getInt("Id").toString().toInt()
        valueOfElement.title = bundleArguments.getString("Title").toString()
        valueOfElement.description = bundleArguments.getString("Description").toString()
        valueOfElement.priority = bundleArguments.getInt("Priority").toString().toInt()
        valueOfElement.type = bundleArguments.getString("Type").toString()
        valueOfElement.count = bundleArguments.getInt("Count").toString().toInt()
        valueOfElement.period = bundleArguments.getInt("Period").toString().toInt()

        this.runOnUiThread (
            java.lang.Runnable {
                this.itemAdapter.updateItem(valueOfElement)
            })
    }


    private fun readItem(bundleArguments: Bundle): Item {
        return this.itemsList[bundleArguments.getInt("Id")]
    }

}
