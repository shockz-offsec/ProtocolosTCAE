package com.example.protocolostcae;

import java.util.ArrayList;

public class IndexBusqueda {
    //Creamos variables
    public int id;// ID único para cada nota
    public String index;
    public boolean realizado;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IndexBusqueda() {
        //inicializamos las variables
        index = "";
        realizado = false;
    }

    public IndexBusqueda(String nota, boolean realizado) {
        //Recibimos parametros del constructor y los agregamos al nuestras variables
        this.index = nota;
        this.realizado = realizado;
    }

    public String getNota() {
        return index;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public ArrayList<IndexBusqueda> getlistaIndexBusqueda() {
        IndexBusqueda objetoNota = null;
        ArrayList<IndexBusqueda> lista = new ArrayList<IndexBusqueda>();
        //Arreglo de IndexBusqueda
        String[] arrayIndexBusqueda = new String[]{"Técnicas para alimentar al paciente encamado",
                "Técnica para la alimentación por sonda nasogástrica",
                "Técnica de aseo del paciente encamado",
                "Técnica para hacer la cama con enfermo encamado",
                "Técnica de cuidados post-mortem",
                "Medición de Diuresis",
                "Técnica para administrar un enema",
                "Técnica para la toma de muestras de esputos",
                "Técnica de medición de la Frecuencia Respiratoria",
                "Técnica para la higiene de la boca del paciente encamado",
                "Técnica para la higiene de los ojos del paciente encamado",
                "Técnica para la higiene del cabello del paciente encamado",
                "Recepción de un nuevo paciente en la Unidad",
                "Técnica para el masaje cardiaco externo",
                "Técnica de administración de Medicación Oral",
                "Técnica de obtención de muestras de orina",
                "Administración de oxígeno mediante Mascarilla o Gafas",
                "Actuaciones básicas en Primeros Auxilios",
                "Técnica de medición del Pulso Arterial",
                "Técnica de reanimación cardio-pulmonar",
                "Métodos y Técnicas para la respiración artificial",
                "Técnica del Sondaje Vesical",
                "Control de la Temperatura del paciente",
                "Técnica de toma de la Tensión Arterial",
                "Prevención de las Úlceras por Presión",
                "Técnica de cura de una Úlcera por Presión"};

        //Arreglo de si se realizo o no la copia
        boolean[] arrayRealizado = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        try {
            //El arrayIndexBusqueda y el arrayRealizado tienen que tener la misma cantidad de elementos
            for (int i = 0; i < arrayIndexBusqueda.length; i++) {
                String nota = arrayIndexBusqueda[i];
                boolean realizado = arrayRealizado[i];
                //Agregamos los elementos al objeto
                objetoNota = new IndexBusqueda(nota, realizado);
                objetoNota.setId(i);//Asignamos el Id de acuerda a su posición en la lista (no la posición en el recycler)
                //agregamos el objeto a nuesta lista asi hasta que se llene con todos los elementos
                lista.add(objetoNota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Retornamos la lista
        return lista;
    }
}

