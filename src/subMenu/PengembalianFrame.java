/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subMenu;

import java.awt.Color;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author perlengkapan
 */
public class PengembalianFrame extends javax.swing.JInternalFrame {

    ArrayList jenisKerusakan = new ArrayList();
    String TotalJenisKerusakan = "0";
    String TotalDenda = "0";

    /**
     * Creates new form PengembalianFrame
     */
    public PengembalianFrame() {
        initComponents();

        //hilangin border 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bif = (BasicInternalFrameUI) this.getUI();
        bif.setNorthPane(null);

        updateComboPenyewa();
        updateComboKeterlambatan();
        clear();
        loadTable();

    }

    public void getIdPengguna(String id) {
        id_pengguna.setText(id);
    }

    public void loadTable() {
        DefaultTableModel dtm = new DefaultTableModel() {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        dtm.addColumn("No");
        dtm.addColumn("Barang Rusak");
        dtm.addColumn("Jumlah");
        dtm.addColumn("Jenis Kerusakan");
        dtm.addColumn("Sub Total Denda");
        tbl_pengembalian.setModel(dtm);
    }

    public void updateComboPenyewa() {
        try {
            String sqlSewaan = "select * from data_sewaan where status = 'proses'";
            Connection conn = koneksi.Connect.GetConnection();
            PreparedStatement pst = conn.prepareStatement(sqlSewaan);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                combo_penyewa.addItem("" + rs.getString("id_sewaan") + " - " + rs.getString("nama_penyewa") + "");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error combo penyewa");
            System.out.println(e.getMessage());
        }
    }

    public void updateComboKeterlambatan() {
        try {
            String sqlTerlambat = "select * from keterlambatan";
            Connection conn = koneksi.Connect.GetConnection();
            PreparedStatement pst = conn.prepareStatement(sqlTerlambat);
            ResultSet rs = pst.executeQuery();
            combo_barangRusak.removeAllItems();
            combo_barangRusak.addItem("Tidak terlambat");
            while (rs.next()) {
                combo_keterlambatan.addItem("" + rs.getString("jumlah_hari") + " Hari - " + rs.getString("biaya_terlambat") + "");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error combo keterlambatan");
            System.out.println(e.getMessage());
        }
    }

    public void updateComboBarangRusak() {
        try {
            String sqlRusak = "SELECT * FROM detail_data_sewaan JOIN data_barang ON detail_data_sewaan.id_barang = data_barang.id_barang WHERE id_sewaan = '" + txt_idSewaan.getText() + "'; ";
            Connection conn = koneksi.Connect.GetConnection();
            PreparedStatement pst = conn.prepareStatement(sqlRusak);
            ResultSet rs = pst.executeQuery();
            combo_barangRusak.removeAllItems();
            combo_barangRusak.addItem("Tidak ada");
            while (rs.next()) {
                combo_barangRusak.addItem("" + rs.getString("nama_barang") + "");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error combo barang rusak");
            System.out.println(e.getMessage());
        }
    }

    public void updateComboJumlah() {
        try {
            String namaBarang;
            if (combo_barangRusak.getSelectedIndex() == 0) {
                namaBarang = "";
            } else {
                namaBarang = combo_barangRusak.getSelectedItem().toString();
            }

            Connection conn = koneksi.Connect.GetConnection();

            //ambil jumlah
            String ambilJumlah = "SELECT id_sewaan, data_barang.id_barang, nama_barang,jumlah FROM detail_data_sewaan "
                    + "JOIN data_barang ON detail_data_sewaan.id_barang = data_barang.id_barang "
                    + "WHERE nama_barang = '" + namaBarang + "'";
            PreparedStatement ps = conn.prepareStatement(ambilJumlah);
            ResultSet res = ps.executeQuery();
            res.next();

            //combo jumlah
            String sqlJumlah = "select * from detail_data_sewaan where id_sewaan = '" + txt_idSewaan.getText() + "' && id_barang = '" + res.getString("id_barang") + "'";
            PreparedStatement pst = conn.prepareStatement(sqlJumlah);
            ResultSet rs = pst.executeQuery();
            combo_jumlah.removeAllItems();
            combo_jumlah.addItem("Pilih...");
            if (rs.next()) {
                int jumlah = Integer.parseInt(rs.getString("jumlah").toString());
                for (int i = 1; i <= jumlah; i++) {
                    combo_jumlah.addItem(Integer.toString(i));
                }
            }
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error combo jumlah");
            System.out.println(e.getMessage());
        }
    }

    public void clear() {
        combo_penyewa.setSelectedIndex(0);
        combo_keterlambatan.setSelectedIndex(0);
        combo_barangRusak.setSelectedIndex(0);
        combo_penyewa.setEnabled(true);
        combo_keterlambatan.setEnabled(false);
        combo_barangRusak.setEnabled(false);
        loadTable();
        btn_tambah.setEnabled(true);
        btn_batal.setEnabled(false);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
        txt_idSewaan.setText("-");
        txt_nama.setText("-");
        txt_hp.setText("-");
        txt_pinjam.setText("-");
        txt_kembali.setText("-");
        txt_transaksi.setText("-");
        txt_total.setText("-");
        txt_bayar.setText("");
        label_blmCukup.setForeground(new Color(242, 242, 242));
        txt_kembalian.setText("");
        txt_alamat.setText("-");
        txt_identitas.setText("-");
        txt_deposit.setText("-");
        combo_jumlah.setEnabled(false);
        combo_jumlah.setSelectedIndex(0);
        chckBox_patah.setSelected(false);
        chckBox_sobek.setSelected(false);
        chckBox_resleting.setSelected(false);
        chckBox_hilang.setSelected(false);
        chckBox_hilang.setEnabled(false);
        chckBox_patah.setEnabled(false);
        chckBox_resleting.setEnabled(false);
        chckBox_sobek.setEnabled(false);
        txt_subDenda.setText("-");
        this.jenisKerusakan.clear();
        this.TotalJenisKerusakan = "0";
    }

//    public void cekTable() {
//        int jumlahRow = tbl_pengembalian.getModel().getRowCount();
//        ArrayList isiTable = new ArrayList();
//        for (int i = 0; i < jumlahRow; i++) {
//            isiTable.add(tbl_pengembalian.getModel().getValueAt(i, 1));
//        }
//
//        boolean isian = isiTable.contains("Barang2");
//        if (isian) {
//            JOptionPane.showMessageDialog(null, "data sama tidak boleh");
//        } else {
//            System.out.println("tadek");
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        combo_jumlah = new javax.swing.JComboBox<>();
        combo_penyewa = new javax.swing.JComboBox<>();
        combo_keterlambatan = new javax.swing.JComboBox<>();
        combo_barangRusak = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pengembalian = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btn_tambah = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        txt_bayar = new javax.swing.JTextField();
        txt_total = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_kembalian = new javax.swing.JLabel();
        label_blmCukup = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_idSewaan = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JLabel();
        txt_hp = new javax.swing.JLabel();
        txt_pinjam = new javax.swing.JLabel();
        txt_kembali = new javax.swing.JLabel();
        txt_transaksi = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_alamat = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_identitas = new javax.swing.JLabel();
        txt_deposit = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        txt_terlambat = new javax.swing.JLabel();
        btn_proses = new javax.swing.JButton();
        chckBox_resleting = new javax.swing.JCheckBox();
        chckBox_patah = new javax.swing.JCheckBox();
        chckBox_hilang = new javax.swing.JCheckBox();
        chckBox_sobek = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        txt_subDenda = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();
        id_pengguna = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 51));
        jLabel6.setText("Rusak & Terlambat");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 470, -1, -1));

        jLabel8.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel8.setText("Keterlambatan");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 30));

        jLabel20.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel20.setText("Jumlah");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, 30));

        jLabel9.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel9.setText("Barang Rusak");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 30));

        jLabel11.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel11.setText("Jenis Kerusakan");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 30));

        jLabel12.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel12.setText("Total Denda    : Rp");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 450, -1, -1));

        combo_jumlah.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        combo_jumlah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih..." }));
        getContentPane().add(combo_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 210, 30));

        combo_penyewa.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        combo_penyewa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih..." }));
        combo_penyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_penyewaActionPerformed(evt);
            }
        });
        getContentPane().add(combo_penyewa, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 210, 30));

        combo_keterlambatan.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        combo_keterlambatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak terlambat" }));
        combo_keterlambatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_keterlambatanActionPerformed(evt);
            }
        });
        getContentPane().add(combo_keterlambatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 210, 30));

        combo_barangRusak.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        combo_barangRusak.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak ada" }));
        combo_barangRusak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_barangRusakActionPerformed(evt);
            }
        });
        getContentPane().add(combo_barangRusak, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 210, 30));

        jLabel13.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        jLabel13.setText("Penyewa");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 30));

        tbl_pengembalian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Title 2", "Title 3"
            }
        ));
        tbl_pengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pengembalianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_pengembalian);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 560, 130));

        jPanel4.setBackground(new java.awt.Color(242, 242, 242));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses"));

        btn_tambah.setBackground(new java.awt.Color(92, 184, 92));
        btn_tambah.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("Tambah");
        btn_tambah.setBorder(null);
        btn_tambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_edit.setBackground(new java.awt.Color(23, 162, 184));
        btn_edit.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setText("Edit");
        btn_edit.setBorder(null);
        btn_edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_hapus.setBackground(new java.awt.Color(220, 53, 69));
        btn_hapus.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("Hapus");
        btn_hapus.setBorder(null);
        btn_hapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

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

        btn_batal.setBackground(new java.awt.Color(108, 117, 125));
        btn_batal.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setText("Batal ");
        btn_batal.setBorder(null);
        btn_batal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_edit, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 360, 110));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, -10, 20, 560));

        jLabel17.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel17.setText("Bayar                   : Rp");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 420, -1, -1));

        txt_bayar.setBackground(new java.awt.Color(255, 255, 255));
        txt_bayar.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        txt_bayar.setForeground(new java.awt.Color(0, 0, 0));
        txt_bayar.setBorder(null);
        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
        });
        getContentPane().add(txt_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 420, 120, 20));

        txt_total.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        txt_total.setText("-");
        getContentPane().add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 450, 120, 20));

        jLabel14.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel14.setText("Kembalian       : Rp");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 470, -1, -1));

        txt_kembalian.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        getContentPane().add(txt_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 470, 120, 20));

        label_blmCukup.setFont(new java.awt.Font("Outfit Light", 0, 12)); // NOI18N
        label_blmCukup.setForeground(new java.awt.Color(242, 242, 242));
        label_blmCukup.setText("Uang tidak cukup");
        getContentPane().add(label_blmCukup, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 440, 120, 20));

        jLabel1.setFont(new java.awt.Font("Outfit Medium", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Tanggal Transaksi             :");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, -1, -1));

        jLabel2.setFont(new java.awt.Font("Outfit Medium", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Ubah Penyewa");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Outfit Medium", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("ID Sewaan                                :");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 180, -1));

        txt_idSewaan.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_idSewaan.setForeground(new java.awt.Color(0, 0, 0));
        txt_idSewaan.setText("-");
        getContentPane().add(txt_idSewaan, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, 170, 21));

        jLabel4.setFont(new java.awt.Font("Outfit Medium", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nama                                           :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Outfit Medium", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("No HP                                           :");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Outfit Medium", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Tanggal Peminjaman      :");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, -1, -1));

        txt_nama.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_nama.setForeground(new java.awt.Color(0, 0, 0));
        txt_nama.setText("-");
        getContentPane().add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, 370, 21));

        txt_hp.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_hp.setForeground(new java.awt.Color(0, 0, 0));
        txt_hp.setText("-");
        getContentPane().add(txt_hp, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, 360, 21));

        txt_pinjam.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_pinjam.setForeground(new java.awt.Color(0, 0, 0));
        txt_pinjam.setText("-");
        getContentPane().add(txt_pinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 50, -1, 21));

        txt_kembali.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_kembali.setForeground(new java.awt.Color(0, 0, 0));
        txt_kembali.setText("-");
        getContentPane().add(txt_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, -1, 21));

        txt_transaksi.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_transaksi.setForeground(new java.awt.Color(0, 0, 0));
        txt_transaksi.setText("-");
        getContentPane().add(txt_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, 360, 21));

        jLabel10.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("s.d.");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, -1, -1));

        jLabel15.setFont(new java.awt.Font("Outfit Medium", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Alamat                                        :");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, -1, -1));

        txt_alamat.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_alamat.setForeground(new java.awt.Color(0, 0, 0));
        txt_alamat.setText("-");
        getContentPane().add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, 370, 20));

        jLabel16.setFont(new java.awt.Font("Outfit Medium", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Nomor Identitas                   :");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, -1, -1));

        jLabel18.setFont(new java.awt.Font("Outfit Medium", 0, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Deposit                                       :");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 231, -1, 20));

        txt_identitas.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_identitas.setForeground(new java.awt.Color(0, 0, 0));
        txt_identitas.setText("-");
        getContentPane().add(txt_identitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 200, -1, -1));

        txt_deposit.setFont(new java.awt.Font("Outfit Light", 0, 16)); // NOI18N
        txt_deposit.setForeground(new java.awt.Color(0, 0, 0));
        txt_deposit.setText("-");
        getContentPane().add(txt_deposit, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 230, -1, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 600, 10));

        jLabel19.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel19.setText("Terlambat        : ");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 420, -1, -1));

        txt_terlambat.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        txt_terlambat.setText("-");
        getContentPane().add(txt_terlambat, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 420, 150, 21));

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

        chckBox_resleting.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        chckBox_resleting.setForeground(new java.awt.Color(0, 0, 0));
        chckBox_resleting.setText("Resleting rusak");
        chckBox_resleting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckBox_resletingActionPerformed(evt);
            }
        });
        getContentPane().add(chckBox_resleting, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 130, 30));

        chckBox_patah.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        chckBox_patah.setForeground(new java.awt.Color(0, 0, 0));
        chckBox_patah.setText("Patah");
        chckBox_patah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckBox_patahActionPerformed(evt);
            }
        });
        getContentPane().add(chckBox_patah, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 70, 30));

        chckBox_hilang.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        chckBox_hilang.setForeground(new java.awt.Color(0, 0, 0));
        chckBox_hilang.setText("Hilang");
        chckBox_hilang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckBox_hilangActionPerformed(evt);
            }
        });
        getContentPane().add(chckBox_hilang, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, -1, 30));

        chckBox_sobek.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        chckBox_sobek.setForeground(new java.awt.Color(0, 0, 0));
        chckBox_sobek.setText("Sobek");
        chckBox_sobek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckBox_sobekActionPerformed(evt);
            }
        });
        getContentPane().add(chckBox_sobek, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 70, 30));

        jLabel21.setForeground(new java.awt.Color(255, 0, 0));
        jLabel21.setText("sub denda : Rp");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 346, -1, 20));

        txt_subDenda.setForeground(new java.awt.Color(255, 0, 0));
        txt_subDenda.setText("00000000000");
        getContentPane().add(txt_subDenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 346, 120, 20));

        bg.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/background.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        id_pengguna.setText("jLabel20");
        getContentPane().add(id_pengguna, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 490, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        //get selected row
        int i = tbl_pengembalian.getSelectedRow();
        String field2 = tbl_pengembalian.getValueAt(i, 1).toString();
        String field3 = tbl_pengembalian.getValueAt(i, 2).toString();
        String field5 = tbl_pengembalian.getValueAt(i, 4).toString();
        int biayaRusak = Integer.parseInt(field5);

        //delete row selected
        if (chckBox_hilang.isSelected() == false && chckBox_patah.isSelected() == false && chckBox_resleting.isSelected() == false && chckBox_sobek.isSelected() == false) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih jenis kerusakan terlebih dahulu");
        } else {
            int confirmEdit = JOptionPane.showConfirmDialog(null, "Yakin ingin mengubah dengan data ini?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmEdit == JOptionPane.YES_OPTION) {
                int jenisKerusakan = Integer.parseInt(txt_subDenda.getText());

                //update biaya
                int totalTerbaru = Integer.parseInt(txt_total.getText());
                int hitungTotal = totalTerbaru - biayaRusak;
                int totalDendaTerbaru = hitungTotal + jenisKerusakan;
                String hitungTotalFix = String.valueOf(totalDendaTerbaru);
                txt_total.setText(hitungTotalFix);

                DefaultTableModel dtm = (DefaultTableModel) tbl_pengembalian.getModel();
                dtm.removeRow(i);
                int no = tbl_pengembalian.getRowCount() + 1;
                String rusak = String.join(", ", this.jenisKerusakan);
                dtm.addRow(new Object[]{
                    no++,
                    field2,
                    field3,
                    rusak,
                    txt_subDenda.getText()
                });

                combo_barangRusak.setSelectedIndex(0);
                combo_barangRusak.setEnabled(true);
                combo_jumlah.setEnabled(false);
                combo_jumlah.setSelectedIndex(0);
                chckBox_patah.setSelected(false);
                chckBox_sobek.setSelected(false);
                chckBox_resleting.setSelected(false);
                chckBox_hilang.setSelected(false);
                chckBox_hilang.setEnabled(false);
                chckBox_patah.setEnabled(false);
                chckBox_resleting.setEnabled(false);
                chckBox_sobek.setEnabled(false);
                txt_subDenda.setText("-");
                btn_tambah.setEnabled(true);
                btn_edit.setEnabled(false);
                btn_hapus.setEnabled(false);
                btn_batal.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        // TODO add your handling code here:
        int clear = JOptionPane.showConfirmDialog(null, "Yakin ingin membersihkan seluruh isian ? (termasuk pada table pilihan barang)", "Clear", JOptionPane.YES_NO_OPTION);
        if (clear == JOptionPane.YES_OPTION) {
            clear();
        }
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        btn_tambah.setEnabled(true);
        btn_edit.setEnabled(false);
        btn_hapus.setEnabled(false);
        btn_clear.setEnabled(true);
        btn_batal.setEnabled(false);
        combo_keterlambatan.setSelectedIndex(0);
        combo_barangRusak.setSelectedIndex(0);
        combo_penyewa.setEnabled(false);
        combo_keterlambatan.setEnabled(true);
        combo_barangRusak.setEnabled(true);
        combo_jumlah.setEnabled(false);
        combo_jumlah.setSelectedIndex(0);
        chckBox_patah.setSelected(false);
        chckBox_sobek.setSelected(false);
        chckBox_resleting.setSelected(false);
        chckBox_hilang.setSelected(false);
        chckBox_hilang.setEnabled(false);
        chckBox_patah.setEnabled(false);
        chckBox_resleting.setEnabled(false);
        chckBox_sobek.setEnabled(false);
        tbl_pengembalian.clearSelection();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void combo_penyewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_penyewaActionPerformed
        // TODO add your handling code here:
        if (combo_penyewa.getSelectedIndex() == 0) {
            combo_keterlambatan.setEnabled(false);
            combo_barangRusak.setEnabled(false);
            combo_keterlambatan.setSelectedIndex(0);
            combo_barangRusak.setSelectedIndex(0);
            chckBox_patah.setSelected(false);
            chckBox_sobek.setSelected(false);
            chckBox_resleting.setSelected(false);
            chckBox_hilang.setSelected(false);
            chckBox_hilang.setEnabled(false);
            chckBox_patah.setEnabled(false);
            chckBox_resleting.setEnabled(false);
            chckBox_sobek.setEnabled(false);
        } else {
            String comboPenyewa = combo_penyewa.getSelectedItem().toString();
            String idPenyewa = comboPenyewa.substring(0, 7);
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sqlSewaan = "select * from data_sewaan where id_sewaan='" + idPenyewa + "'";
                PreparedStatement pst = conn.prepareStatement(sqlSewaan);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    txt_idSewaan.setText(rs.getString("id_sewaan"));
                    txt_nama.setText(rs.getString("nama_penyewa"));
                    txt_hp.setText(rs.getString("noHp"));
                    txt_pinjam.setText(rs.getString("tgl_pinjam"));
                    txt_kembali.setText(rs.getString("tgl_kembali"));
                    txt_transaksi.setText(rs.getString("tgl_transaksi"));
                    txt_alamat.setText(rs.getString("alamat_penyewa"));
                    if (rs.getString("nomor_identitas").equals("")) {
                        txt_identitas.setText("-");
                        txt_deposit.setText(rs.getString("deposit"));
                    } else {
                        txt_identitas.setText(rs.getString("nomor_identitas"));
                        txt_deposit.setText("-");
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error ambil data_sewaan");
                System.out.println(e.getMessage());
            }

            updateComboBarangRusak();
            combo_penyewa.setEnabled(false);
            combo_keterlambatan.setEnabled(true);
            combo_barangRusak.setEnabled(true);
            chckBox_patah.setSelected(false);
            chckBox_sobek.setSelected(false);
            chckBox_resleting.setSelected(false);
            chckBox_hilang.setSelected(false);
            chckBox_hilang.setEnabled(false);
            chckBox_patah.setEnabled(false);
            chckBox_resleting.setEnabled(false);
            chckBox_sobek.setEnabled(false);

        }
    }//GEN-LAST:event_combo_penyewaActionPerformed

    private void combo_barangRusakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_barangRusakActionPerformed
        // TODO add your handling code here:
        if (combo_barangRusak.getSelectedIndex() == 0) {
            chckBox_patah.setSelected(false);
            chckBox_sobek.setSelected(false);
            chckBox_resleting.setSelected(false);
            chckBox_hilang.setSelected(false);
            chckBox_hilang.setEnabled(false);
            chckBox_patah.setEnabled(false);
            chckBox_resleting.setEnabled(false);
            chckBox_sobek.setEnabled(false);
            combo_jumlah.setEnabled(false);
        } else {
            updateComboJumlah();
            combo_jumlah.setEnabled(true);
            chckBox_patah.setSelected(false);
            chckBox_sobek.setSelected(false);
            chckBox_resleting.setSelected(false);
            chckBox_hilang.setSelected(false);
            chckBox_hilang.setEnabled(true);
            chckBox_patah.setEnabled(true);
            chckBox_resleting.setEnabled(true);
            chckBox_sobek.setEnabled(true);
        }
    }//GEN-LAST:event_combo_barangRusakActionPerformed

    private void combo_keterlambatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_keterlambatanActionPerformed
        // TODO add your handling code here:

        if (combo_keterlambatan.getSelectedIndex() == 0) {
            txt_terlambat.setText("-");
            txt_total.setText(this.TotalDenda);
        } else {
            int denda = 0;
            if (txt_total.getText().equals("-")) {
                denda = 0;
            } else {
                denda = Integer.parseInt(this.TotalDenda);
            }

            String terlambat = (String) combo_keterlambatan.getSelectedItem();
            String hari = terlambat.substring(0, 1);
            try {
                String sqlketerlambatan = "select * from keterlambatan where jumlah_hari = " + hari + "";
                Connection conn = koneksi.Connect.GetConnection();
                PreparedStatement pst = conn.prepareStatement(sqlketerlambatan);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    if (tbl_pengembalian.getRowCount() == 0) {
                        int biaya = Integer.parseInt(rs.getString("biaya_terlambat"));
                        String totalFix = String.valueOf(biaya);
                        txt_total.setText(totalFix);
                    } else {
                        int biaya = Integer.parseInt(rs.getString("biaya_terlambat"));
                        int total = denda + biaya;
                        String totalFix = String.valueOf(total);
                        txt_total.setText(totalFix);
                    }

                }
            } catch (Exception e) {

            }
            txt_terlambat.setText(combo_keterlambatan.getSelectedItem().toString());

        }
    }//GEN-LAST:event_combo_keterlambatanActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // cek jumlah row pada table dan memasukkannya pada arrayList
        int jumlahRow = tbl_pengembalian.getModel().getRowCount();
        ArrayList namaBarang = new ArrayList();
        ArrayList kerusakan = new ArrayList();
        for (int i = 0; i < jumlahRow; i++) {
            namaBarang.add(tbl_pengembalian.getModel().getValueAt(i, 1));
        }

        //cek apakah data pada combo barangrusak sama dengan data yang ada pada table
        boolean isianNama = namaBarang.contains(combo_barangRusak.getSelectedItem().toString());

        int subDenda = Integer.parseInt(txt_subDenda.getText());
        int jumlahRusak = Integer.parseInt(combo_jumlah.getSelectedItem().toString());
        int subTotalDenda = subDenda * jumlahRusak;

        if (combo_penyewa.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih penyewa terlebih dahulu");
        } else if (combo_barangRusak.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Data barang belum dipilih");
        } else if (combo_jumlah.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih jumlah barang terlebih dahulu");
        } else if (chckBox_hilang.isSelected() == false && chckBox_patah.isSelected() == false && chckBox_resleting.isSelected() == false && chckBox_sobek.isSelected() == false) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih jenis kerusakan terlebih dahulu");
        } else if (isianNama == true) {
            JOptionPane.showMessageDialog(null, "Data barang yang rusak sudah ada pada table");
        } else {
            String barang = combo_barangRusak.getSelectedItem().toString();
            String jumlah = combo_jumlah.getSelectedItem().toString();
            String rusak = String.join(", ", this.jenisKerusakan);

            int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menambahkan data kerusakan ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int no = 1;

                DefaultTableModel dtm = (DefaultTableModel) tbl_pengembalian.getModel();
                dtm.addRow(new Object[]{
                    no++,
                    barang,
                    jumlah,
                    rusak,
                    subTotalDenda
                });

                //ambil total gettext
                int totalDenda;
                if (txt_total.getText().equals("-")) {
                    totalDenda = 0;
                } else {
                    totalDenda = Integer.parseInt(txt_total.getText().toString());
                }

                //add biaya denda kerusakan
                int biayaKerusakan = subTotalDenda;
                //convert Global totaldenda ke int
                int TotalDenda = Integer.parseInt(this.TotalDenda);
                int HitungTotalDenda = TotalDenda + biayaKerusakan;
                this.TotalDenda = String.valueOf(HitungTotalDenda);

                int hasilAkhir = totalDenda + biayaKerusakan;
                String hasilAkhirFix = String.valueOf(hasilAkhir);
                txt_total.setText(hasilAkhirFix);
                
                combo_barangRusak.setSelectedIndex(0);
                combo_jumlah.setSelectedIndex(0);
                combo_jumlah.setEnabled(false);
                chckBox_patah.setSelected(false);
                chckBox_sobek.setSelected(false);
                chckBox_resleting.setSelected(false);
                chckBox_hilang.setSelected(false);
                chckBox_hilang.setEnabled(false);
                chckBox_patah.setEnabled(false);
                chckBox_resleting.setEnabled(false);
                chckBox_sobek.setEnabled(false);
                txt_subDenda.setText("-");
                this.jenisKerusakan.clear();
                this.TotalJenisKerusakan = "0";
                label_blmCukup.setForeground(Color.red);
            }
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin mengganti penyewa ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            clear();
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        // TODO add your handling code here:
        int total = Integer.parseInt(txt_total.getText().toString());
        int bayar = Integer.parseInt(txt_bayar.getText().toString());
        if (bayar < total) {
            label_blmCukup.setForeground(Color.red);
            txt_kembalian.setText("");
        } else if (bayar > total) {
            label_blmCukup.setForeground(new Color(242, 242, 242));
            int kembalian = bayar - total;
            String kembalianfix = String.valueOf(kembalian);
            txt_kembalian.setText(kembalianfix);
        } else {
            label_blmCukup.setForeground(new Color(242, 242, 242));
            txt_kembalian.setText("-");
        }
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void tbl_pengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pengembalianMouseClicked
        // TODO add your handling code here:
        this.TotalJenisKerusakan = "0";
        this.jenisKerusakan.clear();
        
        int i = tbl_pengembalian.getSelectedRow();
        TableModel tbl = tbl_pengembalian.getModel();

        String field2 = tbl.getValueAt(i, 1).toString();
        String field3 = tbl.getValueAt(i, 2).toString();
        String field4 = tbl.getValueAt(i, 3).toString();

        combo_barangRusak.setSelectedItem(field2);
        combo_jumlah.setSelectedItem(field3);
        List<String> itemsJenisKerusakan = Stream.of(field4.split(",")).map(String::trim).collect(Collectors.toList());
        for (int j = 0; j < itemsJenisKerusakan.size(); j++) {
            String isian = itemsJenisKerusakan.get(j);
            if (isian.equals("Hilang")) {
                chckBox_hilang.setSelected(true);
                this.jenisKerusakan.add(chckBox_hilang.getText());
                try {
                    Connection conn = koneksi.Connect.GetConnection();
                    String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_hilang.getText() + "'";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                        int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                        biayaTerbaru = biayaTerbaru + biayaDb;
                        String biaya = String.valueOf(biayaTerbaru);
                        this.TotalJenisKerusakan = biaya;
                        txt_subDenda.setText(this.TotalJenisKerusakan);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (isian.equals("Patah")) {
                chckBox_patah.setSelected(true);
                this.jenisKerusakan.add(chckBox_patah.getText());
                try {
                    Connection conn = koneksi.Connect.GetConnection();
                    String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_patah.getText() + "'";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                        int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                        biayaTerbaru = biayaTerbaru + biayaDb;
                        String biaya = String.valueOf(biayaTerbaru);
                        this.TotalJenisKerusakan = biaya;
                        txt_subDenda.setText(this.TotalJenisKerusakan);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (isian.equals("Resleting rusak")) {
                chckBox_resleting.setSelected(true);
                this.jenisKerusakan.add(chckBox_resleting.getText());
                try {
                    Connection conn = koneksi.Connect.GetConnection();
                    String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_resleting.getText() + "'";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                        int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                        biayaTerbaru = biayaTerbaru + biayaDb;
                        String biaya = String.valueOf(biayaTerbaru);
                        this.TotalJenisKerusakan = biaya;
                        txt_subDenda.setText(this.TotalJenisKerusakan);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                chckBox_sobek.setSelected(true);
                this.jenisKerusakan.add(chckBox_sobek.getText());
                try {
                    Connection conn = koneksi.Connect.GetConnection();
                    String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_sobek.getText() + "'";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                        int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                        biayaTerbaru = biayaTerbaru + biayaDb;
                        String biaya = String.valueOf(biayaTerbaru);
                        this.TotalJenisKerusakan = biaya;
                        txt_subDenda.setText(this.TotalJenisKerusakan);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        combo_barangRusak.setEnabled(false);
        btn_tambah.setEnabled(false);
        btn_edit.setEnabled(true);
        btn_hapus.setEnabled(true);
        btn_batal.setEnabled(true);
    }//GEN-LAST:event_tbl_pengembalianMouseClicked

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        int i = tbl_pengembalian.getSelectedRow();
        String field2 = tbl_pengembalian.getValueAt(i, 1).toString();
        String field3 = tbl_pengembalian.getValueAt(i, 2).toString();
        String field5 = tbl_pengembalian.getValueAt(i, 4).toString();
        int biayaRusak = Integer.parseInt(field5);

        //delete row selected
            int confirmEdit = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmEdit == JOptionPane.YES_OPTION) {

                //update biaya
                int totalTerbaru = Integer.parseInt(txt_total.getText());
                int hitungTotal = totalTerbaru - biayaRusak;
                String hitungTotalFix = String.valueOf(hitungTotal);
                txt_total.setText(hitungTotalFix);

                DefaultTableModel dtm = (DefaultTableModel) tbl_pengembalian.getModel();
                dtm.removeRow(i);

                combo_barangRusak.setSelectedIndex(0);
                combo_barangRusak.setEnabled(true);
                chckBox_patah.setSelected(false);
                chckBox_sobek.setSelected(false);
                chckBox_resleting.setSelected(false);
                chckBox_hilang.setSelected(false);
                chckBox_hilang.setEnabled(false);
                chckBox_patah.setEnabled(false);
                chckBox_resleting.setEnabled(false);
                chckBox_sobek.setEnabled(false);
                txt_subDenda.setText("-");
                btn_tambah.setEnabled(true);
                btn_edit.setEnabled(false);
                btn_hapus.setEnabled(false);
                btn_batal.setEnabled(false);
            }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prosesActionPerformed
        // TODO add your handling code here:
        try {
            if (combo_penyewa.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Data penyewa harus diisi");
            } else if(label_blmCukup.getForeground().equals(Color.red)){
                JOptionPane.showMessageDialog(null, "Silahkan cek kembali pembayaran");
                txt_bayar.requestFocus();
            }else if (combo_keterlambatan.getSelectedIndex() == 0 && tbl_pengembalian.getModel().getRowCount() == 0) {
                int confirmProses = JOptionPane.showConfirmDialog(null, "Yakin tidak ada keterlambatan dan barang dalam keadaan yang baik ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmProses == JOptionPane.YES_OPTION) {
                    //insert into data_pengembalian
                    Connection conn = koneksi.Connect.GetConnection();
                    String insertPengembalian = "insert into data_pengembalian values (null, '" + id_pengguna.getText() + "', '" + txt_idSewaan.getText() + "', null, 0)";
                    PreparedStatement pst = conn.prepareStatement(insertPengembalian);
                    pst.execute();

                    //update selesai di data_sewaan
                    String updateSewaan = "update data_sewaan set status = 'Selesai' where id_sewaan = '" + txt_idSewaan.getText() + "'";
                    PreparedStatement ps = conn.prepareStatement(updateSewaan);
                    ps.execute();

                    JOptionPane.showMessageDialog(null, "Pengembalian berhasil");
                    clear();
                }
            } else {
                int confirmProses = JOptionPane.showConfirmDialog(null, "Yakin ingin melakukan proses pengembalian?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmProses == JOptionPane.YES_OPTION) {
                    Connection conn = koneksi.Connect.GetConnection();

                    //get id keterlambatan
                    String isiKeterlambatan;
                    String terlambat;
                    if (txt_terlambat.getText().equals("-")) {
                        isiKeterlambatan = null;
                        terlambat = null;
                    } else {
                        isiKeterlambatan = txt_terlambat.getText();
                        terlambat = "'" + isiKeterlambatan.substring(0, 1) + "'";
                    }

                    //insert into pengembalian
                    String insertPengembalian = "insert into data_pengembalian values (null, '" + id_pengguna.getText() + "', '" + txt_idSewaan.getText() + "', " + terlambat + ", '" + txt_total.getText() + "')";
                    PreparedStatement pst = conn.prepareStatement(insertPengembalian);
                    pst.execute();

                    //get id pengembalian
                    String sqlIdPengembalian = "select * from data_pengembalian where id_sewaan = '" + txt_idSewaan.getText() + "'";
                    PreparedStatement ps = conn.prepareStatement(sqlIdPengembalian);
                    ResultSet res = ps.executeQuery();
                    res.next();

                    //update selesai di data_sewaan
                    String updateSewaan = "update data_sewaan set status = 'Selesai' where id_sewaan = '" + txt_idSewaan.getText() + "'";
                    PreparedStatement statements = conn.prepareStatement(updateSewaan);
                    statements.execute();

                    //insert into detail pengembalian
                    int rowCount = tbl_pengembalian.getModel().getRowCount();

                    for (int i = 0; i < rowCount; i++) {
                        String namaBarang = tbl_pengembalian.getValueAt(i, 1).toString();
                        int jumlahKerusakan = Integer.parseInt(tbl_pengembalian.getValueAt(i, 2).toString());
                        String jenisKerusakan = tbl_pengembalian.getValueAt(i, 3).toString();
                        int subTotal = Integer.parseInt(tbl_pengembalian.getValueAt(i, 4).toString());
                        
                        //get id_barang
                        String sqlIdBarang = "select * from data_barang where nama_barang = '" + namaBarang + "'";
                        PreparedStatement statemenBarang = conn.prepareStatement(sqlIdBarang);
                        ResultSet IdBarang = statemenBarang.executeQuery();
                        IdBarang.next();

                        //get id_kerusakan
                        String sqlIdKerusakan = "select * from kerusakan where jenis_kerusakan = '" + jenisKerusakan + "'";
                        PreparedStatement statemenKerusakan = conn.prepareStatement(sqlIdKerusakan);
                        ResultSet IdKerusakan = statemenKerusakan.executeQuery();
                        IdKerusakan.next();

                        //insert into detail_data_pengembalian
                        String insertDetailPengembalian = "insert into detail_data_pengembalian values ('" + res.getString("id_pengembalian") + "', '" + IdBarang.getString("id_barang") + "', '" + IdKerusakan.getString("id_kerusakan") + "', '"+ jumlahKerusakan +"', '"+ subTotal +"')";
                        PreparedStatement statement = conn.prepareStatement(insertDetailPengembalian);
                        statement.execute();
                    }
                    JOptionPane.showMessageDialog(null, "Pengembalian berhasil");
                    clear();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error tombol proses");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btn_prosesActionPerformed

    private void chckBox_sobekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckBox_sobekActionPerformed
        // TODO add your handling code here:
        if (chckBox_sobek.isSelected()) {
            this.jenisKerusakan.add(chckBox_sobek.getText());
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_sobek.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                    int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                    biayaTerbaru = biayaTerbaru + biayaDb;
                    String biaya = String.valueOf(biayaTerbaru);
                    this.TotalJenisKerusakan = biaya;
                    txt_subDenda.setText(this.TotalJenisKerusakan);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.jenisKerusakan.remove(chckBox_sobek.getText());
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_sobek.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                    int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                    biayaTerbaru = biayaTerbaru - biayaDb;
                    String biaya = String.valueOf(biayaTerbaru);
                    this.TotalJenisKerusakan = biaya;
                    txt_subDenda.setText(this.TotalJenisKerusakan);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_chckBox_sobekActionPerformed

    private void chckBox_resletingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckBox_resletingActionPerformed
        // TODO add your handling code here:
        if (chckBox_resleting.isSelected()) {
            this.jenisKerusakan.add(chckBox_resleting.getText());
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_resleting.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                    int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                    biayaTerbaru = biayaTerbaru + biayaDb;
                    String biaya = String.valueOf(biayaTerbaru);
                    this.TotalJenisKerusakan = biaya;
                    txt_subDenda.setText(this.TotalJenisKerusakan);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.jenisKerusakan.remove(chckBox_resleting.getText());
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_resleting.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                    int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                    biayaTerbaru = biayaTerbaru - biayaDb;
                    String biaya = String.valueOf(biayaTerbaru);
                    this.TotalJenisKerusakan = biaya;
                    txt_subDenda.setText(this.TotalJenisKerusakan);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_chckBox_resletingActionPerformed

    private void chckBox_patahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckBox_patahActionPerformed
        // TODO add your handling code here:
        if (chckBox_patah.isSelected()) {
            this.jenisKerusakan.add(chckBox_patah.getText());
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_patah.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                    int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                    biayaTerbaru = biayaTerbaru + biayaDb;
                    String biaya = String.valueOf(biayaTerbaru);
                    this.TotalJenisKerusakan = biaya;
                    txt_subDenda.setText(this.TotalJenisKerusakan);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.jenisKerusakan.remove(chckBox_patah.getText());
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_patah.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                    int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                    biayaTerbaru = biayaTerbaru - biayaDb;
                    String biaya = String.valueOf(biayaTerbaru);
                    this.TotalJenisKerusakan = biaya;
                    txt_subDenda.setText(this.TotalJenisKerusakan);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_chckBox_patahActionPerformed

    private void chckBox_hilangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckBox_hilangActionPerformed
        // TODO add your handling code here:
        if (chckBox_hilang.isSelected()) {
            this.jenisKerusakan.add(chckBox_hilang.getText());
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_hilang.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                    int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                    biayaTerbaru = biayaTerbaru + biayaDb;
                    String biaya = String.valueOf(biayaTerbaru);
                    this.TotalJenisKerusakan = biaya;
                    txt_subDenda.setText(this.TotalJenisKerusakan);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.jenisKerusakan.remove(chckBox_hilang.getText());
            try {
                Connection conn = koneksi.Connect.GetConnection();
                String sql = "select * from kerusakan where jenis_kerusakan = '" + chckBox_hilang.getText() + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int biayaTerbaru = Integer.parseInt(this.TotalJenisKerusakan);
                    int biayaDb = Integer.parseInt(rs.getString("biaya_kerusakan"));
                    biayaTerbaru = biayaTerbaru - biayaDb;
                    String biaya = String.valueOf(biayaTerbaru);
                    this.TotalJenisKerusakan = biaya;
                    txt_subDenda.setText(this.TotalJenisKerusakan);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_chckBox_hilangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_proses;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JCheckBox chckBox_hilang;
    private javax.swing.JCheckBox chckBox_patah;
    private javax.swing.JCheckBox chckBox_resleting;
    private javax.swing.JCheckBox chckBox_sobek;
    private javax.swing.JComboBox<String> combo_barangRusak;
    private javax.swing.JComboBox<String> combo_jumlah;
    private javax.swing.JComboBox<String> combo_keterlambatan;
    private javax.swing.JComboBox<String> combo_penyewa;
    private javax.swing.JLabel id_pengguna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label_blmCukup;
    private javax.swing.JTable tbl_pengembalian;
    private javax.swing.JLabel txt_alamat;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JLabel txt_deposit;
    private javax.swing.JLabel txt_hp;
    private javax.swing.JLabel txt_idSewaan;
    private javax.swing.JLabel txt_identitas;
    private javax.swing.JLabel txt_kembali;
    private javax.swing.JLabel txt_kembalian;
    private javax.swing.JLabel txt_nama;
    private javax.swing.JLabel txt_pinjam;
    private javax.swing.JLabel txt_subDenda;
    private javax.swing.JLabel txt_terlambat;
    private javax.swing.JLabel txt_total;
    private javax.swing.JLabel txt_transaksi;
    // End of variables declaration//GEN-END:variables
}
