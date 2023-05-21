/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package acomp_cs1;

import java.awt.Image;
import java.awt.Graphics;
//import java.awt.Toolkit;
//import java.net.URL;
import javax.swing.ImageIcon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;

/**
 *
 * @author matgo
 */
public class Cod extends javax.swing.JInternalFrame {
    String driver = "com.mysql.cj.jdbc.Driver"; //drive de conexao com o SQL
    String url = "jdbc:mysql://localhost:3306/cs1_control"; //porta padrao pro sql e da agenda
    String user = "root";
    String password = "microondas123@";
    
    /**
     * Creates new form Cadastro
     */
    public Cod() {
        initComponents();
        //CadasterPlus.setVisible(false);
        
    }
    
        private String[] buscarPeca(String peça) {
        String[] resultado = null;
        try {
                Class.forName(driver);
                String query = "SELECT * FROM acompanhamento_cs1 where peça LIKE '" + peça + "%'"; //query ;e a execuçao do banco... o like depoiso % é pra mostrar de forma mais variavel, por exemplo, pesquicar mat e encontrar Matheus
                try {
                        Connection con = DriverManager.getConnection(url, user, password);
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        int colNum = rs.getMetaData().getColumnCount();
                        resultado = new String[colNum];
                        if (rs.next()) {
                                for (int i = 0; i < colNum; i++) {
                                        resultado[i] = rs.getString(i + 1);
                                }
                        }
                        st.close();
                        con.close();

                } catch (SQLException e) {
                        System.out.println(e);
                }
        } catch (ClassNotFoundException e) {
                System.out.println(e);
        }
        return resultado;
}
        
        public String getAcompanhamento_CS1(){
            String resultado = "";
            try {
            //Registrar o driver
            Class.forName(driver);
            String query = "SELECT * FROM acompanhamento_CS1";
                try {
                    //Estabelecer conexão
                    Connection con = DriverManager.getConnection(url, user, password);
                    // Criando o objeto statement - usado para executar consultas
                    Statement st = con.createStatement();
                    // O método executeQuery retorna um objeto ResultSet object o qual 
                    //representa o resultado da consulta.
                    ResultSet rs = st.executeQuery(query);

                    int colNum = rs.getMetaData().getColumnCount();
                    while (rs.next()) {
                            for (int i=1; i<=colNum; i++)
                            {
                                    resultado += rs.getString(i)+"\n";
                            }
                            resultado += "---------------\n";
                    }
                    rs.close();
                    st.close();
                    con.close();

                } catch (SQLException e) {
                        System.out.println(e);
                } 
            } catch (ClassNotFoundException e) {
                    System.out.println(e);
            }


            return resultado;
        }
        private int cadastrar(int peça, int qtd, int qtdNOK, String problema, int re, int turno, int rastreabilidade) {
            int resultado=0;
            DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm");
            try {
                Class.forName(driver);
                String query = "INSERT INTO `cs1_control`.`acompanhamento_cs1` (`hora`, `data`, `peça`, `qtd`, `qtdNOK`, `problema`, `RE`, `turno`, `rastreabilidade`) VALUES "
                                + "('"+hora.format(LocalDateTime.now())+"', '"+data.format(LocalDateTime.now())+"', "+peça+", "+qtd+", "+qtdNOK+", '"+problema+"', "+re+", "+turno+", "+rastreabilidade+");";
                try {
                    Connection con = DriverManager.getConnection(url, user, password);
                    Statement st = con.createStatement();
                    resultado = st.executeUpdate(query);
                    st.close();
                    con.close();

                } catch (SQLException e) {
                    System.out.println(e);
                }

            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }
            return resultado;
            }
        

        private int cadastro_plus(int qtdNOK, String problema){
            int resultado = 0;
            DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm");
            try {
            Class.forName(driver);
            String query = "INSERT INTO `cs1_control`.`acompanhamento_cs1` (`data`, `qtdNOK`, `problema`) VALUES "
                            + "('"+hora.format(LocalDateTime.now())+"', '"+data.format(LocalDateTime.now())+"', "+qtdNOK+", '"+problema+"');";
            try {
                Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                resultado = st.executeUpdate(query);
                
                st.close();
                con.close();

            } catch (SQLException e) {
                System.out.println(e);
            }

        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        return resultado;
            
        }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoTurno = new javax.swing.ButtonGroup();
        grupoProblema = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/CODrev5.png"));
        Image image = icon.getImage();
        jDesktopPane1 =  new javax.swing.JDesktopPane(){

            public void paintComponent(Graphics g){
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }

        };

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(720, 440));
        setPreferredSize(new java.awt.Dimension(663, 700));

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 732, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoProblema;
    private javax.swing.ButtonGroup grupoTurno;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
