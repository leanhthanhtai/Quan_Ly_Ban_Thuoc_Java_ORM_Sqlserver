package Service;

import java.util.List;

import Model.KhachHang;
import Model.NhomThuoc;

public interface LoaiThuocService {

	public List<NhomThuoc> layDanhSachLoaiThuoc();
	public NhomThuoc timNhomThuocTheoTen(String ten);
	public Boolean xoaNhomThuoc(String maLT);
	public Boolean themNhomThuoc(NhomThuoc nt);
	public Boolean suaNhomThuoc(NhomThuoc nt);
	public String setCodeEmployees();
	public List<NhomThuoc> timKiemTheoTen(String ten);
}
