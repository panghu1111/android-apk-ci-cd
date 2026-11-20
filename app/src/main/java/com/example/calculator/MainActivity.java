package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView display;
    private String current = "";
    private Double operand1 = null;
    private String pendingOp = "+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.result);

        View.OnClickListener digitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                current += b.getText().toString();
                display.setText(current);
            }
        };

        int[] digitIds = {
                R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4,
                R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9
        };
        for (int id : digitIds) {
            findViewById(id).setOnClickListener(digitListener);
        }

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                applyCurrent();
                pendingOp = b.getText().toString();
            }
        };
        findViewById(R.id.bPlus).setOnClickListener(opListener);
        findViewById(R.id.bMinus).setOnClickListener(opListener);
        findViewById(R.id.bMultiply).setOnClickListener(opListener);
        findViewById(R.id.bDivide).setOnClickListener(opListener);

        findViewById(R.id.bEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyCurrent();
                if (operand1 != null) {
                    display.setText(String.valueOf(operand1));
                }
                current = "";
            }
        });

        findViewById(R.id.bClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operand1 = null;
                current = "";
                pendingOp = "+";
                display.setText("");
            }
        });
    }

    private void applyCurrent() {
        if (current.isEmpty()) {
            return;
        }
        double value = Double.parseDouble(current);
        if (operand1 == null) {
            operand1 = value;
        } else {
            switch (pendingOp) {
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "/":
                    if (value == 0) {
                        Toast.makeText(this, "Division by zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    operand1 /= value;
                    break;
                default:
                    break;
            }
        }
        current = "";
    }
}
