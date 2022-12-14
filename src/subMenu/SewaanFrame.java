/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subMenu;

import com.mysql.cj.jdbc.Driver;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import koneksi.Connect;

/**
 *
 * @author perlengkapan
 */
public class SewaanFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form SewaanFrame
     */
    public SewaanFrame() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bif = (BasicInternalFrameUI) this.getUI();
        bif.setNorthPane(null);
        
        loadTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_carisewaan = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_sewaan = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_carisewaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_carisewaanKeyReleased(evt);
            }
        });
        getContentPane().add(txt_carisewaan, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 40, 190, 30));

        table_sewaan.setAutoCreateRowSorter(true);
        table_sewaan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table_sewaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_sewaan.setRowHeight(40);
        table_sewaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_sewaanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_sewaan);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 960, 230));

        jLabel5.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel5.setText("Cari");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, -1, 30));

        jPanel6.setBackground(new java.awt.Color(252, 191, 73));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Outfit", 1, 14)); // NOI18N
        jLabel6.setText("Cetak");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/icons8_print_25px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 110, 40));

        jPanel7.setBackground(new java.awt.Color(252, 191, 73));

        jLabel8.setFont(new java.awt.Font("Outfit", 1, 14)); // NOI18N
        jLabel8.setText("Detail Transaksi");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 330, 120, 50));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/background.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void loadTable() {
        DefaultTableModel tableModel = new DefaultTableModel(){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        tableModel.addColumn("No");
        tableModel.addColumn("Id Sewaan");
        tableModel.addColumn("Nama Penyewa");
        tableModel.addColumn("Jumlah Barang");
        tableModel.addColumn("Tanggal Pinjam");
        tableModel.addColumn("Tanggal Kembali");
        tableModel.addColumn("Total");
        tableModel.addColumn("Status");

        try {
            String sql = "SELECT data_sewaan.id_sewaan, data_sewaan.nama_penyewa, COUNT(detail_data_sewaan.id_barang) AS jumlah_barang ,"
                    + "data_sewaan.tgl_pinjam, data_sewaan.tgl_kembali, data_sewaan.total, STATUS "
                    + "FROM data_sewaan JOIN detail_data_sewaan ON data_sewaan.id_sewaan = detail_data_sewaan.id_sewaan "
                    + "GROUP BY id_sewaan";

            Connection connect = koneksi.Connect.GetConnection();
            PreparedStatement pst = connect.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int no = 1;
                tableModel.addRow(new Object[]{no++ ,rs.getString("id_sewaan"), rs.getString("nama_penyewa"),
                    rs.getString("jumlah_barang"), rs.getString("tgl_pinjam"), rs.getString("tgl_kembali"), rs.getString("total"),
                    rs.getString("status")});
            }
            table_sewaan.setModel(tableModel);
        } catch (Exception e) {

        }
    }
    
    private void txt_carisewaanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_carisewaanKeyReleased
        // TODO add your handling code here:
        String cari = txt_carisewaan.getText();

        DefaultTableModel dtm = new DefaultTableModel(){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        dtm.addColumn("No");
        dtm.addColumn("Id Sewaan");
        dtm.addColumn("Nama Penyewa");
        dtm.addColumn("Jumlah Barang");
        dtm.addColumn("Tanggal Pinjam");
        dtm.addColumn("Tanggal Kembali");
        dtm.addColumn("Total");
        dtm.addColumn("Status");
        table_sewaan.setModel(dtm);

        try {
            Statement statement = (Statement) Connect.GetConnection().createStatement();
            ResultSet res = statement.executeQuery("SELECT data_sewaan.id_sewaan, data_sewaan.nama_penyewa, data_sewaan.jenis_identitas,\n"
                + "data_sewaan.dp, data_barang.nama_barang, data_sewaan.tanggal_pinjam, data_sewaan.tanggal_kembali,\n"
                + "data_sewaan.jumlah, data_sewaan.total\n"
                + "FROM data_sewaan join data_barang\n"
                + "on data_sewaan.id_barang = data_barang.id_barang \n"
                + "where nama_penyewa like '%"+cari+"%' or jenis_identitas like '%"+cari+"%'");

            while (res.next()) {
                dtm.addRow(new Object[]{
                    res.getString("id_sewaan"),
                    res.getString("nama_penyewa"),
                    res.getString("jenis_identitas"),
                    res.getString("dp"),
                    res.getString("nama_barang"),
                    res.getString("tanggal_pinjam"),
                    res.getString("tanggal_kembali"),
                    res.getString("jumlah"),
                    res.getString("total")
                });
                table_sewaan.setModel(dtm);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_txt_carisewaanKeyReleased

    private void table_sewaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_sewaanMouseClicked
        // TODO add your handling code here:
        int i = table_sewaan.getSelectedRow();
        TableModel tbl = table_sewaan.getModel();
    }//GEN-LAST:event_table_sewaanMouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel6MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_sewaan;
    private javax.swing.JTextField txt_carisewaan;
    // End of variables declaration//GEN-END:variables
}
