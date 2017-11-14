package com.ccs.agenda.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccs.agenda.R;
import com.ccs.agenda.providers.EventsContract;


public class ActivitiesAdapter extends CursorAdapter {

    public ActivitiesAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView titulo = (TextView) view.findViewById(R.id.TituloTV1);
        titulo.setText(cursor.getString(
                cursor.getColumnIndex(EventsContract.Columnas.TITULO)));

        TextView descripcion = (TextView) view.findViewById(R.id.DescripcionTV1);
        descripcion.setText(cursor.getString(
                cursor.getColumnIndex(EventsContract.Columnas.DESCRIPCION)));

        TextView fecha = (TextView) view.findViewById(R.id.FechaTV1);
        fecha.setText(cursor.getString(
                cursor.getColumnIndex(EventsContract.Columnas.FECHA_EVENTO)));

        TextView hora = (TextView) view.findViewById(R.id.HoraTV1);
        hora.setText(cursor.getString(
                cursor.getColumnIndex(EventsContract.Columnas.HORA_EVENTO)));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.row_layout, parent, false);
    }
}
