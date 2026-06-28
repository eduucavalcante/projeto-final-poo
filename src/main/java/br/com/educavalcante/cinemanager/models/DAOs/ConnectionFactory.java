package br.com.educavalcante.cinemanager.models.DAOs;

import br.com.educavalcante.cinemanager.controllers.UsuarioController;
import br.com.educavalcante.cinemanager.models.entities.Usuario;
import br.com.educavalcante.cinemanager.utils.Role;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:sqlite:database.db?journal_mode=WAL";
    
    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados: ", e);
        }
    }
    
    public static void setupTables() {
        try (Connection conn = createConnection()) {
            createTableUsuarios(conn);
            createTableFilmes(conn);
            createTableSalas(conn);
            createTableSessoes(conn);
            createTableReservas(conn);
            createTableIngressos(conn);
            seedAdmin("Admin", "admin@cine.com", "admin123");
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao criar tabelas: ", e);
        }
    }
    
    private static void createTableUsuarios(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    senha TEXT NOT NULL,
                    role TEXT NOT NULL CHECK(role IN ('CLIENTE', 'ADMIN'))
                )""";
        
        conn.createStatement().execute(sql);
    }
    
    private static void createTableFilmes(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS filmes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo TEXT NOT NULL,
                    sinopse TEXT NOT NULL UNIQUE,
                    genero TEXT NOT NULL,
                    duracao INTEGER NOT NULL,
                    em_cartaz INTEGER NOT NULL DEFAULT 0
                )""";
        
        conn.createStatement().execute(sql);
    }
    
    private static void createTableSalas(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS salas (
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     numero INTEGER NOT NULL,
                     total_fileiras INTEGER NOT NULL,
                     total_assentos_pf INTEGER NOT NULL
                )""";
        
        conn.createStatement().execute(sql);
    }
    
    private static void createTableSessoes(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS sessoes (
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     filmeId INTEGER NOT NULL,
                     salaId INTEGER NOT NULL,
                     dataHora TEXT NOT NULL,
                     precoBase REAL NOT NULL,
                     
                     FOREIGN KEY (filmeId) REFERENCES filmes(id),
                     FOREIGN KEY (salaId) REFERENCES salas(id)
                )""";
        
        conn.createStatement().execute(sql);
    }
    
    private static void createTableReservas(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS reservas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    sessaoId INTEGER NOT NULL,
                    fileira INTEGER NOT NULL,
                    numero INTEGER NOT NULL,
                    usuarioId INTEGER NOT NULL,
                    
                    FOREIGN KEY (sessaoId) REFERENCES sessoes(id),
                    FOREIGN KEY (usuarioId) REFERENCES usuarios(id),
                    UNIQUE (sessaoId, fileira, numero)
                )""";
        
        conn.createStatement().execute(sql);
    }
    
    private static void createTableIngressos(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS ingressos (
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     usuarioId INTEGER NOT NULL,
                     sessaoId INTEGER NOT NULL,
                     numero_sala INTEGER NOT NULL,
                     numero_assento INTEGER NOT NULL,
                     numero_fileira INTEGER NOT NULL,
                     filme TEXT NOT NULL,
                     dataHora TEXT NOT NULL,
                     preco REAL NOT NULL,
                     tipo TEXT NOT NULL CHECK(tipo IN ('MEIA', 'INTEIRA')),
                     
                     FOREIGN KEY (sessaoId) REFERENCES sessoes(id),
                     FOREIGN KEY (usuarioId) REFERENCES usuarios(id)
                     UNIQUE (sessaoId, numero_sala, numero_assento, numero_fileira)
                )""";
        
        conn.createStatement().execute(sql);
    }
    
    // Cria usuário administrador caso não exista, para permitir primeiro acesso ao sistema
    private static void seedAdmin(String nome, String email, String senha) {        
        String countSql = "SELECT COUNT(*) FROM usuarios WHERE role = 'ADMIN'";
        
        try(Connection conn = ConnectionFactory.createConnection();
            PreparedStatement countStmt = conn.prepareStatement(countSql);
            ResultSet rs = countStmt.executeQuery()) {
            if(rs.next()) {
                if(rs.getInt(1) == 0) {
                    Usuario admin = new UsuarioController(new UsuarioDAO()).criarConta(nome, email, senha, Role.ADMIN);
                    System.out.println("Admin criado.\nEmail: " + admin.getEmail() + "\nSenha: " + senha);
                } else {
                    System.out.println("Admin já existe.");
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao criar usuário admin: " + e);
        }
    }
}
