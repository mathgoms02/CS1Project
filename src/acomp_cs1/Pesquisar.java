/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package acomp_cs1;


import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
//import net.proteanit.sql.DbUtils;

/**
 *
 * @author matgo
 */
public class Pesquisar extends javax.swing.JInternalFrame {
    
        PreparedStatement pst = null;
        //ResultSet rs = null;

    String driver = "com.mysql.cj.jdbc.Driver"; //drive de conexao com o SQL
    String url = "jdbc:mysql://localhost:3306/cs1_control"; //porta padrao pro sql e da agenda
    String user = "root";               //atributos
    String password = "microondas123@";

    /**
     * Creates new form Cadastro
     */
    public Pesquisar() {
        initComponents();
        carregaTabela();
        
        setFrameIcon(new ImageIcon(this.getClass().getResource("/imagens/iconP.jpeg")));
        

    }

    private String[] buscarPeca(String peça) {
        String[] resultado = null;
        try {
            Class.forName(driver);
            String query = "SELECT * FROM acompanhamento_cs1 where peça LIKE " + peça; //query ;e a execuçao do banco... o like depoiso % é pra mostrar de forma mais variavel, por exemplo, pesquicar mat e encontrar Matheus
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

    public String getAcompanhamento_CS1() {
        String resultado = "";
        try {
            //Registrar o driver
            Class.forName(driver);
            String query = "SELECT * FROM cs1_control.acompanhamento_cs1;";
            try {
                //Estabelecer conexão com o banco de dados
                Connection con = DriverManager.getConnection(url, user, password);
                // Criando o objeto statement - usado para executar consultas
                Statement st = con.createStatement();
                // O método executeQuery retorna um objeto ResultSet object o qual 
                //representa o resultado da consulta.
                ResultSet rs = st.executeQuery(query);

                int colNum = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= colNum; i++) {
                        resultado += rs.getString(i) + "\n";
                    }
                    ///////////////////////
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

    private int pesquisar(int peça, int qtd, int qtdNOK, String problema, int turno, int turnoInsp) {
        int resultado = 0;
        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            Class.forName(driver);
            String query = "INSERT INTO `cs1_control`.`acompanhamento_cs1` (`data`, `peça`, `qtd`, `qtdNOK`, `problema`, `turno`, `rastreabilidade`) VALUES "
                    + "('" +data.format(LocalDateTime.now())+ "', " + peça + ", " + qtd + ", " + qtdNOK + ", '" + problema + "', " + turno + ", " + turnoInsp + ");";
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

    private void carregaTabela() {
        
        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtPDH.setText(data.format(LocalDateTime.now()));
        

        //selecionando o modelo pra table de pesquisa
        DefaultTableModel modelo = (DefaultTableModel) tbCS1.getModel();
        //sempre que iniciar, vai zerar a tabela e recarregar???
        modelo.setNumRows(0);
        
        tbCS1.getColumnModel().getColumn(0).setPreferredWidth(20);
        

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);

            Statement st = con.createStatement();

            //query
            String query = "SELECT * FROM acompanhamento_cs1"; //erro nessa linha
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                //adicionando linhas, dentro do resultset
                String Id = String.valueOf(rs.getInt("idAcompanhamento_CS1"));
                String Data = rs.getString("data");
                String Hora = rs.getString("hora");
                String RE = String.valueOf(rs.getInt("RE"));
                String Peca = String.valueOf(rs.getInt("peça"));
                String qtd = String.valueOf(rs.getInt("qtd"));
                String Turno = String.valueOf(rs.getInt("turnoInsp"));
                String Problem = rs.getString("problema");
                

                String tbData[] = {Id, Data, Hora, RE, Peca, qtd, Turno, Problem};

                modelo.addRow(tbData);
            }

            st.close();
            rs.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar a pagina" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
        //colocar data atual no campo de pesquisa automaticamente
        pesquisar_data();
    }

    //metodo para pesquisar cliente
    private void pesquisar_peca() {
        DefaultTableModel modelo = (DefaultTableModel) tbCS1.getModel();
        modelo.setNumRows(0);
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM acompanhamento_cs1 WHERE peça LIKE ?";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
            
            //passando o conteudo da caixa de pesquisa pro '?'
            //atencao ao % para a continuacao da string sql
            pst = con.prepareStatement(sql);
            pst.setString(1, txtPecap.getText() +"%");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                //adicionando linhas, dentro do resultset
                String Id = String.valueOf(rs.getInt("idAcompanhamento_CS1"));
                String Data = rs.getString("data");
                String Hora = rs.getString("hora");
                String RE = String.valueOf(rs.getInt("RE"));
                String Peca = String.valueOf(rs.getInt("peça"));
                String qtd = String.valueOf(rs.getInt("qtd"));
                String Turno = String.valueOf(rs.getInt("turnoInsp"));
                String Problem = rs.getString("problema");
                

                String tbData[] = {Id, Data, Hora, RE, Peca, qtd, Turno, Problem};

                modelo.addRow(tbData);
            }
            

            pst.close();
            rs.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar a pagina" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void pesquisar_data() {
        
        DefaultTableModel modelo = (DefaultTableModel) tbCS1.getModel();
        modelo.setNumRows(0);
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        String sqlD = "SELECT * FROM acompanhamento_cs1 WHERE data LIKE ?";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);            
            //passando o conteudo da caixa de pesquisa pro '?'
            //atencao ao % para a continuacao da string sql
            pst = con.prepareStatement(sqlD);
            pst.setString(1, txtPDH.getText() +"%");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                //adicionando linhas, dentro do resultset
                String Id = String.valueOf(rs.getInt("idAcompanhamento_CS1"));
                String Data = rs.getString("data");
                String Hora = rs.getString("hora");
                String RE = String.valueOf(rs.getInt("RE"));
                String Peca = String.valueOf(rs.getInt("peça"));
                String qtd = String.valueOf(rs.getInt("qtd"));
                String Turno = String.valueOf(rs.getInt("turnoInsp"));
                String Problem = rs.getString("problema");
                

                String tbData[] = {Id, Data, Hora, RE, Peca, qtd, Turno, Problem};

                modelo.addRow(tbData);
            }
            

            pst.close();
            rs.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar a pagina" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    //metodo para setar os campos do formulario com conteudo da tabela
    public void setar_campos(){
        int setar = tbCS1.getSelectedRow();
        
        txtID.setText(tbCS1.getModel().getValueAt(setar, 0).toString());
        txtPeca.setText(tbCS1.getModel().getValueAt(setar, 4).toString());
        txtDH.setText(tbCS1.getModel().getValueAt(setar, 1).toString());
        txtTurno.setText(tbCS1.getModel().getValueAt(setar, 6).toString());
        txtProblema.setText(tbCS1.getModel().getValueAt(setar, 7).toString());
    }
    
        //metodo pro DELETE
    private void remover(){
        //a estrutura abaixo confirma a remoçào
        int confirma = JOptionPane.showConfirmDialog(null, "Certeza que deseja remover essa peça?", "Atenção", JOptionPane.YES_NO_OPTION);
        if(confirma == JOptionPane.YES_OPTION){
            String sql = "DELETE from acompanhamento_cs1 WHERE idAcompanhamento_CS1=?";
            try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);

            pst = con.prepareStatement(sql);
            pst.setString(1, txtID.getText());
            pst.executeUpdate();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar a pagina" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    
    //tentando criar o botao delete, mas primeiro cria um sistema para ver os dados igual o modelo do video: https://www.youtube.com/watch?v=faPR87RX2c0&list=PLjDqpIL09FbeUp5LVgSRPSbVg3uMOHpwA&index=10&t=4s&ab_channel=M%C3%A1rcioTostes
//    public void delete(int peca){
//        Connection con = DriverManager.getConnection();
//        PreparedStatement smt = null;
//        try{
//            smt = con.prepareStatement("DELETE FROM cs1_control WHERE peça = ?");
//            
//        }
//    }
    
    
    /**
     * Creates new form Pesquisar
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPecap = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCS1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtDH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTurno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtProblema = new javax.swing.JTextField();
        txtID = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPeca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPDH = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel1.setText("Pesquisa p/ Peça:");

        txtPecap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPecapActionPerformed(evt);
            }
        });
        txtPecap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPecapKeyReleased(evt);
            }
        });

        tbCS1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Data", "Hora", "RE", "Peça", "qtd", "Turno", "Problema"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCS1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCS1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbCS1);
        if (tbCS1.getColumnModel().getColumnCount() > 0) {
            tbCS1.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton2.setText("Deletar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Data:");

        txtDH.setEditable(false);

        jLabel3.setText("Turno:");

        txtTurno.setEditable(false);

        jLabel4.setText("Problema:");

        txtProblema.setEditable(false);
        txtProblema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProblemaActionPerformed(evt);
            }
        });

        txtID.setText(".");

        jLabel5.setText("Peça:");

        txtPeca.setEditable(false);

        jLabel6.setText("Pesquisar p/ Data:");

        txtPDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPDHActionPerformed(evt);
            }
        });
        txtPDH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPDHKeyReleased(evt);
            }
        });

        jButton3.setText("X");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPDH, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPecap)))
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtPeca, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDH, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProblema, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(23, 23, 23)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtPecap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtPDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtID)
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtPeca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtProblema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addComponent(jButton2)))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//o evento abaixo é do tipo "enquanto for digitando"
    private void txtPecapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPecapKeyReleased
        //chamar metodo pesquisar cliente
        pesquisar_peca();
        
    }//GEN-LAST:event_txtPecapKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
//        String passwordADM = "52952math";
//        String passwordUser = "cs1martinrea2022";
//        
//        Box box = Box.createHorizontalBox();
//        
//        JLabel set = new JLabel("Senha: ");
//        box.add(set);
//        
//        JPasswordField pass = new JPasswordField(15);
//        box.add(pass);
//        
//        int button = JOptionPane.showConfirmDialog(null, box, "Entre com a senha", JOptionPane.OK_CANCEL_OPTION);
//        
//        if (button == JOptionPane.OK_OPTION){
//            char[] input = pass.getPassword();
//            if(input.equals(passwordADM)){
//                remover(); //removendo
//                carregaTabela();//atualizando
//            }else{
//                JOptionPane.showMessageDialog(null, "Senha incorreta!!");
//            }
//            
//        }
        
        String senha = JOptionPane.showInputDialog(null, "Senha:", "CS1", JOptionPane.WARNING_MESSAGE);
        if ("cs1martinrea2022".equals(senha)){
            remover(); //removendo
            carregaTabela();//atualizando
            }else{
            JOptionPane.showMessageDialog(null, "Senha incorreta!!");
        }
        
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    //evento usado para setar os campos da tabela clicando com o mouse
    private void tbCS1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCS1MouseClicked
        setar_campos();
    }//GEN-LAST:event_tbCS1MouseClicked

    private void txtPecapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPecapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPecapActionPerformed

    private void txtPDHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPDHKeyReleased
        pesquisar_data();
    }//GEN-LAST:event_txtPDHKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        txtPDH.setText("");
        pesquisar_data();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtProblemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProblemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProblemaActionPerformed

    private void txtPDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPDHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPDHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbCS1;
    private javax.swing.JTextField txtDH;
    private javax.swing.JLabel txtID;
    private javax.swing.JTextField txtPDH;
    private javax.swing.JTextField txtPeca;
    private javax.swing.JTextField txtPecap;
    private javax.swing.JTextField txtProblema;
    private javax.swing.JTextField txtTurno;
    // End of variables declaration//GEN-END:variables
}
