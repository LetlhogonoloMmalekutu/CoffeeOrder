package com.example.ekasilabalexcdtb.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 2;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method early because there's is nothing left to do
            return;
        }
        quantity=quantity + 1;
        display(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity==0) {
            // Show an error as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early because there's is nothing left to do
            return;
        }
        quantity=quantity - 1;
        display(quantity);
    }
        /**
         * This method is called when the Order button is clicked.
         */
    public void submitOrder(View view){
        //find the user's name
        EditText namefield = (EditText) findViewById(R.id.name_field);
        String name = namefield.getText().toString();

        //Figure out if the user wants whipped cream topping
        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();

        //Figure out if the user wants whipped cream topping
        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        //Display the order summary on the screen
        String priceMessage = createOrderSummary(name,price , hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

            displayMessage(priceMessage);



    }
    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private  int  calculatePrice (boolean addWhippedCream, boolean addChocolate) {
        //First Calculate the price of the coffee
       int basePrice = 5;
        // If the user wants whipped cream, add R1 per cup
        if (addWhippedCream) {
           basePrice =basePrice + 1;
        }

        // If the user wants chocolate, add R2 per cup
               if (addChocolate) {
                   basePrice = basePrice + 2;
               }
        //Calculate the total order price by multiplying by quantity
            return quantity *basePrice;
    }
    private String createOrderSummary(String name ,int price, boolean addWhippedCream,boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage = priceMessage +"\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage = priceMessage +"\nAdd Chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity : " + quantity;
        priceMessage = priceMessage + "\nTotal: R" +price ;
        priceMessage =  priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This Method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffees){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
    /**
     *This method displays the given quantity value on the screen
     */
    private void displayPrice(int number) {
        TextView priceTextView=(TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));

    }
    /**
     *This method displays the given text on the screen
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView= (TextView)findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
