package arek.carcostsharing;

import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainScreen extends AppCompatActivity {

    private Button calculate;
    private EditText peopleInTrip;
    private EditText pricePerPerson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        calculate = (Button) findViewById(R.id.calculate);
        peopleInTrip = (EditText) findViewById(R.id.nbOfPeople);
        pricePerPerson = (EditText) findViewById(R.id.pricePerPerson);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double distanceInKm = 150;

                double factor  = (distanceInKm<200 ? 1 : 0.5);

                double valuePerPersonForDistance = distanceInKm * factor;

                int nbOfPeopleInTrip = Integer.valueOf(peopleInTrip.getText().toString());

                double totalValueForAllPeoples = nbOfPeopleInTrip * valuePerPersonForDistance;

                double totalCars = 2;

                double additionalExpensPerCar = 0;

                double totalCarExpenses = totalCars*additionalExpensPerCar;


                double totalCost = totalCarExpenses + totalValueForAllPeoples;

                double personShare = totalCarExpenses / (nbOfPeopleInTrip);
                double carShare = totalCarExpenses / totalCars;
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");

                pricePerPerson.setText(df.format(personShare));

            }
        });


    }
}
