package com.example.logbook2023_3_60_057;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddressBookList extends Activity {

    private ListView lvAddressBook;
    private ArrayList<ActivityList> addressList;
    private ActivityAdapter adapter;
    private EventDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book_list);

        db = new EventDB(this);
        lvAddressBook = findViewById(R.id.lvAddressBook);
        addressList = new ArrayList<>();
        adapter = new ActivityAdapter(this, addressList);
        lvAddressBook.setAdapter(adapter);

        Button btnExit = findViewById(R.id.btnExit);
        Button btnAddNew = findViewById(R.id.btnAddNew);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressBookList.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressBookList.this, AddressBookDetails.class);
                startActivity(intent);
            }
        });
        lvAddressBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ActivityList selectedItem = addressList.get(position);
                Intent intent = new Intent(AddressBookList.this, AddressBookDetails.class);
                intent .putExtra("ID", selectedItem.getId());
                intent .putExtra("NAME", selectedItem.getName());
                intent .putExtra("EMAIL", selectedItem.getEmail());
                intent .putExtra("PHONE", selectedItem.getPhone());
                intent .putExtra("DOB", selectedItem.getDob());
                intent .putExtra("PRESENT", selectedItem.getPresent());
                intent .putExtra("PERMANENT", selectedItem.getPermanent());


                startActivity(intent);

            }

        });

        lvAddressBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityList selectedItem = addressList.get(position);

                // Show confirmation dialog
                new AlertDialog.Builder(AddressBookList.this)
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure you want to delete " + selectedItem.getName() + "?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete from database
                                db.deleteEvent(selectedItem.getId());

                                // Remove from list and refresh
                                addressList.remove(position);
                                adapter.notifyDataSetChanged();

                                Toast.makeText(AddressBookList.this,
                                        "Contact deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        addressList.clear();
        Cursor cursor = db.selectEvents("SELECT * FROM events");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                String dob = cursor.getString(4);
                String present = cursor.getString(5);
                String permanent = cursor.getString(6);
                
                addressList.add(new ActivityList(id, name, email, phone, dob, present, permanent));
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }
}
