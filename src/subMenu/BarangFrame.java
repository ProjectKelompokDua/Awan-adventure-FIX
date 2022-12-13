/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subMenu;

import com.mysql.cj.jdbc.Driver;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import koneksi.Connect;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

/**
 *
 * @author perlengkapan
 */
public class BarangFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form BarangFrame
     */
    public BarangFrame() {
        initComponents();
        
        //hilangin border 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bif = (BasicInternalFrameUI) this.getUI();
        bif.setNorthPane(null);
        
        load_table();
        kosong();
        
        //cek masukan
        cekInputanIntegerOnly(txt_harga1);
        cekInputanIntegerOnly(txt_harga2);
        cekInputanIntegerOnly(txt_stok);
    }
    
    public void cekInputanIntegerOnly(JTextField textfield){
        textfield.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke) {
            String value = textfield.getText();
            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' ) {
               textfield.setEditable(true);
            }else if(ke.getKeyCode() == 8){
               textfield.setEditable(true);
            }else {
               textfield.setEditable(false);
            }
         }
      });
    }
    
    public void cekInputanStringOnly(JTextField textfield){
        textfield.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent ke) {
            String value = textfield.getText();
            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' ) {
               textfield.setEditable(false);
            }else if(ke.getKeyCode() == 8){
               textfield.setEditable(true);
            }else {
               textfield.setEditable(true);
            }
         }
      });
    }
    
    private void bersih(){
        txt_cari.setText("");
        txt_stok.setText("");
        txt_harga1.setText("");
        txt_harga2.setText("");
        Terang.setText("");
    }

   
    private void load_table(){
        DefaultTableModel model = new DefaultTableModel(){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        model.addColumn("No");
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Stok");
        model.addColumn("Harga 1 Hari");
        model.addColumn("Harga < 2 Hari");
        model.addColumn("Keterangan");
            
         try{
            int no = 1;
            String sql = "SELECT * FROM data_barang ORDER BY id_barang desc";
            java.sql.Connection conn=(Connection)Connect.GetConnection();
            java.sql.Statement pst = conn.createStatement();
            java.sql.ResultSet res = pst.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{no++,res.getString("id_barang"),res.getString("nama_barang"),res.getString("stok")
                ,res.getString("harga_hari"),res.getString("harga_2hari"),res.getString("keterangan")});
            }
            table_barang.setModel(model);
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e.getMessage());
        }
  
    }
    private void kosong(){
        txt_namaBarang.setText(null);
        txt_stok.setText(null);
        txt_harga1.setText(null);
        txt_harga2.setText(null);
        Terang.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbfilter = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        id_barang = new javax.swing.JLabel();
        txt_stok = new javax.swing.JTextField();
        txt_namaBarang = new javax.swing.JTextField();
        txt_harga1 = new javax.swing.JTextField();
        txt_harga2 = new javax.swing.JTextField();
        txt_cari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_barang = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btn_tambah = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Terang = new javax.swing.JTextArea();
        bg = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmbfilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Urutkan Dari--", "Data Paling Baru", "Data Paling lama", "Stok Paling Sedikit", "Stok Paling Banyak" }));
        cmbfilter.setBorder(null);
        cmbfilter.setName(""); // NOI18N
        cmbfilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbfilterActionPerformed(evt);
            }
        });
        getContentPane().add(cmbfilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 290, 160, 30));

        jLabel6.setFont(new java.awt.Font("Outfit", 0, 15)); // NOI18N
        jLabel6.setText("Keterangan");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, 30));

        jLabel5.setFont(new java.awt.Font("Outfit", 0, 15)); // NOI18N
        jLabel5.setText("Harga < 2 hari");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, 30));

        jLabel4.setFont(new java.awt.Font("Outfit", 0, 15)); // NOI18N
        jLabel4.setText("Harga 1 Hari");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, 30));

        jLabel3.setFont(new java.awt.Font("Outfit", 0, 15)); // NOI18N
        jLabel3.setText("Stok");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, 30));

        jLabel2.setFont(new java.awt.Font("Outfit", 0, 15)); // NOI18N
        jLabel2.setText("Nama Barang");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 30));

        id_barang.setForeground(new java.awt.Color(242, 242, 242));
        id_barang.setText("jLabel21");
        getContentPane().add(id_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 180, -1, -1));
        getContentPane().add(txt_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 310, 30));
        getContentPane().add(txt_namaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 310, 30));
        getContentPane().add(txt_harga1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 310, 30));
        getContentPane().add(txt_harga2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 310, 30));

        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
        });
        getContentPane().add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 290, 240, 30));

        table_barang.setAutoCreateRowSorter(true);
        table_barang.setModel(new javax.swing.table.DefaultTableModel(
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
        table_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_barangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_barang);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 940, 190));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_tambah.setBackground(new java.awt.Color(252, 191, 74));
        btn_tambah.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, 40));

        btn_edit.setBackground(new java.awt.Color(252, 191, 74));
        btn_edit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_edit.setText("Edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        jPanel1.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 100, 40));

        btn_hapus.setBackground(new java.awt.Color(252, 191, 74));
        btn_hapus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 100, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 370, 90));

        Terang.setColumns(20);
        Terang.setRows(5);
        jScrollPane1.setViewportView(Terang);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 310, 100));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/background.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbfilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfilterActionPerformed
        try{
            int filter = cmbfilter.getSelectedIndex();
            Connection conn = koneksi.Connect.GetConnection();

            Statement stm = (Statement) conn.createStatement();
            String msql;

            if (filter == 1){
                msql = "SELECT * FROM data_barang GROUP BY id_barang ORDER BY id_barang DESC";
            }
            else if (filter == 2){
                msql = "SELECT * FROM data_barang GROUP BY id_barang ORDER BY id_barang ASC";
            }
            else if (filter == 3){
                msql = "SELECT * FROM data_barang GROUP BY id_barang ORDER BY stok ASC";
            }
            else if (filter == 4){
                msql = "SELECT * FROM data_barang GROUP BY id_barang ORDER BY stok DESC";
            }
            else{
                msql = "SELECT * FROM data_barang GROUP BY id_barang ORDER BY id_barang DESC";
            }

            ResultSet res = stm.executeQuery(msql);
            DefaultTableModel dtm = new DefaultTableModel(){
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            };
            dtm.addColumn("No.");
            dtm.addColumn("ID Barang");
            dtm.addColumn("Nama Barang");
            dtm.addColumn("Stok");
            dtm.addColumn("harga 1 hari");
            dtm.addColumn("harga < 2 hari");
            dtm.addColumn("keterangan");
            table_barang.setModel(dtm);

            while(res.next()){
                dtm.addRow(new Object[]{
                    res.getString("id_barang"),
                    res.getString("nama_barang"),
                    res.getString("stok"),
                    res.getString("harga_hari"),
                    res.getString("harga_2hari"),
                    res.getString("keterangan")
                });
                table_barang.setModel(dtm);

            }
        }catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_cmbfilterActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased

        DefaultTableModel dtm = new DefaultTableModel(){
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        dtm.addColumn("No.");
        dtm.addColumn("ID Barang");
        dtm.addColumn("Nama Barang");
        dtm.addColumn("Stok");
        dtm.addColumn("harga 1 hari");
        dtm.addColumn("harga < 2 hari");
        dtm.addColumn("keterangan");
        table_barang.setModel(dtm);

        try {
            Statement statement = (Statement)Connect.GetConnection().createStatement();
            ResultSet res = statement.executeQuery("select * from data_barang where nama_barang like '%"+txt_cari.getText()+"%'");

            while(res.next()){
                dtm.addRow(new Object[]{
                    res.getString("id_barang"),
                    res.getString("nama_barang"),
                    res.getString("stok"),
                    res.getString("harga_hari"),
                    res.getString("harga_2hari"),
                    res.getString("keterangan")
                });
                table_barang.setModel(dtm);

            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_txt_cariKeyReleased

    private void table_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_barangMouseClicked
        int i = table_barang.getSelectedRow();
        TableModel tbl = table_barang.getModel();

        String field1 = tbl.getValueAt(i, 1).toString();
        String field2 = tbl.getValueAt(i, 2).toString();
        String field3 = tbl.getValueAt(i, 3).toString();
        String field4 = tbl.getValueAt(i, 4).toString();
        String field5 = tbl.getValueAt(i, 5).toString();
        String field6 = tbl.getValueAt(i, 6).toString();

        txt_namaBarang.setText(field2);
        txt_stok.setText(field3);
        txt_harga1.setText(field4);
        txt_harga2.setText(field5);
        Terang.setText(field6);

        try{
            String sql = "SELECT * FROM data_barang WHERE nama_barang ='"+field2+"'";
            Connection conn = koneksi.Connect.GetConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
            id_barang.setText(rs.getString("id_barang"));
        } catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_table_barangMouseClicked

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        try{
            String sql = "INSERT INTO data_barang VALUES (null,'"+txt_namaBarang.getText()+"','"
            +txt_stok.getText()+"','"+txt_harga1.getText()+"','"+txt_harga2.getText()+"','"
            +Terang.getText()+"')";
            java.sql.Connection conn=(Connection)Connect.GetConnection();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error");
            System.out.println(e.getMessage());
        }
        load_table();
        kosong();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        try{
            if(txt_namaBarang.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data Nama harus diisi");
                txt_namaBarang.requestFocus();
            }else if(txt_stok.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data Stok harus diisi");
                txt_stok.requestFocus();
            }else if(txt_harga1.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data Harga 1 hari harus diisi");
                txt_harga1.requestFocus();
            }else if(txt_harga2.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data Alamat harus diisi");
                txt_harga2.requestFocus();
            }else if(Terang.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data Password harus diisi");
            }else{
                String insertdata = "update data_barang set nama_barang='"+ txt_namaBarang.getText()
                +"',stok='"+ txt_stok.getText() +"', harga_hari='"+ txt_harga1.getText()
                +"', harga_2hari='"+ txt_harga2.getText() +"', keterangan='"+ Terang.getText()
                +"' where id_barang='"+id_barang.getText()+"'";

                Connection connect = koneksi.Connect.GetConnection();
                PreparedStatement ps = connect.prepareStatement(insertdata);
                ps.executeUpdate();

                load_table();
                kosong();

                JOptionPane.showMessageDialog(rootPane, "Data Barang berhasil diperbarui");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        try{
            String sql = "DELETE FROM data_barang Where id_barang ='"+id_barang.getText()+"'";
            java.sql.Connection conn=(Connection)Connect.GetConnection();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            int confirmLogout = JOptionPane.showConfirmDialog(rootPane, "Ingin menghapus data? ?", "Hapus Data", JOptionPane.YES_NO_OPTION);
            if (confirmLogout == JOptionPane.YES_OPTION) {
                pst.execute();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                kosong();
                load_table();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btn_hapusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Terang;
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> cmbfilter;
    private javax.swing.JLabel id_barang;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_barang;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_harga1;
    private javax.swing.JTextField txt_harga2;
    private javax.swing.JTextField txt_namaBarang;
    private javax.swing.JTextField txt_stok;
    // End of variables declaration//GEN-END:variables
}
