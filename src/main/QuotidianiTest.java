package main;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import bean.Quotidiani;
import dao.QuotidianiDAO;

public class QuotidianiTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner input = new Scanner(System.in);
        QuotidianiDAO quotidianiDao = new QuotidianiDAO();

        boolean ancora = true;

        do {
            System.out.println("1 - Per Aggiungere Quotidiano");
            System.out.println("2 - Per Eliminare Quotidiano");
            System.out.println("3 - Per Gestire un Quotidiano");
            System.out.println("4 - Per Ottenere Rendiconto");
            System.out.println("5 - Per uscire dal programma");

            int risp = input.nextInt();
            switch (risp) {
                case 1:
                    Quotidiani quot = new Quotidiani();
                    try {
                        quotidianiDao.registate();
                        System.out.println();
                        System.out.println("Inserisci il nome del quotidiano: ");
                        input.nextLine();
                        quot.setNome(input.nextLine());
                        System.out.println("Inserisci il prezzo del quotidiano: ");
                        quot.setPrezzo(input.nextDouble());
                        System.out.println("Inserisci l'aggio del quotidiano:");
                        quot.setAggio(input.nextInt());
                        quotidianiDao.aggiungiQuotidiano(quot);
                    } catch (InputMismatchException in) {
                        System.out.println("Procedura da ripetere! L'id, il prezzo e l'aggio devono essere numeri!");
                    } catch (Exception e) {
                        System.out.println("Procedura da ripetere! L'id, il prezzo e l'aggio devono essere numeri!");
                    }
                    break;
                case 2:
                    quotidianiDao.registate();
                    System.out.println();
                    System.out.println("Inserisci l'id del quotidiano da rimuovere");
                    quotidianiDao.eliminaQuotidiani(input.nextInt());
                    break;
                case 3:
                    System.out.println("1 - Per inserire le copie ricevute");
                    System.out.println("2 - Per Incremenatre in caso di copie Vendute < di Copie Ricevute");
                    System.out.println("3 - Modifica prezzo copertina (solo se non hai venduto copie)");
                    System.out.println("4 - Modificare aggio quotidiano (solo se non hai venduto copie)");
                    System.out.println("5 - Azzerare copie ricevute e vendute");
                    switch (input.nextInt()) {
                        case 1:
                            quotidianiDao.registate();
                            System.out.println();
                            System.out.println(
                                    "Inserisci l'id del quotidiano per aggiungere le copie ricevute di quel quotidiano: ");
                            try {
                                int id = input.nextInt();
                                System.out.println("Inserisci la quantità di copie ricevute da aggiungere: ");
                                int Cricevute = input.nextInt();

                                quotidianiDao.aggiungiCricevute(id, Cricevute);

                            } catch (InputMismatchException in) {
                                System.out.println("Procedura da ripetere! L'id e la quantità devono essere numeri!");
                                input.nextLine();
                            } catch (Exception e) {
                                System.out.println("Procedura da ripetere! L'id e la quantità devono essere numeri!");
                            }
                            break;

                        case 2:
                            quotidianiDao.registate();
                            System.out.println();
                            System.out.println(
                                    "Inserisci l'id di un tiolo con copie Vendute < di copie Ricevute per fare l'Incremento: ");
                            try {
                                quotidianiDao.vediIncremento(input.nextInt());
                            } catch (InputMismatchException in) {
                                System.out.println("Procedura da ripetere! L'id deve essere un numero!");
                            } catch (Exception e) {
                                System.out.println("Procedura da ripetere! L'id deve essere un numero!");
                            }
                            break;

                        case 3:
                            quotidianiDao.registate();
                            System.out.println();
                            System.out.println("Inserisci l'id con le copie vendute = 0");
                            try {
                                int id = input.nextInt();
                                quotidianiDao.modificaPrezzo(id);
                            } catch (InputMismatchException in) {
                                System.out.println("Procedura da ripetere! L'id e il prezzo devono essere numeri!");
                            } catch (Exception e) {
                                System.out.println("Procedura da ripetere! L'id e il prezzo devono essere numeri!");
                            }
                            break;

                        case 4:
                            quotidianiDao.registate();
                            System.out.println();
                            System.out.println("Inserisci l'id con le copie vendute = 0");
                            try {
                                int id = input.nextInt();
                                quotidianiDao.modificaAggio(id);
                            } catch (InputMismatchException in) {
                                System.out.println("Procedura da ripetere! L'id e l'aggio devono essere numeri!");
                            } catch (Exception e) {
                                System.out.println("Procedura da ripetere! L'id e l'aggio devono essere numeri!");
                            }
                            break;

                        case 5:
                            quotidianiDao.resetGiornaliero();
                            break;
                        default:
                            break;
                    }
                    break;
                case 4:
                    quotidianiDao.resocontoQuotidiani();
                    break;
                case 5:
                    System.out.println("Grazie e Arrivederci!");
                    ancora = false;
                    break;
                default:
                    break;
            }

        } while (ancora);

        input.close();
    }

}
