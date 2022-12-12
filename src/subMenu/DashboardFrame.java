/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subMenu;

import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import Logic.DashboardL;
/**
 *
 * @author perlengkapan
 */
public class DashboardFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form DashboardFrame
     */
    public String filter = "Hari";
    public DashboardFrame() {
        initComponents();
        
        //hilangin border 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bif = (BasicInternalFrameUI) this.getUI();
        bif.setNorthPane(null);
        
        tanggal();
        showTime();
    }
    
    public void load_data(){
        hari();
        if(filter.equals("hari")){
            hari();
        }else if(filter.equals("Bulan")){
            bulan();
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
        cmbFilter = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setBorder(null);
        setVisible(true);
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

        cmbFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "............", "Hari", "Bulan" }));
        cmbFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFilterActionPerformed(evt);
            }
        });
        getContentPane().add(cmbFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 140, 30));

        jLabel9.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel9.setText("Pesanan Selesai");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/dashboard.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterActionPerformed
        String flt = String.valueOf(cmbFilter.getSelectedItem());
        if(flt.equals("Hari")){
            filter = "Hari";
            load_data();
        }else if(flt.equals("Bulan")){
            filter = "Bulan";
            load_data();
        }
    }//GEN-LAST:event_cmbFilterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JComboBox<String> cmbFilter;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel total_transaksi;
    private javax.swing.JLabel txt_pesanan_lngsng;
    private javax.swing.JLabel txt_pesanan_selesai;
    private javax.swing.JLabel txt_ttl_pesanan;
    private javax.swing.JLabel txtwaktu;
    // End of variables declaration//GEN-END:variables
}
