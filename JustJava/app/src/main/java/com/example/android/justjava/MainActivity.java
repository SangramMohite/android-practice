
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    double total = 0.0;
    double basePrice = 5.0;
    boolean isWhippedCreamChecked = false;
    boolean isChocolateChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOrderButtonState();
    }

    private void setOrderButtonState() {
        Button orderButton = (Button) findViewById(R.id.order_button);
        orderButton.setEnabled(quantity > 0);
    }

    /**
     * This method displays the total of the coffee
     *
     * @param price the total cost of the order
     */
    private void displayPrice(double price) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(price));
    }

    public void onWhippedCreamClicked(View view) {
        isWhippedCreamChecked = ((CheckBox) view).isChecked();
        if (isWhippedCreamChecked)
            basePrice += 1;
        else
            basePrice -= 1;
        calculatePrice();
        displayPrice(total);
    }

    public void onChocolateClicked(View view) {
        isChocolateChecked = ((CheckBox) view).isChecked();
        if (isChocolateChecked)
            basePrice += 2;
        else
            basePrice -= 2;

        calculatePrice();
        displayPrice(total);
    }

    public void incrementOrder(View view) {
        if (quantity < 100)
            quantity++;
        else
            displayToast("The number drinks cannot exceed 100");
        setOrderButtonState();
        calculatePrice();
        display(quantity);
        displayPrice(total);
    }

    public void decrementOrder(View view) {
        if (quantity >= 1)
            quantity--;
        else
            displayToast("The number drinks cannot be less than 0");

        setOrderButtonState();
        calculatePrice();
        display(quantity);
        displayPrice(total);
    }

    private void calculatePrice() {
        total = basePrice * quantity;
    }

    private void displayToast(String toastMessage) {
        Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void submitOrder(View view) {
        StringBuffer orderSummary = new StringBuffer();
        calculatePrice();

        EditText name = (EditText) findViewById(R.id.name_text_view);
        orderSummary.append(getResources().getString(R.string.order_name)).append(name.getText().toString()).append("\n");
        if (isWhippedCreamChecked)
            orderSummary.append(getResources().getString(R.string.order_add_whipped_cream)).append(isWhippedCreamChecked).append("\n");
        if (isChocolateChecked)
            orderSummary.append(getResources().getString(R.string.order_add_chocolate)).append(isChocolateChecked).append("\n");
        orderSummary.append(getResources().getString(R.string.order_quantity)).append(quantity).append("\n");
        orderSummary.append(getResources().getString(R.string.order_total_price)).append(total).append("\n").append(getResources().getString(R.string.order_thank_you));
//            orderView.setText(orderSummary);

        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:"))
                .putExtra(Intent.EXTRA_EMAIL, "tem@gmail.com")
                .putExtra(Intent.EXTRA_SUBJECT, "Your Coffee order " + name.getText().toString())
                .putExtra(Intent.EXTRA_TEXT, orderSummary.toString());

        /*Calendar start = Calendar.getInstance();
        start.set(2016, 2, 9, 22, 30);
        Calendar end = Calendar.getInstance();
        end.set(2016, 2, 9, 23, 30);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "Happy Birthday")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Home")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis());*/

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    /**
     * this method displays the quantity of
     *
     * @param number the number of coffees
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
