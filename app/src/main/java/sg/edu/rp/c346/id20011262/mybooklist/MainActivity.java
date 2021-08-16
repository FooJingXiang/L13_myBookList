package sg.edu.rp.c346.id20011262.mybooklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etCategory, etAuthor, etYear;
    Button btnInsert, btnShow;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_main));

        etTitle = (EditText) findViewById(R.id.etTitle);
        etCategory = (EditText) findViewById(R.id.etCategory);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        etYear = (EditText) findViewById(R.id.etYear);
        rb = findViewById(R.id.ratingBar);
        btnShow = findViewById(R.id.buttonList);
        btnInsert = findViewById(R.id.buttonInsert);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String category = etCategory.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                if (title.length() == 0 || category.length() == 0 || author.length() == 0){
                    Toast.makeText(MainActivity.this, "One of the Fields is Incomplete!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String year_str = etYear.getText().toString().trim();
                int year = 0;
                try {
                    year = Integer.valueOf(year_str);
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid Year Entered!", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBHelper dbh = new DBHelper(MainActivity.this);
                int stars = getRatings();
                dbh.insertBook(title, category, author, year, stars);
                dbh.close();
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_LONG).show();
                etTitle.setText("");
                etAuthor.setText("");
                etCategory.setText("");
                etYear.setText("");
                rb.setRating(0);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });
    }

    private int getRatings() {
        int stars = 1;
        stars = (int) rb.getRating();
        return stars;
    }
}
