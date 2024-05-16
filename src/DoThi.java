
public class DoThi {
	int soDinh;
    DSKe[] dsKe;
    Dinh[] dinh;

    // Constructor
    public DoThi(int soDinh) {
        this.soDinh = soDinh;
        dsKe = new DSKe[soDinh];
        dinh = new Dinh[soDinh];
    }

    // Method to initialize adjacent list for a vertex
    public void khoiTaoDanhSachKe(int dinhIndex, DSKe dinhKe) {
        dsKe[dinhIndex] = dinhKe;
    }
}
