package example.logitech.pradeep.logitechjsonparse;

import android.content.Context;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    //private List<AmazonProducts> productsList = new ArrayList<>();
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

        //mAdapter = new AmazonProductsAdapter(productsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(mAdapter);

        if(isNetworkAvailable()) {
            prepareMovieData();
        }else{

            Snackbar.make(recyclerView, "Please enable internet access", Snackbar.LENGTH_LONG)
                    .show();

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                if(response.isSuccessful()) {
                    AmazonProductsArray jsonResponse = response.body();
                    products = new ArrayList<>(Arrays.asList(jsonResponse.getDevices()));
                    mAdapter = new AmazonProductsAdapter(products);
                    recyclerView.setAdapter(mAdapter);
                    Snackbar.make(recyclerView, "Json has been parsed", Snackbar.LENGTH_LONG)
                            .show();
                }else{
                    Snackbar.make(recyclerView, "Some Error has occurred", Snackbar.LENGTH_LONG)
                            .show();

                }
                }
            @Override
            public void onFailure(Call<AmazonProductsArray> call, Throwable t) {
                Log.d("logitech error >>> ",t.getMessage());
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
