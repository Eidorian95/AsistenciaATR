package com.edu.ort.gruposiete.asistenciaatr;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParser {

    public static ArrayList<Usuario> Usuarios(Context context){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document document = builder.parse(context.getResources().openRawResource(R.raw.usuarios));
            document.getDocumentElement().normalize();

            NodeList usuarios = document.getElementsByTagName("usuario");
            ArrayList<Usuario> lista = new ArrayList<>();
             String id ="";
             String user="";
             String pass="";
             String nombre="";
             String apellido="";
             String tipo="";

            for (int i = 0; i < usuarios.getLength(); i++) {
                Node nodo = usuarios.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    id = element.getElementsByTagName("id").item(0).getTextContent();
                    user = element.getElementsByTagName("user").item(0).getTextContent();
                    pass = element.getElementsByTagName("pass").item(0).getTextContent();
                    nombre=element.getElementsByTagName("nombre").item(0).getTextContent();
                    apellido = element.getElementsByTagName("apellido").item(0).getTextContent();
                    tipo = element.getElementsByTagName("tipo").item(0).getTextContent();

                    lista.add(new Usuario(id,user,pass,nombre,apellido,tipo));
                }
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    }

