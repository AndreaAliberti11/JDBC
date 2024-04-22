package bean;

import java.util.Scanner;

public class Quotidiani {

    private int id;
    private String nome;
    private double prezzo;
    private int aggio;
    private int cRicevute;
    private int cVendute;

    Scanner inputNumeri = new Scanner(System.in);
    Scanner inputTesti = new Scanner(System.in);

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        boolean ancora = true;

        do {
            if (nome.length() <= 3) {
                System.out
                        .println(
                                "Il nome del giornale/rivista deve avere più di 3 lettere, inserisci il nome di nuovo: ");
                nome = inputNumeri.nextLine();
            } else {
                this.nome = nome;
                ancora = false;
            }

        } while (ancora);

    }

    public String getNome() {
        return nome;
    }

    public void setPrezzo(double prezzo) {
        boolean ancora = true;

        do {
            if (prezzo > 0) {
                this.prezzo = prezzo;
                ancora = false;
            } else {
                System.out.println("Devi inserire un prezzo maggiore di 0, inserisci il prezzo di nuovo: ");
                prezzo = inputNumeri.nextDouble();
            }
        } while (ancora);

    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setAggio(int aggio) {
        boolean ancora = true;

        do {

            if (aggio >= 5 && aggio <= 15) {
                this.aggio = aggio;
                ancora = false;
            } else {
                System.out.println("Inserisci una percentuale di aggio compresa tra 5-15%:");
                aggio = inputNumeri.nextInt();
            }
        } while (ancora);

    }

    public int getAggio() {
        return aggio;
    }

    public void setCRicevute(int cRicevute) {
        boolean ancora = true;

        do {
            if (cRicevute >= 0) {
                this.cRicevute = cRicevute;
                ancora = false;
            } else {
                System.out.println("Devi inserire una quantità maggiore o uguale di 0, inserisci la quantità di nuovo: ");
                cRicevute = inputNumeri.nextInt();
            }

        } while (ancora);

    }

    public int getCRicevute() {
        return cRicevute;
    }

    public void setCVendute(int cVendute) {
        boolean ancora = true;

        do {

            if (cVendute < 0) {
                System.out.println("Inserisci un numero positivo o 0");
                cVendute = inputNumeri.nextInt();
            } else {
                this.cVendute = cVendute;
                ancora = false;
            }
        } while (ancora);

    }

    public int getCVendute() {
        return cVendute;
    }

    @Override
    public String toString() {
        return "quotidiani{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prezzo='" + prezzo + '\'' +
                ", aggio=" + aggio +
                ", cricevute=" + cRicevute +
                ", cvendute=" + cVendute +
                '}';
    }

}
