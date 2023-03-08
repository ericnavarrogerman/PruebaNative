package com.navadev.pruebanative.feature.list.presenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.feature.add.presenter.AddViewModel;
import com.navadev.pruebanative.feature.add.presenter.StatusAction;
import com.navadev.pruebanative.feature.list.usecase.GetAllIncidentesUseCaseImpl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListViewmodel extends ViewModel {


    GetAllIncidentesUseCaseImpl getAllIncidentes;

    private Disposable disposable;

    private final MutableLiveData<List<Incidente>> _incidentes = new MutableLiveData<>();
    public LiveData<List<Incidente>> incidentes = _incidentes;

    private final MutableLiveData<StatusAction> _statusView = new MutableLiveData<>();

    public LiveData<StatusAction> statusView = _statusView;

    public ListViewmodel(GetAllIncidentesUseCaseImpl getAllIncidentes) {
        this.getAllIncidentes  = getAllIncidentes;

        getAllIncidentes();

    }


    public void getAllIncidentes(){

        disposable = Observable.fromCallable(() -> {
                    //para que se alcance a mostrar el dialogo
                    Thread.sleep(1000);
                    return getAllIncidentes.execute();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> _statusView.setValue(StatusAction.InProgress))
                .doFinally(() -> _statusView.setValue(StatusAction.None))
                .onErrorResumeNext(throwable -> {
                    _statusView.setValue(StatusAction.ErrorToSave);
                    return Observable.empty();
                })
                .subscribe(_incidentes::postValue);

    }


    @Override
    protected void onCleared() {
        if (disposable!=null) {
            disposable.dispose();
        }
        super.onCleared();
    }




}
