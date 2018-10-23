package com.manjurulhoque.bmicalculator;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.AgeTextInputLayout)
    TextInputLayout ageTextInputLayout;
    @BindView(R.id.WeightTextInputLayout)
    TextInputLayout weightTextInputLayout;
    @BindView(R.id.HeightTextInputLayout)
    TextInputLayout heightTextInputLayout;
    @BindView(R.id.HeightInchTextInputLayout)
    TextInputLayout heightInchTextInputLayout;
    @BindView(R.id.btnCalculate)
    Button btnCalculate;
    @BindView(R.id.heightSpinner)
    Spinner heightSpinner;
    @BindView(R.id.weightSpinner)
    Spinner weightSpinner;
    @BindView(R.id.resultTextView)
    TextView resultTextView;

    // Global Variables
    int heightSpinnerPosition = 0; // default position
    int weightSpinnerPosition = 0; // default position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // set default position of the spinner
        heightSpinner.setSelection(1);
        // click listeners
        heightSpinner.setOnItemSelectedListener(this);
        weightSpinner.setOnItemSelectedListener(this);
        btnCalculate.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView.getId() == R.id.heightSpinner) {
            heightSpinnerPosition = position;
            if (heightSpinnerPosition == 0) {
                heightInchTextInputLayout.setVisibility(View.INVISIBLE);
                heightTextInputLayout.setHint("Cm");
            } else {
                heightInchTextInputLayout.setVisibility(View.VISIBLE);
                heightTextInputLayout.setHint("Ft");
            }
        } else if (adapterView.getId() == R.id.weightSpinner) {
            weightSpinnerPosition = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCalculate) {
            String ageS = ageTextInputLayout.getEditText().getText().toString();
            String weightS = weightTextInputLayout.getEditText().getText().toString();
            String heightS = heightTextInputLayout.getEditText().getText().toString();
            String inchS = "";
            if (heightSpinnerPosition == 1) {
                inchS = heightInchTextInputLayout.getEditText().getText().toString();
            }
            float bmiResult = 0;

            if (!TextUtils.isEmpty(ageS) && !TextUtils.isEmpty(weightS) || !TextUtils.isEmpty(heightS)) {
                int age = Integer.parseInt(ageS);
                float weight = Float.parseFloat(weightS);
                int feet = Integer.parseInt(heightS);
                int inches;
                try {
                    inches = Integer.parseInt(inchS);
                } catch (Exception e) {
                    inches = 0;
                }

                if (weightSpinnerPosition == 0 && heightSpinnerPosition == 0) {
                    bmiResult = (float) BMIHelper.getBMIKg(feet, weight);
                } else if (weightSpinnerPosition == 1 && heightSpinnerPosition == 0) {
                    bmiResult = (float) BMIHelper.getBMIKg(feet, BMIHelper.lbToKgConverter(weight));
                } else if (weightSpinnerPosition == 0 && heightSpinnerPosition == 1) {
                    bmiResult = (float) BMIHelper.getBMIKg(BMIHelper.feetInchToCmConverter(feet, inches), weight);
                } else if (weightSpinnerPosition == 1 && heightSpinnerPosition == 1) {
                    bmiResult = (float) BMIHelper.getBMILb(feet, inches, weight);
                }

                String result = "Your BMI is " + bmiResult + " and " + BMIHelper.getBMIClassification(bmiResult);
                resultTextView.setText("Result: " + result);
                // if height in foot and inch
//                if (heightSpinnerPosition == 1) {
//                    if (!TextUtils.isEmpty(inchS)) {
//                        int age = Integer.parseInt(ageS);
//                        float weight = Float.parseFloat(weightS);
//                        int feet = Integer.parseInt(heightS);
//                        int inches = Integer.parseInt(inchS);
//
//                        //convert measurements
//                    }
//                } else { // else only height in cm
//                    int age = Integer.parseInt(ageS);
//                    float weight = Float.parseFloat(weightS);
//                    int cm = Integer.parseInt(heightS);
//
//                }
            } else {
                Toast.makeText(getApplicationContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
