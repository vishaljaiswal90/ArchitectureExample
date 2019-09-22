package com.architectureexample.viewModel;

import android.app.Application;

import com.architectureexample.repository.NoteRepository;
import com.architectureexample.roomDatabase.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNote;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNote = noteRepository.getAllNotes();
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.Update(note);

    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public void deleteAllNote() {
        noteRepository.deleteAllData();
    }

    public LiveData<List<Note>> getAllNote() {
        return allNote;
    }
}
