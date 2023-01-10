/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subMenu;

import java.sql.*;
import java.awt.Color;
import java.awt.Dimension;
import logic.DashboardL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author perlengkapan
 */
public class DashboardFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form Dashboard
     */
    public DashboardFrame() {
        initComponents();
        
        //hilangin border 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bif = (BasicInternalFrameUI) this.getUI();
        bif.setNorthPane(null);
        
        JTableHeader Theader = tbl_seringSewa.getTableHeader();
        Theader.setBackground(new Color(247,249,251));
        jScrollPane1.getViewport().setOpaque(false);
        tbl_seringSewa.setShowGrid(false);
        tbl_seringSewa.setIntercellSpacing(new Dimension(0, 0));
        jScrollPane1.setViewportBorder(null);
        
        JTableHeader Theader2 = tbl_kontrolStok.getTableHeader();
        Theader2.setBackground(new Color(247,249,251));
        jScrollPane2.getViewport().setOpaque(false);
        tbl_kontrolStok.setShowGrid(false);
        tbl_kontrolStok.setIntercellSpacing(new Dimension(0, 0));
        jScrollPane2.setViewportBorder(null);
        
        tableSeringSewa();
        tableKontrolBarang();
        
        hari();
        tanggal();
        showTime();
    }
    
    public void tableSeringSewa(){
        DefaultTableModel dtm = new DefaultTableModel() {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        dtm.addColumn("No");
        dtm.addColumn("Kode Barang");
        dtm.addColumn("Nama Barang");
        dtm.addColumn("Jumlah Barang yang Disewa");
        tbl_seringSewa.setModel(dtm);
        
        try{
            Connection conn = koneksi.Connect.GetConnection();
            String sql = "SELECT detail_data_sewaan.id_barang, nama_barang, SUM(jumlah) AS jumlah_seringSewa "
                        + "FROM detail_data_sewaan JOIN data_barang ON data_barang.id_barang = detail_data_sewaan.id_barang "
                        + "GROUP BY id_barang "
                        + "ORDER BY jumlah_seringSewa DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel dtmm = (DefaultTableModel) tbl_seringSewa.getModel();
            int no = 1;
            while(rs.next()){
                dtm.addRow(new Object[]{
                    no++,
                    rs.getString("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("jumlah_seringSewa")
                });
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error load data table sering sewa");
            System.out.println(e);
        }
    }
    
    public void tableSeringSewa2(){
        DefaultTableModel dtm = new DefaultTableModel() {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        dtm.addColumn("No");
        dtm.addColumn("Kode Barang");
        dtm.addColumn("Nama Barang");
        dtm.addColumn("Jumlah Barang yang Disewa");
        tbl_seringSewa.setModel(dtm);
        
        try{
            Connection conn = koneksi.Connect.GetConnection();
            String sql = "SELECT detail_data_sewaan.id_barang, nama_barang, SUM(jumlah) AS jumlah_seringSewa "
                        + "FROM detail_data_sewaan JOIN data_barang ON data_barang.id_barang = detail_data_sewaan.id_barang "
                        + "GROUP BY id_barang "
                        + "ORDER BY jumlah_seringSewa ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel dtmm = (DefaultTableModel) tbl_seringSewa.getModel();
            int no = 1;
            while(rs.next()){
                dtm.addRow(new Object[]{
                    no++,
                    rs.getString("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("jumlah_seringSewa")
                });
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error load data table sering sewa");
            System.out.println(e);
        }
    }
    
    public void tableKontrolBarang(){
        DefaultTableModel dtm = new DefaultTableModel() {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        dtm.addColumn("No");
        dtm.addColumn("Kode Barang");
        dtm.addColumn("Nama Barang");
        dtm.addColumn("Jumlah Stok");
        tbl_kontrolStok.setModel(dtm);
        
        try{
            Connection conn = koneksi.Connect.GetConnection();
            String sql = "SELECT id_barang, nama_barang, stok FROM data_barang ORDER BY stok ASC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel dtmm = (DefaultTableModel) tbl_kontrolStok.getModel();
            int no = 1;
            while(rs.next()){
                dtm.addRow(new Object[]{
                    no++,
                    rs.getString("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("stok")
                });
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error load data table sering sewa");
            System.out.println(e);
        }
    }
    
    public void tableKontrolBarang2(){
        DefaultTableModel dtm = new DefaultTableModel() {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        dtm.addColumn("No");
        dtm.addColumn("Kode Barang");
        dtm.addColumn("Nama Barang");
        dtm.addColumn("Jumlah Stok");
        tbl_kontrolStok.setModel(dtm);
        
        try{
            Connection conn = koneksi.Connect.GetConnection();
            String sql = "SELECT id_barang, nama_barang, stok FROM data_barang ORDER BY stok DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel dtmm = (DefaultTableModel) tbl_kontrolStok.getModel();
            int no = 1;
            while(rs.next()){
                dtm.addRow(new Object[]{
                    no++,
                    rs.getString("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("stok")
                });
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error load data table sering sewa");
            System.out.println(e);
        }
    }

    public void hari(){
        DashboardL db = new DashboardL();
        String date = db.date();
        String Pesanan_Berlangsung = String.valueOf(db.Pesanan_Berlangsung(db.dateStart(),db.dateEnd()));
        String Pesanan_Selesai = String.valueOf(db.Pesanan_Selesai(db.dateStart(), db.dateEnd()));
        String Total_Transaksi = String.valueOf(db.Total_Transaksi(db.dateStart(), db.dateEnd()));
        String Total_Pesanan = String.valueOf(db.Total_Pesanan(db.dateStart(), db.dateEnd()));
        lblDate.setText(date);
        txt_pesanan_lngsng.setText(Pesanan_Berlangsung);
        txt_pesanan_selesai.setText(Pesanan_Selesai);
        total_transaksi.setText(Total_Transaksi);
        txt_ttl_pesanan.setText(Total_Pesanan);
    }
    
    public void bulan(){
        DashboardL db = new DashboardL();
        String dateEnd = db.dateEnd();
        String dateStart = db.dateMonthAgo();
        String Pesanan_Berlangsung = String.valueOf(db.Pesanan_Berlangsung(dateStart, dateEnd));
        String Pesanan_Selesai = String.valueOf(db.Pesanan_Selesai(dateStart, dateEnd));
        String Total_Transaksi = String.valueOf(db.Total_Transaksi(dateStart, dateEnd));
        String Total_Pesanan = String.valueOf(db.Total_Pesanan(dateStart, dateEnd));
        lblDate.setText(dateStart+" Sampai " +db.date());
        txt_pesanan_lngsng.setText(Pesanan_Berlangsung);
        txt_pesanan_selesai.setText(Pesanan_Selesai);
        total_transaksi.setText(Total_Transaksi);
        txt_ttl_pesanan.setText(Total_Pesanan);
    }
        
    public void tanggal() {
        Date ys = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        lblDate.setText(s.format(ys));
    }

    public void showTime() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh-mm-ss");
                String tim = s.format(d);
                txtwaktu.setText(tim);
            }
        }).start();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        txt_pesanan_lngsng = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_pesanan_selesai = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        total_transaksi = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_ttl_pesanan = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtwaktu = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_kontrolStok = new javax.swing.JTable();
        combo_stok = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbFilter = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        combo_terlaris = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_seringSewa = new javax.swing.JTable();
        bg = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel5.setText("Pesanan Berlangsung");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        txt_pesanan_lngsng.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_pesanan_lngsng.setText("***");
        getContentPane().add(txt_pesanan_lngsng, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 50, -1));

        jLabel6.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel6.setText("Filter By");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, 30));

        txt_pesanan_selesai.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_pesanan_selesai.setText("***");
        getContentPane().add(txt_pesanan_selesai, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        jLabel7.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel7.setText("Total Transaksi");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, -1, -1));

        total_transaksi.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        total_transaksi.setText("***");
        getContentPane().add(total_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, -1, -1));

        jLabel8.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel8.setText("Total Pesanan");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, -1, -1));

        txt_ttl_pesanan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txt_ttl_pesanan.setText("***");
        getContentPane().add(txt_ttl_pesanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 80, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/icons8_pay_date_30px.png"))); // NOI18N
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 520, 30, 30));

        lblDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDate.setText("tanggal");
        getContentPane().add(lblDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 520, -1, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/icons8_time_30px.png"))); // NOI18N
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 520, 30, 30));

        txtwaktu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtwaktu.setText("waktu");
        getContentPane().add(txtwaktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 520, -1, 30));

        jLabel9.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel9.setText("Pesanan Selesai");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        jScrollPane2.setBackground(new java.awt.Color(247, 249, 251));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setOpaque(false);

        tbl_kontrolStok.setBackground(new java.awt.Color(247, 249, 251));
        tbl_kontrolStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_kontrolStok.setGridColor(new java.awt.Color(247, 249, 251));
        tbl_kontrolStok.setShowGrid(false);
        jScrollPane2.setViewportView(tbl_kontrolStok);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 270, 410, 240));

        combo_stok.setFont(new java.awt.Font("Outfit", 0, 12)); // NOI18N
        combo_stok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stok Sedikit", "Stok Banyak" }));
        combo_stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_stokActionPerformed(evt);
            }
        });
        getContentPane().add(combo_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 240, 140, -1));

        jLabel2.setFont(new java.awt.Font("Outfit Medium", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Informasi Stok Barang");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 260, 40));

        cmbFilter.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        cmbFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hari ini", "Sebulan Terakhir" }));
        cmbFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFilterActionPerformed(evt);
            }
        });
        getContentPane().add(cmbFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 140, 30));

        jLabel1.setFont(new java.awt.Font("Outfit Medium", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Penyewaan Barang Terlaris");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 270, 40));

        combo_terlaris.setFont(new java.awt.Font("Outfit", 0, 12)); // NOI18N
        combo_terlaris.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Terlaris", "Terendah" }));
        combo_terlaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_terlarisActionPerformed(evt);
            }
        });
        getContentPane().add(combo_terlaris, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 240, 140, -1));

        jScrollPane1.setBackground(new java.awt.Color(247, 249, 251));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setOpaque(false);

        tbl_seringSewa.setBackground(new java.awt.Color(247, 249, 251));
        tbl_seringSewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_seringSewa.setGridColor(new java.awt.Color(247, 249, 251));
        tbl_seringSewa.setShowGrid(false);
        jScrollPane1.setViewportView(tbl_seringSewa);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 410, 240));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/dashboard 3.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterActionPerformed
        if(cmbFilter.getSelectedIndex() == 0){
            hari();
        }else if(cmbFilter.getSelectedIndex() == 1){
            bulan();
        }
    }//GEN-LAST:event_cmbFilterActionPerformed

    private void combo_terlarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_terlarisActionPerformed
        // TODO add your handling code here:
        if(combo_terlaris.getSelectedIndex() == 0){
            tableSeringSewa();
            jLabel1.setText("Penyewaan Barang Terlaris");
        }else{
            tableSeringSewa2();
            jLabel1.setText("Penyewaan Barang Terendah");
        }
    }//GEN-LAST:event_combo_terlarisActionPerformed

    private void combo_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_stokActionPerformed
        // TODO add your handling code here:
        if(combo_stok.getSelectedIndex() == 0){
            tableKontrolBarang();
        }else{
            tableKontrolBarang2();
        }
    }//GEN-LAST:event_combo_stokActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JComboBox<String> cmbFilter;
    private javax.swing.JComboBox<String> combo_stok;
    private javax.swing.JComboBox<String> combo_terlaris;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDate;
    private javax.swing.JTable tbl_kontrolStok;
    private javax.swing.JTable tbl_seringSewa;
    private javax.swing.JLabel total_transaksi;
    private javax.swing.JLabel txt_pesanan_lngsng;
    private javax.swing.JLabel txt_pesanan_selesai;
    private javax.swing.JLabel txt_ttl_pesanan;
    private javax.swing.JLabel txtwaktu;
    // End of variables declaration//GEN-END:variables
}
