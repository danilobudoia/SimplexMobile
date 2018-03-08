package com.ntecprograms.simplexmobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EntradaActivity extends AppCompatActivity {

    Matriz<Double> matriz, TMP_matriz;
    ArrayList<String> restr, TMP_restr;
    String maxZ, TMP_maxZ;
    int x=0, r=0, l=0, i, j;
    int TMP_x=0, TMP_r=0, TMP_l=0;

    Intent intent;
    Alerts alerts;
    TextView txtE, txtS, txtN, opc;
    double aux = 0;
    boolean inverter = false, excluir = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        alerts = new Alerts(this);

        //pega valores da Intent
        matriz = (Matriz<Double>) getIntent().getSerializableExtra("matriz");
        restr = (ArrayList<String>) getIntent().getSerializableExtra("restr");
        maxZ = getIntent().getStringExtra("maxZ");
        x = getIntent().getIntExtra("x", 0);
        r = getIntent().getIntExtra("r", 0);
        l = getIntent().getIntExtra("l", 0);

        TMP_matriz = new Matriz<Double>();
        for (i=0; i < matriz.size(); i++) {
            TMP_matriz.addLinha();
            for (j = 0; j < matriz.size(i); j++)
                TMP_matriz.add(i, matriz.get(i, j));
        }
        TMP_restr = new ArrayList<String>();
        for (i=0; i < restr.size(); i++)
            TMP_restr.add(restr.get(i));
        TMP_maxZ = maxZ;
        TMP_x = x;
        TMP_r = r;
        TMP_l = l;

        txtE = (TextView)findViewById(R.id.txtEntrada);
        txtS = (TextView)findViewById(R.id.txtSinal);
        txtN = (TextView)findViewById(R.id.txtNumero);
        opc = (TextView)findViewById(R.id.textView26);
        intent = new Intent(this, LinearActivity.class);

        if (l == 0){
            while (x != 0){
                matriz.remove(0, 1);
                x--;
            }
            opc.setText("x1");
        }
        else {
            if (getIntent().getBooleanExtra("editar", false)) {
                for (i = 0; i < matriz.size(0); i++)
                    matriz.set(l, i, 0d);
                matriz.set(l, x+l, 1d);
            }
            else {
                matriz.addLinha(matriz.size(0), 0d);
                for (i = 0; i < matriz.size() - 1; i++)
                    matriz.add(i, 0d);
                matriz.add(matriz.size() - 1, 1d);
            }
            opc.setText("Xn");
        }
    }

    public void onBackPressed() {
        if (l != 0 && !getIntent().getBooleanExtra("editar", false))
            TMP_r--;
        intent.putExtra("inicio", false);
        intent.putExtra("matriz", TMP_matriz);
        intent.putExtra("restr", TMP_restr);
        intent.putExtra("maxZ", TMP_maxZ);
        intent.putExtra("x", TMP_x);
        intent.putExtra("r", TMP_r);
        startActivity(intent);
        finish();
    }

    public void fDel(View view){ txtN.setText(""); }

    public void n1(View view){ txtN.setText(txtN.getText()+"1"); }

    public void n2(View view){ txtN.setText(txtN.getText()+"2"); }

    public void n3(View view){ txtN.setText(txtN.getText()+"3"); }

    public void n4(View view){ txtN.setText(txtN.getText()+"4"); }

    public void n5(View view){ txtN.setText(txtN.getText()+"5"); }

    public void n6(View view){ txtN.setText(txtN.getText()+"6"); }

    public void n7(View view){ txtN.setText(txtN.getText()+"7"); }

    public void n8(View view){ txtN.setText(txtN.getText()+"8"); }

    public void n9(View view){ txtN.setText(txtN.getText()+"9"); }

    public void n0(View view){ txtN.setText(txtN.getText()+"0"); }

    public void fPonto(View view){
        if (txtN.getText().equals("")) {
            txtN.setText("0");
        }
        txtN.setText(txtN.getText()+".");
    }

    public void fMaisMenos(View view){
        if (txtS.getText().equals("+")) {
            txtS.setText("-");
        }
        else {
            txtS.setText("+");
        }
    }

    public void fMaior(View view){
        if (opc.getText().equals("Xn")) {
            txtE.setText(txtE.getText() + " > ");
            opc.setText("OK");
            txtS.setText("+");
            inverter = true;
        }
        else alerts.Toast(getString(R.string.error_unknown));
    }

    public void fMaiorIgual(View view){
        if (opc.getText().equals("Xn")) {
            txtE.setText(txtE.getText() + " >= ");
            opc.setText("OK");
            txtS.setText("+");
            inverter = true;
        }
        else alerts.Toast(getString(R.string.error_unknown));
    }

    public void fMenor(View view){
        if (opc.getText().equals("Xn")) {
            txtE.setText(txtE.getText() + " < ");
            opc.setText("OK");
            txtS.setText("+");
        }
        else alerts.Toast(getString(R.string.error_unknown));
    }

    public void fMenorIgual(View view){
        if (opc.getText().equals("Xn")) {
            txtE.setText(txtE.getText() + " <= ");
            opc.setText("OK");
            txtS.setText("+");
        }
        else alerts.Toast(getString(R.string.error_unknown));
    }

    public void fIgual(View view){
        if (!opc.getText().equals("") && !opc.getText().equals("OK")) {
            if (opc.getText().equals("Xn"))
                excluir = true;
            txtE.setText(txtE.getText() + " = ");
            opc.setText("OK");
            txtS.setText("+");
        }
        else alerts.Toast(getString(R.string.error_unknown));
    }

    public void fSalvar(View view) {
        if (l == 0 || opc.getText().equals("")) {
            if (l == 0) {
                if (opc.getText().equals(""))
                    matriz.set(0, 0, aux);
                else {
                    // INVERTE OS VALORES
                    for (i=1; i<=x; i++)
                        matriz.set(0, i, (matriz.get(0, i)*(-1)));
                    matriz.set(0, 0, 0d);
                }
                if (r != 0){
                    // ADICIONA COLUNAS CASO TENHA NOVAS VARIAVEIS (LINHA ZERO MAIOR QUE LINHA UM)
                    while (matriz.size(0) > matriz.size(1)) {
                        int newCol = matriz.size(1) - r;
                        for (i = 1; i < matriz.size(); i++)
                            matriz.add(i, newCol, 0d);
                    }
                    // REMOVE COLUNAS CASO ESTEJA FALTANDO VARIÁVEIS (LINHA ZERO MENOR QUE LINHA UM)
                    while (matriz.size(0) < matriz.size(1)){
                        for (i = 1; i < matriz.size(); i++)
                            matriz.remove(i, x+1);
                    }
                }
                maxZ = txtE.getText().toString();
            }
            else {
                matriz.set(l, 0, aux);
                if (inverter)  matriz.set(l, x+l, -1d);
                if (excluir)  matriz.set(l, x+l, 0d);
                if (getIntent().getBooleanExtra("editar", false)) restr.set(l-1, txtE.getText().toString());
                else restr.add(txtE.getText().toString());
            }
            intent.putExtra("inicio", false);
            intent.putExtra("matriz", matriz);
            intent.putExtra("restr", restr);
            intent.putExtra("maxZ", maxZ);
            intent.putExtra("x", x);
            intent.putExtra("r", r);
            startActivity(intent);
            finish();
        }
        else alerts.Toast(getString(R.string.add_restr));
    }

    public void fVoltar(View view){
        if (l != 0 && !getIntent().getBooleanExtra("editar", false))
            TMP_r--;
        intent.putExtra("inicio", false);
        intent.putExtra("matriz", TMP_matriz);
        intent.putExtra("restr", TMP_restr);
        intent.putExtra("maxZ", TMP_maxZ);
        intent.putExtra("x", TMP_x);
        intent.putExtra("r", TMP_r);
        startActivity(intent);
        finish();
    }

    public void fClear(View view){
        matriz.clear();
        for (i=0; i < TMP_matriz.size(); i++) {
            matriz.addLinha();
            for (j = 0; j < TMP_matriz.size(i); j++)
                matriz.add(i, TMP_matriz.get(i, j));
        }
        restr.clear();
        for (i=0; i < TMP_restr.size(); i++)
            restr.add(TMP_restr.get(i));

        maxZ = TMP_maxZ;
        x = TMP_x;
        r = TMP_r;
        l = TMP_l;
        txtE.setText("");
        txtN.setText("");
        txtS.setText("+");

        if (l == 0){
            while (x != 0){
                matriz.remove(0, 1);
                x--;
            }
            opc.setText("x1");
        }
        else {
            if (getIntent().getBooleanExtra("editar", false)) {
                for (i = 0; i < matriz.size(0); i++)
                    matriz.set(l, i, 0d);
                matriz.set(l, x+l, 1d);
            }
            else {
                matriz.addLinha(matriz.size(0), 0d);
                for (i = 0; i < matriz.size() - 1; i++)
                    matriz.add(i, 0d);
                matriz.add(matriz.size() - 1, 1d);
            }
            opc.setText("Xn");
        }
    }

    public void fAddX(View view){
        if (l == 0 && !opc.getText().equals("OK")) {
            x++;
            matriz.add(0, x, getNum());
            if (txtE.getText().equals("") && txtS.getText().equals("+"))
                txtE.setText(txtN.getText().toString() + "x" + x);
            else
                txtE.setText(txtE.getText().toString() + txtS.getText().toString() + txtN.getText().toString() + "x" + x);
            txtN.setText("");
            opc.setText("x" + (x + 1));
        }
        else if (opc.getText().equals("OK")) {
            if (txtN.getText().equals(""))
                txtN.setText("0");
            aux = getNum();
            if (txtS.getText().equals("+"))
                txtE.setText(txtE.getText().toString() + txtN.getText().toString());
            else
                txtE.setText(txtE.getText().toString() + " -" + txtN.getText().toString());
            txtN.setText("");
            opc.setText("");
        }
        else if (opc.getText().equals("Xn")) {
            // CRIA O ALERTDIALOG PARA ADICIONAR O X
            final List<String> mList = new ArrayList<String>();
            for (i = 0; i < x; i++)
                mList.add("x" + (i + 1));

            GridView gridView = new GridView(this);
            gridView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, mList));
            if (x<5) gridView.setNumColumns(x);
            else gridView.setNumColumns(5);
            gridView.setGravity(Gravity.CENTER);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(gridView);

            final AlertDialog show = builder.show();
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    if (matriz.get(l, pos + 1) == 0d) {
                        if (txtE.getText().equals("") && txtS.getText().equals("+"))
                            txtE.setText(txtN.getText().toString() + mList.get(pos));
                        else
                            txtE.setText(txtE.getText().toString() + txtS.getText().toString() + txtN.getText().toString() + mList.get(pos));
                        matriz.set(l, pos + 1, getNum());
                        txtN.setText("");
                        show.dismiss();
                    } else
                        alerts.Toast(getString(R.string.index_used));
                }
            });
        }
        else alerts.Toast(getString(R.string.error_unknown));
    }

    public double getNum(){
        double num;
        if (txtN.getText().equals(""))
            num = 1;
        else
            num = Double.parseDouble(txtN.getText().toString());
        if (txtS.getText().equals("-"))
            num = num*(-1);
        return num;
    }

}
