package com.mestaoui.rvproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mestaoui.rvproject.beans.Actor;
import com.mestaoui.rvproject.service.ActorService;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    private CircleImageView image;
    private ImageButton remove;
    private EditText fullName;
    private RatingBar rating;
    private Button add;
    private ActorService as;
    private String link = "android.resource://com.mestaoui.rvproject/drawable/avatar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        image = findViewById(R.id.imageA);
        remove = findViewById(R.id.remove);
        fullName = findViewById(R.id.fullNameA);
        rating = findViewById(R.id.ratingA);
        add = findViewById(R.id.add);
        as = ActorService.getInstance();

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = "android.resource://com.mestaoui.rvproject/drawable/avatar";
                Glide
                        .with(getApplicationContext())
                        .load(Uri.parse(link))
                        .centerCrop()
                        .apply(new RequestOptions().override(120, 120))
                        .into(image);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullName.getText().toString().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Veuillez saisir le nom complet!", Toast.LENGTH_SHORT).show();
                }else {
                    Actor a = new Actor(fullName.getText().toString(), link, rating.getRating());
                    as.create(a);
                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            link = data.getData().toString();
            Glide
                    .with(getApplicationContext())
                    .load(data.getData())
                    .centerCrop()
                    .apply(new RequestOptions().override(120, 120))
                    .into(image);
        }
    }
}