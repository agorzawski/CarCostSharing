package arek.carcostsharing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainScreen extends AppCompatActivity {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.#");
    private static final double DISTANCE_IN_KM_AT_WHICH_DIFFERENT_VALUE_PER_KM = 200;
    private static final double VALUE_PER_KM_BELOW_BORDER_DISTANCE = 0.1;
    private static final double VALUE_PER_KM_ABOVE_BORDER_DISTANCE = 0.05;

    private Button calculate;

    private EditText peopleInTrip;
    private EditText distance;
    private EditText carsInTrip;
    private EditText tollPerCar;

    private TextView pricePerPerson;
    private TextView pricePerCar;
    private TextView valuePerPerson;

    private TextView commentOnPrice;

    private CheckBox includeInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initializeComponents();
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateFromInputFields();
            }
        });

        includeInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (includeInstructor.isChecked()) {
                    commentOnPrice.setText("(excluding chief)");
                } else {
                    commentOnPrice.setText("(including chief)");
                }
            }
        });
    }
    private void initializeComponents() {
        calculate = (Button) findViewById(R.id.calculate);

        includeInstructor = (CheckBox) findViewById(R.id.calcWithoutInstructor);

        peopleInTrip = (EditText) findViewById(R.id.nbOfPeople);
        carsInTrip = (EditText) findViewById(R.id.numberOfCars);
        distance = (EditText) findViewById(R.id.totalDistance);
        tollPerCar = (EditText) findViewById(R.id.tollPerCar);

        commentOnPrice = (TextView) findViewById(R.id.commentOnShare);
        pricePerPerson = (TextView) findViewById(R.id.costPerPerson);
        pricePerCar = (TextView) findViewById(R.id.costPerCar);
        valuePerPerson = (TextView) findViewById(R.id.valuePerPerson);
    }

    private void calculateFromInputFields() {
        double distanceInKm = Double.valueOf(distance.getText().toString());
        double valuePerPersonForDistance = getValueForDistance(distanceInKm);
        valuePerPerson.setText(DECIMAL_FORMAT.format(valuePerPersonForDistance));

        int nbOfPeopleInTrip = Integer.valueOf(peopleInTrip.getText().toString());

        double totalValueForAllPeoples = nbOfPeopleInTrip * valuePerPersonForDistance;

        double totalCars = Integer.valueOf(carsInTrip.getText().toString());
        double additionalExpensPerCar = Double.valueOf(tollPerCar.getText().toString());

        double totalCarExpenses = totalCars * additionalExpensPerCar;
        double totalCost = totalCarExpenses + totalValueForAllPeoples;

        int reduction = includeInstructor.isChecked() ? 1 : 0;

        double personShare = totalCost / (nbOfPeopleInTrip - reduction);
        double carShare = totalCost / totalCars;

        pricePerPerson.setText(DECIMAL_FORMAT.format(personShare));
        pricePerCar.setText(DECIMAL_FORMAT.format(carShare));
    }

    private double getValueForDistance(double distance) {
        double value = 0;
        if (distance < DISTANCE_IN_KM_AT_WHICH_DIFFERENT_VALUE_PER_KM) {
            value = distance * VALUE_PER_KM_BELOW_BORDER_DISTANCE;
        } else {
            value = DISTANCE_IN_KM_AT_WHICH_DIFFERENT_VALUE_PER_KM * VALUE_PER_KM_BELOW_BORDER_DISTANCE +
                    (distance - DISTANCE_IN_KM_AT_WHICH_DIFFERENT_VALUE_PER_KM) * VALUE_PER_KM_ABOVE_BORDER_DISTANCE;
        }
        return value;
    }
}
