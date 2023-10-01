package Aplicacio;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import Estructura.Actor.*;
import Estructura.Message.Message;
import Estructura.Proxies.ActorProxy;
import Exceptions.CreationException;

public class Grafica {
    public static void main(String[] args) throws CreationException {
        JFrame ventana = new JFrame("Interfaz de ejemplo");
        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        JButton boton1 = new JButton("ActorContext");
        JButton boton2 = new JButton("Monitorizar cola mensajes");
        JButton boton3 = new JButton("Mensajes recibidos");
        JButton anadir = new JButton("Añadir");
        JPanel contenedor1 = new JPanel();
        JPanel contenedor2 = new JPanel();
        JPanel contenedor3 = new JPanel();
        JPanel botones = new JPanel();

        botones.add(boton1);
        botones.add(boton2);
        botones.add(boton3);

        ActorProxy av=ActorContext.getActorContext().spawnActor("Hei", new ActorHelloWorld());
        ActorProxy bb=ActorContext.getActorContext().spawnActor("Hola", new ActorHelloWorld());
        av.getActorReal().send(bb, new Message(av, "Holaaa"));


        botones.setVisible(true);

        contenedor2.setVisible(false);
        contenedor3.setVisible(false);

        contenedorPrincipal.add(botones, BorderLayout.NORTH);
        contenedorPrincipal.add(contenedor1, BorderLayout.CENTER);
        contenedorPrincipal.add(contenedor2, BorderLayout.CENTER);
        contenedorPrincipal.add(contenedor3, BorderLayout.CENTER);

        JTextField tf=new JTextField();
        tf.setColumns(20);

        //para la llista en defecto

        JList<String> actorList = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();
        JScrollPane scrollPane = new JScrollPane(actorList);
        scrollPane.setPreferredSize(new Dimension(300, 600));
       


        actorList.setModel(model);
        contenedor1.add(scrollPane);
        contenedor1.add(tf);
        contenedor1.add(anadir);

        contenedor1.setBounds(100, 50, 700, 700);
        contenedor2.setBounds(100, 50, 700, 700);
        contenedor3.setBounds(100, 50, 700, 700);

        contenedor1.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        contenedor2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        contenedor3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        ventana.add(contenedorPrincipal);
        ventana.setVisible(true);
        
        //Acciones de los botones

        boton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ActorContext.getActorContext().getMap().values().forEach((Actor a)->{
                    model.addElement(a.getName());
                });
                contenedor1.setVisible(true);
                contenedor2.setVisible(false);
                contenedor3.setVisible(false);
                contenedor2.removeAll();
                contenedor3.removeAll();

                

            }
        });

        boton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ActorContext.getActorContext().getMap().values().forEach((Actor a)->{
                    JProgressBar prog = new JProgressBar();
                    prog.setValue(a.getMesList().size());
                    prog.setStringPainted(true);
                    prog.setString(a.getName()+": "+a.getMesList().size());
                    contenedor2.add(prog);
                    contenedor3.removeAll();

                });

                
                contenedor1.setVisible(false);
                contenedor2.setVisible(true);
                contenedor3.setVisible(false);

                model.clear();
            }
        });
        boton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ActorContext.getActorContext().getMap().values().forEach((Actor a)->{
                    JProgressBar prog = new JProgressBar();
                    prog.setValue(a.getObservers().get(3).getNum());
                    prog.setStringPainted(true);
                    prog.setString(a.getName()+": "+a.getObservers().get(3).getNum());
                    contenedor3.add(prog);
                });
               
               
                contenedor1.setVisible(false);
                contenedor2.setVisible(false);
                contenedor3.setVisible(true);
                model.clear();
                contenedor2.removeAll();
            }
        });

        anadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a=JOptionPane.showConfirmDialog(ventana, "Yes(ActorProxy). No(ClientProxy)");
                if(a==JOptionPane.YES_OPTION){
                    try {
                        ActorContext.getActorContext().spawnActor(tf.getText(), new ActorHelloWorld());
                        model.addElement(tf.getText());
                    } catch (CreationException e1) {
                        // TODO Auto-generated catch block
                        JOptionPane.showMessageDialog(ventana, "No se puede crear.", "Error", JOptionPane.ERROR_MESSAGE);  
                    }
                }
                else{
                    try {
                        ActorContext.getActorContext().c_spawnActor(tf.getText(), new ActorHelloWorld());
                        model.addElement(tf.getText());
                    } catch (CreationException e1) {
                        // TODO Auto-generated catch block
                        JOptionPane.showMessageDialog(ventana, "No se puede crear.", "Error", JOptionPane.ERROR_MESSAGE);  

                    }

                }
                actorList.setModel(model);
                tf.setText("");

            }
        });
        ventana.setSize(900, 800);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}