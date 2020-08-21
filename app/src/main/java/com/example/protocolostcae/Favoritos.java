package com.example.protocolostcae;


import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Favoritos extends AppCompatActivity {
    private static Favoritos myinstance = null;
    private static ArrayList<Integer> favs = new ArrayList<>();


    public static synchronized Favoritos getInstance() {
        if (null == myinstance) {
            myinstance = new Favoritos();
        }
        return myinstance;
    }

    public static ArrayList<Integer> getFavs() {
        return favs;
    }

    public static void setFavs(ArrayList<Integer> favs) {
        Favoritos.favs = favs;
    }

    public static Integer[] toArray() {
        Integer[] array = new Integer[favs.size()];
        for (int i = 0; i < favs.size(); i++) {
            array[i] = favs.get(i);
        }
        return array;
    }

    public static Set<String> exportarConjunto() {
        Set<String> conjunto = new HashSet<>();
        for (Integer i : favs) {
            conjunto.add(String.valueOf(i));
        }
        return conjunto;
    }

    public static void importarConjunto(Set<String> datos) {
        favs = new ArrayList<>();

        for (String d : datos) {
            favs.add(Integer.valueOf(d));
        }
    }

    public boolean esFavorito(Integer id) {
        return favs.contains(id);
    }

    public void addFav(Integer id) {
        favs.add(id);
    }

    public void deleteFav(Integer id) {
        favs.remove(id);
    }

    public void clearAll() {
        favs.clear();
    }

    public String toString() {
        String datos = "";
        for (Integer i : favs) {
            datos += i + "\n";
        }
        return datos;
    }


}
