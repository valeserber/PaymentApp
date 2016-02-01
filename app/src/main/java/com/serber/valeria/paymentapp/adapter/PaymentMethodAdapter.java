package com.serber.valeria.paymentapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serber.valeria.paymentapp.R;
import com.serber.valeria.paymentapp.model.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentViewHolder> {

    private Context mContext;
    private List<PaymentMethod> mPaymentList;

    public PaymentMethodAdapter(Context context) {
        this.mContext = context;
        this.mPaymentList = new ArrayList<>();
    }

    public void addResults(List<PaymentMethod> list) {
        Log.d("lala", String.valueOf(list.size()));
        mPaymentList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mPaymentList.clear();
        notifyDataSetChanged();
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View adapterView = inflater.inflate(R.layout.adapter_payment_method, parent, false);
        PaymentViewHolder viewHolder = new PaymentViewHolder(adapterView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        PaymentMethod method = mPaymentList.get(position);
        holder.mNameTextView.setText(method.getName());

    }

    @Override
    public int getItemCount() {
        return mPaymentList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameTextView;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.adapter_payment_method_name);
        }
    }

}
