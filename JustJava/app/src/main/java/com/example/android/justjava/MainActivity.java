package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    double price = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method displays the price of the coffee
     * @param price the total cost of the order
     */
    private void displayPrice(double price) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
    }

    public void incrementOrder(View view) {
        quantity++;
        price = 5 * quantity;
        display(quantity);
        displayPrice(price);
    }

    public void decrementOrder(View view) {
        if (quantity >= 1)
            quantity--;

        price = 5 * quantity;
        display(quantity);
        displayPrice(price);
    }


    public void submitOrder(View view) {
        TextView orderView = (TextView) findViewById(R.id.total_cost_text_view);
        if (quantity > 0)
            orderView.setText("" + "The price of " + quantity + " coffees is $" + price);
        else
            orderView.setText("" + "0 coffees selected. Please increase the quantity before placing order");
    }

    /**
     * this method displays the quantity of
     * @param number the number of coffees
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
