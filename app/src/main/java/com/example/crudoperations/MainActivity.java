package com.example.crudoperations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<UserItem> usersItemArrayList;
    UsersRecyclerAdapter adapter;

    Button buttonAdd;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersItemArrayList = new ArrayList<>();

        buttonAdd = findViewById(R.id.buttonAddUser);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(MainActivity.this);

            }
        });

        readData();

    }

    private void readData() {
        databaseReference.child("USERS").orderByChild("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserItem users = dataSnapshot.getValue(UserItem.class);
                    usersItemArrayList.add(users);
                }

                adapter = new UsersRecyclerAdapter(MainActivity.this,usersItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewDialogAdd {
        public void showDialog(Context context){
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_user);

            EditText Name = dialog.findViewById(R.id.textname);
            EditText Des = dialog.findViewById(R.id.textdes);
            EditText Pdate = dialog.findViewById(R.id.textdate);

            Button addButton = dialog.findViewById(R.id.buttonadd);
            Button cancelButton = dialog.findViewById(R.id.buttonCancel);

            addButton.setText("ADD");

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "user" + new Date().getTime();
                    String name = Name.getText().toString();
                    String des = Des.getText().toString();
                    String date = Pdate.getText().toString();

                    if(name.isEmpty() || des.isEmpty() || date.isEmpty())
                    {
                        Toast.makeText(context, "Please Enter All data....", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        databaseReference.child("USERS").child(id).setValue(new UserItem(id,name,des,date));
                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }


}




