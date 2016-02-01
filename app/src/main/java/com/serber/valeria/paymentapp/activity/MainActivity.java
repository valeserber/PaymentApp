package com.serber.valeria.paymentapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.AlertDialog;

import com.serber.valeria.paymentapp.R;
import com.serber.valeria.paymentapp.adapter.PaymentMethodAdapter;
import com.serber.valeria.paymentapp.model.PaymentMethod;
import com.serber.valeria.paymentapp.model.PaymentType;
import com.serber.valeria.paymentapp.provider.PaymentMethodProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private RecyclerView mPaymentMethodView;
    private ProgressBar mLoadingView;
    private SwipeRefreshLayout  mRefreshView;
    private FrameLayout mErrorView;
    private FrameLayout mFilterTypeView;
    private FrameLayout mNoResultsView;
    private TextView mErrorMessage;
    private TextView mFilterTypeText;
    private PaymentMethodAdapter mPaymentAdapter;
    private PaymentMethodProvider mProvider;
    private PaymentType mCurrentFilter;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLayout();
        initialize();
        setUpAlertDialog();
        setListeners();
        startLoading();
    }

    private void setUpLayout() {
        setContentView(R.layout.activity_main);
        mLoadingView = (ProgressBar) findViewById(R.id.activity_main_loading);
        mRefreshView = (SwipeRefreshLayout) findViewById(R.id.activity_main_refresh_view);
        mErrorView = (FrameLayout) findViewById(R.id.error_view);
        mFilterTypeView = (FrameLayout) findViewById(R.id.activity_main_filter_type);
        mNoResultsView = (FrameLayout) findViewById(R.id.no_results_view);
        mErrorMessage = (TextView) findViewById(R.id.error_view_message);
        mFilterTypeText = (TextView) findViewById(R.id.activity_main_filter_type_text);
        mErrorView.setVisibility(View.GONE);
        mNoResultsView.setVisibility(View.GONE);
        mPaymentMethodView = (RecyclerView) findViewById(R.id.activity_main_payment_view);
        mPaymentAdapter = new PaymentMethodAdapter(this);
        mPaymentMethodView.setAdapter(mPaymentAdapter);
        mPaymentMethodView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initialize() {
        if (mProvider == null) {
            mProvider = new PaymentMethodProvider(getBaseContext());
        }
        mProvider.setView(this);
        setFilter(PaymentType.CREDIT_CARD);
    }

    private void setUpAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_payment_method));
        builder.setItems(R.array.payment_methods, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        setFilter(PaymentType.CREDIT_CARD);
                        break;
                    case 1:
                        setFilter(PaymentType.TICKET);
                        break;
                    case 2:
                        setFilter(PaymentType.ATM);
                        break;
                    case 3:
                        setFilter(PaymentType.DEBIT_CARD);
                        break;
                    case 4:
                        setFilter(PaymentType.PREPAID_CARD);
                        break;
                }
                startLoading();
            }
        });
        mAlertDialog = builder.create();
    }

    private void setListeners() {
        mRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshView.setRefreshing(true);
                loadData();
            }
        });
        mFilterTypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });
    }

    private void setFilter(PaymentType type) {
        this.mCurrentFilter = type;
        mFilterTypeText.setText(String.format(getString(R.string.filter), mCurrentFilter.getName()));
    }

    private void startLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        loadData();
    }

    private void finishLoading() {
        mLoadingView.setVisibility(View.GONE);
        mPaymentMethodView.setVisibility(View.VISIBLE);
        mRefreshView.setRefreshing(false);
    }

    private void clearPreviousResults() {
        mPaymentMethodView.setVisibility(View.GONE);
        mPaymentAdapter.clear();
        mErrorView.setVisibility(View.GONE);
        mNoResultsView.setVisibility(View.GONE);
    }

    private void loadData() {
        clearPreviousResults();
        mProvider.getPaymentMethods();
    }

    private void hideMainView() {
        mRefreshView.setRefreshing(false);
        mLoadingView.setVisibility(View.GONE);
        mPaymentMethodView.setVisibility(View.GONE);
    }

    public void loadNoResultsView() {
        hideMainView();
        mNoResultsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadErrorView(String message) {
        hideMainView();
        mErrorMessage.setText(message);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void filterResponse(List<PaymentMethod> list) {
        List<PaymentMethod> creditCardList = new ArrayList<>();
        for (PaymentMethod method: list) {
            if (method.getPaymentTypeId().equals(mCurrentFilter.getTypeId())) {
                creditCardList.add(method);
            }
        }
        if (creditCardList.size() == 0) {
            loadNoResultsView();
        } else {
            mPaymentAdapter.addResults(creditCardList);
            finishLoading();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProvider.setView(null);
    }
}
