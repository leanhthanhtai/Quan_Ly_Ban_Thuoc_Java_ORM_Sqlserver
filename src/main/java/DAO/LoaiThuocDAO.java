package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Database.SessionFactoryUtil;
import Model.KhachHang;
import Model.NhomThuoc;
import Service.LoaiThuocService;

public class LoaiThuocDAO implements LoaiThuocService{
	private SessionFactory sessionFactory =null;
	
	public LoaiThuocDAO() {
		sessionFactory = SessionFactoryUtil.getSessionFactory();
	}

	@Override
	public List<NhomThuoc> layDanhSachLoaiThuoc() {
		List<NhomThuoc> dskh = new ArrayList<NhomThuoc>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String sql = "select * from [dbo].[NhomThuoc]";
		try {
			transaction.begin();
			dskh = session.createNativeQuery(sql, NhomThuoc.class).getResultList();
			transaction.commit();
			return dskh;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}

	@Override
	public NhomThuoc timNhomThuocTheoTen(String ten) {
		NhomThuoc nt = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String sql = "select * from [dbo].[NhomThuoc] as nt where nt.tenNhomThuoc like N'"+ten+"'";
		try {
			transaction.begin();
			nt = session.createNativeQuery(sql, NhomThuoc.class).getSingleResult();
			transaction.commit();
			return nt;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}

	@Override
	public Boolean xoaNhomThuoc(String maLT) {
		Boolean check=false;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			NhomThuoc kh = session.get(NhomThuoc.class, maLT);
			session.delete(kh);
			transaction.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return false;
	}

	@Override
	public Boolean themNhomThuoc(NhomThuoc nt) {
		Boolean check=false;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.save(nt);
			transaction.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return false;
	}

	@Override
	public Boolean suaNhomThuoc(NhomThuoc nt) {
		Boolean check=false;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(nt);
			transaction.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return false;
	}

	@Override
	public String setCodeEmployees() {
		String sql = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String ma = null;
		sql = "select top 1 nt.maNhomThuoc from [dbo].[NhomThuoc] as nt where nt.maNhomThuoc  like '%NT%' order by nt.maNhomThuoc DESC";
		try {
			transaction.begin();
			ma = (String)session.createNativeQuery(sql).getSingleResult();
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		Integer count = Integer.parseInt(ma.substring(2));
		String countStr = String.valueOf(count);
		
			if(countStr.length()==1 && count<9) {
				return "NT00"+(count+1);
			}
			else if((countStr.length()==2 && count<99) || (countStr.length()==1 && count==9)) {
				return "NV0"+(count+1);
			}
			else if((countStr.length()==3 && count<999) || (countStr.length()==2 && count==99)) {
				return "NV"+(count+1);
			}
			else return "VQ";
		
		
	}

	@Override
	public List<NhomThuoc> timKiemTheoTen(String ten) {
		List<NhomThuoc> dskh = new ArrayList<NhomThuoc>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		String sql = "select * from [dbo].[NhomThuoc] as nt where nt.tenNhomThuoc like N'%"+ten+"%'";
		try {
			transaction.begin();
			dskh = session.createNativeQuery(sql, NhomThuoc.class).getResultList();
			transaction.commit();
			return dskh;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}
		return null;
	}


}
