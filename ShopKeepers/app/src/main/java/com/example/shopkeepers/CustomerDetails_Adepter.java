package com.example.shopkeepers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerDetails_Adepter extends RecyclerView.Adapter<CustomerDetails_Adepter.CustomerDetails_ViewHolder> {
   private Context context;
   private ArrayList<Customer_User_Details> SpecificDetails_Arr;

    public CustomerDetails_Adepter(Context context, ArrayList<Customer_User_Details> specificDetails_Arr) {
        this.context = context;
        SpecificDetails_Arr = specificDetails_Arr;
    }

    @NonNull
    @Override
    public CustomerDetails_Adepter.CustomerDetails_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.customer_details_list_cardview, parent,false);
        return new CustomerDetails_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerDetails_Adepter.CustomerDetails_ViewHolder holder, int position) {
        Customer_User_Details customer_user_details = SpecificDetails_Arr.get(position);
        holder.itemName.setText(customer_user_details.getItem());
        holder.Date.setText(customer_user_details.getDate());
        holder.Amount.setText(customer_user_details.getCustomerAmount());

    }

    @Override
    public int getItemCount() {
        return SpecificDetails_Arr.size();
    }

    public class CustomerDetails_ViewHolder extends RecyclerView.ViewHolder {
       TextView itemName, Date, Amount;
        public CustomerDetails_ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name);
            Date = itemView.findViewById(R.id.item_date);
            Amount = itemView.findViewById(R.id.specific_amount);



        }
    }
}
