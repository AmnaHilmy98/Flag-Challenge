package com.android.project.chefschoice.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.project.chefschoice.DB.ProductData;
import com.android.project.chefschoice.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtName, txtWeight, txtPrice, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtName = (EditText) findViewById(R.id.txtName);
        txtWeight = (EditText) findViewById(R.id.txtWeight);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtDescription = (EditText) findViewById(R.id.txtDescription);

        Button btnAdd = (Button) findViewById(R.id.btnRegisterProduct);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                double weight = Double.parseDouble(txtWeight.getText().toString());
                double price = Double.parseDouble(txtPrice.getText().toString());
                String description = txtDescription.getText().toString();

                if (txtName.length() != 0 || txtWeight.length() != 0 || txtPrice.length() != 0 ||
                        txtDescription.length() != 0) {
                    addData(name, weight, price, description);
                    txtName.setText("");
                    txtWeight.setText("");
                    txtPrice.setText("");
                    txtDescription.setText("");
                } else {
                    Toast.makeText(RegisterActivity.this, "You must put something in the text field!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addData(String name, double weight, double price, String description) {
        ProductData db = new ProductData(this);
        boolean insertData = db.addProduct(name, weight, price, description);

        if (insertData) {
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }
}

