package Service;

import java.util.List;

import Model.ChiTietHoaDon;
import Model.HoaDon;
import Model.NhanVien;
import Model.Thuoc;
import dto.HoaDonDTO;

public interface HoaDonService {

	public NhanVien layThongTinNhanVienTheoMaNV(String maNV);
	public Boolean SuaLo(int sl,String ma);
	public String setCodeHD();
	public String layMaT(String maL);
	public Boolean themHD(HoaDon hd);
	public Boolean themCTHD(ChiTietHoaDon cthd);
	public HoaDon layHDTM(String maHD);
	public Thuoc layThuocTM(String ma);
	public List<HoaDon> layDanhSachHoaDon();
	public List<HoaDonDTO> layDanhSachHoaDonDTO();
	public List<HoaDonDTO> timDanhSachHoaDonDTOKH(String ten);
	public List<HoaDonDTO> timDanhSachHoaDonDTONV(String tennv);
}
