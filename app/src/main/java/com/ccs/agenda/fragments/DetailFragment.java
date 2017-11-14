package com.ccs.agenda.fragments;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ccs.agenda.R;
import com.ccs.agenda.providers.EventsContract;


public class DetailFragment extends Fragment {

    private TextView descripcion, titulo, fecha, hora, geo;
    private long id;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        titulo = (TextView) view.findViewById(R.id.titulo_text);
        descripcion = (TextView) view.findViewById(R.id.descripcion_text);
        fecha =(TextView) view.findViewById(R.id.fecha_label);
        hora = (TextView) view.findViewById(R.id.hora_label);
        geo = (TextView) view.findViewById(R.id.geo_label);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        id = getActivity().getIntent().getLongExtra(EventsContract.Columnas._ID, -1);
        updateView(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:

                return true;

            case R.id.action_delete:
                Toast toast1 =
                        Toast.makeText(getActivity(),
                                "Evento Eliminado", Toast.LENGTH_SHORT);
                toast1.show();
                deleteData();
                getActivity().finish();
                return true;

            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Titulo: "+titulo.getText().toString()+System.getProperty("line.separator")+
                        " Descripción: "+descripcion.getText().toString()+System.getProperty("line.separator")+
                        " Fecha del evento: "+ fecha.getText().toString()+System.getProperty("line.separator")+
                        " Hora del evento: "+hora.getText().toString()+System.getProperty("line.separator")+
                        " Localización: "+geo.getText().toString());
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void deleteData() {
        Uri uri = ContentUris.withAppendedId(EventsContract.CONTENT_URI, id);
        getActivity().getContentResolver().delete(
                uri,
                null,
                null
        );
    }


    private void updateView(long id) {
        if (id == -1) {
            titulo.setText("");
            descripcion.setText("");
            fecha.setText("");
            hora.setText("");
            geo.setText("");

            return;
        }

        Uri uri = ContentUris.withAppendedId(EventsContract.CONTENT_URI, id);
        Cursor c = getActivity().getContentResolver().query(
                uri,
                null, null, null, null);

        if (!c.moveToFirst())
            return;

        String titulo_text = c.getString(c.getColumnIndex(EventsContract.Columnas.TITULO));
        String descripcion_text = c.getString(c.getColumnIndex(EventsContract.Columnas.DESCRIPCION));
        String fecha_text = c.getString(c.getColumnIndex(EventsContract.Columnas.FECHA_EVENTO));
        String hora_text = c.getString(c.getColumnIndex(EventsContract.Columnas.HORA_EVENTO));
        String geo_text = c.getString(c.getColumnIndex(EventsContract.Columnas.LOCALIZACION));

        titulo.setText(titulo_text);
        descripcion.setText(descripcion_text);
        fecha.setText(fecha_text);
        hora.setText(hora_text);
        geo.setText(geo_text);

        c.close();
    }
}
