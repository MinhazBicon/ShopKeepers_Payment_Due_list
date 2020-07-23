package com.example.shopkeepers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerList_adepter extends RecyclerView.Adapter<CustomerList_adepter.customerList_View_holder> {
    private Context context;
    private ArrayList<Customer_User_Details> CustomerName_arr;
    private OnClickListener customerList_onClickListener;

    public interface OnClickListener{
        void OnItemClickListener(int position);
    }

    public void SetOnItemClickListener(OnClickListener onClickListener){
        customerList_onClickListener = onClickListener;
    }



    public CustomerList_adepter(Context context, ArrayList<Customer_User_Details> customerName_arr) {
        this.context = context;
        CustomerName_arr = customerName_arr;
    }

    @NonNull
    @Override
    public CustomerList_adepter.customerList_View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.customer_list_cardview, parent,false);
        return new customerList_View_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerList_adepter.customerList_View_holder holder, int position) {
        Customer_User_Details customer_user_details = CustomerName_arr.get(position);
        holder.name.setText(customer_user_details.getName());
        holder.total_amount.setText(customer_user_details.getTotalAmount());
        //notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return CustomerName_arr.size();
    }

    public class customerList_View_holder extends RecyclerView.ViewHolder {
        TextView name, total_amount;
        public customerList_View_holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customerLisName_textView);
            total_amount = itemView.findViewById(R.id.customerList_totalAMount_TextView);

         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (customerList_onClickListener != null){
                     int position = getAdapterPosition();
                     if (position != RecyclerView.NO_POSITION){
                         customerList_onClickListener.OnItemClickListener(position);
                     }
                 }
             }
         });
        }
    }
}
