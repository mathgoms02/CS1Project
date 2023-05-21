/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package acomp_cs1;

import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author matgo
 */
public class ficha extends javax.swing.JInternalFrame {
    String driver = "com.mysql.cj.jdbc.Driver"; //drive de conexao com o SQL
    String url = "jdbc:mysql://localhost:3306/cs1_control"; //porta padrao pro sql e do cs1controler
    String user = "root";
    String password = "microondas123@";
    PreparedStatement pst = null;
    /**
     * Creates new form ficha
     */
    public ficha() {
        initComponents();
        carregaTabela();
        pesquisar_data();
        
        setFrameIcon(new ImageIcon(this.getClass().getResource("/imagens/iconF1.png")));
    }
        private void carregaTabela() {
        
        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtDT.setText(data.format(LocalDateTime.now()));
        
        tbFICHA1.getColumnModel().getColumn(0).setPreferredWidth(20);
        tbFICHA1.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbFICHA1.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbFICHA1.getColumnModel().getColumn(4).setPreferredWidth(50);
        tbFICHA1.getColumnModel().getColumn(5).setPreferredWidth(50);
        tbFICHA1.getColumnModel().getColumn(6).setPreferredWidth(90);
        tbFICHA1.getColumnModel().getColumn(7).setPreferredWidth(90);
        tbFICHA1.getColumnModel().getColumn(8).setPreferredWidth(200);

        //selecionando o modelo pra table de pesquisa
        DefaultTableModel modelo = (DefaultTableModel) tbFICHA1.getModel();
        //sempre que iniciar, vai zerar a tabela e recarregar???
        modelo.setNumRows(0);
        
        //tbFICHA.getColumnModel().getColumn(0).setPreferredWidth(20);
        

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);

            Statement st = con.createStatement();

            //query
            String query = "SELECT * FROM fichaacomp"; //erro nessa linha
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                //adicionando linhas, dentro do resultset
                String Id = String.valueOf(rs.getInt("idFichaAcomp"));
                String re = rs.getString("re");
                String Peca = String.valueOf(rs.getInt("peca"));
                String Qtd = String.valueOf(rs.getInt("qtd"));
                String Problema = String.valueOf(rs.getInt("problema"));
                String Turno = String.valueOf(rs.getInt("turno"));
                String data1 = rs.getString("data");
                String acao = rs.getString("acao");
                String obs = rs.getString("obs");

                String tbData[] = {Id, re, Peca, Qtd, Problema, Turno, data1, acao, obs};

                modelo.addRow(tbData);
            }

            st.close();
            rs.close();
            con.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar a pagina" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void pesquisar_data() {
        
        DefaultTableModel modelo = (DefaultTableModel) tbFICHA1.getModel();
        modelo.setNumRows(0);
        
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        String sqlD = "SELECT * FROM fichaacomp WHERE data LIKE ?";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);            
            //passando o conteudo da caixa de pesquisa pro '?'
            //atencao ao % para a continuacao da string sql
            pst = con.prepareStatement(sqlD);
            pst.setString(1, txtDT.getText() +"%");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                //adicionando linhas, dentro do resultset
                String Id = String.valueOf(rs.getInt("idFichaAcomp"));
                String re = rs.getString("re");
                String Peca = String.valueOf(rs.getInt("peca"));
                String Qtd = String.valueOf(rs.getInt("qtd"));
                String Problema = String.valueOf(rs.getInt("problema"));
                String Turno = String.valueOf(rs.getInt("turno"));
                String data1 = rs.getString("data");
                String acao = rs.getString("acao");
                String obs = rs.getString("obs");
                

                String tbData[] = {Id, re, Peca, Qtd, Problema, Turno, data1, acao, obs};

                modelo.addRow(tbData);
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

        ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/fundoCinza.png"));
        Image image = icon.getImage();
        jDesktopPane1 = new javax.swing.JDesktopPane(){

            public void paintComponent(Graphics g){
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }

        };
        jScrollPane2 = new javax.swing.JScrollPane();
        tbFICHA1 = new javax.swing.JTable();
        txtDT = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        tbFICHA1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "RE", "Peça", "qNOK", "Problema", "Turno", "Data", "Ação", "OBS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, true, true, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbFICHA1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFICHA1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbFICHA1);

        txtDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDTKeyReleased(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data:");

        jDesktopPane1.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txtDT, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDT, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 507, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbFICHA1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFICHA1MouseClicked
        carregaTabela();
//        txtDT.setText("");
        pesquisar_data();
    }//GEN-LAST:event_tbFICHA1MouseClicked

    private void txtDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDTKeyReleased
        pesquisar_data();
    }//GEN-LAST:event_txtDTKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbFICHA1;
    private javax.swing.JTextField txtDT;
    // End of variables declaration//GEN-END:variables
}
