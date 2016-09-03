package example.logitech.pradeep.logitechjsonparse;

/**
 * Created by antonypradeep on 03/09/16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AmazonProductsAdapter extends RecyclerView.Adapter<AmazonProductsAdapter.MyViewHolder> {

    private List<AmazonProducts> productsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView deviceType, name, model;

        public MyViewHolder(View view) {
            super(view);
            deviceType = (TextView) view.findViewById(R.id.deviceType);
            name = (TextView) view.findViewById(R.id.name);
            model = (TextView) view.findViewById(R.id.model);
        }
    }


    public AmazonProductsAdapter(List<AmazonProducts> productsList) {
        this.productsList = productsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_single_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AmazonProducts movie = productsList.get(position);
        holder.deviceType.setText(movie.getDeviceType());
        holder.name.setText(movie.getName());
        holder.model.setText(movie.getModel());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}