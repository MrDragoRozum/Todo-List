package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao;
    private MutableLiveData<Boolean> shouldClosedScreen = new MutableLiveData<>();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    @SuppressLint("CheckResult")
    public void addNote(Note note) {
        notesDao.add(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> shouldClosedScreen.setValue(true));

//        Thread thread = new Thread(() -> {
//            notesDao.add(note);
//            shouldClosedScreen.pastValue(true));
//        });
//        thread.start();
    }

    public LiveData<Boolean> getShouldClosedScreen() {
        return shouldClosedScreen;
    }
}
