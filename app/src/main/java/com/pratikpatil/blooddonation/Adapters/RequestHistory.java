package com.pratikpatil.blooddonation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pratikpatil.blooddonation.DataModels.RequestDataModel;
import com.example.blooddonation.R;

import java.util.List;

public class RequestHistory extends RecyclerView.Adapter<RequestHistory.ViewHolder> {
    private List<RequestDataModel> dataSet;
    private Context context;

    public RequestHistory(List<RequestDataModel> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.historylayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RequestDataModel requestDataModel = dataSet.get(position);

        holder.message.setText(requestDataModel.getMessage());
        // Bind other data to your views as needed
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        ImageView imageView ;
        Button button ;
        ViewHolder(final View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            imageView = itemView.findViewById(R.id.image);
            button=itemView.findViewById(R.id.buttonDelete);
        }
    }
}
