package es.uji.ei1027.clubesportiu;

public class Nadador {
    private String nom;
    private String numFederat;
    private String pais;
    private int edat;
    private String genere;

    public int getEdat() {
        return edat;
    }

    public String getGenere() {
        return genere;
    }

    public String getNom() {
        return nom;
    }

    public String getNumFederat() {
        return numFederat;
    }

    public String getPais() {
        return pais;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNumFederat(String numFederat) {
        this.numFederat = numFederat;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Nadador{" +
                "nom='" + this.nom + "\'" +
                ", numFederat='" + this.numFederat + "\'" +
                ", pais='" + this.pais + "\'" +
                ", edat=" + this.edat +
                ", genere='" + this.genere + "\'" +
                "}";
    }
}

