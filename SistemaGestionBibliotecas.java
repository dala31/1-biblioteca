import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaGestionBibliotecas {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTextField tituloField, isbnField, autorNombreField, autorApellidoField, categoriaField;
    private JTextField usuarioNombreField, usuarioApellidoField;
    private JTextArea infoTextArea;
    private Biblioteca biblioteca;

    public SistemaGestionBibliotecas() {
        inicializarGUI();
    }

    private void inicializarGUI() {
        frame = new JFrame("Sistema de Gestión de Bibliotecas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        tabbedPane = new JTabbedPane();

        JPanel libroPanel = crearLibroPanel();
        tabbedPane.addTab("Libros", libroPanel);

        JPanel usuarioPanel = crearUsuarioPanel();
        tabbedPane.addTab("Usuarios", usuarioPanel);

        JPanel infoPanel = crearInfoPanel();
        tabbedPane.addTab("Información", infoPanel);

        frame.add(tabbedPane);

        biblioteca = new Biblioteca(infoTextArea);
    }

    private JPanel crearLibroPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        tituloField = new JTextField();
        isbnField = new JTextField();
        autorNombreField = new JTextField();
        autorApellidoField = new JTextField();
        categoriaField = new JTextField();

        inputPanel.add(new JLabel("Título:"));
        inputPanel.add(tituloField);
        inputPanel.add(new JLabel("ISBN:"));
        inputPanel.add(isbnField);
        inputPanel.add(new JLabel("Nombre del Autor:"));
        inputPanel.add(autorNombreField);
        inputPanel.add(new JLabel("Apellido del Autor:"));
        inputPanel.add(autorApellidoField);
        inputPanel.add(new JLabel("Categoría:"));
        inputPanel.add(categoriaField);

        JButton agregarLibroButton = new JButton("Agregar Libro");
        agregarLibroButton.addActionListener(e -> agregarLibro());

        inputPanel.add(new JLabel());
        inputPanel.add(agregarLibroButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel crearUsuarioPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        usuarioNombreField = new JTextField();
        usuarioApellidoField = new JTextField();

        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(usuarioNombreField);
        inputPanel.add(new JLabel("Apellido:"));
        inputPanel.add(usuarioApellidoField);

        JButton agregarUsuarioButton = new JButton("Agregar Usuario");
        agregarUsuarioButton.addActionListener(e -> agregarUsuario());

        inputPanel.add(new JLabel());
        inputPanel.add(agregarUsuarioButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel crearInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton eliminarLibroButton = new JButton("Eliminar Libro");
        JButton eliminarUsuarioButton = new JButton("Eliminar Usuario");
        eliminarLibroButton.addActionListener(e -> eliminarLibro());
        eliminarUsuarioButton.addActionListener(e -> eliminarUsuario());
        buttonPanel.add(eliminarLibroButton);
        buttonPanel.add(eliminarUsuarioButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void agregarLibro() {
        String titulo = tituloField.getText();
        String isbn = isbnField.getText();
        String autorNombre = autorNombreField.getText();
        String autorApellido = autorApellidoField.getText();
        String categoriaNombre = categoriaField.getText();

        Autor autor = new Autor(autorNombre, autorApellido);
        Categoria categoria = new Categoria(categoriaNombre);
        biblioteca.agregarLibro(titulo, isbn, autor, categoria);
        mostrarInformacion();

        tituloField.setText("");
        isbnField.setText("");
        autorNombreField.setText("");
        autorApellidoField.setText("");
        categoriaField.setText("");
    }

    private void agregarUsuario() {
        String nombre = usuarioNombreField.getText();
        String apellido = usuarioApellidoField.getText();
        biblioteca.agregarUsuario(nombre, apellido);
        mostrarInformacion();

        usuarioNombreField.setText("");
        usuarioApellidoField.setText("");
    }

    private void mostrarInformacion() {
        biblioteca.mostrarInformacion();
    }

    private void eliminarLibro() {
        String seleccion = infoTextArea.getSelectedText();
        if (seleccion != null) {
            for (Libro libro : biblioteca.getLibros()) {
                if (seleccion.contains(libro.getTitulo())) {
                    biblioteca.eliminarLibro(libro);
                    break;
                }
            }
            mostrarInformacion();
        }
    }

    private void eliminarUsuario() {
        String seleccion = infoTextArea.getSelectedText();
        if (seleccion != null) {
            for (Usuario usuario : biblioteca.getUsuarios()) {
                if (seleccion.contains(usuario.getNombre()) || seleccion.contains(usuario.getApellido())) {
                    biblioteca.eliminarUsuario(usuario);
                    break;
                }
            }
            mostrarInformacion();
        }
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaGestionBibliotecas sistema = new SistemaGestionBibliotecas();
            sistema.mostrar();
        });
    }
}

class Libro {
    private String titulo;
    private String isbn;
    private Autor autor;
    private Categoria categoria;

    public Libro(String titulo, String isbn, Autor autor, Categoria categoria) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.categoria = categoria;
    }

    public String mostrarInfo() {
        return "Título: " + titulo + "\nISBN: " + isbn + "\nAutor: " + autor.getNombre() + " " + autor.getApellido() + "\nCategoría: " + categoria.getNombre();
    }

    public String getTitulo() {
        return titulo;
    }
}

class Autor {
    private String nombre;
    private String apellido;

    public Autor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String mostrarInfo() {
        return "Nombre: " + nombre + " " + apellido;
    }
}

class Usuario {
    private String nombre;
    private String apellido;
    private List<Prestamo> prestamos;

    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.prestamos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}

class Prestamo {
    private Libro libro;
    private Usuario usuario;

    public Prestamo(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }
}

class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

class Biblioteca {
    private List<Libro> libros;
    private List<Autor> autores;
    private List<Usuario> usuarios;
    private List<Prestamo> prestamos;
    private List<Categoria> categorias;
    private JTextArea infoTexto;

    public Biblioteca(JTextArea infoTexto) {
        this.libros = new ArrayList<>();
        this.autores = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.infoTexto = infoTexto;
    }

    public void agregarLibro(String titulo, String isbn, Autor autor, Categoria categoria) {
        Autor autorExistente = autores.stream()
                .filter(a -> a.getNombre().equals(autor.getNombre()) && a.getApellido().equals(autor.getApellido()))
                .findFirst().orElse(null);
        if (autorExistente == null) {
            autores.add(autor);
            autorExistente = autor;
        }

        Categoria categoriaExistente = categorias.stream()
                .filter(c -> c.getNombre().equals(categoria.getNombre()))
                .findFirst().orElse(null);
        if (categoriaExistente == null) {
            categorias.add(categoria);
            categoriaExistente = categoria;
        }

        Libro libro = new Libro(titulo, isbn, autorExistente, categoriaExistente);
        libros.add(libro);
    }

    public void agregarUsuario(String nombre, String apellido) {
        Usuario usuario = new Usuario(nombre, apellido);
        usuarios.add(usuario);
    }

    public void prestarLibro(Libro libro, Usuario usuario) {
        Prestamo prestamo = new Prestamo(libro, usuario);
        prestamos.add(prestamo);
        usuario.getPrestamos().add(prestamo);
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        prestamos.removeAll(usuario.getPrestamos());
        usuario.getPrestamos().clear();
    }

    public void mostrarInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("Libros:\n");
        for (Libro libro : libros) {
            info.append(libro.mostrarInfo()).append("\n\n");
        }
        info.append("Usuarios:\n");
        for (Usuario usuario : usuarios) {
            info.append(usuario.getNombre()).append(" ").append(usuario.getApellido()).append("\n");
            info.append("Préstamos:\n");
            for (Prestamo prestamo : usuario.getPrestamos()) {
                info.append(prestamo.getLibro().getTitulo()).append("\n");
            }
            info.append("\n");
        }
        infoTexto.setText(info.toString());
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}