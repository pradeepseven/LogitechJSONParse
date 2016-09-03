package example.logitech.pradeep.logitechjsonparse;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AmazonProductsActivity extends AppCompatActivity {

    private List<AmazonProducts> productsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AmazonProductsAdapter mAdapter;
    private ArrayList<AmazonProducts> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new AmazonProductsAdapter(productsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void prepareMovieData() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://s3.amazonaws.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
        RetroRequestInterface request = retrofit.create(RetroRequestInterface.class);
        Call<AmazonProductsArray> call = request.getDevices();
        call.enqueue(new Callback<AmazonProductsArray>() {
            @Override
            public void onResponse(Call<AmazonProductsArray> call, Response<AmazonProductsArray> response) {
                AmazonProductsArray jsonResponse = response.body();
                products = new ArrayList<>(Arrays.asList(jsonResponse.getDevices()));
                mAdapter = new AmazonProductsAdapter(products);
                recyclerView.setAdapter(mAdapter);
                }
            @Override
            public void onFailure(Call<AmazonProductsArray> call, Throwable t) {
                Log.d("Error",t.getMessage());
                }
            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_amazon_products, menu);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
