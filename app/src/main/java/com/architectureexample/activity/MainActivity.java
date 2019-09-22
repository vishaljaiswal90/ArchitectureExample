package com.architectureexample.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.architectureexample.adapter.NoteAdapter;
import com.architectureexample.R;
import com.architectureexample.databinding.ActivityMainBinding;
import com.architectureexample.roomDatabase.Note;
import com.architectureexample.viewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    private static int ADD_SAVE_NOTE = 1;
    private static int EDIT_SAVE_NOTE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoteAdapter adapter = new NoteAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, AddEditNote.class);
                startActivityForResult(intent, ADD_SAVE_NOTE);*/

                Intent intent =  new Intent(MainActivity.this,ArticleActivity.class);
                startActivity(intent);

            }
        });
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNote().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                adapter.submitList(notes);
            }
        });

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNote.class);
                intent.putExtra(AddEditNote.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNote.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNote.EXTRA_PRIORITY, note.getPriority());
                intent.putExtra(AddEditNote.EXTRA_ID, note.getId());
                startActivityForResult(intent, EDIT_SAVE_NOTE);

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getPositionAt(viewHolder.getAdapterPosition()));

                Toast.makeText(MainActivity.this, "delete Note", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SAVE_NOTE && resultCode == RESULT_OK) {
            if (data != null) {
                Note note = new Note(data.getStringExtra(AddEditNote.EXTRA_TITLE),
                        data.getStringExtra(AddEditNote.EXTRA_DESCRIPTION),
                        data.getIntExtra(AddEditNote.EXTRA_PRIORITY, 1));
                noteViewModel.insert(note);
                Toast.makeText(this, "Note save", Toast.LENGTH_SHORT).show();

            }

        } else if (requestCode == EDIT_SAVE_NOTE && resultCode == RESULT_OK) {
            int id  = data.getIntExtra(AddEditNote.EXTRA_ID,-1);
            if (id == -1){
                Toast.makeText(this, "Note Count be Update", Toast.LENGTH_SHORT).show();
                return;
            }
            Note note =  new Note(data.getStringExtra(AddEditNote.EXTRA_TITLE),
                    data.getStringExtra(AddEditNote.EXTRA_DESCRIPTION),
                    data.getIntExtra(AddEditNote.EXTRA_PRIORITY, 1));
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note Update Successfully ", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Note not save", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_note:
                noteViewModel.deleteAllNote();
                Toast.makeText(this, "Delete All Notes", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
