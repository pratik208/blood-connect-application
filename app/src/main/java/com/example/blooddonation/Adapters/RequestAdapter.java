package com.example.blooddonation.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blooddonation.DataModels.RequestDataModel;
import com.example.blooddonation.R;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<RequestDataModel> dataSet;
    private Context context;

    public RequestAdapter(
            List<RequestDataModel> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item_layout, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RequestDataModel requestDataModel = dataSet.get(position);

        holder.message.setText(requestDataModel.getMessage());

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle call button click here
                String number = requestDataModel.getNumber();
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                context.startActivity(callIntent);
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = requestDataModel.getMessage();
                String number = requestDataModel.getNumber();

                String shareText = "Number: " + number + "\n" + "Message: " + message;

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareText);

                if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                    view.getContext().startActivity(Intent.createChooser(intent, "Share via"));
                }
            }
        });
    }





    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView message ;
        ImageView imageView, callButton , shareButton ;
        ViewHolder(final View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
            imageView=itemView.findViewById(R.id.image);
            callButton=itemView.findViewById(R.id.call_button) ;
            shareButton=itemView.findViewById(R.id.share_button) ;
        }

    }

}