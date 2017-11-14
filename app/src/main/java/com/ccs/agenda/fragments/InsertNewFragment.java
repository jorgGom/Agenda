package com.ccs.agenda.fragments;


import android.content.ContentValues;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ccs.agenda.R;
import com.ccs.agenda.providers.EventsContract;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class InsertNewFragment extends Fragment {

    private EditText descripcion,titulo,geo;
    private TextView hora,fecha;



    public InsertNewFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_insert, container, false);


        titulo = (EditText) view.findViewById(R.id.et_title);
        descripcion = (EditText) view.findViewById(R.id.et_desc);
        fecha =(TextView) view.findViewById(R.id.tv5);
        hora = (TextView) view.findViewById(R.id.tv6);
        geo = (EditText) view.findViewById(R.id.et_geo);

        Date fechaActual = new Date();
        SimpleDateFormat date= new SimpleDateFormat("dd-MM-yyyy");
        String today =date.format(fechaActual);

        fecha.setText(today);

        Date horas=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String formattedTime=sdf.format(horas);

        hora.setText(formattedTime);


        ImageButton fab = (ImageButton) view.findViewById(R.id.fav_enviar);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast1 =
                                Toast.makeText(getActivity(),
                                        "Evento Insertado", Toast.LENGTH_SHORT);
                        toast1.show();
                        GuardarEvento();
                        getActivity().finish();

                    }
                }
        );

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            case R.id.action_discard:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void GuardarEvento() {

        String dirs = geo.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        String resultado = null;
        try {
            List<Address> list = geocoder.getFromLocationName(dirs, 1);
            if (list != null && list.size() > 0) {
                Address address = list.get(0);

                resultado = address.getAddressLine(0) + ", " + address.getLocality();
            }
        }
        catch (IOException e) {
            Toast.makeText(getActivity(), "Error al recoger la dirrecion", Toast.LENGTH_SHORT).show();
        }


            ContentValues values = new ContentValues();
            values.put(EventsContract.Columnas.TITULO, titulo.getText().toString());
            values.put(EventsContract.Columnas.DESCRIPCION, descripcion.getText().toString());
            values.put(EventsContract.Columnas.FECHA_EVENTO, fecha.getText().toString());
            values.put(EventsContract.Columnas.HORA_EVENTO, hora.getText().toString());
            values.put(EventsContract.Columnas.LOCALIZACION, resultado);

            getActivity().getContentResolver().insert(
                    EventsContract.CONTENT_URI,
                    values
            );
        }

    }
