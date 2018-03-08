package com.ntecprograms.simplexmobile;

import java.util.List;

/**
 * Created by Danilo on 30/05/2016.
 */
public class Algoritmo {
    public List lista;
    public String varBasicas, varNAOBasicas, valorZ;
    public int nAlg, col, lin;

    public Algoritmo(List lista, int nAlg, String varBasicas, String varNAOBasicas, String valorZ, int col, int lin){
        this.lista = lista;
        this.nAlg = nAlg;
        this.col = col;
        this.lin = lin;
        this.varBasicas = varBasicas;
        this.varNAOBasicas = varNAOBasicas;
        this.valorZ = valorZ;
    }

}
