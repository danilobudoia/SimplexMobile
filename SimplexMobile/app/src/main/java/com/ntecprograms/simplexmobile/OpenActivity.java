package com.ntecprograms.simplexmobile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenActivity extends Activity {

    File[] arquivos;
    File diretorio;
    List<String> nomes;

    ListView listView;
    ArrayAdapter adapter;

    TextView txt_nome;
    Button button;

    Intent intent;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        txt_nome = (TextView)findViewById(R.id.txt_nome);
        listView = (ListView)findViewById(R.id.listView);
        button = (Button)findViewById(R.id.bt_open);

        // PEGA O DIRETORIO E O ARRAY DE DIRETORIOS
        //diretorio = new File(getString(R.string.local));
        diretorio = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "");
        if(!diretorio.isDirectory())
            diretorio.mkdirs();
        arquivos = diretorio.listFiles();

        // EXIBE A LISTA DE DIRETORIOS NO LISTVIEW
        if (arquivos.length <= 0)
            txt_nome.setText(getString(R.string.no_projects));
        nomes = new ArrayList<String>();
        for(i = 0; i < arquivos.length; i++) {
            File f = arquivos[i];
            if (f.isFile())
                nomes.add(f.getName());
        }
        adapter = new ArrayAdapter(OpenActivity.this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);

        //NORMAL CLICK
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                txt_nome.setText(arquivos[pos].getName());
            }
        });

        // LONG CLICK
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
                // CRIA O ALERTDIALOG PARA EXCLUSÃO
                AlertDialog.Builder builder = new AlertDialog.Builder(OpenActivity.this);
                builder.setTitle(getString(R.string.title_del));
                builder.setMessage(arquivos[pos].getName());

                // EVENTO DO BOTÃO EXCLUIR
                builder.setPositiveButton(getString(R.string.bt_del), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arquivos[pos].delete();
                        nomes.remove(pos);
                        adapter.notifyDataSetChanged();
                        if (arquivos.length <= 0)
                            txt_nome.setText(getString(R.string.no_projects));
                        else
                            txt_nome.setText(getString(R.string.select));
                        Toast.makeText(OpenActivity.this, getString(R.string.del_success), Toast.LENGTH_SHORT).show();
                    }
                });

                // EVENTO DO BOTÃO CANCELAR
                builder.setNeutralButton(getString(R.string.bt_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {}
                });
                builder.create().show();
                return false;
            }
        });

        // EVENTO DO BOTÃO
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matriz<Double> matriz = new Matriz<Double>();
                matriz.addLinha();
                ArrayList<String> restr = new ArrayList<String>();
                String maxZ = "", linha;
                int x=0, r=0, c=0, qtdC = 0, qtdL=0, linhaAtual=0;
                try {
                    File arquivo = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), txt_nome.getText().toString());
                    FileReader fr = new FileReader(arquivo);
                    BufferedReader br = new BufferedReader(fr);
                    while(br.ready()){
                        linha = br.readLine();
                        if (linhaAtual == 0)
                            maxZ = linha;
                        else if (linhaAtual == 1)
                            c = Integer.parseInt(linha);
                        else if (linhaAtual == 2)
                            x = Integer.parseInt(linha);
                        else if (linhaAtual == 3)
                            r = Integer.parseInt(linha);
                        else if (linhaAtual >= 4 && linhaAtual < r+4)
                            restr.add(linha);
                        else {
                            if (qtdC < c) {
                                matriz.add(qtdL, Double.parseDouble(linha));
                                qtdC++;
                            }
                            else {
                                qtdC = 1;
                                qtdL++;
                                matriz.addLinha();
                                matriz.add(qtdL, Double.parseDouble(linha));
                            }
                        }
                        linhaAtual++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent = new Intent(OpenActivity.this, LinearActivity.class);
                intent.putExtra("matriz", matriz);
                intent.putExtra("restr", restr);
                intent.putExtra("maxZ", maxZ);
                intent.putExtra("x", x);
                intent.putExtra("r", r);
                intent.putExtra("inicio", false);
                intent.putExtra("abriAgora", true);
                intent.putExtra("nomeAtualDoArquivo", txt_nome.getText());
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackPressed() {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**MAPA DE CARACTERES
     * fomaxZ
     * coluna
     * xis
     * restr
     * lista com restricoes
     * lista com matriz
     */

}
