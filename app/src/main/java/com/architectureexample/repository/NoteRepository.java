package com.architectureexample.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.architectureexample.roomDatabase.Note;
import com.architectureexample.roomDatabase.NoteDao;
import com.architectureexample.roomDatabase.NoteDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;


    public  NoteRepository(Application application){
        NoteDatabase noteDatabase  = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNote();
    }

    public void insert(Note note){

        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void Update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllData(){
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }


    //
    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        private DeleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }


    }
}
