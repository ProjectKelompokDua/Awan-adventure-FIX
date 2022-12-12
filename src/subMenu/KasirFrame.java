/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subMenu;

import javax.swing.plaf.basic.BasicInternalFrameUI;
import com.toedter.calendar.JDateChooser;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import koneksi.Connect;

/**
 *
 * @author perlengkapan
 */
public class KasirFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form KasirFrame
     */
    public KasirFrame() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bif = (BasicInternalFrameUI) this.getUI();
        bif.setNorthPane(null);
        
        updateComboBarang();
        clear();
        tanggalHariIni();
        cekInputanStringOnly(txt_nama);
        cekInputanIntegerOnly(txt_identitas);
        cekInputanIntegerOnly(txt_dp);
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
    
    private void updateComboBarang(){
        try{
            String sql = "select * from data_barang";
            Connection conn = koneksi.Connect.GetConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet res = pst.executeQuery();
            while(res.next()){
                combo_barang.addItem(res.getString("nama_barang"));
            }
            combo_jumlah.disable();
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e.getMessage());
        }
    }
    
    private void updateComboJumlah(){
        try{
            String namaBarang = (String) combo_barang.getSelectedItem();
            
            String sql = "select * from data_barang where nama_barang='"+namaBarang+"'";
            Connection conn = koneksi.Connect.GetConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet res = pst.executeQuery();
            if(res.next()){
                txt_stok.setText(res.getString("stok"));
                int stok = Integer.parseInt(res.getString("stok"));
                combo_jumlah.removeAllItems();
                combo_jumlah.addItem("0");
                for (int i = 1; i <= stok; i++) {
                    combo_jumlah.addItem(Integer.toString(i));
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e.getMessage());
        }
    }
    
    private void clear(){
        txt_nama.setText("");
        text_areaAlamat.setText("");
        combo_identitas.setSelectedIndex(0);
        txt_identitas.setText("");
        txt_identitas.disable();
        tgl_pinjam.setDate(null);
        tgl_kembali.setDate(null);
        txt_dp.setText("");
        txt_dp.enable();
        tbl_barang.removeAll();
        tbl_barang.setRowSelectionAllowed(true);
        combo_barang.setSelectedIndex(0);
        combo_jumlah.setSelectedItem("");
        combo_jumlah.setEnabled(false);
        btn_editBarang.setEnabled(false);
        btn_hapusBarang.setEnabled(false);
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("No");
        dtm.addColumn("Nama Barang");
        dtm.addColumn("Jumlah");
        dtm.addColumn("Harga/Hari");
        dtm.addColumn("Harga > 2 Hari");
        tbl_barang.setModel(dtm);
        tbl_barang.setDefaultEditor(Object.class, null);
        btn_clearSelection.setEnabled(false);
        total_harga.setText("");
    }
    
    private void tanggalHariIni(){
        long waktu = System.currentTimeMillis();
        Date today = new Date(waktu);
        tgl_pinjam.setMinSelectableDate(today);
        tgl_pinjam.setDate(today);
        tgl_pinjam.setLocale(new Locale("id", "ID"));
        tgl_kembali.setMinSelectableDate(today);
        tgl_kembali.setLocale(new Locale("id", "ID"));
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_nama = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        label_alamat = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_barang = new javax.swing.JTable();
        btn_proses = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        combo_barang = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        combo_jumlah = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txt_stok = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_areaAlamat = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        combo_identitas = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txt_identitas = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_dp = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btn_tambahBarang = new javax.swing.JButton();
        btn_editBarang = new javax.swing.JButton();
        btn_hapusBarang = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_clearSelection = new javax.swing.JButton();
        tgl_kembali = new com.toedter.calendar.JDateChooser();
        tgl_pinjam = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        total_harga = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_nama.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        label_nama.setText("Nama");
        getContentPane().add(label_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, 30));

        txt_nama.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        txt_nama.setBorder(null);
        getContentPane().add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 360, 30));

        label_alamat.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        label_alamat.setText("Alamat");
        getContentPane().add(label_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        tbl_barang.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        tbl_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nama Barang", "Jumlah", "Harga/hari", "Harga > 2 hari"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_barang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbl_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_barang);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 230, 550, 250));

        btn_proses.setBackground(new java.awt.Color(252, 191, 73));
        btn_proses.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        btn_proses.setText("Proses");
        btn_proses.setBorder(null);
        btn_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prosesActionPerformed(evt);
            }
        });
        getContentPane().add(btn_proses, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 500, 100, 40));

        jPanel3.setBackground(new java.awt.Color(242, 242, 242));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Barang"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel3.setText("Barang");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 27, -1, -1));

        combo_barang.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        combo_barang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Barang..." }));
        combo_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_barangActionPerformed(evt);
            }
        });
        jPanel3.add(combo_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 24, 244, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel5.setText("Jumlah");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 72, -1, -1));

        combo_jumlah.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        combo_jumlah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0" }));
        jPanel3.add(combo_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 69, 70, -1));

        jLabel11.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("Ready Stok : ");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 74, -1, -1));

        txt_stok.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        txt_stok.setForeground(new java.awt.Color(255, 0, 0));
        jPanel3.add(txt_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 69, 69, 27));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 350, 120));

        text_areaAlamat.setColumns(15);
        text_areaAlamat.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        text_areaAlamat.setRows(5);
        text_areaAlamat.setBorder(null);
        jScrollPane2.setViewportView(text_areaAlamat);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 360, -1));

        jLabel8.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel8.setText("Identitas diri");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, -1, 30));

        combo_identitas.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        combo_identitas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Identitas...", "KTP", "SIM", "Kartu Pelajar" }));
        combo_identitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_identitasActionPerformed(evt);
            }
        });
        getContentPane().add(combo_identitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 30, 260, 30));

        jLabel9.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel9.setText("Nomor Identitas");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, -1, 30));

        txt_identitas.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        txt_identitas.setBorder(null);
        txt_identitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_identitasActionPerformed(evt);
            }
        });
        getContentPane().add(txt_identitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 70, 260, 30));

        jLabel10.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel10.setText("Tanggal penyewaan");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, -1, 30));

        jLabel13.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel13.setText("Total    :      Rp");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 510, -1, -1));

        jLabel14.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(-180, -120, -1, -1));

        jLabel12.setFont(new java.awt.Font("Outfit Medium", 0, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("To");
        jLabel12.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 110, 40, 30));

        jLabel16.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel16.setText("DP");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, -1, 30));

        txt_dp.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        txt_dp.setBorder(null);
        getContentPane().add(txt_dp, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 150, 260, 30));

        jPanel4.setBackground(new java.awt.Color(242, 242, 242));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_tambahBarang.setBackground(new java.awt.Color(92, 184, 92));
        btn_tambahBarang.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        btn_tambahBarang.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambahBarang.setText("Tambah");
        btn_tambahBarang.setBorder(null);
        btn_tambahBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_tambahBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahBarangActionPerformed(evt);
            }
        });
        jPanel4.add(btn_tambahBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 33, 101, 31));

        btn_editBarang.setBackground(new java.awt.Color(23, 162, 184));
        btn_editBarang.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        btn_editBarang.setForeground(new java.awt.Color(255, 255, 255));
        btn_editBarang.setText("Edit");
        btn_editBarang.setBorder(null);
        btn_editBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_editBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editBarangActionPerformed(evt);
            }
        });
        jPanel4.add(btn_editBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 33, 101, 32));

        btn_hapusBarang.setBackground(new java.awt.Color(220, 53, 69));
        btn_hapusBarang.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        btn_hapusBarang.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapusBarang.setText("Hapus");
        btn_hapusBarang.setBorder(null);
        btn_hapusBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_hapusBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusBarangActionPerformed(evt);
            }
        });
        jPanel4.add(btn_hapusBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 33, 101, 32));

        btn_clear.setBackground(new java.awt.Color(108, 117, 125));
        btn_clear.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        btn_clear.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear.setText("Bersihkan");
        btn_clear.setBorder(null);
        btn_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        jPanel4.add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 77, 155, 31));

        btn_clearSelection.setBackground(new java.awt.Color(108, 117, 125));
        btn_clearSelection.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        btn_clearSelection.setForeground(new java.awt.Color(255, 255, 255));
        btn_clearSelection.setText("Bersihkan Pilihan");
        btn_clearSelection.setBorder(null);
        btn_clearSelection.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_clearSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearSelectionActionPerformed(evt);
            }
        });
        jPanel4.add(btn_clearSelection, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 77, 160, 31));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 350, 120));
        getContentPane().add(tgl_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 110, 110, 30));
        getContentPane().add(tgl_pinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 110, 110, 30));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 1010, 20));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 530, 200, 10));

        total_harga.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        total_harga.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(total_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 510, 100, 20));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/background.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barangMouseClicked
        // TODO add your handling code here:
        int i = tbl_barang.getSelectedRow();
        TableModel tbl = tbl_barang.getModel();

        String field2 = tbl.getValueAt(i, 1).toString();
        String field3 = tbl.getValueAt(i, 2).toString();

        combo_barang.setSelectedItem(field2);
        combo_jumlah.setEnabled(true);
        combo_jumlah.setSelectedItem(field3);
        btn_tambahBarang.setEnabled(false);
        btn_editBarang.setEnabled(true);
        btn_hapusBarang.setEnabled(true);
        btn_clearSelection.setEnabled(true);
    }//GEN-LAST:event_tbl_barangMouseClicked

    private void btn_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prosesActionPerformed
        // TODO add your handling code here:
        try{
            //check identitas ada atau tidak
            if(txt_nama.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane,  "Data Nama harus diisi");
                txt_nama.requestFocus();
            }else if(text_areaAlamat.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data Alamat harus diisi");
                text_areaAlamat.requestFocus();
            }else if(combo_identitas.getSelectedIndex() == 0 && txt_dp.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data DP harus diisi, karena tidak menyertakan kartu identitas");
                txt_dp.requestFocus();
            }else if(combo_identitas.getSelectedIndex() != 0 && txt_identitas.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data Nomor Identitas harus diisi, karena menyertakan kartu identitas");
                txt_dp.requestFocus();
            }else if(tgl_pinjam.getDate() == null){
                JOptionPane.showMessageDialog(rootPane, "Data tanggal pinjam harus diisi");
                tgl_pinjam.requestFocus();
            }else if(tgl_kembali.getDate() == null){
                JOptionPane.showMessageDialog(rootPane, "Data tanggal kembali harus diisi");
                tgl_kembali.requestFocus();
            }else if(tbl_barang.getModel().getRowCount() == 0){
                JOptionPane.showMessageDialog(rootPane, "Silahkan pilih barang yang ingin disewa");
            }else{

                Connection conn = koneksi.Connect.GetConnection();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
                int rowCount = tbl_barang.getModel().getRowCount();

                //input data_sewaan
                String sqlDataSewaan = "insert into data_sewaan values(null,'"+ txt_nama.getText() +"' ,'"+ total_harga.getText() +"', 'proses', "
                + "'"+ sdf.format(tgl_pinjam.getDate()) +"', '"+ sdf.format(tgl_kembali.getDate()) +"', now())";
                PreparedStatement prepare = conn.prepareStatement(sqlDataSewaan);
                prepare.execute();

                //ambil id_sewaan
                String sqlAmbilId = "select id_sewaan from data_sewaan where nama_penyewa='"+ txt_nama.getText() +"'";
                PreparedStatement stm = conn.prepareStatement(sqlAmbilId);
                ResultSet hasilId = stm.executeQuery();
                hasilId.next();

                String identitas = null;
                if(combo_identitas.getSelectedIndex() == 0){
                    identitas = "";
                }else{
                    identitas = combo_identitas.getSelectedItem().toString();
                }
                for (int i = 0; i < rowCount; i++){
                    //ngambil nama barang dan stok barang dari jTable
                    Object namaBarang = tbl_barang.getValueAt(i, 1);
                    Object stokBarang = tbl_barang.getValueAt(i, 2);

                    //ngambil data berdasarkan nama barang di jTable;
                    String dataBarang = "Select * from data_barang where nama_barang='"+ namaBarang +"'";
                    PreparedStatement pst = conn.prepareStatement(dataBarang);
                    ResultSet res = pst.executeQuery();
                    if(res.next()){
                        String sqlDetailSewaan = "insert into detail_data_sewaan values ('"+ hasilId.getString("id_sewaan") +"',"
                        + "'"+ txt_nama.getText() +"', '"+ text_areaAlamat.getText() +"', '"+ identitas +"',"
                        + "'"+ txt_identitas.getText() +"', '"+ txt_dp.getText() +"', '"+ res.getString("id_barang") +"', "
                        + "'"+ sdf.format(tgl_pinjam.getDate()) +"', '"+ sdf.format(tgl_kembali.getDate()) +"',"
                        + "'"+ stokBarang +"', '"+ total_harga.getText() +"')";
                        PreparedStatement statemen = conn.prepareStatement(sqlDetailSewaan);
                        statemen.execute();
                    }
                }
                JOptionPane.showMessageDialog(rootPane, "Transaksi Behasil");
                clear();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_prosesActionPerformed

    private void combo_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_barangActionPerformed
        // TODO add your handling code here:
        combo_jumlah.setEnabled(true);
        updateComboJumlah();
        if(combo_barang.getSelectedItem().equals("Pilih Barang...")){
            combo_jumlah.removeAllItems();
            combo_jumlah.addItem("0");
            combo_jumlah.disable();
            txt_stok.setText("");
        }
    }//GEN-LAST:event_combo_barangActionPerformed

    private void combo_identitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_identitasActionPerformed
        // TODO add your handling code here:
        if(combo_identitas.getSelectedItem().equals("Pilih Identitas...")){
            txt_identitas.disable();
            txt_dp.enable();
            txt_identitas.setText("");
            txt_dp.setText("");
        }else{
            txt_identitas.enable();
            txt_dp.disable();
            txt_identitas.setText("");
            txt_dp.setText("");
        }
    }//GEN-LAST:event_combo_identitasActionPerformed

    private void txt_identitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_identitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_identitasActionPerformed

    private void btn_tambahBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahBarangActionPerformed
        // TODO add your handling code here:

        if(tgl_pinjam.getDate() == null && tgl_kembali.getDate() == null){
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih tanggal pinjam dan tanggal kembali terlebih dahulu");
        }else{
            try{
                //query cari jarakHari
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
                String tanggalPinjamString = sdf.format(tgl_pinjam.getDate());
                String tanggalKembaliString = sdf.format(tgl_kembali.getDate());
                LocalDate tanggalPinjam = LocalDate.parse(tanggalPinjamString);
                LocalDate tanggalKembali = LocalDate.parse(tanggalKembaliString);
                Period jarak = Period.between(tanggalPinjam , tanggalKembali);

                if(combo_barang.getSelectedItem().equals("Pilih Barang...")){
                    JOptionPane.showMessageDialog(rootPane, "Silahkan pilih barang terlebih dahulu");
                }else if(combo_jumlah.getSelectedItem().equals("0")){
                    JOptionPane.showMessageDialog(rootPane, "Silahkan pilih jumlah barang terlebih dahulu");
                }else if(jarak.getDays() < 1){
                    JOptionPane.showMessageDialog(rootPane, "Silahkan dicek kembali tanggal peminjaman dan pengembalian");
                }else{

                    //ambil data barang
                    String sql = "select * from data_barang where nama_barang='"+ combo_barang.getSelectedItem() +"'";
                    Connection conn = koneksi.Connect.GetConnection();
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet res = pst.executeQuery();

                    res.next();
                    if(jarak.getDays() > 2){
                        int jumlah = Integer.parseInt(combo_jumlah.getSelectedItem().toString());
                        int harga = Integer.parseInt(res.getString("harga_2hari"));
                        int hasil = jumlah * harga;
                        String total = total_harga.getText().toString();
                        if(total.equals("")){
                            int totalSebelumnya = 0;
                            int totalFix = totalSebelumnya + hasil;
                            String hasilString = String.valueOf(totalFix);

                            total_harga.setText(hasilString);

                            //query tambah data ke jTable
                            DefaultTableModel dtm = (DefaultTableModel)tbl_barang.getModel();
                            int rowCount = tbl_barang.getModel().getRowCount() + 1;
                            dtm.addRow(new Object[]{
                                rowCount++,
                                res.getString("nama_barang"),
                                combo_jumlah.getSelectedItem(),
                                res.getString("harga_hari"),
                                res.getString("harga_2hari")
                            });
                        }else{
                            int totalSebelumnya = Integer.parseInt(total_harga.getText().toString());
                            int totalFix = totalSebelumnya + hasil;
                            String hasilString = String.valueOf(totalFix);

                            total_harga.setText(hasilString);

                            //query tambah data ke jTable
                            DefaultTableModel dtm = (DefaultTableModel)tbl_barang.getModel();
                            int rowCount = tbl_barang.getModel().getRowCount() + 1;
                            dtm.addRow(new Object[]{
                                rowCount++,
                                res.getString("nama_barang"),
                                combo_jumlah.getSelectedItem(),
                                res.getString("harga_hari"),
                                res.getString("harga_2hari")
                            });
                        }

                    }else{
                        int jumlah = Integer.parseInt(combo_jumlah.getSelectedItem().toString());
                        int harga = Integer.parseInt(res.getString("harga_hari"));
                        int hasil = jumlah * harga;
                        String total = total_harga.getText().toString();
                        if(total.equals("")){
                            int totalSebelumnya = 0;
                            int totalFix = totalSebelumnya + hasil;
                            String hasilString = String.valueOf(totalFix);

                            total_harga.setText(hasilString);

                            //query tambah data ke jTable
                            DefaultTableModel dtm = (DefaultTableModel)tbl_barang.getModel();
                            int rowCount = tbl_barang.getModel().getRowCount() + 1;
                            dtm.addRow(new Object[]{
                                rowCount++,
                                res.getString("nama_barang"),
                                combo_jumlah.getSelectedItem(),
                                res.getString("harga_hari"),
                                res.getString("harga_2hari")
                            });
                        }else{
                            int totalSebelumnya = Integer.parseInt(total_harga.getText().toString());
                            int totalFix = totalSebelumnya + hasil;
                            String hasilString = String.valueOf(totalFix);

                            total_harga.setText(hasilString);

                            //query tambah data ke jTable
                            DefaultTableModel dtm = (DefaultTableModel)tbl_barang.getModel();
                            int rowCount = tbl_barang.getModel().getRowCount() + 1;
                            dtm.addRow(new Object[]{
                                rowCount++,
                                res.getString("nama_barang"),
                                combo_jumlah.getSelectedItem(),
                                res.getString("harga_hari"),
                                res.getString("harga_2hari")
                            });
                        }
                    }

                    combo_barang.setSelectedIndex(0);
                    combo_jumlah.removeAllItems();
                    combo_jumlah.disable();

                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(rootPane, "Error");
                System.out.println(e.getMessage());
            }
        }

    }//GEN-LAST:event_btn_tambahBarangActionPerformed

    private void btn_editBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editBarangActionPerformed
        // TODO add your handling code here:
        if(tgl_pinjam.getDate() == null && tgl_kembali.getDate() == null){
            JOptionPane.showMessageDialog(rootPane, "Silahkan pilih tanggal pinjam dan tanggal kembali terlebih dahulu");
        }else{
            int confirmEdit = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin mengubah data ini?", "Edit", JOptionPane.YES_NO_OPTION);
            if (confirmEdit == JOptionPane.YES_OPTION) {
                try{
                    int i = tbl_barang.getSelectedRow();

                    //ambil value harga
                    int jumlahbeli = Integer.parseInt(tbl_barang.getValueAt(i, 2).toString());
                    int fieldhargaHari = Integer.parseInt(tbl_barang.getValueAt(i, 3).toString()) * jumlahbeli;
                    int fieldharga3Hari = Integer.parseInt(tbl_barang.getValueAt(i, 4).toString()) * jumlahbeli;

                    //query cari jarakHari
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
                    String tanggalPinjamString = sdf.format(tgl_pinjam.getDate());
                    String tanggalKembaliString = sdf.format(tgl_kembali.getDate());
                    LocalDate tanggalPinjam = LocalDate.parse(tanggalPinjamString);
                    LocalDate tanggalKembali = LocalDate.parse(tanggalKembaliString);
                    Period jarak = Period.between(tanggalPinjam , tanggalKembali);

                    if(jarak.getDays() > 2){
                        int totalHarga = Integer.parseInt(total_harga.getText()) - fieldharga3Hari;
                        String total = String.valueOf(totalHarga);
                        total_harga.setText(total);
                    }else{
                        int totalHarga = Integer.parseInt(total_harga.getText()) - fieldhargaHari;
                        String total = String.valueOf(totalHarga);
                        total_harga.setText(total);
                    }

                    DefaultTableModel hapus = (DefaultTableModel) tbl_barang.getModel();
                    hapus.removeRow(i);

                    if(combo_barang.getSelectedItem().equals("Pilih Barang...")){
                        JOptionPane.showMessageDialog(rootPane, "Silahkan pilih barang terlebih dahulu");
                    }else if(combo_jumlah.getSelectedItem().equals("0")){
                        JOptionPane.showMessageDialog(rootPane, "Silahkan pilih jumlah barang terlebih dahulu");
                    }else if(jarak.getDays() < 1){
                        JOptionPane.showMessageDialog(rootPane, "Silahkan dicek kembali tanggal peminjaman dan pengembalian");
                    }else{

                        //ambil data barang
                        String sql = "select * from data_barang where nama_barang='"+ combo_barang.getSelectedItem() +"'";
                        Connection conn = koneksi.Connect.GetConnection();
                        PreparedStatement pst = conn.prepareStatement(sql);
                        ResultSet res = pst.executeQuery();

                        res.next();
                        if(jarak.getDays() > 2){
                            int jumlah = Integer.parseInt(combo_jumlah.getSelectedItem().toString());
                            int harga = Integer.parseInt(res.getString("harga_2hari"));
                            int hasil = jumlah * harga;
                            String total = total_harga.getText().toString();
                            if(total.equals("")){
                                int totalSebelumnya = 0;
                                int totalFix = totalSebelumnya + hasil;
                                String hasilString = String.valueOf(totalFix);

                                total_harga.setText(hasilString);

                                //query tambah data ke jTable
                                DefaultTableModel dtm = (DefaultTableModel)tbl_barang.getModel();
                                int rowCount = tbl_barang.getModel().getRowCount() + 1;
                                dtm.addRow(new Object[]{
                                    rowCount++,
                                    res.getString("nama_barang"),
                                    combo_jumlah.getSelectedItem(),
                                    res.getString("harga_hari"),
                                    res.getString("harga_2hari")
                                });
                            }else{
                                int totalSebelumnya = Integer.parseInt(total_harga.getText().toString());
                                int totalFix = totalSebelumnya + hasil;
                                String hasilString = String.valueOf(totalFix);

                                total_harga.setText(hasilString);

                                //query tambah data ke jTable
                                DefaultTableModel dtm = (DefaultTableModel)tbl_barang.getModel();
                                int rowCount = tbl_barang.getModel().getRowCount() + 1;
                                dtm.addRow(new Object[]{
                                    rowCount++,
                                    res.getString("nama_barang"),
                                    combo_jumlah.getSelectedItem(),
                                    res.getString("harga_hari"),
                                    res.getString("harga_2hari")
                                });
                            }

                        }else{
                            int jumlah = Integer.parseInt(combo_jumlah.getSelectedItem().toString());
                            int harga = Integer.parseInt(res.getString("harga_hari"));
                            int hasil = jumlah * harga;
                            String total = total_harga.getText().toString();
                            if(total.equals("")){
                                int totalSebelumnya = 0;
                                int totalFix = totalSebelumnya + hasil;
                                String hasilString = String.valueOf(totalFix);

                                total_harga.setText(hasilString);

                                //query tambah data ke jTable
                                DefaultTableModel dtm = (DefaultTableModel)tbl_barang.getModel();
                                int rowCount = tbl_barang.getModel().getRowCount() + 1;
                                dtm.addRow(new Object[]{
                                    rowCount++,
                                    res.getString("nama_barang"),
                                    combo_jumlah.getSelectedItem(),
                                    res.getString("harga_hari"),
                                    res.getString("harga_2hari")
                                });
                            }else{
                                int totalSebelumnya = Integer.parseInt(total_harga.getText().toString());
                                int totalFix = totalSebelumnya + hasil;
                                String hasilString = String.valueOf(totalFix);

                                total_harga.setText(hasilString);

                                //query tambah data ke jTable
                                DefaultTableModel dtm = (DefaultTableModel)tbl_barang.getModel();
                                int rowCount = tbl_barang.getModel().getRowCount() + 1;
                                dtm.addRow(new Object[]{
                                    rowCount++,
                                    res.getString("nama_barang"),
                                    combo_jumlah.getSelectedItem(),
                                    res.getString("harga_hari"),
                                    res.getString("harga_2hari")
                                });
                            }
                        }
                        btn_tambahBarang.setEnabled(true);
                        btn_editBarang.setEnabled(false);
                        btn_hapusBarang.setEnabled(false);
                        combo_barang.setSelectedIndex(0);
                        combo_jumlah.setSelectedIndex(0);
                        combo_jumlah.setEnabled(false);
                        btn_clearSelection.setEnabled(false);
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(rootPane, "Error");
                    System.out.println(e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btn_editBarangActionPerformed

    private void btn_hapusBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusBarangActionPerformed
        // TODO add your handling code here:
        int confirmDelete = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin dihapus ?", "Hapus", JOptionPane.YES_NO_OPTION);
        if(confirmDelete == JOptionPane.YES_OPTION){
            int i = tbl_barang.getSelectedRow();

            //ambil value harga
            int jumlahbeli = Integer.parseInt(tbl_barang.getValueAt(i, 2).toString());
            int fieldhargaHari = Integer.parseInt(tbl_barang.getValueAt(i, 3).toString()) * jumlahbeli;
            int fieldharga3Hari = Integer.parseInt(tbl_barang.getValueAt(i, 4).toString()) * jumlahbeli;

            //query cari jarakHari
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
            String tanggalPinjamString = sdf.format(tgl_pinjam.getDate());
            String tanggalKembaliString = sdf.format(tgl_kembali.getDate());
            LocalDate tanggalPinjam = LocalDate.parse(tanggalPinjamString);
            LocalDate tanggalKembali = LocalDate.parse(tanggalKembaliString);
            Period jarak = Period.between(tanggalPinjam , tanggalKembali);

            if(jarak.getDays() > 2){
                int totalHarga = Integer.parseInt(total_harga.getText()) - fieldharga3Hari;
                String total = String.valueOf(totalHarga);
                total_harga.setText(total);
            }else{
                int totalHarga = Integer.parseInt(total_harga.getText()) - fieldhargaHari;
                String total = String.valueOf(totalHarga);
                total_harga.setText(total);
            }

            DefaultTableModel dtm = (DefaultTableModel) tbl_barang.getModel();
            dtm.removeRow(i);

            btn_tambahBarang.setEnabled(true);
            btn_editBarang.setEnabled(false);
            btn_hapusBarang.setEnabled(false);
            combo_barang.setSelectedIndex(0);
            combo_jumlah.setSelectedIndex(0);
            combo_jumlah.setEnabled(false);
            btn_clearSelection.setEnabled(false);
        }

    }//GEN-LAST:event_btn_hapusBarangActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        // TODO add your handling code here:
        int clear = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin membersihkan seluruh isian ? (termasuk pada table pilihan barang)", "Clear", JOptionPane.YES_NO_OPTION);
        if (clear == JOptionPane.YES_OPTION) {
            clear();
            tanggalHariIni();
        }

    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_clearSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearSelectionActionPerformed
        // TODO add your handling code here:
        tbl_barang.clearSelection();
        combo_barang.setSelectedIndex(0);
        combo_jumlah.setSelectedIndex(0);
        combo_jumlah.setEnabled(false);
        btn_clearSelection.setEnabled(false);
        btn_tambahBarang.setEnabled(true);
        btn_editBarang.setEnabled(false);
        btn_hapusBarang.setEnabled(false);
    }//GEN-LAST:event_btn_clearSelectionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_clearSelection;
    private javax.swing.JButton btn_editBarang;
    private javax.swing.JButton btn_hapusBarang;
    private javax.swing.JButton btn_proses;
    private javax.swing.JButton btn_tambahBarang;
    private javax.swing.JComboBox<String> combo_barang;
    private javax.swing.JComboBox<String> combo_identitas;
    private javax.swing.JComboBox<String> combo_jumlah;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label_alamat;
    private javax.swing.JLabel label_nama;
    private javax.swing.JTable tbl_barang;
    private javax.swing.JTextArea text_areaAlamat;
    private com.toedter.calendar.JDateChooser tgl_kembali;
    private com.toedter.calendar.JDateChooser tgl_pinjam;
    private javax.swing.JLabel total_harga;
    private javax.swing.JTextField txt_dp;
    private javax.swing.JTextField txt_identitas;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JLabel txt_stok;
    // End of variables declaration//GEN-END:variables
}
