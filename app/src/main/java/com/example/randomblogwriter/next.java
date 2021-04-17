package com.example.randomblogwriter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class next extends AppCompatActivity {

    TextView t1, t2, t3, t4, t5, b1, b2, b3, b4, b5;
    SwipeRefreshLayout r;
    LinearLayout l1, l2, l3, l4, l5;
    ImageView i1, i2, i3, i4, i5;
    int n;
    long c_count;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);

        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);
        l5 = findViewById(R.id.l5);

        i1 = findViewById(R.id.i1);
        i2 = findViewById(R.id.i2);
        i3 = findViewById(R.id.i3);
        i4 = findViewById(R.id.i4);
        i5 = findViewById(R.id.i5);

        r = findViewById(R.id.r);

        db = FirebaseDatabase.getInstance().getReference();

        n = 0;

        r.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                n = n + 5;

                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        c_count = snapshot.getChildrenCount();


                        if( snapshot.child(String.valueOf(c_count-n)).exists() ){
                            t1.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n)).child("TITLE").getValue()).toString());
                            b1.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n)).child("BODY").getValue()).toString());
                            l1.setBackgroundResource(R.drawable.layout);

                            if(snapshot.child(String.valueOf(c_count-n)).child("URL").exists()){
                                Picasso.get().load(snapshot.child(String.valueOf(c_count-n)).child("URL").getValue().toString()).into(i1);
                            }
                            else{
                                i1.setImageResource(0);
                            }

                        }

                        if( snapshot.child(String.valueOf(c_count-(n+1))).exists() ){
                            t2.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-1)).child("TITLE").getValue()).toString());
                            b2.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-1)).child("BODY").getValue()).toString());
                            l2.setBackgroundResource(R.drawable.layout);

                            if(snapshot.child(String.valueOf(c_count-(n+1))).child("URL").exists()){
                                Picasso.get().load(snapshot.child(String.valueOf(c_count-(n+1))).child("URL").getValue().toString()).into(i2);
                            }
                            else{
                                i2.setImageResource(0);
                            }

                        }

                        if( snapshot.child(String.valueOf(c_count-(n+2))).exists() ){
                            t3.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-2)).child("TITLE").getValue()).toString());
                            b3.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-2)).child("BODY").getValue()).toString());
                            l3.setBackgroundResource(R.drawable.layout);

                            if(snapshot.child(String.valueOf(c_count-(n+2))).child("URL").exists()){
                                Picasso.get().load(snapshot.child(String.valueOf(c_count-(n+2))).child("URL").getValue().toString()).into(i3);
                            }
                            else{
                                i3.setImageResource(0);
                            }

                        }

                        if( snapshot.child(String.valueOf(c_count-(n+3))).exists() ){
                            t4.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-3)).child("TITLE").getValue()).toString());
                            b4.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-3)).child("BODY").getValue()).toString());
                            l4.setBackgroundResource(R.drawable.layout);

                            if(snapshot.child(String.valueOf(c_count-(n+3))).child("URL").exists()){
                                Picasso.get().load(snapshot.child(String.valueOf(c_count-(n+3))).child("URL").getValue().toString()).into(i4);
                            }
                            else{
                                i4.setImageResource(0);
                            }

                        }

                        if( snapshot.child(String.valueOf(c_count-(n+4))).exists() ){
                            t5.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-4)).child("TITLE").getValue()).toString());
                            b5.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-4)).child("BODY").getValue()).toString());
                            l5.setBackgroundResource(R.drawable.layout);

                            if(snapshot.child(String.valueOf(c_count-(n+4))).child("URL").exists()){
                                Picasso.get().load(snapshot.child(String.valueOf(c_count-(n+4))).child("URL").getValue().toString()).into(i5);
                            }
                            else{
                                i5.setImageResource(0);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                r.setRefreshing(false);

            }
        });

    }

    public void click(View view){
        Intent i = new Intent(next.this, blogWrite.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {

        db = FirebaseDatabase.getInstance().getReference();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                c_count = snapshot.getChildrenCount();

                if( snapshot.child(String.valueOf(c_count-n)).exists() ){
                    t1.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n)).child("TITLE").getValue()).toString());
                    b1.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n)).child("BODY").getValue()).toString());
                    l1.setBackgroundResource(R.drawable.layout);

                    if(snapshot.child(String.valueOf(c_count-n)).child("URL").exists()){
                        Picasso.get().load(snapshot.child(String.valueOf(c_count-n)).child("URL").getValue().toString()).into(i1);
                    }
                    else{
                        i1.setImageResource(0);
                    }

                }

                if( snapshot.child(String.valueOf(c_count-(n+1))).exists() ){
                    t2.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-1)).child("TITLE").getValue()).toString());
                    b2.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-1)).child("BODY").getValue()).toString());
                    l2.setBackgroundResource(R.drawable.layout);

                    if(snapshot.child(String.valueOf(c_count-(n+1))).child("URL").exists()){
                        Picasso.get().load(snapshot.child(String.valueOf(c_count-(n+1))).child("URL").getValue().toString()).into(i2);
                    }
                    else{
                        i2.setImageResource(0);
                    }

                }

                if( snapshot.child(String.valueOf(c_count-(n+2))).exists() ){
                    t3.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-2)).child("TITLE").getValue()).toString());
                    b3.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-2)).child("BODY").getValue()).toString());
                    l3.setBackgroundResource(R.drawable.layout);

                    if(snapshot.child(String.valueOf(c_count-(n+2))).child("URL").exists()){
                        Picasso.get().load(snapshot.child(String.valueOf(c_count-(n+2))).child("URL").getValue().toString()).into(i3);
                    }
                    else{
                        i3.setImageResource(0);
                    }

                }

                if( snapshot.child(String.valueOf(c_count-(n+3))).exists() ){
                    t4.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-3)).child("TITLE").getValue()).toString());
                    b4.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-3)).child("BODY").getValue()).toString());
                    l4.setBackgroundResource(R.drawable.layout);

                    if(snapshot.child(String.valueOf(c_count-(n+3))).child("URL").exists()){
                        Picasso.get().load(snapshot.child(String.valueOf(c_count-(n+3))).child("URL").getValue().toString()).into(i4);
                    }
                    else{
                        i4.setImageResource(0);
                    }

                }

                if( snapshot.child(String.valueOf(c_count-(n+4))).exists() ){
                    t5.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-4)).child("TITLE").getValue()).toString());
                    b5.setText(Objects.requireNonNull(snapshot.child(String.valueOf(c_count-n-4)).child("BODY").getValue()).toString());
                    l5.setBackgroundResource(R.drawable.layout);

                    if(snapshot.child(String.valueOf(c_count-(n+4))).child("URL").exists()){
                        Picasso.get().load(snapshot.child(String.valueOf(c_count-(n+4))).child("URL").getValue().toString()).into(i5);
                    }
                    else{
                        i5.setImageResource(0);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        super.onStart();

    }

}