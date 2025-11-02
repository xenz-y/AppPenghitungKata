/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author USER
 */
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;


public class FormPenghitungKata extends javax.swing.JFrame {

    /**
     * Creates new form FormPenghitungKata
     */
    public FormPenghitungKata() {
        initComponents();
        setupUI();
        setupListeners();
        initializeLabels();   
    }
    private void setupUI() {
    // Set ukuran frame
    setSize(900, 700);
    setLocationRelativeTo(null);
    
    // Styling Header
    panelHeader.setBackground(new Color(41, 128, 185));
    lblJudul.setForeground(Color.WHITE);
    lblJudul.setHorizontalAlignment(SwingConstants.CENTER);
    
    // Styling Input Area
    txtInput.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    txtInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    
    // Styling Panel Statistik
    panelStatistik.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
        "Statistik Teks",
        javax.swing.border.TitledBorder.LEFT,
        javax.swing.border.TitledBorder.TOP,
        new Font("Segoe UI", Font.BOLD, 12),
        new Color(52, 152, 219)
    ));
    panelStatistik.setBackground(Color.WHITE);
    
    // Styling Panel Pencarian
    panelPencarian.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(39, 174, 96), 2),
        "Pencarian Kata",
        javax.swing.border.TitledBorder.LEFT,
        javax.swing.border.TitledBorder.TOP,
        new Font("Segoe UI", Font.BOLD, 12),
        new Color(39, 174, 96)
    ));
    panelPencarian.setBackground(Color.WHITE);
    
    // Styling Tombol
    styleButton(btnHitung, new Color(52, 152, 219));
    styleButton(btnClear, new Color(231, 76, 60));
    styleButton(btnSimpan, new Color(39, 174, 96));
    styleButton(btnCari, new Color(243, 156, 18));
    
    // Styling Label Hasil
    Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
    Color labelColor = new Color(41, 128, 185);
    
    lblKata.setFont(labelFont);
    lblKata.setForeground(labelColor);
    lblKarakter.setFont(labelFont);
    lblKarakter.setForeground(labelColor);
    lblKalimat.setFont(labelFont);
    lblKalimat.setForeground(labelColor);
    lblParagraf.setFont(labelFont);
    lblParagraf.setForeground(labelColor);
    lblKarakterNoSpace.setFont(labelFont);
    lblKarakterNoSpace.setForeground(labelColor);
}

private void styleButton(JButton button, Color color) {
    button.setBackground(color);
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Segoe UI", Font.BOLD, 12));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    // Hover effect
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(color.darker());
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(color);
        }
    });
}

private void initializeLabels() {
    lblKata.setText("0");
    lblKarakter.setText("0");
    lblKalimat.setText("0");
    lblParagraf.setText("0");
    lblKarakterNoSpace.setText("0");
    lblHasilCari.setText("");
}

private void hitungStatistik() {
    String teks = txtInput.getText();
    
    // Hitung jumlah kata
    int jumlahKata = hitungKata(teks);
    
    // Hitung jumlah karakter (dengan spasi)
    int jumlahKarakter = teks.length();
    
    // Hitung jumlah karakter (tanpa spasi)
    int jumlahKarakterNoSpace = teks.replaceAll("\\s+", "").length();
    
    // Hitung jumlah kalimat
    int jumlahKalimat = hitungKalimat(teks);
    
    // Hitung jumlah paragraf
    int jumlahParagraf = hitungParagraf(teks);
    
    // Update label
    lblKata.setText(String.valueOf(jumlahKata));
    lblKarakter.setText(String.valueOf(jumlahKarakter));
    lblKarakterNoSpace.setText(String.valueOf(jumlahKarakterNoSpace));
    lblKalimat.setText(String.valueOf(jumlahKalimat));
    lblParagraf.setText(String.valueOf(jumlahParagraf));
}

private int hitungKata(String teks) {
    if (teks == null || teks.trim().isEmpty()) {
        return 0;
    }
    
    // Gunakan regex untuk menghitung kata
    String[] kata = teks.trim().split("\\s+");
    return kata.length;
}

private int hitungKalimat(String teks) {
    if (teks == null || teks.trim().isEmpty()) {
        return 0;
    }
    
    // Hitung berdasarkan tanda baca akhir kalimat (. ! ?)
    Pattern pattern = Pattern.compile("[.!?]+");
    Matcher matcher = pattern.matcher(teks);
    
    int count = 0;
    while (matcher.find()) {
        count++;
    }
    
    return count;
}

private int hitungParagraf(String teks) {
    if (teks == null || teks.trim().isEmpty()) {
        return 0;
    }
    
    // Hitung paragraf berdasarkan line break
    String[] paragraf = teks.split("\\n\\s*\\n");
    
    // Filter paragraf kosong
    int count = 0;
    for (String p : paragraf) {
        if (!p.trim().isEmpty()) {
            count++;
        }
    }
    
    return count > 0 ? count : 1; // Minimal 1 paragraf jika ada teks
}
private void setupListeners() {
    // DocumentListener untuk real-time counting
    txtInput.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            hitungStatistik();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            hitungStatistik();
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {
            hitungStatistik();
        }
        
    });
    txtCari.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cariKata();
        }
    });
}
private void cariKata() {
    String teks = txtInput.getText();
    String kataCari = txtCari.getText().trim();
    
    if (kataCari.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Masukkan kata yang ingin dicari!", 
            "Peringatan", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Hapus highlight sebelumnya
    Highlighter highlighter = txtInput.getHighlighter();
    highlighter.removeAllHighlights();
    
    // Hitung kemunculan kata
    int count = 0;
    String lowerTeks = teks.toLowerCase();
    String lowerKataCari = kataCari.toLowerCase();
    
    int index = 0;
    while ((index = lowerTeks.indexOf(lowerKataCari, index)) != -1) {
        try {
            // Highlight kata yang ditemukan
            highlighter.addHighlight(
                index, 
                index + kataCari.length(),
                new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW)
            );
            count++;
            index += kataCari.length();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    // Tampilkan hasil
    if (count > 0) {
        lblHasilCari.setText("Kata \"" + kataCari + "\" ditemukan " + count + " kali");
        lblHasilCari.setForeground(new Color(39, 174, 96));
    } else {
        lblHasilCari.setText("Kata \"" + kataCari + "\" tidak ditemukan");
        lblHasilCari.setForeground(new Color(231, 76, 60));
    }
}
private void simpanKeFile() {
    String teks = txtInput.getText();
    
    if (teks.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Tidak ada teks untuk disimpan!",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Simpan Teks dan Hasil Perhitungan");
    
    int result = fileChooser.showSaveDialog(this);
    
    if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        
        // Tambahkan ekstensi .txt jika belum ada
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            file = new File(file.getAbsolutePath() + ".txt");
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // Tulis teks
            writer.println("==========================================");
            writer.println("       APLIKASI PENGHITUNG KATA");
            writer.println("==========================================");
            writer.println();
            writer.println("TEKS:");
            writer.println("------------------------------------------");
            writer.println(teks);
            writer.println();
            writer.println("==========================================");
            writer.println("       HASIL PERHITUNGAN");
            writer.println("==========================================");
            writer.println("Jumlah Kata              : " + lblKata.getText());
            writer.println("Jumlah Karakter          : " + lblKarakter.getText());
            writer.println("Jumlah Karakter (no space): " + lblKarakterNoSpace.getText());
            writer.println("Jumlah Kalimat           : " + lblKalimat.getText());
            writer.println("Jumlah Paragraf          : " + lblParagraf.getText());
            writer.println("==========================================");
            
            JOptionPane.showMessageDialog(this,
                "File berhasil disimpan ke:\n" + file.getAbsolutePath(),
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal menyimpan file: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void clearAll() {
    txtInput.setText("");
    txtCari.setText("");
    lblHasilCari.setText("");
    initializeLabels();
    
    // Hapus highlight
    txtInput.getHighlighter().removeAllHighlights();
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHeader = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        panelCenter = new javax.swing.JPanel();
        scrollInput = new javax.swing.JScrollPane();
        txtInput = new javax.swing.JTextArea();
        panelBottom = new javax.swing.JPanel();
        panelStatistik = new javax.swing.JPanel();
        lblTextKata = new javax.swing.JLabel();
        lblKata = new javax.swing.JLabel();
        lblTextKarakter = new javax.swing.JLabel();
        lblKarakter = new javax.swing.JLabel();
        lblTextKalimat = new javax.swing.JLabel();
        lblKalimat = new javax.swing.JLabel();
        lblTextParagraf = new javax.swing.JLabel();
        lblParagraf = new javax.swing.JLabel();
        lblTextKarakterNoSpace = new javax.swing.JLabel();
        lblKarakterNoSpace = new javax.swing.JLabel();
        panelPencarian = new javax.swing.JPanel();
        cariKata = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        lblHasilCari = new javax.swing.JLabel();
        panelTombol = new javax.swing.JPanel();
        btnHitung = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblJudul.setText("APLIKASI PENGHITUNG KATA");
        panelHeader.add(lblJudul);

        getContentPane().add(panelHeader, java.awt.BorderLayout.NORTH);

        panelCenter.setLayout(new java.awt.BorderLayout());

        scrollInput.setColumnHeader(null);
        scrollInput.setColumnHeaderView(null);

        txtInput.setColumns(50);
        txtInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtInput.setLineWrap(true);
        txtInput.setRows(15);
        txtInput.setWrapStyleWord(true);
        txtInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollInput.setViewportView(txtInput);

        panelCenter.add(scrollInput, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelCenter, java.awt.BorderLayout.CENTER);

        panelBottom.setLayout(new java.awt.BorderLayout());

        panelStatistik.setLayout(new java.awt.GridLayout(5, 2));

        lblTextKata.setText("Jumlah Kata");
        panelStatistik.add(lblTextKata);

        lblKata.setText("0");
        panelStatistik.add(lblKata);

        lblTextKarakter.setText("Jumlah Karakter");
        panelStatistik.add(lblTextKarakter);

        lblKarakter.setText("0");
        panelStatistik.add(lblKarakter);

        lblTextKalimat.setText("Jumlah Kalimat");
        panelStatistik.add(lblTextKalimat);

        lblKalimat.setText("0");
        panelStatistik.add(lblKalimat);

        lblTextParagraf.setText("Jumlah Paragraf");
        panelStatistik.add(lblTextParagraf);

        lblParagraf.setText("0");
        panelStatistik.add(lblParagraf);

        lblTextKarakterNoSpace.setText("Karakter (tanpa spasi)");
        panelStatistik.add(lblTextKarakterNoSpace);

        lblKarakterNoSpace.setText("0");
        panelStatistik.add(lblKarakterNoSpace);

        panelBottom.add(panelStatistik, java.awt.BorderLayout.WEST);

        panelPencarian.setLayout(new java.awt.GridLayout());

        cariKata.setText("Cari Kata");
        panelPencarian.add(cariKata);

        txtCari.setColumns(20);
        panelPencarian.add(txtCari);

        btnCari.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnCari.setText("CARI");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        panelPencarian.add(btnCari);
        panelPencarian.add(lblHasilCari);

        panelBottom.add(panelPencarian, java.awt.BorderLayout.CENTER);

        btnHitung.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHitung.setText("HITUNG");
        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });
        panelTombol.add(btnHitung);

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setText("CLEAR");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        panelTombol.add(btnClear);

        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        panelTombol.add(btnSimpan);

        panelBottom.add(panelTombol, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panelBottom, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed
        // TODO add your handling code here:
        hitungStatistik();
        JOptionPane.showMessageDialog(this,
        "Perhitungan selesai!",
        "Informasi",
        JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnHitungActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
     int confirm = JOptionPane.showConfirmDialog(this,
        "Apakah Anda yakin ingin menghapus semua teks?",
        "Konfirmasi",
        JOptionPane.YES_NO_OPTION);
     if (confirm == JOptionPane.YES_OPTION) {
        clearAll();
     }
     // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    simpanKeFile();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
    cariKata();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCariActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPenghitungKata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPenghitungKata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPenghitungKata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPenghitungKata.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPenghitungKata().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel cariKata;
    private javax.swing.JLabel lblHasilCari;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblKalimat;
    private javax.swing.JLabel lblKarakter;
    private javax.swing.JLabel lblKarakterNoSpace;
    private javax.swing.JLabel lblKata;
    private javax.swing.JLabel lblParagraf;
    private javax.swing.JLabel lblTextKalimat;
    private javax.swing.JLabel lblTextKarakter;
    private javax.swing.JLabel lblTextKarakterNoSpace;
    private javax.swing.JLabel lblTextKata;
    private javax.swing.JLabel lblTextParagraf;
    private javax.swing.JPanel panelBottom;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelPencarian;
    private javax.swing.JPanel panelStatistik;
    private javax.swing.JPanel panelTombol;
    private javax.swing.JScrollPane scrollInput;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextArea txtInput;
    // End of variables declaration//GEN-END:variables
}
