package com.ccs.agenda.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ccs.agenda.R;
import com.ccs.agenda.activity.DetailActivity;
import com.ccs.agenda.activity.InsertNewActivity;
import com.ccs.agenda.activity.MainActivity;
import com.ccs.agenda.adapters.ActivitiesAdapter;
import com.ccs.agenda.providers.EventsContract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor>, InsertNewFragment.NuevoDialogListener  {

    private ActivitiesAdapter adaptador;
    public String consulta;
    public TextView fecha;
    public View view;



    public MyListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_list, container, false);

        fecha =(TextView) view.findViewById(R.id.FechaSelecionada);

        Date fechaActual = new Date();
        SimpleDateFormat date= new SimpleDateFormat("dd-MM-yyyy");
        String today =date.format(fechaActual);
        fecha.setText(today);

        String FechaSelect = fecha.getText().toString();
        consulta =
                EventsContract.Columnas.FECHA_EVENTO + " = '"+ FechaSelect + "' order by "+
                        EventsContract.Columnas.HORA_EVENTO + " asc ;";

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, " Evento Creado ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getActivity().startActivity(
                        new Intent(getActivity(), InsertNewActivity.class)
                );*/
                dialogoPersonalizado();
            }
        });


        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.calender:
                MainDatePickerFragment newFragment = new MainDatePickerFragment();
                newFragment.setHostingFragment(this);
                newFragment.show(getFragmentManager(), "datePicker");

        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adaptador = new ActivitiesAdapter(getActivity());
        setListAdapter(adaptador);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getActivity(), EventsContract.CONTENT_URI,null,consulta,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptador.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        adaptador.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getActivity().startActivity(new Intent(getActivity(), DetailActivity.class)
                .putExtra(EventsContract.Columnas._ID, id));

        Log.d("MyListFragment","" + id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getLoaderManager().destroyLoader(0);
            if (adaptador != null) {
                adaptador.changeCursor(null);
                adaptador = null;
            }
        } catch (Throwable localThrowable) {
        }
    }

    public void setDate(String DateSelect){


        getLoaderManager().destroyLoader(0);
        consulta =
                EventsContract.Columnas.FECHA_EVENTO + " = '"+ DateSelect + "' order by "+
                        EventsContract.Columnas.HORA_EVENTO + " asc ;";

        getLoaderManager().initLoader(0,null,this);

    }

    public void dialogoPersonalizado(){
        InsertNewFragment dialogoPersonalizado = new InsertNewFragment();
        dialogoPersonalizado.show(getFragmentManager(), "personalizado");

        android.support.v4.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado");

        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
    }

    @Override
    public void FinalizaCuadroDialogo(String texto) {

    }
}
