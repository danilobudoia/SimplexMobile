package com.ntecprograms.simplexmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Danilo on 30/05/2016.
 */
public class AlgoritmoAdapter extends BaseAdapter {

    Context ctx;
    List<Algoritmo> alg;
    public AlgoritmoAdapter(Context c, List<Algoritmo> l){
        ctx = c;
        alg = l;
    }

    @Override
    public int getCount(){
        return alg.size();
    }

    @Override
    public Object getItem(int position){
        return alg.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Algoritmo algoritmo = alg.get(position);
        View linha = LayoutInflater.from(ctx).inflate(R.layout.lista_algoritmos, null);

        TextView nome = (TextView)linha.findViewById(R.id.nomeAlg);
        nome.setText(ctx.getString(R.string.algorithm)+algoritmo.nAlg);

        TextView nome2 = (TextView)linha.findViewById(R.id.txtSolucao);
        nome2.setText(ctx.getString(R.string.solution)+algoritmo.nAlg);

        TextView varB = (TextView)linha.findViewById(R.id.txtVbasicas);
        varB.setText(algoritmo.varBasicas);

        TextView varN = (TextView)linha.findViewById(R.id.txtVNAObasicas);
        varN.setText(algoritmo.varNAOBasicas);

        TextView valorZ = (TextView)linha.findViewById(R.id.txtValorZ);
        valorZ.setText(algoritmo.valorZ);

        GridView gride = (GridView)linha.findViewById(R.id.gridView);
        gride.setLayoutParams(new LinearLayout.LayoutParams(150*algoritmo.col, 125*algoritmo.lin));
        gride.setNumColumns(algoritmo.col);
        gride.setAdapter(new ArrayAdapter(ctx, android.R.layout.simple_list_item_1, algoritmo.lista));

        return linha;
    }

}
