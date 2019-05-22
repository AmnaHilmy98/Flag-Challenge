package com.android.project.chefschoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.project.chefschoice.DB.ProductData;
import com.android.project.chefschoice.DTO.ProductModel;
import com.android.project.chefschoice.R;

public class EditPageActivity extends AppCompatActivity {

    private ProductModel productModel;
    private EditText txtName, txtWeight, txtPrice, txtDescription, txtAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        Intent intent = getIntent();
        productModel = (ProductModel) intent.getSerializableExtra("product");

        Log.d("VALUES", productModel.toString());

        ProductData data = new ProductData(this);

        txtName = (EditText) findViewById(R.id.txtName);
        txtWeight = (EditText) findViewById(R.id.txtWeight);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtAvailability = (EditText) findViewById(R.id.txtAvailability);
        Button btnEdit = (Button) findViewById(R.id.btnEditProduct);

        txtName.setText(productModel.getName());
        txtWeight.setText(String.valueOf(productModel.getWeight()));
        txtPrice.setText(String.valueOf(productModel.getPrice()));
        txtDescription.setText(productModel.getDescription());
        txtAvailability.setText(productModel.getAvailability());

    }

    public void editSave(View view) {
        String name = txtName.getText().toString();
        double weight = Double.parseDouble(txtWeight.getText().toString());
        double price = Double.parseDouble(txtPrice.getText().toString());
        String description = txtDescription.getText().toString();
        String availability = txtAvailability.getText().toString();

        if (txtName.length() != 0 || txtWeight.length() != 0 || txtPrice.length() != 0 ||
                txtDescription.length() != 0 || txtDescription.length() != 0) {
            addData(productModel.getId(), name, weight, price, description, availability);
            txtName.setText("");
            txtWeight.setText("");
            txtPrice.setText("");
            txtDescription.setText("");
            txtAvailability.setText("");
        } else {
            Toast.makeText(EditPageActivity.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
        }

        this.finish();
        Intent intent = new Intent(EditPageActivity.this, EditActivity.class);
        startActivity(intent);
    }

    public void addData(int id, String name, double weight, double price, String description, String availability) {
        ProductData db = new ProductData(this);
        boolean insertData = db.editProducts(id, name, weight, price, description, availability);

        if (insertData) {
            Toast.makeText(this, "Updated Product Successfully!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }
}
