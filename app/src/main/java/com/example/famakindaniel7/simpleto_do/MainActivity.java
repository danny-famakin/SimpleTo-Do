package com.example.famakindaniel7.simpleto_do;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import java.util.ArrayList;
import android.view.View;
import android.util.Log;
import java.io.*;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvitems = (ListView) findViewById(R.id.Items);
        lvitems.setAdapter(itemsAdapter);

        //items.add("First item");
        //items.add("Second item");
    }

    public void onAddItem(View v) {
        EditText AddedItem = (EditText) findViewById(R.id.AddedItem);
        String itemText = AddedItem.getText().toString();
        itemsAdapter.add(itemText);
        AddedItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }
    private void setupListViewListener() {
        Log.i("MainActivity", "Setting up listener on list view");
        lvitems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "Item removed from list: " + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }
    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }
    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading file", e);
            items = new ArrayList<>();
        }
    }
    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing file", e);
        }
    }
}
