package com.ntecprograms.simplexmobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LinearActivity extends AppCompatActivity {

    Matriz<Double> matriz;
    ArrayList<String> restr, lista;
    String maxZ = "";
    int l=-1, x=0, r=0, i;

    Intent intent;
    Alerts alerts;
    ListView listView;
    ArrayAdapter adplista;

    public static String nomeAtualDoArquivo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        alerts = new Alerts(this);
        if (getIntent().getBooleanExtra("abriAgora", false))
            nomeAtualDoArquivo = getIntent().getStringExtra("nomeAtualDoArquivo");

        if (getIntent().getBooleanExtra("inicio", true) == false){
            matriz = (Matriz<Double>) getIntent().getSerializableExtra("matriz");
            restr = (ArrayList<String>) getIntent().getSerializableExtra("restr");
            maxZ = getIntent().getStringExtra("maxZ");
            x = getIntent().getIntExtra("x", 0);
            r = getIntent().getIntExtra("r", 0);

            criarListaDeRestricao();
            // MUDA A FORMULA QUE APARECE NO TXTMaxZ
            TextView textoZ = (TextView)findViewById(R.id.txtZ);
            textoZ.setText(maxZ);
            // ADICIONA AS VARIAVEIS NO TXTVar
            TextView textoVar = (TextView)findViewById(R.id.txtV);
            for (i=1; i<=x; i++) {
                textoVar.setText(textoVar.getText() + " x" + i);
                if (i != x)
                    textoVar.setText(textoVar.getText() + ",");
            }
        }
        else {
            matriz = new Matriz<Double>();
            matriz.addLinha(1, 0d);
            restr = new ArrayList<String>();
            nomeAtualDoArquivo = "";
        }
    }

    public void onBackPressed() {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.linear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save:
                alerts.showAlertSave(nomeAtualDoArquivo, matriz, restr, maxZ, matriz.size(0), x, r);
                break;
            case R.id.help:
                alerts.showAlertHelp();
                break;
            case R.id.back:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // RESTRIÇÕES
    public void criarListaDeRestricao(){
        // PEGA A LISTA DE RESTRIÇÕES E ADICIONA A REGRA DE Ñ NEGATIVIDADE
        lista = new ArrayList<String>();
        if (x != 0)
            lista.add(getString(R.string.new_restr));
        for (i = 0; i < restr.size(); i++)
            lista.add(restr.get(i).toString());
        for (i = 1; i <= x; i++)
            lista.add("x" + i + " >= 0");

        // VINCULA A LISTA EM UM ADAPTER, E O ADAPTER EM UM LISTVIEW
        adplista = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        listView = (ListView)findViewById(R.id.listRestr);
        listView.setAdapter(adplista);

        // EVENTO DO CLIQUE NORMAL NO ITEM DA LISTA
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                if (pos == 0) entradaRestricao(-1);
            }
        });

        // EVENTO DO CLIQUE LONGO NO ITEM DA LISTA
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                if (pos == 0) entradaRestricao(-1);
                else if (pos <= r) {
                    // CRIA O ALERTDIALOG PARA EDIÇÃO/EXCLUSÃO
                    AlertDialog.Builder builder = new AlertDialog.Builder(LinearActivity.this);
                    builder.setTitle(getString(R.string.title_restr));
                    builder.setMessage(lista.get(pos));

                    // EVENTO DO BOTÃO EXCLUIR
                    builder.setPositiveButton(getString(R.string.bt_del), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            r--;
                            lista.remove(pos);
                            restr.remove(pos - 1);
                            matriz.remove(pos);
                            for (i = 0; i < matriz.size(); i++)
                                matriz.remove(i, pos + x);
                            adplista.notifyDataSetChanged();
                            alerts.Toast(getString(R.string.del_success));
                        }
                    });

                    // EVENTO DO BOTÃO EDITAR
                    builder.setNegativeButton(getString(R.string.bt_edit), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            entradaRestricao(pos);
                        }
                    });

                    // EVENTO DO BOTÃO CANCELAR (Ñ FAZ NADA)
                    builder.setNeutralButton(getString(R.string.bt_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {}
                    });

                    builder.create().show();
                }
                else alerts.Toast(getString(R.string.rest_error));
                return false;
            }
        });
    }

    public void entradaRestricao(int l2) {
        intent = new Intent(this, EntradaActivity.class);
        if (l2 == -1) {
            r++;
            l = r;
            intent.putExtra("editar", false);
        } else {
            l = l2;
            intent.putExtra("editar", true);
        }
        intent.putExtra("matriz", matriz);
        intent.putExtra("restr", restr);
        intent.putExtra("maxZ", maxZ);
        intent.putExtra("l", l);
        intent.putExtra("x", x);
        intent.putExtra("r", r);
        startActivity(intent);
        finish();
    }

    public void funcaoDeTeste(){
        String temp = "";
        lista.add("");
        lista.add("x = " + x + " / r = " + r);
        for (i = 0; i<matriz.size(); i++){
            for (int j = 0; j<matriz.size(i); j++)
                temp = temp + matriz.get(i, j) + " / ";
            lista.add(temp);
            temp = "";
        }
    }

    // FUNÇÃO FOMAXZ
    public void entradaFOMaxZ(View view){
        intent = new Intent(this, EntradaActivity.class);
        intent.putExtra("matriz", matriz);
        intent.putExtra("restr", restr);
        intent.putExtra("maxZ", maxZ);
        intent.putExtra("x", x);
        intent.putExtra("r", r);
        intent.putExtra("l", 0d);
        startActivity(intent);
        finish();
    }

    // BOTÃO DE CALCULAR
    public void calcular(View view){
        if (x>=1 && r>=1) {
            intent = new Intent(this, CalcularActivity.class);
            intent.putExtra("matriz", matriz);
            intent.putExtra("x", x);
            intent.putExtra("r", r);
            startActivity(intent);
        }
        else alerts.Toast(getString(R.string.calc_error));
    }

}
