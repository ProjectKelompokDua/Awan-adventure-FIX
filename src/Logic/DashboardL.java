/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import koneksi.Connect;

/**
 *
 * @author DELL
 */
public class DashboardL {
    private int pesanan_berlangsung;
    private int ttl_pesanan;
    private int pesanan_selesai;
    private int total;


    public String date(){
        LocalDateTime myDateObj = LocalDateTime.now();   
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        String formattedDate = myDateObj.format(myFormatObj);  
        return formattedDate;
    }

    public String dateStart(){
        LocalDateTime myDateObj = LocalDateTime.now();   
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd 00-00-00");  
        String formattedDate = myDateObj.format(myFormatObj);  
        return formattedDate;
    }
    public String dateEnd(){
        LocalDateTime myDateObj = LocalDateTime.now();   
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd 23-59-59");  
        String formattedDate = myDateObj.format(myFormatObj);  
        return formattedDate;
    }
    public int Pesanan_Berlangsung(String dateStart ,String dateEnd){
        try {
            String sql = "SELECT COUNT(Status) AS psn_brlngsg FROM data_sewaan WHERE status = 'Proses' AND tgl_transaksi BETWEEN '"+dateStart+"' AND '"+dateEnd+"'";
            java.sql.Connection conn = (Connection) koneksi.Connect.GetConnection();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                pesanan_berlangsung = rs.getInt("psn_brlngsg");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        };
        return pesanan_berlangsung;
    }
    
    public int Pesanan_Selesai(String dateStart ,String dateEnd) {
        try {
            String sql = "SELECT COUNT(id_sewaan) AS pesanan_selesai FROM data_sewaan WHERE status = 'Selesai' AND tgl_transaksi BETWEEN '"+dateStart+"' AND '"+dateEnd+"'";
            java.sql.Connection conn = (Connection) koneksi.Connect.GetConnection();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                pesanan_selesai = rs.getInt("pesanan_selesai");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pesanan_selesai;
    }
    
    public int Total_Transaksi(String dateStart ,String dateEnd) {
        try {
            String sql = "SELECT SUM(total) AS jml FROM data_sewaan WHERE tgl_transaksi BETWEEN '"+dateStart+"' AND '"+dateEnd+"'";
            java.sql.Connection conn = (Connection) koneksi.Connect.GetConnection();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt("jml");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return total;
    }
    
    public int Total_Pesanan(String dateStart ,String dateEnd) {
        try {
            String sql = "SELECT COUNT(id_sewaan) AS jml_pesanan FROM data_sewaan WHERE tgl_transaksi BETWEEN '"+dateStart+"' AND '"+dateEnd+"'";
            java.sql.Connection conn = (Connection) koneksi.Connect.GetConnection();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                ttl_pesanan = rs.getInt("jml_pesanan");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return ttl_pesanan;
    }
    
    public String dateMonthAgo(){
        LocalDate thirtyDaysAgo = LocalDate.parse(date()).minusDays(30);
        return String.valueOf(thirtyDaysAgo);
    }
    
    public static void main(String[] args) {
        DashboardL db = new DashboardL();
        System.out.println(db.Pesanan_Selesai("2022-12-01","2022-12-30"));
        System.out.println(db.Pesanan_Berlangsung("2022-12-01","2022-12-30"));
    }
}
