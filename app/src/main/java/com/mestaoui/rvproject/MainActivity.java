package com.mestaoui.rvproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.mestaoui.rvproject.adapter.ActorAdapter;
import com.mestaoui.rvproject.beans.Actor;
import com.mestaoui.rvproject.service.ActorService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ActorService as;
    private ActorAdapter actorAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle);
        as = ActorService.getInstance();

        if(as.findAll().isEmpty()) {
            as.create(new Actor("Keanu Reeves","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4D0PpNI0kmP58hgrwGC3wCjxhnm.jpg",5));
            as.create(new Actor("Henry Cavill", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/iWdKjMry5Pt7vmxU7bmOQsIUyHa.jpg", 5));
            as.create(new Actor("Bryan Cranston", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7Jahy5LZX2Fo8fGJltMreAI49hC.jpg", 4));
            as.create(new Actor("Angelina Jolie", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/k3W1XXddDOH2zibPkNotIh5amHo.jpg", 2));
            as.create(new Actor("Rami Malek", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2OuFzCbMibXGouG79tG1U4BLPbe.jpg", 4.5f));
            as.create(new Actor("Will Smith", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6a6cl4ZNufJzrx5HZKWPU1BjjRF.jpg", 3.5f));
            as.create(new Actor("Emilia Clarke", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/r6i4C3kYrBRzUzZ8JKAHYQ0T0dD.jpg", 4));
            as.create(new Actor("Cillian Murphy", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7Th9L8eXj71cxuqlj6o26X9tICr.jpg", 3));
        }

        actorAdapter = new ActorAdapter(this, as.findAll());
        recyclerView.setAdapter(actorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Actor a = actorAdapter.removeActorAt(position);
                as.delete(a);
                actorAdapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (actorAdapter != null){
                    actorAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.addActor:
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                break;
            case R.id.reset:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Voulez-vous réinitialiser la liste ?");

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        as.findAll().clear();
                        as.create(new Actor("Keanu Reeves","https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4D0PpNI0kmP58hgrwGC3wCjxhnm.jpg",5));
                        as.create(new Actor("Henry Cavill", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/iWdKjMry5Pt7vmxU7bmOQsIUyHa.jpg", 5));
                        as.create(new Actor("Bryan Cranston", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7Jahy5LZX2Fo8fGJltMreAI49hC.jpg", 4));
                        as.create(new Actor("Angelina Jolie", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/k3W1XXddDOH2zibPkNotIh5amHo.jpg", 2));
                        as.create(new Actor("Rami Malek", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2OuFzCbMibXGouG79tG1U4BLPbe.jpg", 4.5f));
                        as.create(new Actor("Will Smith", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6a6cl4ZNufJzrx5HZKWPU1BjjRF.jpg", 3.5f));
                        as.create(new Actor("Emilia Clarke", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/r6i4C3kYrBRzUzZ8JKAHYQ0T0dD.jpg", 4));
                        as.create(new Actor("Cillian Murphy", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7Th9L8eXj71cxuqlj6o26X9tICr.jpg", 3));
                        actorAdapter = new ActorAdapter(MainActivity.this, as.findAll());
                        recyclerView.setAdapter(actorAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.share:
                String txt = "Actors";
                String mimeType = "text/plain";
                ShareCompat.IntentBuilder
                        .from(this)
                        .setType(mimeType)
                        .setChooserTitle("Actors")
                        .setText(txt)
                        .startChooser();

                return true;
            case R.id.exit:
                finishAffinity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}