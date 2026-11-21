import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ConexionTester extends JFrame {

    private JTextField txtUrl;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JTextArea txtResultado;
    private JButton btnProbar;

    public ConexionTester() {
        setTitle("Prueba de Conexión a Base de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("URL de conexión:"));
        txtUrl = new JTextField("jdbc:sqlite:inventario.db");
        panel.add(txtUrl);

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField("");
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField("");
        panel.add(txtContrasena);

        btnProbar = new JButton("Probar conexión");
        panel.add(new JLabel(""));
        panel.add(btnProbar);

        add(panel, BorderLayout.NORTH);
        txtResultado = new JTextArea(8, 40);
        txtResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResultado);
        add(scrollPane, BorderLayout.CENTER);

        btnProbar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                probarConexion();
            }
        });
    }

    private void probarConexion() {
        String url = txtUrl.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        txtResultado.setText("🔄 Intentando conectar...\n");

        try (Connection conexion = DriverManager.getConnection(url, usuario, contrasena)) {
            txtResultado.setForeground(new Color(0, 128, 0));
            txtResultado.setText("✅ CONEXIÓN EXITOSA\n\n" +
                    "📍 Conectado a: " + url + "\n" +
                    "👤 Usuario: " + (usuario.isEmpty() ? "Sin usuario (SQLite)" : usuario) + "\n" +
                    "🕐 Hora: " + java.time.LocalTime.now());

        } catch (SQLException e) {
            txtResultado.setForeground(new Color(153, 0, 0));
            txtResultado.setText("❌ FALLO DE CONEXIÓN\n\n" +
                    "No se pudo conectar a la base de datos.\n" +
                    "Mensaje técnico: " + e.getMessage() + "\n" +
                    "Código de error: " + e.getErrorCode());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConexionTester().setVisible(true);
            }
        });
    }
}