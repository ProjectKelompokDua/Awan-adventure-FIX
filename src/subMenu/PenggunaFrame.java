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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import koneksi.Connect;

/**
 *
 * @author perlengkapan
 */
public class PenggunaFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form PenggunaFrame
     */
    public PenggunaFrame() {
        initComponents();

        //hilangin border 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bif = (BasicInternalFrameUI) this.getUI();
        bif.setNorthPane(null);

        loadTable();
        txt_pin.enable();
        btn_tambah.setEnabled(true);

        cekInputanStringOnly(txt_nama);
        cekInputanIntegerOnly(txt_pin);
    }

    public void cekInputanIntegerOnly(JTextField textfield) {
        textfield.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = textfield.getText();
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    textfield.setEditable(true);
                } else if (ke.getKeyCode() == 8) {
                    textfield.setEditable(true);
                } else {
                    textfield.setEditable(false);
                }
            }
        });
    }

    public void cekInputanStringOnly(JTextField textfield) {
        textfield.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = textfield.getText();
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    textfield.setEditable(false);
                } else if (ke.getKeyCode() == 8) {
                    textfield.setEditable(true);
                } else {
                    textfield.setEditable(true);
                }
            }
        });
    }

    private void loadTable() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        tableModel.addColumn("No");
        tableModel.addColumn("Id User");
        tableModel.addColumn("Nama");
        tableModel.addColumn("Username");
        tableModel.addColumn("Password");
        tableModel.addColumn("Hak Akses");
        tableModel.addColumn("PIN");

        try {
            int no = 1;
            String sql = "select * from pengguna";
            Connection connect = koneksi.Connect.GetConnection();
            PreparedStatement pst = connect.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{no++, rs.getString("id_pengguna"), rs.getString("nama_pengguna"),
                    rs.getString("username"), rs.getString("password"), rs.getString("hak_akses"), rs.getString("pin")});
            }
            tbl_pengguna.setModel(tableModel);
        } catch (Exception e) {

        }
    }

    private void clear() {
        txt_nama.setText(null);
        txt_username.setText(null);
        txt_password.setText(null);
        combo_akses.setSelectedIndex(0);
        txt_pin.setText(null);
        btn_tambah.setEnabled(true);
        tbl_pengguna.clearSelection();
        txt_pin.enable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pengguna = new javax.swing.JTable();
        txt_cariPengguna = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btn_tambah = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        tempat_id = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_password = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_pin = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        combo_akses = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_pengguna.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        tbl_pengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_pengguna.setRowHeight(30);
        tbl_pengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_penggunaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_pengguna);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 300, 930, 240));

        txt_cariPengguna.setFont(new java.awt.Font("Outfit", 0, 18)); // NOI18N
        txt_cariPengguna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariPenggunaKeyReleased(evt);
            }
        });
        getContentPane().add(txt_cariPengguna, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 260, 220, -1));

        jPanel4.setBackground(new java.awt.Color(242, 242, 242));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses"));

        btn_tambah.setBackground(new java.awt.Color(92, 184, 92));
        btn_tambah.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
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
        btn_clear.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        btn_clear.setForeground(new java.awt.Color(255, 255, 255));
        btn_clear.setText("Clear");
        btn_clear.setBorder(null);
        btn_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        tempat_id.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tempat_id, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_clear, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tempat_id, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, -1, -1));

        jPanel3.setBackground(new java.awt.Color(242, 242, 242));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Data"));

        jLabel3.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel3.setText("Nama");

        txt_nama.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N

        txt_username.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel5.setText("Username");

        txt_password.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel6.setText("Password");

        jLabel7.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel7.setText("Hak Akses");

        txt_pin.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel8.setText("PIN");

        combo_akses.setFont(new java.awt.Font("Outfit", 0, 14)); // NOI18N
        combo_akses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih...", "Admin", "Kasir" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_password)
                            .addComponent(txt_pin)
                            .addComponent(combo_akses, 0, 310, Short.MAX_VALUE)))
                    .addComponent(jLabel8)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(64, 64, 64))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(96, 96, 96)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nama, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                            .addComponent(txt_username))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(combo_akses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_pin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Outfit", 0, 16)); // NOI18N
        jLabel9.setText("Cari");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 260, 40, 30));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/background.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_penggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_penggunaMouseClicked
        // TODO add your handling code here:
        int i = tbl_pengguna.getSelectedRow();
        TableModel tbl = tbl_pengguna.getModel();

        String field2 = tbl.getValueAt(i, 1).toString();
        String field3 = tbl.getValueAt(i, 2).toString();
        String field4 = tbl.getValueAt(i, 3).toString();
        String field5 = tbl.getValueAt(i, 4).toString();
        String field6 = tbl.getValueAt(i, 5).toString();
        String field7 = tbl.getValueAt(i, 6).toString();

        tempat_id.setText(field2);
        txt_nama.setText(field3);
        txt_username.setText(field4);
        txt_password.setText(field5);
        combo_akses.setSelectedItem(field6);
        txt_pin.setText(field7);
        btn_tambah.setEnabled(false);
        txt_pin.disable();
    }//GEN-LAST:event_tbl_penggunaMouseClicked

    private void txt_cariPenggunaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariPenggunaKeyReleased
        // TODO add your handling code here:
        String cari = txt_cariPengguna.getText();
        int no = 1;

        DefaultTableModel dtm = new DefaultTableModel() {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        dtm.addColumn("No");
        dtm.addColumn("Id User");
        dtm.addColumn("Nama");
        dtm.addColumn("Username");
        dtm.addColumn("Password");
        dtm.addColumn("Hak Akses");
        dtm.addColumn("PIN");
        tbl_pengguna.setModel(dtm);

        try {
            Statement statement = (Statement) Connect.GetConnection().createStatement();
            ResultSet res = statement.executeQuery("select * from pengguna where nama_pengguna like '%" + cari + "%' or username like '%" + cari + "%'");

            while (res.next()) {
                dtm.addRow(new Object[]{
                    no++,
                    res.getString("id_pengguna"),
                    res.getString("nama_pengguna"),
                    res.getString("username"),
                    res.getString("password"),
                    res.getString("hak_akses"),
                    res.getString("pin")
                });
                tbl_pengguna.setModel(dtm);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_txt_cariPenggunaKeyReleased

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        try {
            if (txt_nama.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data Nama harus diisi");
                txt_nama.requestFocus();
            } else if (txt_username.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data Username harus diisi");
                txt_username.requestFocus();
            } else if (txt_password.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data Password harus diisi");
                txt_password.requestFocus();
            } else if (combo_akses.getSelectedItem().equals("Pilih...")) {
                JOptionPane.showMessageDialog(null, "Data Hak Akses harus diisi");
            } else if (txt_pin.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data PIN harus diisi");
                txt_pin.requestFocus();
            } else if (txt_pin.getText().length() > 6) {
                JOptionPane.showMessageDialog(null, "Data PIN hanya 6 karakter");
                txt_pin.requestFocus();
            } else {
                int confirm = JOptionPane.showConfirmDialog(null, "Apakah yakin ingin menambahkan data?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String sql = "insert into pengguna values (null, '" + txt_nama.getText() + "', '" + txt_username.getText() + "', '" + txt_password.getText() + "', '" + combo_akses.getSelectedItem() + "', '" + txt_pin.getText() + "')";
                    Connection conn = koneksi.Connect.GetConnection();
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
                    loadTable();
                    clear();
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error tambah");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        try {
            if (txt_nama.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data Nama harus diisi");
                txt_nama.requestFocus();
            } else if (txt_username.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data Username harus diisi");
                txt_username.requestFocus();
            } else if (txt_password.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data Password harus diisi");
                txt_password.requestFocus();
            } else if (combo_akses.getSelectedItem().equals("Pilih...")) {
                JOptionPane.showMessageDialog(null, "Data Hak Akses harus diisi");
            } else if (txt_pin.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Data PIN harus diisi");
                txt_pin.requestFocus();
            } else if (txt_pin.getText().length() > 6) {
                JOptionPane.showMessageDialog(null, "Data PIN hanya 6 karakter");
                txt_pin.requestFocus();
            } else {
                int confirm = JOptionPane.showConfirmDialog(null, "Apakah yakin ingin mengubah data ini?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {

                    String sql = "update pengguna set nama_pengguna='" + txt_nama.getText() + "', username='" + txt_username.getText() + "', password='" + txt_password.getText() + "', hak_akses='" + combo_akses.getSelectedItem() + "', pin='" + txt_pin.getText() + "' where id_pengguna='" + tempat_id.getText() + "'";
                    Connection conn = koneksi.Connect.GetConnection();
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Data berhasil diperbaharui");
                    loadTable();
                    clear();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error edit");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        try {

            String sql = "delete from pengguna where id_pengguna='" + tempat_id.getText() + "'";
            Connection conn = koneksi.Connect.GetConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            int confirmDelete = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmDelete == JOptionPane.YES_OPTION) {
                pst.execute();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                loadTable();
                clear();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error hapus");
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        // TODO add your handling code here:
        int confirmClear = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmClear == JOptionPane.YES_OPTION) {
            clear();
        }

    }//GEN-LAST:event_btn_clearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> combo_akses;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_pengguna;
    private javax.swing.JLabel tempat_id;
    private javax.swing.JTextField txt_cariPengguna;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_pin;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
