import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	static DSKe taoDinhKe(int dinhKe, DSKe lienKet) {
        return new DSKe(dinhKe, lienKet);
    }

    static void inDoThi(DoThi doThi) {
        for (int i = 0; i < doThi.soDinh; i++) {
            DSKe hienTai = doThi.dsKe[i];
            System.out.print("Danh sach ke cua dinh " + i + ": ");
            while (hienTai != null) {
                System.out.print("(" + hienTai.dinhKe + ") ");
                hienTai = hienTai.lienKet;
            }
            System.out.println();
        }
    }

    static void hienThiThongTinDinh(DoThi doThi) {
        System.out.println("Thong tin ve dinh: ");
        System.out.println("Dinh\tBac\tMau");
        for (int i = 0; i < doThi.soDinh; i++) {
            System.out.println(doThi.dinh[i].dinh + "\t" + doThi.dinh[i].bac + "\t" + doThi.dinh[i].mau);
        }
    }

    static void khoiTaoThongTinDinh(DoThi doThi) {
        for (int i = 0; i < doThi.soDinh; i++) {
            doThi.dinh[i] = new Dinh(i, 0, 0);
            DSKe ke = doThi.dsKe[i];
            int bac = 0;
            while (ke != null) {
                bac++;
                ke = ke.lienKet;
            }
            doThi.dinh[i].bac = bac;
        }
    }

    static void lapDanhSachVPhay(DoThi doThi, List<Integer> danhSachVPhay) {
        khoiTaoThongTinDinh(doThi);
        for (int i = 0; i < doThi.soDinh; i++) {
            danhSachVPhay.add(i);
        }
        sapXepDanhSachTheoBacGiamDan(doThi, danhSachVPhay);
    }

    static void toMau(DoThi doThi, int dinh, int mau) {
        doThi.dinh[dinh].mau = mau;
    }

    static boolean laDinhKe(DoThi doThi, int dinh, int dinhKe) {
        DSKe ke = doThi.dsKe[dinh];
        while (ke != null) {
            if (ke.dinhKe == dinhKe) {
                return true;
            }
            ke = ke.lienKet;
        }
        return false;
    }
    
    static void sapXepDanhSachTheoBacGiamDan(DoThi doThi, List<Integer> danhSach) {
        Collections.sort(danhSach, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return Integer.compare(doThi.dinh[b].bac, doThi.dinh[a].bac);
            }
        });
    }

    static int toMauDoThi(DoThi doThi) {
        int i = 1;
        List<Integer> danhSachVPhay = new ArrayList<>();
        lapDanhSachVPhay(doThi, danhSachVPhay);

        while (true) {
            toMau(doThi, danhSachVPhay.get(0), i);
            // duyet qua cac dinh con lai trong v'
            for (int j = 1; j < danhSachVPhay.size(); j++) {
                int dinh = danhSachVPhay.get(j);
                // la dinh ke
                /*
                 * b1: v'=
                 * b2: to mau dinh v'[i], va dinh ko ke voi dinh v'[i]
                 * b3: kiem tra thay cac dinh trong v 
                 * */
                if (!laDinhKe(doThi, danhSachVPhay.get(0), dinh))
                    toMau(doThi, dinh, i);
            }

            boolean tatCaDaToMau = true;
            for (int j = 0; j < doThi.soDinh; j++) {
                if (doThi.dinh[j].mau == 0) {
                    tatCaDaToMau = false;
                    break;
                }
            }

            if (tatCaDaToMau)
                break;
            // Loại bỏ các đỉnh đã tô màu khỏi danh sách V'
            List<Integer> newDanhSachVPhay = new ArrayList<>();
            for (int j = 0; j < doThi.soDinh; j++) {
                if (doThi.dinh[j].mau == 0)
                	newDanhSachVPhay.add(j);
            }
            danhSachVPhay = newDanhSachVPhay;
            // Sắp xếp lại danh sách V' theo thứ tự bậc giảm dần
            sapXepDanhSachTheoBacGiamDan(doThi, danhSachVPhay);

            i++;
        }
        return i;
    }
    
    static void sapXepMauVaHienThiMonThi(DoThi doThi, int soMau) {
        // Tạo một bản đồ để lưu danh sách các đỉnh tương ứng với từng màu
        Map<Integer, List<Integer>> mauDinhMap = new HashMap<>();
        
        // Khởi tạo danh sách trống cho từng màu trong khoảng từ 1 đến soMau
        for (int i = 0; i < soMau; i++) {
            mauDinhMap.put(i + 1, new ArrayList<>());
        }

        // Duyệt qua tất cả các đỉnh trong đồ thị
        for (int i = 0; i < doThi.soDinh; i++) {
            // Lấy màu của đỉnh hiện tại
            int mau = doThi.dinh[i].mau;
            // Thêm đỉnh hiện tại vào danh sách của màu tương ứng
            mauDinhMap.get(mau).add(i);
        }

        // Duyệt qua từng màu từ 1 đến soMau để in các đợt thi
        for (int i = 1; i <= soMau; i++) {
            // Lấy danh sách các đỉnh có màu hiện tại
            List<Integer> danhSachDinh = mauDinhMap.get(i);
            // Sắp xếp danh sách đỉnh theo thứ tự tăng dần
            Collections.sort(danhSachDinh);
            // In ra các đỉnh thuộc màu hiện tại
            System.out.print("Dot thi " + i + ": ");
            for (int dinh : danhSachDinh) {
                System.out.print(dinh + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        DoThi doThi = new DoThi(7);

        doThi.dsKe[0] = taoDinhKe(1, taoDinhKe(2, taoDinhKe(3, taoDinhKe(6, null))));
        doThi.dsKe[1] = taoDinhKe(0, taoDinhKe(2, taoDinhKe(3, taoDinhKe(4, taoDinhKe(6, null)))));
        doThi.dsKe[2] = taoDinhKe(0, taoDinhKe(1, taoDinhKe(5, taoDinhKe(6, null))));
        doThi.dsKe[3] = taoDinhKe(0, taoDinhKe(1, taoDinhKe(4, taoDinhKe(5, null))));
        doThi.dsKe[4] = taoDinhKe(1, taoDinhKe(3, taoDinhKe(5, taoDinhKe(6, null))));
        doThi.dsKe[5] = taoDinhKe(2, taoDinhKe(3, taoDinhKe(4, taoDinhKe(6, null))));
        doThi.dsKe[6] = taoDinhKe(0, taoDinhKe(1, taoDinhKe(2, taoDinhKe(4, taoDinhKe(5, null)))));
        inDoThi(doThi);

        int mauDaTo = toMauDoThi(doThi);

        System.out.println("\nDo thi sau khi to mau:");
        hienThiThongTinDinh(doThi);
        /*
         * i = 4;
         * 1,1, 2,2, 3,3, 4 -> 1,5, 3,6, 0,4, 2
         * */
        
        // Thong ke va ket luan
        System.out.println("\nThong ke ket qua:");
        sapXepMauVaHienThiMonThi(doThi, mauDaTo);
    }
}
