package com.example.trantan.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {
    EditText mEditBillAmount;
    TextView mTextTip, mTextToTal, mTextPercent;
    Button mButtonDownPercent, mButtonUpPercent, mButtonCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUi();
        setListener();
    }

    private void setUpUi() {
        mEditBillAmount = findViewById(R.id.edit_bill_amount);
        mTextPercent = findViewById(R.id.text_percent);
        mTextTip = findViewById(R.id.text_tip);
        mTextToTal = findViewById(R.id.text_total);
        mButtonDownPercent = findViewById(R.id.button_down_percent);
        mButtonUpPercent = findViewById(R.id.button_up_percent);
        mButtonCalculate = findViewById(R.id.button_calculate);
    }

    private void setListener() {
        mEditBillAmount.setOnEditorActionListener(this);

        mButtonUpPercent.setOnClickListener(this);
        mButtonDownPercent.setOnClickListener(this);
        mButtonCalculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_down_percent: {
                double tipPercent = getTipPercent();
                tipPercent = (tipPercent == 0 ? tipPercent : tipPercent - 0.1);
                NumberFormat percentFormat = NumberFormat.getPercentInstance();
                mTextPercent.setText(percentFormat.format(tipPercent));
                break;
            }
            case R.id.button_up_percent: {
                double tipPercent = getTipPercent() + 0.1;
                NumberFormat percentFormat = NumberFormat.getPercentInstance();
                mTextPercent.setText(percentFormat.format(tipPercent));
                break;
            }
            case R.id.button_calculate: {
                calculateAndDisplay();
                break;
            }
        }
    }

    private void calculateAndDisplay() {
        //get billAmount
        double billAmount = getBillAmount();

        //get tipPercent
        double tipPercent = getTipPercent();

        //calculate
        double tipAmount = tipPercent * billAmount;
        double totalAmount = billAmount + tipAmount;

        //display
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        currencyFormat.setMaximumFractionDigits(1);
        mTextTip.setText(currencyFormat.format(tipAmount));
        mTextToTal.setText(currencyFormat.format(totalAmount));
    }

    private double getTipPercent() {
        double tipPercent;
        String tipPercentString = mTextPercent.getText().toString().replace("%", "");
        if (tipPercentString.equals("")) {
            tipPercent = 0;
        } else {
            tipPercent = Double.parseDouble(tipPercentString) / 100;
        }
        return tipPercent;
    }

    private double getBillAmount() {
        String billAmountString = mEditBillAmount.getText().toString();
        double billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        } else {
            billAmount = Double.parseDouble(billAmountString);
        }
        return billAmount;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        calculateAndDisplay();
        return true;
    }
}
