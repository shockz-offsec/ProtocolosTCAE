package com.example.protocolostcae;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    SharedPreferences preferencias;
    ImageView[] array;
    int[] arrayids;
    int size = 26;
    ArrayList<IndexBusqueda> listIndex;
    RecyclerAdaptador recyclerAdaptador;
    RecyclerView recyclerView;
    ArrayList<IndexBusqueda> listaFiltrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencias = getPreferences(Activity.MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        //inicializo el array que servira para controlar el estado de los iconos de favoritos
        array = new ImageView[size];
        arrayids = new int[size];
        listIndex = new ArrayList<>();
        listaFiltrada = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rvIndex);
        cargarNotas();
        checkPermission();

    }


    @Override
    public void onResume() {
        super.onResume();
        Favoritos.importarConjunto(preferencias.getStringSet("favoritos", new HashSet<String>()));
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putStringSet("favoritos", Favoritos.exportarConjunto());
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {

                recyclerAdaptador.setFilter(listIndex);

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int t = item.getItemId();
        if (t == R.id.Info) {
            Intent j = new Intent(MainActivity.this, Info.class);
            startActivity(j);
        }
        return true;

    }


    @Override
    public boolean onQueryTextChange(String newText) {
        try {

            listaFiltrada = filter(listIndex, newText);
            recyclerAdaptador.setFilter(listaFiltrada);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }


    private ArrayList<IndexBusqueda> filter(ArrayList<IndexBusqueda> indexs, String texto) {

        listaFiltrada = new ArrayList<>();

        try {

            texto = texto.toLowerCase();

            for (IndexBusqueda index : indexs) {
                String index1 = index.getNota().toLowerCase();

                if (index1.contains(texto)) {
                    listaFiltrada.add(index);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaFiltrada;
    }

    //Buildea el Recycler View
    public void cargarNotas() {
        //Llamamos el recycler iniciamos y declaramos la orientacion
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        //llamamos el metodo  getlistaNotas de la clase Notas
        listIndex = new IndexBusqueda().getlistaIndexBusqueda();

        //Verificamos que exista un valor en la lista
        if (listIndex != null) {

            //creamos el adaptador del recycler agregamos la lista de notas y el onIntemClickListener
            recyclerAdaptador = new RecyclerAdaptador(listIndex, new RecyclerAdaptador.OnItemClickListener() {
                @Override
                //Obtenemos la posicion
                public void onItemClick(final int position) {
                    String pos = String.valueOf(position + 1);
                    Intent i = new Intent(MainActivity.this, Gestor_De_Textos.class);
                    i.putExtra("i", pos);
                    startActivity(i);
                }

                @Override
                public void onFavClick(int posicion) {

                }

            });
            //Agregamos el adaptador al recycler
            recyclerView.setAdapter(recyclerAdaptador);
        }
    }


    private void checkPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);

                Toast.makeText(this, "Requesting permissions", Toast.LENGTH_SHORT).show();

            } else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "The permissions are already granted ", Toast.LENGTH_SHORT).show();

            }

        }

        return;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "OK Permissions granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permissions are not granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
