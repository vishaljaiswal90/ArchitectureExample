package com.architectureexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.architectureexample.R;
import com.architectureexample.databinding.ActivityAddNoteBinding;

public class AddEditNote extends AppCompatActivity {

    ActivityAddNoteBinding binding;

    public static String EXTRA_TITLE
            = "com.architectureexample.activity.EXTRA_TITLE";
    public static String EXTRA_DESCRIPTION
            = "com.architectureexample.activity.EXTRA_DESCRIPTION";
    public static String EXTRA_PRIORITY
            = "com.architectureexample.activity.EXTRA_PRIORITY";
    public static String EXTRA_ID
            = "com.architectureexample.activity.EXTRA_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_note);
        binding.numberPicker.setMaxValue(10);
        binding.numberPicker.setMinValue(0);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("EDIT NOTE");
            binding.editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            binding.editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            binding.numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else {
            setTitle("ADD NOTE");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.add_notes,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {

        if (binding.editTextTitle.getText().toString().trim().isEmpty() || binding.editTextDescription.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent =  new Intent();
        intent.putExtra(EXTRA_TITLE,binding.editTextTitle.getText().toString().trim());
        intent.putExtra(EXTRA_DESCRIPTION,binding.editTextDescription.getText().toString().trim());
        intent.putExtra(EXTRA_PRIORITY,binding.numberPicker.getValue());

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            intent.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,intent);
        finish();
    }
}
