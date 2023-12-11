package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
import static spark.Spark.*;

        public class Main {
            static int cont = 0;
            public static void main(String[] args) {
                port(8080);
                JFrame frame = new JFrame("Servidor");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600,  400);
                frame.setLocationRelativeTo(null);

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(3, 3));

                JTextField inputField1 = new JTextField();
                inputField1.setFont((new Font("Arial", Font.PLAIN, 30)));

                JTextField inputField2 = new JTextField();
                inputField2.setFont((new Font("Arial", Font.PLAIN, 30)));

                JTextField inputField3 = new JTextField();
                inputField3.setFont((new Font("Arial", Font.PLAIN, 30)));

                JLabel label1 = new JLabel("altura:");
                label1.setFont((new Font("Arial", Font.BOLD, 30)));

                JLabel label2 = new JLabel("massa:");
                label2.setFont((new Font("Arial", Font.BOLD, 30)));

                JLabel label3 = new JLabel("IMC:");
                label3.setFont((new Font("Arial", Font.BOLD, 30)));

                JButton botao = new JButton("enviar");

                get("/nome/:p", (request, response) -> {
                    cont++;
                    String nome = request.params("p");
                    inputField1.setText(nome);
                    inputField2.setText(String.valueOf(cont));
                    return "acesso: " + cont;
                });

        post("/api" , (request, response) -> {
            String corpoRequisicao = request.body();
            System.out.println("Corpo json: " +  corpoRequisicao );
            JsonElement jsonElement = JsonParser.parseString(corpoRequisicao);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            inputField1.setText(jsonObject.get("altura").getAsString());
            inputField2.setText(jsonObject.get("massa").getAsString());
            double altura = Double.parseDouble(inputField1.getText());
            double massa = Double.parseDouble(inputField2.getText());
            double imc = massa/Math.pow(altura, 2) ;
            String imcStr = String.valueOf(imc);
            System.out.println("{\"imc\":\""+imcStr+"\"}");
            return  "{\"imc\":\""+imcStr+"\"}";
        });



        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        panel.add(label1);
        panel.add(inputField1);
        panel.add(label2);
        panel.add(inputField2);
        panel.add(label3);
        panel.add(inputField3);
        panel.add(botao);
        frame.add(panel);
        frame.setVisible(true);

    }
}