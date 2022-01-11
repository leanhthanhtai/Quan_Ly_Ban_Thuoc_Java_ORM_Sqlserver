package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Database.SessionFactoryUtil;
import Model.ChiTietHoaDon;
import Model.HoaDon;
import Model.KhachHang;
import Model.LoThuoc;
import Model.NhomThuoc;
import Model.NhaCungCap;
import Model.NhanVien;
import Model.Thuoc;
import Service.HoaDonService;
import dto.HoaDonDTO;
import dto.ThuocDTO;

public class HoaDonDAO implements HoaDonService{
	
	private SessionFactory sessionFactory=null;
	
	public HoaDonDAO() {
		sessionFactory = SessionFactoryUtil.getSessionFactory();
	}

	@Override
	public NhanVien layThongTinNhanVienTheoMaNV(String maNV) {
		NhanVien nv =null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			nv = session.get(NhanVien.class, maNV);
			transaction.commit();
			return nv;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}


	@Override
	public Boolean SuaLo(int sl,String ma) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String sql = "update LoThuoc set soLuongTon = "+sl+" where LoThuoc.maLoThuoc='"+ma+"'";
		try {
			transaction.begin();
			session.createNativeQuery(sql, LoThuoc.class).executeUpdate();
			transaction.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return false;
	}
	public static void main(String[] args) {
		HoaDonDAO hd = new HoaDonDAO();
		hd.layDanhSachHoaDonDTO().forEach(x->{
			System.out.println(x.toString());
		});
	}

	@Override
	public String setCodeHD() {
		String sql = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String maKH = null;
		sql = "select top 1 hd.maHD  from [dbo].[HoaDon] as hd order by hd.maHD DESC";
		try {
			transaction.begin();
			maKH = (String) session.createNativeQuery(sql).getSingleResult();
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		Integer count = Integer.parseInt(maKH.substring(2));
		String countStr = String.valueOf(count);
	
			if(countStr.length()==1 && count<9) {
				return "HD000000000"+(count+1);
			}
			else if((countStr.length()==2 && count<99) || (countStr.length()==1 && count==9)) {
				return "HD00000000"+(count+1);
			}
			else if((countStr.length()==3 && count<999) || (countStr.length()==2 && count==99)) {
				return "HD0000000"+(count+1);
			}
			else if((countStr.length()==4 && count<9999) || (countStr.length()==3 && count==999)) {
				return "HD000000"+(count+1);
			}
			
			else if((countStr.length()==5 && count<99999) || (countStr.length()==4 && count==99999)) {
				return "HD00000"+(count+1);
			}
			else if((countStr.length()==6 && count<999999) || (countStr.length()==5 && count==99999)) {
				return "HD0000"+(count+1);
			}
			else if((countStr.length()==7 && count<9999999) || (countStr.length()==6 && count==999999)) {
				return "HD000"+(count+1);
			}
			else if((countStr.length()==8 && count<99999999) || (countStr.length()==7 && count==9999999)) {
				return "HD00"+(count+1);
			}
			else if((countStr.length()==9 && count<999999999) || (countStr.length()==8 && count==99999999)) {
				return "HD0"+(count+1);
			}
			else if((countStr.length()==10 && count<9999999999L) || (countStr.length()==7 && count==9999999999L)) {
				return "HD"+(count+1);
			}
			else return "VQ";
	}

	@Override
	public Boolean themHD(HoaDon hd) {
		Boolean check=false;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.save(hd);
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return true;
	}

	@Override
	public Boolean themCTHD(ChiTietHoaDon cthd) {
		Boolean check=false;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.save(cthd);
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return true;
	}

	@Override
	public HoaDon layHDTM(String maHD) {
		HoaDon nv =null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			nv = session.get(HoaDon.class, maHD);
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return nv;
	}

	@Override
	public Thuoc layThuocTM(String ma) {
		Thuoc nv =null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			nv = session.get(Thuoc.class, ma);
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return nv;
	}

	@Override
	public String layMaT(String maL) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String sql = "select lt.maThuoc  from [dbo].[LoThuoc] as lt where lt.maLoThuoc = '"+maL+"'";
		try {
			transaction.begin();
			String ma = (String) session.createNativeQuery(sql).getSingleResult();
			transaction.commit();
			return ma;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return "Không có";
	}

	@Override
	public List<HoaDon> layDanhSachHoaDon() {
		List<HoaDon> dsnv = new ArrayList<HoaDon>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			dsnv = session.createQuery("from HoaDon", HoaDon.class).getResultList();
			transaction.commit();
			return dsnv;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}

	@Override
	public List<HoaDonDTO> layDanhSachHoaDonDTO() {
		List<HoaDonDTO> lstHD = new ArrayList<HoaDonDTO>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String sql = "select hd.maHD, hd.ngayLapHD,kh.tenKH, kh.sdtKH, kh.email ,kh.diaChi,nv.tenNV, nv.sdtNV  from [dbo].[HoaDon] as hd\r\n"
				+ "join [dbo].[NhanVien] as nv on hd.maNV = nv.maNV\r\n"
				+ "join [dbo].[KhachHang] as kh on hd.maKH = kh.maKH";
				try {
			transaction.begin();
			List<?> dsHD = session.createNativeQuery(sql).getResultList();
			transaction.commit();
			for (Object object : dsHD) {
				Object[] obj = (Object[])object;
				String maHD = String.valueOf(obj[0]);
				String ngayLHD = String.valueOf(obj[1]);
				String tenKH = String.valueOf(obj[2]);
				String sdtKH = String.valueOf(obj[3]);
				String email = String.valueOf(obj[4]);
				String diaChiKH = String.valueOf(obj[5]);
				String tenNV = String.valueOf(obj[6]);
				String sdtNV = String.valueOf(obj[7]);	
				HoaDonDTO hoaDonDTO = new HoaDonDTO(maHD, ngayLHD, tenKH, sdtKH, email, diaChiKH, tenNV, sdtNV);
				lstHD.add(hoaDonDTO);
			}
			return lstHD;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}

	@Override
	public List<HoaDonDTO> timDanhSachHoaDonDTOKH(String ten) {
		List<HoaDonDTO> lstHD = new ArrayList<HoaDonDTO>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String sql = "select hd.maHD, hd.ngayLapHD,kh.tenKH, kh.sdtKH, kh.email ,kh.diaChi,nv.tenNV, nv.sdtNV  from [dbo].[HoaDon] as hd\r\n"
				+ "join [dbo].[NhanVien] as nv on hd.maNV = nv.maNV\r\n"
				+ "join [dbo].[KhachHang] as kh on hd.maKH = kh.maKH\r\n"
				+ "where kh.tenKH like N'%"+ten+"%'\r\n"
				+ "";
				try {
			transaction.begin();
			List<?> dsHD = session.createNativeQuery(sql).getResultList();
			transaction.commit();
			for (Object object : dsHD) {
				Object[] obj = (Object[])object;
				String maHD = String.valueOf(obj[0]);
				String ngayLHD = String.valueOf(obj[1]);
				String tenKH = String.valueOf(obj[2]);
				String sdtKH = String.valueOf(obj[3]);
				String email = String.valueOf(obj[4]);
				String diaChiKH = String.valueOf(obj[5]);
				String tenNV = String.valueOf(obj[6]);
				String sdtNV = String.valueOf(obj[7]);	
				HoaDonDTO hoaDonDTO = new HoaDonDTO(maHD, ngayLHD, tenKH, sdtKH, email, diaChiKH, tenNV, sdtNV);
				lstHD.add(hoaDonDTO);
			}
			return lstHD;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}

	@Override
	public List<HoaDonDTO> timDanhSachHoaDonDTONV(String tennv) {
		List<HoaDonDTO> lstHD = new ArrayList<HoaDonDTO>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String sql = "select hd.maHD, hd.ngayLapHD,kh.tenKH, kh.sdtKH, kh.email ,kh.diaChi,nv.tenNV, nv.sdtNV  from [dbo].[HoaDon] as hd\r\n"
				+ "join [dbo].[NhanVien] as nv on hd.maNV = nv.maNV\r\n"
				+ "join [dbo].[KhachHang] as kh on hd.maKH = kh.maKH\r\n"
				+ "where nv.tenNV like N'%"+tennv+"%'\r\n"
				+ "";
				try {
			transaction.begin();
			List<?> dsHD = session.createNativeQuery(sql).getResultList();
			transaction.commit();
			for (Object object : dsHD) {
				Object[] obj = (Object[])object;
				String maHD = String.valueOf(obj[0]);
				String ngayLHD = String.valueOf(obj[1]);
				String tenKH = String.valueOf(obj[2]);
				String sdtKH = String.valueOf(obj[3]);
				String email = String.valueOf(obj[4]);
				String diaChiKH = String.valueOf(obj[5]);
				String tenNV = String.valueOf(obj[6]);
				String sdtNV = String.valueOf(obj[7]);	
				HoaDonDTO hoaDonDTO = new HoaDonDTO(maHD, ngayLHD, tenKH, sdtKH, email, diaChiKH, tenNV, sdtNV);
				lstHD.add(hoaDonDTO);
			}
			return lstHD;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}

}
