package com.example.softexpert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.softexpert.POJOs.Car;
import java.util.List;


public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private final Context context;
    private final List<Car> cars;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView brand, isUesd, year;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;
        public View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
            brand = v.findViewById(R.id.brand);
            isUesd = v.findViewById(R.id.isUsed);
            year = v.findViewById(R.id.year);
            imageView = v.findViewById(R.id.img);
            constraintLayout = v.findViewById(R.id.row);
        }
    }

    public CarAdapter(Context _context, List<Car> myData) {
        cars = myData;
        context = _context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View view = inflater.inflate(R.layout.list_row, recyclerView, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.brand.setText(cars.get(position).getBrand());
        holder.isUesd.setText(cars.get(position).getIsUsed());
        holder.year.setText(cars.get(position).getConstractionYear());
        Glide.with(context).load(cars.get(position).getImageUrl()).apply(new RequestOptions().
                override(200, 200).placeholder(R.drawable.ic_launcher_background).
                error(R.drawable.ic_launcher_foreground)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
