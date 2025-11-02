public class Main {
    public static void main(String[] args) {
        // Run aplikasi
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPenghitungKata().setVisible(true);
            }
        });
    }
}