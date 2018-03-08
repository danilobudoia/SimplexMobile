package com.ntecprograms.simplexmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalcularActivity extends AppCompatActivity {

    Matriz<Double> matriz;
    AlgoritmoAdapter adapter;
    List<String> tmp;
    List<Algoritmo> lista;
    int x, r, c, i, j, k = 0, alg=0, colunaPIVO, linhaPIVO;
    double aux;

    ListView listView;
    TextView txtMelhor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular);

        x = getIntent().getIntExtra("x", 0);
        r = getIntent().getIntExtra("r", 0);
        matriz = (Matriz<Double>) getIntent().getSerializableExtra("matriz");

        c = x+r+2;
        lista = new ArrayList<Algoritmo>();
        calculo();

        adapter = new AlgoritmoAdapter(this, lista);
        listView = (ListView)findViewById(R.id.listView2);
        listView.setAdapter(adapter);
    }

    public void calculo(){
        while (k != matriz.size(0)) {
            // MONTA O ALGORITMO
            DecimalFormat format = new DecimalFormat("0.##");
            tmp = new ArrayList<String>();
            tmp.add("Z");
            for (i=0; i<x; i++)
                tmp.add("x"+(i+1));
            for (i=0; i<r; i++)
                tmp.add("xf"+(i+1));
            tmp.add("b");
            alg++;
            for (i = 0; i<matriz.size(); i++) {
                if (i == 0) tmp.add("1");
                else tmp.add("0");
                for (j = 1; j < matriz.size(i); j++)
                    tmp.add(format.format(matriz.get(i, j)));
                tmp.add(format.format(matriz.get(i, 0)));
            }

            // MONTA A SOLUÇÃO
            String varB = "", varN = "";
            for (i=1; i<matriz.size(0); i++){
                int um=0, zero=0;
                String b = "";
                for (j=0; j<matriz.size(); j++){
                    if (matriz.get(j, i) == 0)
                        zero++;
                    if (matriz.get(j, i) == 1) {
                        um++;
                        b = format.format(matriz.get(j, 0));
                    }
                }
                if (zero != matriz.size()) {
                    if (zero == matriz.size() - 1 && um == 1) {
                        if (i <= x)
                            varB = varB + "x" + i + " = " + b + "; ";
                        else
                            varB = varB + "xf" + (i - x) + " = " + b + "; ";
                    } else {
                        if (i <= x)
                            varN = varN + "x" + i + " = 0; ";
                        else
                            varN = varN + "xf" + (i - x) + " = 0; ";
                    }
                }
            }

            // EXIBE OS ALGORITMOS E AS SOLUÇÕES
            lista.add(new Algoritmo(tmp, alg, varB, varN, "R$ "+format.format(matriz.get(0,0)), c, r+1));

            // VERIFICA SE TEM NUMEROS NEGATIVOS PARA GERAR O PROXIMO ALGORITMO
            for (i = 0; i < matriz.size(0); i++)
                if (matriz.get(0, i)>=0)
                    k++;
            // ALGORTIMO
            if (k != matriz.size(0)) {
                k = 0;
                aux = matriz.get(0, 1);
                colunaPIVO = 1;
                for (i = 1; i < matriz.size(0); i++)
                    if (matriz.get(0, i) < aux) {
                        aux = matriz.get(0, i);
                        colunaPIVO = i;
                    }
                aux = Double.MAX_VALUE;
                linhaPIVO = 1;
                for (i = 1; i < matriz.size(); i++)
                    if ((matriz.get(i, 0) / matriz.get(i, colunaPIVO)) < aux && (matriz.get(i, 0) / matriz.get(i, colunaPIVO)) >= 0) {
                        aux = (matriz.get(i, 0) / matriz.get(i, colunaPIVO));
                        linhaPIVO = i;
                    }
                aux = matriz.get(linhaPIVO, colunaPIVO);
                for (i = 0; i < matriz.size(linhaPIVO); i++)
                    matriz.set(linhaPIVO, i, (matriz.get(linhaPIVO, i) / aux));
                for (i = 0; i < matriz.size(); i++) {
                    if (i != linhaPIVO) {
                        aux = matriz.get(i, colunaPIVO)*(-1);
                        for (j = 0; j < matriz.size(i); j++) {
                            double aux2 = (matriz.get(linhaPIVO, j) * aux) + matriz.get(i, j);
                            matriz.set(i, j, aux2);
                        }
                    }
                }
                // FIM
            }
            else{
                txtMelhor = (TextView) findViewById(R.id.textView21);
                txtMelhor.setText("R$ "+format.format(matriz.get(0,0)));
            }
        }
    }

}
