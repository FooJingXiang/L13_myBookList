package sg.edu.rp.c346.id20011262.mybooklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Book> bookList;
    ArrayList<String> year;
    CustomAdapter adapter;
    Button btn;
    Spinner spn;
    ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onResume() {

        super.onResume();
        DBHelper dbh = new DBHelper(this);
        bookList.clear();
        bookList.addAll(dbh.getAllBook());
        adapter.notifyDataSetChanged();
        year.clear();
        year.addAll(dbh.getYears());
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.listView);
        btn = (Button) this.findViewById(R.id.button);
        spn = (Spinner) this.findViewById(R.id.spinner2);
        DBHelper dbh = new DBHelper(this);
        bookList = dbh.getAllBook();
        year = dbh.getYears();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, bookList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("book", bookList.get(position));
                startActivity(i);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                bookList.clear();
                bookList.addAll(dbh.getAllSongsByStars(4));
                adapter.notifyDataSetChanged();
            }
        });
        spinnerAdapter = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_spinner_dropdown_item, year);
        spn.setAdapter(spinnerAdapter);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                bookList.clear();
                bookList.addAll(dbh.getAllBooksByYear(Integer.valueOf(year.get(position))));
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}