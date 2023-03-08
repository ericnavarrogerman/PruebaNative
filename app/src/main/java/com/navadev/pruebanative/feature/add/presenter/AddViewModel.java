package com.navadev.pruebanative.feature.add.presenter;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.navadev.pruebanative.feature.add.model.Incidente;
import com.navadev.pruebanative.feature.add.usecase.CrearIncidenteUseCase;
import com.navadev.pruebanative.feature.add.usecase.GetNextAvailableId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddViewModel extends ViewModel {


    CrearIncidenteUseCase crearIncidenteUseCase;

    GetNextAvailableId getNextAvailableIdUseCase;

    private Disposable disposable;

    private final MutableLiveData<String> _fecha = new MutableLiveData<>();
    public LiveData<String> fecha = _fecha;

    private final MutableLiveData<String> _hora = new MutableLiveData<>();
    public LiveData<String> hora = _hora;

    private final MutableLiveData<String> _nextId = new MutableLiveData<>();
    public LiveData<String> nextId = _nextId;


    private final MutableLiveData<Boolean> _descriptionIsValid = new MutableLiveData<>();

    public LiveData<Boolean> descriptionIsValid = _descriptionIsValid;

    private final MutableLiveData<StatusAction> _statusView = new MutableLiveData<>();

    public LiveData<StatusAction> statusView = _statusView;

    private final MutableLiveData<Boolean> _haveImage = new MutableLiveData<>();

    public LiveData<Boolean> haveImage = _haveImage;

    private Handler handler;
    private Runnable runnable;


    private String photo;

    public AddViewModel(CrearIncidenteUseCase useCase,GetNextAvailableId getNextAvailableId) {
        this.crearIncidenteUseCase = useCase;
        this.getNextAvailableIdUseCase = getNextAvailableId;
        _statusView.setValue(StatusAction.None);
        _descriptionIsValid.setValue(false);
        _haveImage.setValue(false);
        startUpdatingDateTime();
        getNextAvailableId();

    }


    public void takeAPhoto(){
        if (photo==null){
            _statusView.setValue(StatusAction.TakeAPhoto);
        }else {
            _statusView.setValue(StatusAction.ImagePresent);
        }
    }


    public void crearIncidente(String ciudad,String descripcion) {
        Boolean isValidDescription = descriptionIsValid.getValue();
        Boolean getHaveImage = haveImage.getValue();

        if (!isValidDescription){
            _statusView.setValue(StatusAction.DialogNoDescriptionValid);
            return;
        }

        if (!getHaveImage){
            _statusView.setValue(StatusAction.DialogNoPhotoPresent);
            return;
        }


            Incidente incidente = new Incidente();
            incidente.setFoto(photo);
            String date = fecha.getValue()+hora.getValue();
            incidente.setFecha(convertirFechaHora(date));
            incidente.setUbicacion(ciudad);
            incidente.setDescripcion(descripcion);


        disposable = Observable.fromCallable(() -> {
                    return crearIncidenteUseCase.execute(incidente);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> _statusView.setValue(StatusAction.InProgress))
                .doFinally(() -> _statusView.setValue(StatusAction.None))
                .subscribe(result -> {
                     if (result){
                         _statusView.setValue(StatusAction.GoList);
                     }else {
                         _statusView.setValue(StatusAction.ErrorToSave);
                     }
                });

    }




    public String convertirFechaHora(String fechaHoraString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("'Fecha:' dd/MM/yyyy'Hora:' HH:mm:ss");
        try {
            Date date = dateFormat.parse(fechaHoraString);
            dateFormat.applyPattern("dd/MM/yyyy HH:mm:ss");
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void validateText(Editable text){
       boolean result = isValidDescripcion(text.toString());
        _descriptionIsValid.setValue(result);
    }
    public boolean isValidDescripcion(String descripcion) {
        if (TextUtils.isEmpty(descripcion)) {
            return false;
        }

        String[] words = descripcion.split("\\s+");
        return words.length >= 3;
    }



    public void getNextAvailableId(){
        disposable = Observable.fromCallable(() -> {
                    return getNextAvailableIdUseCase.execute();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> _nextId.postValue("Loading....."))
                .subscribe(result -> {
                    _nextId.postValue("Numero:"+String.format("%08d", result));
                });
    }


    public void startUpdatingDateTime() {
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String fechaString = dateFormat.format(date);
                String horaString = timeFormat.format(date);

                _fecha.postValue("Fecha: "+fechaString);
                _hora.postValue("Hora: "+horaString);

                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(runnable, 0);
    }


    public void stopUpdatingDateTime() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }




    @Override
    protected void onCleared() {
        if (disposable!=null) {
            disposable.dispose();
        }
        stopUpdatingDateTime();
        super.onCleared();
    }


    public void setPhoto(String photo) {
        this.photo = photo;
        if (photo==null){
            _haveImage.setValue(false);
        }else{
            _haveImage.setValue(true);
        }

    }


}

