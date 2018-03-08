package com.ntecprograms.simplexmobile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danilo on 18/05/2016.
 */
public class Matriz<T> implements Serializable {

    public final List<List<T>> elementos = new ArrayList<List<T>>();

    public int addLinha(){
        // ADICIONA UMA NOVA LINHA  E RETORNA O INDEX DELA
        List<T> colunas = new ArrayList<T>();
        colunas.clear();
        elementos.add(colunas);
        return elementos.size()-1;
    }

    public int addLinha(int qtd, T elemento){
        // ADICIONA UMA NOVA LINHA COM X QTD DE COLUNAS, RETORNA O INDEX DELA
        List<T> colunas = new ArrayList<T>();
        for (int i=0; i<qtd; i++)
            colunas.add(elemento);
        elementos.add(colunas);
        return elementos.size()-1;
    }

    public int add(int linha, T elemento){
        // ADICIONA UMA NOVA COLUNA COM O ELEMENTO NO FINAL DA LINHA E RETORNA O INDEX DA COLUNA
        List<T> colunas = elementos.get(linha);
        if (elemento != null)
            colunas.add(elemento);
        return colunas.size()-1;
    }

    public void add(int linha, int coluna, T elemento){
        // ADICIONA O ELEMENTO NA LINHA E COLUNA ESPECIFICADA
        List<T> colunas = elementos.get(linha);
        if (elemento != null)
            colunas.add(coluna, elemento);
    }

    public void remove(int linha){
        // REMOVE A LINHA INTEIRA
        elementos.remove(linha);
    }

    public void remove(int linha, int coluna){
        // REMOVE O ITEM DA LINHA E COLUNA ESPECIFICADA
        List<T> colunas = elementos.get(linha);
        colunas.remove(coluna);
    }

    public T get(int linha, int coluna) {
        // RETORNA O VALOR QUE ESTA PRESENTE NA LINHA E COLUNA INSERIDO
        List<T> colunas = elementos.get(linha);
        T elemento = colunas.get(coluna);
        return elemento;
    }

    public int set(int linha, int coluna, T elemento){
        // MUDA O VALOR NA LINHA E COLUNA ESPECIFICADA
        List<T> colunas = elementos.get(linha);
        colunas.set(coluna, elemento);
        return elementos.size()-1;
    }

    public int size(){
        // RETORNA A QUANTIDADE DE LINHAS
        return elementos.size();
    }

    public int size(int linha){
        // RETORNA A QUANTIDADE DE COLUNAS NA LINHA
        List<T> colunas = elementos.get(linha);
        return colunas.size();
    }

    public void clear(){
        elementos.clear();
    }

}
