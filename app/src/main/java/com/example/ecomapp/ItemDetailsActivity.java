package com.example.ecomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ItemDetailsActivity extends AppCompatActivity {
    TextView mTitle,mdetails;
    ImageView mImage;
    FirebaseAuth fAuth;
    FirebaseFirestore firestore;
    FirebaseDatabase mfirebaseDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailsActivity.this,AddCartActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailsActivity.this,OrderListActivity.class);
                startActivity(intent);
            }
        });



        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();

        mTitle = findViewById(R.id.title01);
        mdetails = findViewById(R.id.description01);
        mImage = findViewById(R.id.image01);

        String image = getIntent().getStringExtra("image");
        String title = getIntent().getStringExtra("title");
        String detail = getIntent().getStringExtra("description");



        mTitle.setText(title);
        mdetails.setText(detail);
        Picasso.get().load(image).into(mImage);


    }


}
