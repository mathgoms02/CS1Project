/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package acomp_cs1;

import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author matgo
 */
public class FichaINSP extends javax.swing.JInternalFrame {
    String driver = "com.mysql.cj.jdbc.Driver"; //drive de conexao com o SQL
    String url = "jdbc:mysql://localhost:3306/cs1_control"; //porta padrao pro sql e do cs1controler
    String user = "root";
    String password = "microondas123@";
    PreparedStatement pst = null;
    /**
     * Creates new form FichaINSP
     */
    public FichaINSP() {
        initComponents();
        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        carregaTabela();
        
        //txtData.setText(data.format(LocalDateTime.now()));
        
        setFrameIcon(new ImageIcon(this.getClass().getResource("/imagens/ficha.png")));
    }
    

    
        public void setar_campos(){
            
        int set = tbFICHA.getSelectedRow();
        
        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtDATA.setText(data.format(LocalDateTime.now()));
        
        txtID.setText(tbFICHA.getModel().getValueAt(set, 0).toString());
        txtPECA.setText(tbFICHA.getModel().getValueAt(set, 2).toString());
        txtQTD.setText(tbFICHA.getModel().getValueAt(set, 4).toString());
        txtPROBLEMA.setText(tbFICHA.getModel().getValueAt(set, 5).toString());
        txtTURNO.setText(tbFICHA.getModel().getValueAt(set, 6).toString());
        //txtDATA.setText(tbFICHA.getModel().getValueAt(set, 5).toString());
       // txtAREA.setText(tbFICHA.getModel().getValueAt(set, 8).toString());
        txtID.setText(tbFICHA.getModel().getValueAt(set, 10).toString());

    }
        
    private void carregaTabela() {
        //selecionando o modelo pra table de pesquisa
        DefaultTableModel modelo = (DefaultTableModel) tbFICHA.getModel();
        //sempre que iniciar, vai zerar a tabela e recarregar???
        modelo.setNumRows(0);
        
        //tbFICHA.getColumnModel().getColumn(0).setPreferredWidth(20);
        

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);

            Statement st = con.createStatement();

            //query
            String query = "SELECT * FROM acompanhamento_cs1"; //erro nessa linha
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                //adicionando linhas, dentro do resultset
                String id = String.valueOf(rs.getInt("idAcompanhamento_CS1"));
                String Hora = rs.getString("hora");
                String Peca = String.valueOf(rs.getInt("peça"));
                String qtd = String.valueOf(rs.getInt("qtd"));
                String qNOK = String.valueOf(rs.getInt("qtdNOK"));
                String Problem = rs.getString("problema");
                String Turno = String.valueOf(rs.getInt("turnoInsp"));
                String TurnoF = String.valueOf(rs.getInt("turnoFabr"));
                String Data = rs.getString("data");
                //String Area = rs.getString("pRaiz");
                String Acao = rs.getString("acao");
                
                for(int k=0; k<1; k++){
                    if("0".equals(qNOK)){
                        break;
                    }else{
                    String tbData[] = {id, Hora, Peca, qtd, qNOK, Problem, Turno, TurnoF, Data, Acao};
                    modelo.addRow(tbData);
                    }
                }
            }
            

            st.close();
            rs.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar a pagina" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        
   
       private void alterar(){
           
           
           String query = "UPDATE acompanhamento_cs1 SET peça=?, qtdNOK=?, problema=?, turnoInsp=?, acao=? WHERE idAcompanhamento_CS1=?";
           try{
                Connection con = DriverManager.getConnection(url, user, password);
                pst = con.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(txtPECA.getText()));
                pst.setInt(2, Integer.parseInt(txtQTD.getText()));
                pst.setInt(3, Integer.parseInt(txtPROBLEMA.getText()));
                pst.setInt(4, Integer.parseInt(txtTURNO.getText()));
                //pst.setString(5, txtDATA.getText());
                //pst.setString(5, txtAREA.getText());
                pst.setString(5, txtACAO.getText());
                pst.setInt(6, Integer.parseInt(txtID.getText()));
                
                
                int adicionado = pst.executeUpdate();
                
                if (adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Adicionado a Ficha!!");
                }
                //ver se os campos estão todos preenchidos
                
                
                
           }catch (Exception e){
               JOptionPane.showMessageDialog(null, "ERROR:"+e);
           }
       }
    
    
    
    
    
    
    
    private int cadastrarF(int peça, int qtd, int problema, int turno, String date, String acao, String obs, String re) {
        int resultado=0;
        try {
            Class.forName(driver);
            String query = "INSERT INTO `cs1_control`.`fichaacomp` (`peca`, `qtd`, `problema`, `turno`, `data`, `acao`, `obs`, `re`) VALUES "
                            + "("+peça+", "+qtd+", "+problema+", "+turno+", '"+date+"', '"+acao+"', '"+obs+"', '"+re+"');";
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
        
        private void pesquisar_data() {

            DefaultTableModel modelo = (DefaultTableModel) tbFICHA.getModel();
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
                pst.setString(1, txtDAT.getText() +"%");
                rs = pst.executeQuery();

                while (rs.next()) {
                    //adicionando linhas, dentro do resultset
                    String id = String.valueOf(rs.getInt("idAcompanhamento_CS1"));
                    String Hora = rs.getString("hora");
                    String Peca = String.valueOf(rs.getInt("peça"));
                    String qtd = String.valueOf(rs.getInt("qtd"));
                    String qNOK = String.valueOf(rs.getInt("qtdNOK"));
                    String Problem = rs.getString("problema");
                    String Turno = String.valueOf(rs.getInt("turnoInsp"));
                    String TurnoF = String.valueOf(rs.getInt("turnoFabr"));
                    String Data = rs.getString("data");
                    // String Area = rs.getString("pRaiz");
                    String Acao = rs.getString("acao");

                    String tbCar[] = {id, Hora, Peca, qtd, qNOK, Problem, TurnoF, Turno, Data, Acao};

                    modelo.addRow(tbCar);
                }


                pst.close();
                rs.close();
                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar a pagina" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            }
    }
        
        private void pesquisar_turno() {

            DefaultTableModel modelo = (DefaultTableModel) tbFICHA.getModel();
            modelo.setNumRows(0);

            PreparedStatement pst = null;
            ResultSet rs = null;

            String sqlD = "SELECT * FROM acompanhamento_cs1 WHERE turnoFabr LIKE ?";
            try {
                Class.forName(driver);
                Connection con = DriverManager.getConnection(url, user, password);            
                //passando o conteudo da caixa de pesquisa pro '?'
                //atencao ao % para a continuacao da string sql
                pst = con.prepareStatement(sqlD);
                pst.setString(1, txtTUR.getText() +"%");
                rs = pst.executeQuery();

                while (rs.next()) {
                    //adicionando linhas, dentro do resultset
                    String id = String.valueOf(rs.getInt("idAcompanhamento_CS1"));
                    String Hora = rs.getString("hora");
                    String Peca = String.valueOf(rs.getInt("peça"));
                    String qtd = String.valueOf(rs.getInt("qtd"));
                    String qNOK = String.valueOf(rs.getInt("qtdNOK"));
                    String Problem = rs.getString("problema");
                    String Turno = String.valueOf(rs.getInt("turnoInsp"));
                    String TurnoF = String.valueOf(rs.getInt("turnoFabr"));
                    String Data = rs.getString("data");
                    // String Area = rs.getString("pRaiz");
                    String Acao = rs.getString("acao");

                    String tbCar[] = {id, Hora, Peca, qtd, qNOK, Problem, TurnoF, Turno, Data, Acao};

                    modelo.addRow(tbCar);
                }


                pst.close();
                rs.close();
                con.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar a pagina" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoturno = new javax.swing.ButtonGroup();
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/fundoFicha1.png"));
        Image image = icon.getImage();
        jDesktopPane1 = new javax.swing.JDesktopPane(){

            public void paintComponent(Graphics g){
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }

        };
        jScrollPane2 = new javax.swing.JScrollPane();
        tbFICHA = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtPECA = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtQTD = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txtPROBLEMA = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txtTURNO = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtDATA = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        txtACAO = new javax.swing.JTextField();
        btFi = new javax.swing.JButton();
        txtID = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtOBS = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDAT = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        txtTUR = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        tbFICHA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "Hora", "Peça", "qtd", "qNOK", "Problema", "TurnoFab", "Turno", "Data", "Ação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbFICHA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFICHAMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbFICHA);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Peça:");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("QtdNOK:");

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Problema:");

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Turno:");

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Data:");

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ação:");

        btFi.setText("Enter");
        btFi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFiActionPerformed(evt);
            }
        });

        txtID.setBackground(new java.awt.Color(255, 255, 255));
        txtID.setForeground(new java.awt.Color(255, 255, 255));
        txtID.setText(".");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Obs:");

        txtOBS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOBSActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Sem tom1.png"))); // NOI18N
        jLabel6.setText("Data:");

        txtDAT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDATKeyReleased(evt);
            }
        });

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Turno:");

        txtTUR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTURKeyReleased(evt);
            }
        });

        jDesktopPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtPECA, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtQTD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jSeparator2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtPROBLEMA, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jSeparator3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtTURNO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jSeparator4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtDATA, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jSeparator5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtACAO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btFi, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtID, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtOBS, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtDAT, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jSeparator6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtTUR, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btFi, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(txtPECA, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(txtQTD, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(txtPROBLEMA, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtACAO)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(txtTURNO, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(txtDATA, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtOBS, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txtID)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDAT, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTUR, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 49, Short.MAX_VALUE))
                    .addComponent(jSeparator6))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addComponent(txtID)
                .addGap(11, 11, 11)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPECA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQTD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPROBLEMA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTURNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDATA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtACAO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtOBS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(txtDAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtTUR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btFi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbFICHAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFICHAMouseClicked
        setar_campos();
    }//GEN-LAST:event_tbFICHAMouseClicked

    private void btFiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiActionPerformed
        
        
        String re = JOptionPane.showInputDialog("Digite RE: ");
        //int aux = Integer.parseInt(txtQTD.getText());
        
        
        alterar();
        
        int peça = Integer.parseInt(txtPECA.getText());
        int qtd = Integer.parseInt(txtQTD.getText());
        int problema = Integer.parseInt(txtPROBLEMA.getText());
        int turno = Integer.parseInt(txtTURNO.getText());
        String data = txtDATA.getText();
        //String area = txtAREA.getText();
        String acao = txtACAO.getText();
        String obs = txtOBS.getText();
        //int re = Integer.parseInt(txtPECA.getText()); usar JOption pane, assim que clicar o botão ENTER para entrar com o RE//////////////////////
        

        cadastrarF(peça, qtd, problema, turno, data, acao, obs, re);
        
        txtPECA.setText("");
        txtQTD.setText("");
        txtTURNO.setText("");
        txtPROBLEMA.setText("");
        txtDATA.setText("");
        //txtAREA.setText("");
        txtACAO.setText("");
        txtOBS.setText("");
        
        carregaTabela();

    }//GEN-LAST:event_btFiActionPerformed

    private void txtOBSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOBSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOBSActionPerformed

    private void txtDATKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDATKeyReleased
        pesquisar_data();
    }//GEN-LAST:event_txtDATKeyReleased

    private void txtTURKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTURKeyReleased
        pesquisar_turno();
    }//GEN-LAST:event_txtTURKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFi;
    private javax.swing.ButtonGroup grupoturno;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTable tbFICHA;
    private javax.swing.JTextField txtACAO;
    private javax.swing.JTextField txtDAT;
    private javax.swing.JTextField txtDATA;
    private javax.swing.JLabel txtID;
    private javax.swing.JTextField txtOBS;
    private javax.swing.JTextField txtPECA;
    private javax.swing.JTextField txtPROBLEMA;
    private javax.swing.JTextField txtQTD;
    private javax.swing.JTextField txtTUR;
    private javax.swing.JTextField txtTURNO;
    // End of variables declaration//GEN-END:variables
}
