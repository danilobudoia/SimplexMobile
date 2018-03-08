package com.ntecprograms.simplexmobile;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Danilo on 30/05/2016.
 */
public class Alerts {

    Context context;
    public Alerts(Context context) {
        this.context = context;
    }

    public void Toast(String mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void showAlertHelp(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View alertView = inflater.inflate(R.layout.alert_help, null);
        alertDialog.setView(alertView);

        final AlertDialog show = alertDialog.show();

        Button alertButton = (Button) alertView.findViewById(R.id.button5);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    public void showAlertInfo(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View alertView = inflater.inflate(R.layout.alert_info, null);
        alertDialog.setView(alertView);

        final AlertDialog show = alertDialog.show();

        Button alertButton = (Button) alertView.findViewById(R.id.button4);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    public void showAlertSave(String nomeDoArquivo, final Matriz matriz, List listaRestr, String maxZ, int colunas, int xis, int restr){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View alertView = inflater.inflate(R.layout.alert_save, null);
        alertDialog.setView(alertView);

        final AlertDialog show = alertDialog.show();

        final Matriz m = matriz;
        final List lista = listaRestr;
        final String txt = maxZ;
        final int c = colunas, x = xis, r = restr;

        final TextView txt_nome = (TextView)show.findViewById(R.id.txt_nome);
        txt_nome.setText(nomeDoArquivo);

        Button alertButton = (Button) alertView.findViewById(R.id.bt_save);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome;
                if (txt_nome.getText().toString().trim().equals("")) nome = obterData();
                else nome = txt_nome.getText().toString();

                try {
                    // Abre o arquivo para escrita ou cria se não existir
                    File arquivo = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), nome);
                    if (!arquivo.exists()) arquivo.createNewFile();

                    FileWriter fw = new FileWriter(arquivo);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(txt); bw.newLine();
                    bw.write(Integer.toString(c)); bw.newLine();
                    bw.write(Integer.toString(x)); bw.newLine();
                    bw.write(Integer.toString(r)); bw.newLine();
                    for (int i = 0; i<lista.size(); i++ ){
                        bw.write(lista.get(i).toString());
                        bw.newLine();
                    }
                    for (int i = 0; i<matriz.size(); i++){
                        for (int j = 0; j<matriz.size(0); j++){
                            bw.write(matriz.get(i, j).toString());
                            bw.newLine();
                        }
                    }

                    bw.close();
                    fw.close();
                    Toast.makeText(context, context.getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.save_error), Toast.LENGTH_SHORT).show();
                }
                show.dismiss();
            }
        });
    }

    private String obterData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Calendar  cal = Calendar.getInstance();
        cal.setTime(new Date());
        Date data_atual = cal.getTime();
        return dateFormat.format(data_atual);
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
