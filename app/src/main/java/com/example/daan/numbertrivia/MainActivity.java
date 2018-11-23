package com.example.daan.numbertrivia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NumbersApiService service;
    private RecyclerView mRecyclerView;
    private NumberAdapter mAdapter;
    private List<Number> mNumberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init
        service = NumbersApiService.retrofit.create(NumbersApiService.class);
        mRecyclerView = findViewById(R.id.recyclerView);
        mNumberList = new ArrayList<>();

        //Set layoutmanager with span of 1
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,
                LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NumberAdapter(mNumberList);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popup = (LayoutInflater.from(MainActivity.this))
                        .inflate(R.layout.user_input, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setView(popup);

                final EditText enteredValue = popup.findViewById(R.id.editNumber);

                alertBuilder.setCancelable(true)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int enteredNumber = Integer.parseInt(enteredValue.getText().toString());
                        fetchNumber(enteredNumber);
                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }

    public void fetchNumber(int number) {
        Call<Number> call = service.getNumber(number);
        call.enqueue(new Callback<Number>() {
            @Override
            public void onResponse(Call<Number> call, Response<Number> response) {
                Log.i("Response success", response.body().getText());
                mNumberList.add(response.body());
                updateView();
            }

            @Override
            public void onFailure(Call<Number> call, Throwable t) {
                Log.d("Response failed", t.toString());
            }
        });
    }

    public void updateView() {
        this.mAdapter.notifyDataSetChanged();
        this.mAdapter.switchDisplayDirection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mNumberList.clear();
            this.mAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}
