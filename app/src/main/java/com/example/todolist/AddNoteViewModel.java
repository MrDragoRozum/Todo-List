package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao;
    private MutableLiveData<Boolean> shouldClosedScreen = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    public void addNote(Note note) {
        Disposable disposable = addNoteRX(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> shouldClosedScreen.setValue(true));
        compositeDisposable.add(disposable);

    }

    private Completable addNoteRX(Note note) {
        return Completable.fromAction(() -> notesDao.add(note));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public LiveData<Boolean> getShouldClosedScreen() {
        return shouldClosedScreen;
    }
}
