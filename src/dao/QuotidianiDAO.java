package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import bean.Quotidiani;

public class QuotidianiDAO {

    private Connection connection;

    public QuotidianiDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        String jdbcUrl = "jdbc:mysql://localhost:3306/edicola";
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "");

        this.connection = DriverManager.getConnection(jdbcUrl, props);
    }

    Scanner input = new Scanner(System.in);

    public void registate() throws SQLException {
        String query = "SELECT id, nome FROM quotidiani";
    
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
    
                System.out.println("ID: " + id + " Nome: " + nome);
            }
        }
    }    

    public Quotidiani trovaQuotidianiperId(int id) throws SQLException {
        String query = "SELECT * FROM quotidiani WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Quotidiani qu = new Quotidiani();
                    qu.setId(resultSet.getInt("id"));
                    qu.setNome(resultSet.getString("nome"));
                    qu.setPrezzo(resultSet.getDouble("prezzo"));
                    qu.setAggio(resultSet.getInt("aggio"));
                    qu.setCRicevute(resultSet.getInt("cricevute"));
                    qu.setCVendute(resultSet.getInt("cvendute"));
                    return qu;
                } else {
                    return null;
                }
            }
        }
    }

    public void aggiungiQuotidiano(Quotidiani quotidiani) throws SQLException {
        String query = "INSERT INTO quotidiani (nome, prezzo, aggio, cRicevute, Cvendute) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quotidiani.getNome());
            statement.setDouble(2, quotidiani.getPrezzo());
            statement.setInt(3, quotidiani.getAggio());
            statement.setInt(4, 0);
            statement.setInt(5, 0);

            int risultato = statement.executeUpdate();

            if (risultato > 0) {
                System.out.println("RECORD AGGIUNTO!");
            } else {
                System.out.println("RECORD NON AGGIUNTO!");
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento " + e.getMessage());
        }

    }

    public void eliminaQuotidiani(int id) throws SQLException {
        String query = "DELETE FROM quotidiani WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int result = statement.executeUpdate();

            if (result > 0) {
                System.out.println("RECORD ELIMINATO CORRETTAMENTE!");
            } else {
                System.out.println("RECORD NON ELIMINATO!");
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento " + e.getMessage());
        }

    }

    public void resocontoQuotidiani() throws SQLException {
        double gParziale = 0.0, gTotale = 0.0;
        String query = "SELECT * FROM quotidiani";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                double prezzo = resultSet.getDouble("prezzo");
                int aggio = resultSet.getInt("aggio");
                int cRicevute = resultSet.getInt("cricevute");
                int cVendute = resultSet.getInt("cvendute");
                System.out.println("Quotidiano: " + nome);
                System.out.println(
                        "Copie Ricevute: " + cRicevute + ", Vendute: " + cVendute + " con Aggio: " + aggio + "%");
                gParziale = cVendute * prezzo * aggio / 100;
                System.out.println("Guadagno Parziale: " + gParziale);
                gTotale += gParziale;
            }
            System.out.println("Guadagno Totale: " + gTotale);
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }

    }

    public void aggiungiCricevute(int id, int cRicevute) throws SQLException {
        String query = "UPDATE quotidiani SET cricevute = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cRicevute);
            statement.setInt(2, id);
            int risultato = statement.executeUpdate();
            if (risultato > 0) {
                System.out.println("COPIE RICEVUTE AGGIUNTE");
            } else {
                System.out.println("QUALCOSA è ANDATO STORTO");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void aggiungiCvendute(int id, int cVendute) throws SQLException {
        String query = "UPDATE quotidiani SET cvendute = cvendute + 1 WHERE id = ? AND cvendute < cricevute";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cVendute);
            statement.setInt(2, id);
            int risultato = statement.executeUpdate();
            if (risultato > 0) {
                System.out.println("COPIE Vendute AGGIUNTE");
            } else {
                System.out.println("QUALCOSA è ANDATO STORTO");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void vediIncremento(int id) throws SQLException {
        String query = "UPDATE quotidiani SET cvendute = cvendute + 1 WHERE id = ? AND cvendute < cricevute";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            int risultato = statement.executeUpdate();

            if (risultato > 0) {
                System.out.println("INCREMENTO FATTO CON SUCCESSO!");
            } else {
                System.out.println("ERRORE DURANTE L'INCREMENTO");
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public void modificaPrezzo(int id) throws SQLException {
        String query = "UPDATE quotidiani SET prezzo = ? WHERE id = ? AND cvendute = 0";
        if (trovaQuotidianiperId(id).getCVendute() == 0) {
            System.out.println("Inserisci nuovo prezzo: ");
            int prezzo = input.nextInt();
            if (prezzo > 0) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDouble(1, prezzo);
                    statement.setInt(2, id);
                    int result = statement.executeUpdate();

                    if (result > 0) {
                        System.out.println("PREZZO AGGIORNATO!");
                    } else {
                        System.out.println("PREZZO NON AGGIORNATO");
                    }
                } catch (SQLException e) {
                    System.out.println("Errore: " + e.getMessage());
                }
            } else {
                System.out.println("Il prezzo non può essere minore o uguale a 0!");
            }
        } else {
            System.out.println("Impossibile modificare il prezzo, copie già vendute");
        }
    }

    public void modificaAggio(int id) throws SQLException {
        String query = "UPDATE quotidiani SET aggio = ? WHERE id = ? AND cvendute = 0";
        if (trovaQuotidianiperId(id).getCVendute() == 0) {
            System.out.println("Inserisci nuovo aggio: ");
            int aggio = input.nextInt();
            if (aggio >= 5 && aggio <= 15) {
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDouble(1, aggio);
                    statement.setInt(2, id);
                    int result = statement.executeUpdate();

                    if (result > 0) {
                        System.out.println("AGGIO AGGIORNATO!");
                    } else {
                        System.out.println("AGGIO NON AGGIORNATO");
                    }
                } catch (SQLException e) {
                    System.out.println("Errore: " + e.getMessage());
                }
            } else {
                System.out.println("L'aggio non può essere minore di 5 e maggiore di 15!");
            }
        } else {
            System.out.println("Impossibile modificare l'aggio, copie già vendute");
        }
    }

    public void resetGiornaliero() throws SQLException {
        String query = "UPDATE quotidiani SET cvendute = 0, cricevute = 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int result = statement.executeUpdate();

            if (result > 0) {
                System.out.println("AZZERAMENTO FATTO CON SUCCESSO");
            } else {
                System.out.println("ERRORE DURANTE AZZERAMENTO");
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'aggiornamento: " + e.getMessage());
        }
    }

    public void chiudiConnessione() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}