public class Mahasiswa {
    private int idmhs;
    private String nama;
    private String nim;

    // Constructor, Getter dan Setter
    public Mahasiswa(int idmhs, String nama, String nim) {
        this.idmhs = idmhs;
        this.nama = nama;
        this.nim = nim;
    }

    public int getIdmhs() {
        return idmhs;
    }

    public void setIdmhs(int idmhs) {
        this.idmhs = idmhs;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
