package com.example.protocolostcae;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Gestor_De_Textos extends AppCompatActivity {

    String idTexto;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getIntent().getStringExtra("i");
        setContentView(R.layout.textos);
        TextView select;
        Resources res = getResources();
        String[] textos = res.getStringArray(R.array.Textos);
        pos = Integer.parseInt(id) - 1;
        idTexto = textos[pos];
        select = findViewById(R.id.Texto);
        select.setText(idTexto);
        String[] indices = getResources().getStringArray(R.array.Indices);
        this.setTitle(indices[pos]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secundario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int t = item.getItemId();
        if (t == R.id.Info) {
            Intent j = new Intent(Gestor_De_Textos.this, Info.class);
            startActivity(j);
        }
        return true;

    }
}
