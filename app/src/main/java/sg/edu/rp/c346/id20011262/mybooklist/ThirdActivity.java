package sg.edu.rp.c346.id20011262.mybooklist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etTitle, etCategory, etAuthor, etYear;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString()+ " ~ " + getResources().getText(R.string.title_activity_third));

        btnCancel = (Button) findViewById(R.id.buttonBack);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        etID = findViewById(R.id.etIDs);
        etTitle = findViewById(R.id.etTitles);
        etCategory = findViewById(R.id.etCategories);
        etAuthor = findViewById(R.id.etAuthors);
        etYear = findViewById(R.id.etYears);
        rb = findViewById(R.id.ratingBar2);

        Intent i = getIntent();
        final Book currentBook = (Book) i.getSerializableExtra("book");

        etID.setText(currentBook.getId() + "");
        etTitle.setText(currentBook.getTitle());
        etAuthor.setText(currentBook.getAuthor());
        etCategory.setText(currentBook.getCategory());
        etYear.setText(currentBook.getYear() + "");
        rb.setRating(currentBook.getRatings());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentBook.setTitle(etTitle.getText().toString().trim());
                currentBook.setCategory(etCategory.getText().toString().trim());
                currentBook.setAuthor(etAuthor.getText().toString().trim());
                currentBook.setRatings((int) rb.getRating());
                int year = 0;
                try {
                    year = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e) {
                    Toast.makeText(ThirdActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentBook.setYear(year);
                int result = dbh.updateBook(currentBook);
                if (result > 0) {
                    Toast.makeText(ThirdActivity.this, "Book Updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Book Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the Book \n" + etTitle.getText().toString());
                myBuilder.setCancelable(false);
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        int result = dbh.deleteBook(currentBook.getId());
                        if (result > 0) {
                            Toast.makeText(ThirdActivity.this, "Book deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Book failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                    myBuilder.setPositiveButton("Cancel",null);
                    AlertDialog myDialog = myBuilder.create();
                    myBuilder.show();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                    myBuilder.setTitle("Danger");
                    myBuilder.setMessage("Are you sure you want to discard the changes?");
                    myBuilder.setCancelable(false);

                    myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    myBuilder.setPositiveButton("Do Not Discard",null);
                    AlertDialog myDialog = myBuilder.create();
                    myBuilder.show();
                }
            });
        }
}