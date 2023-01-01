/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subMenu;

import com.mysql.cj.jdbc.Driver;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import koneksi.Connect;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author perlengkapan
 */
public class LaporanFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form LaporanFrame
     */
    public LaporanFrame() {

        initComponents();
        loadTable();

        //hilangin border 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bif = (BasicInternalFrameUI) this.getUI();
        bif.setNorthPane(null);

        if (tbl_laporan.getRowCount() == 0) {
            msg_tblKosong.setText("Data pada bulan " + combo_filterBulan.getSelectedItem() + " tidak ada");
            msg_tblKosong.setForeground(Color.red);
        } else {
            msg_tblKosong.setForeground(new Color(242, 242, 242));
        }

        //set max year
        long waktu = System.currentTimeMillis();
        Date today = new Date(waktu);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int tahunNow = Integer.parseInt(sdf.format(today));
        pilihTahun.setEndYear(tahunNow);

        cekInputanStringOnly(txt_cari);

        cekTxtCari();
    }

    private void cekInputanStringOnly(JTextField textfield) {
        textfield.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = textfield.getText();
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    textfield.setEditable(false);
                } else if (ke.getKeyCode() == 8 || ke.getKeyCode() == 49) {
                    textfield.setEditable(true);
                } else {
                    textfield.setEditable(true);
                }
            }
        });
    }

    public void cekTxtCari() {
        if (tbl_laporan.getColumnCount() == 5) {
            txt_cari.setEditable(false);
            txt_cari.setEnabled(false);
        } else {
            txt_cari.setEditable(true);
            txt_cari.setEnabled(true);
        }
    }

    public void loadTable() {
        DefaultTableModel model = new DefaultTableModel(){
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        model.addColumn("No");
        model.addColumn("Hak Akses");
        model.addColumn("Total Transaksi");
        model.addColumn("Total Barang Disewa");
        model.addColumn("Total Pendapatan");

        try {
            int no = 1;
            int bulan = combo_filterBulan.getSelectedIndex() + 1;
            Connection conn = koneksi.Connect.GetConnection();
            String sqlTransaksi = "SELECT hak_akses, COUNT(laporan.id_laporan) AS total_transaksi, SUM(laporan.total) AS total_pendapatan, SUM(laporan.jumlah) AS totalBarang "
                    + "FROM laporan JOIN data_sewaan ON laporan.id_sewaan = data_sewaan.id_sewaan "
                    + "JOIN pengguna ON data_sewaan.id_pengguna = pengguna.id_pengguna "
                    + "WHERE MONTH(laporan.tanggal_transaksi) = " + bulan + " AND YEAR(laporan.tanggal_transaksi) = " + pilihTahun.getYear() + " "
                    + "GROUP BY hak_akses";
            PreparedStatement pst = conn.prepareStatement(sqlTransaksi);
            ResultSet res = pst.executeQuery();

            while (res.next()) {
                model.addRow(new Object[]{
                    no++,
                    res.getString("hak_akses"),
                    res.getString("total_transaksi"),
                    res.getString("totalBarang"),
                    res.getString("total_pendapatan")
                });
            }
            tbl_laporan.setModel(model);
            tbl_laporan.setRowHeight(30);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error load table");
            System.out.println(e.getMessage());
        }
    }

    public void loadTableAkses(String hak_akses, int bulan, int tahun) {
        try {
            DefaultTableModel model = new DefaultTableModel(){
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
            model.addColumn("No.");
            model.addColumn("Id Sewaan");
            model.addColumn("Nama Penyewa");
            model.addColumn("Nomor Identitas");
            model.addColumn("Deposit");
            model.addColumn("Total Barang");
            model.addColumn("Total Biaya");
            model.addColumn("Tanggal Pinjam");
            model.addColumn("Tanggal Kembali");
            model.addColumn("Tanggal Transaksi");

            int no = 1;
            Connection conn = koneksi.Connect.GetConnection();
            String sql = "SELECT data_sewaan.id_sewaan, data_sewaan.nama_penyewa, nomor_identitas, deposit, "
                    + "COUNT(id_barang) AS total_barang, total, tgl_pinjam, tgl_kembali, tgl_transaksi "
                    + "FROM data_sewaan JOIN detail_data_sewaan ON data_sewaan.id_sewaan = detail_data_sewaan.id_sewaan "
                    + "JOIN pengguna ON pengguna.id_pengguna = data_sewaan.id_pengguna "
                    + "WHERE hak_akses = '" + hak_akses + "' && MONTH(tgl_transaksi) = " + bulan + " && YEAR(tgl_transaksi) = " + tahun + " "
                    + "GROUP BY id_sewaan";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                model.addRow(new Object[]{
                    no++,
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7),
                    res.getString(8),
                    res.getString(9)
                });
            }
            tbl_laporan.setModel(model);
            tbl_laporan.setRowHeight(30);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error load table akses");
            System.out.println(e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        pilihTahun = new com.toedter.calendar.JYearChooser();
        combo_filterBulan = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        combo_filterAkses = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_laporan = new javax.swing.JTable();
        txt_cari = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        msg_tblKosong = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn_submit = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        bg = new javax.swing.JLabel();
        label_bulan = new javax.swing.JLabel();
        label_tahun = new javax.swing.JLabel();
        label_akses = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Urut Berdasarkan"));
        jPanel1.setForeground(new java.awt.Color(0, 0, 0));

        combo_filterBulan.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        combo_filterBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));

        jLabel1.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Bulan");

        jLabel2.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Tahun");

        jLabel3.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Hak Akses");

        combo_filterAkses.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        combo_filterAkses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih...", "Admin", "Kasir" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(combo_filterAkses, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(combo_filterBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(pilihTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(7, 7, 7)
                        .addComponent(combo_filterBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(combo_filterAkses, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pilihTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 480, 200));

        tbl_laporan.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        tbl_laporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Nama Penyewa", "Nama Karyawan", "Tanggal Transaksi", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_laporan.setRowHeight(30);
        tbl_laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_laporanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_laporan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 890, 210));

        txt_cari.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
        });
        getContentPane().add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, 190, 30));

        jLabel5.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        jLabel5.setText("Cari");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 250, -1, 30));

        msg_tblKosong.setFont(new java.awt.Font("Outfit", 0, 12)); // NOI18N
        msg_tblKosong.setForeground(new java.awt.Color(242, 242, 242));
        msg_tblKosong.setText("Data yang anda cari kosong / tidak ada");
        getContentPane().add(msg_tblKosong, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 230, 20));

        jPanel2.setBackground(new java.awt.Color(242, 242, 242));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses"));

        btn_submit.setBackground(new java.awt.Color(66, 139, 202));
        btn_submit.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        btn_submit.setForeground(new java.awt.Color(255, 255, 255));
        btn_submit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/checked.png"))); // NOI18N
        btn_submit.setText("Terapkan");
        btn_submit.setBorder(null);
        btn_submit.setIconTextGap(15);
        btn_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submitActionPerformed(evt);
            }
        });

        btn_refresh.setBackground(new java.awt.Color(92, 184, 92));
        btn_refresh.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        btn_refresh.setForeground(new java.awt.Color(255, 255, 255));
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/reload.png"))); // NOI18N
        btn_refresh.setText("Segarkan");
        btn_refresh.setBorder(null);
        btn_refresh.setIconTextGap(15);
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        btn_cetak.setBackground(new java.awt.Color(91, 192, 222));
        btn_cetak.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        btn_cetak.setForeground(new java.awt.Color(255, 255, 255));
        btn_cetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/printer.png"))); // NOI18N
        btn_cetak.setText("Cetak");
        btn_cetak.setBorder(null);
        btn_cetak.setIconTextGap(15);
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_submit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_submit, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 400, 80));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/background.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        label_bulan.setText("jLabel4");
        getContentPane().add(label_bulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 280, -1, -1));

        label_tahun.setText("jLabel6");
        getContentPane().add(label_tahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, -1, -1));

        label_akses.setText("jLabel7");
        getContentPane().add(label_akses, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 280, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submitActionPerformed
        int bulan = combo_filterBulan.getSelectedIndex() + 1;

        if (combo_filterAkses.getSelectedIndex() == 0) {
            loadTable();
        } else {
            loadTableAkses(combo_filterAkses.getSelectedItem().toString(), bulan, pilihTahun.getYear());
        }

        if (tbl_laporan.getRowCount() == 0) {
            msg_tblKosong.setText("Data pada bulan " + combo_filterBulan.getSelectedItem() + " tidak ada");
            msg_tblKosong.setForeground(Color.red);
        } else {
            msg_tblKosong.setForeground(new Color(242, 242, 242));
        }

        label_akses.setText(combo_filterAkses.getSelectedItem().toString());
        label_bulan.setText(String.valueOf(bulan));
        label_tahun.setText(String.valueOf(pilihTahun.getYear()));
        cekTxtCari();
    }//GEN-LAST:event_btn_submitActionPerformed

    private void tbl_laporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_laporanMouseClicked
        int i = tbl_laporan.getSelectedRow();
        TableModel tbl = tbl_laporan.getModel();

        String field1 = tbl.getValueAt(i, 1).toString();
        String field2 = tbl.getValueAt(i, 2).toString();
        String field3 = tbl.getValueAt(i, 3).toString();
        String field4 = tbl.getValueAt(i, 4).toString();
        String field5 = tbl.getValueAt(i, 5).toString();
        String field6 = tbl.getValueAt(i, 6).toString();

        try {
            String sql = "SELECT * FROM laporan WHERE laporan ='" + field1 + "'";
            Connection conn = koneksi.Connect.GetConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            rs.next();
        } catch (Exception e) {

        }

    }//GEN-LAST:event_tbl_laporanMouseClicked

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
        String cari = txt_cari.getText();
        try {

            DefaultTableModel model = new DefaultTableModel(){
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
            model.addColumn("No.");
            model.addColumn("Id Sewaan");
            model.addColumn("Nama Penyewa");
            model.addColumn("Nomor Identitas");
            model.addColumn("Deposit");
            model.addColumn("Total Barang");
            model.addColumn("Total Biaya");
            model.addColumn("Tanggal Pinjam");
            model.addColumn("Tanggal Kembali");
            model.addColumn("Tanggal Transaksi");

            int no = 1;
            Connection conn = koneksi.Connect.GetConnection();
            String sql = "SELECT data_sewaan.id_sewaan, data_sewaan.nama_penyewa, nomor_identitas, deposit, "
                    + "COUNT(id_barang) AS total_barang, total, tgl_pinjam, tgl_kembali, tgl_transaksi "
                    + "FROM data_sewaan JOIN detail_data_sewaan ON data_sewaan.id_sewaan = detail_data_sewaan.id_sewaan "
                    + "JOIN pengguna ON pengguna.id_pengguna = data_sewaan.id_pengguna "
                    + "WHERE hak_akses = '" + label_akses.getText() + "' && MONTH(tgl_transaksi) = " + label_bulan.getText() + " && YEAR(tgl_transaksi) = " + label_tahun.getText() + " && data_sewaan.nama_penyewa LIKE '%" + cari + "%'"
                    + "GROUP BY id_sewaan";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                model.addRow(new Object[]{
                    no++,
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7),
                    res.getString(8),
                    res.getString(9)
                });
            }
            tbl_laporan.setModel(model);
            tbl_laporan.setRowHeight(30);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error cari");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_txt_cariKeyReleased

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        //set max year
        long waktu = System.currentTimeMillis();
        Date today = new Date(waktu);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int tahunNow = Integer.parseInt(sdf.format(today));
        pilihTahun.setEndYear(tahunNow);

        combo_filterBulan.setSelectedIndex(0);
        combo_filterAkses.setSelectedIndex(0);
        pilihTahun.setYear(tahunNow);
        msg_tblKosong.setForeground(new Color(242, 242, 242));
        loadTable();
        cekTxtCari();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        if (tbl_laporan.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang bisa dicetak, silahkan cari datanya terlebih dahulu");
        } else if (tbl_laporan.getColumnCount() == 5) {
            String bulan = label_bulan.getText();
            String tahun = label_tahun.getText();

            try {
                Connection conn = koneksi.Connect.GetConnection();
                Statement stm = conn.createStatement();

                String report = ("C:\\Users\\perlengkapan\\Documents\\KULIAH\\Project Tugas Akhir\\Awan-adventure-FIX\\src\\subMenu\\nota\\notaLaporan.jrxml");
                HashMap hash = new HashMap();
                hash.put("bulan", bulan);
                hash.put("tahun", tahun);
                JasperReport jasper = JasperCompileManager.compileReport(report);
                JasperPrint jasperP = JasperFillManager.fillReport(jasper, hash, conn);
                JasperViewer.viewReport(jasperP, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error iReport");
            }
        } else if (tbl_laporan.getColumnCount() == 9) {
            String bulan = label_bulan.getText();
            String tahun = label_tahun.getText();
            String akses = label_akses.getText();

            try {
                Connection conn = koneksi.Connect.GetConnection();
                Statement stm = conn.createStatement();

                String report = ("C:\\Users\\perlengkapan\\Documents\\KULIAH\\Project Tugas Akhir\\Awan-adventure-FIX\\src\\subMenu\\nota\\notaLaporanAkses.jrxml");
                HashMap hash = new HashMap();
                hash.put("bulan", bulan);
                hash.put("tahun", tahun);
                hash.put("akses", akses);
                JasperReport jasper = JasperCompileManager.compileReport(report);
                JasperPrint jasperP = JasperFillManager.fillReport(jasper, hash, conn);
                JasperViewer.viewReport(jasperP, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error iReport");
            }
        }
    }//GEN-LAST:event_btn_cetakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_submit;
    private javax.swing.JComboBox<String> combo_filterAkses;
    private javax.swing.JComboBox<String> combo_filterBulan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_akses;
    private javax.swing.JLabel label_bulan;
    private javax.swing.JLabel label_tahun;
    private javax.swing.JLabel msg_tblKosong;
    private com.toedter.calendar.JYearChooser pilihTahun;
    private javax.swing.JTable tbl_laporan;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
}
