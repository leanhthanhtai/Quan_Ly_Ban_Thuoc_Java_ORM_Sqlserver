package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import DAO.HoaDonDAO;
import DAO.ThuocDAO;
import Model.LoThuoc;
import Model.Thuoc;
import Service.HoaDonService;
import dto.HoaDonDTO;

public class PanelTimKiemHoaDon extends JPanel implements ActionListener {

	private List<HoaDonDTO> hoaDons;
	private DefaultTableModel tbModel;
	private JTable tb;
	private JScrollPane jScrollPane;
	private JButton btnTimKiem, btnLamMoi, btnThoat;
	private JPanel pCenter, pSouth;
	private JTextField txtTenNV, txtTenKH;
	private HoaDonService hoaDonDAO = new HoaDonDAO();

	public PanelTimKiemHoaDon() {
		setLayout(new BorderLayout());
		hoaDons = new ArrayList<HoaDonDTO>();

		taoCenterPanel();
		taoSouthPanel();
		lamMoiUI();
		addBegin();
		lamMoiDSHoaDon();
		this.add(pCenter, BorderLayout.CENTER);
		this.add(pSouth, BorderLayout.SOUTH);

	}

	public void taoCenterPanel() {
		pCenter = new JPanel(new GridLayout(1, 1));
		pCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pCenter.setBackground(new Color(0, 128, 128));

		String[] columnNames = {"Mã Hóa đơn", "Ngày Lập Hóa Đơn", "Tên Khách Hàng", "Số Điện Thoại","Email" ,"Địa Chỉ" ,"Tên Nhân Viên","Số Điện Thoại"};
		tbModel = new DefaultTableModel(columnNames, 30);
		tb = new JTable(tbModel);
		tb.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 14));
		tb.setRowHeight(20);
		JTableHeader tableHeader = tb.getTableHeader();
		tableHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

		jScrollPane = new JScrollPane(tb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setBorder(BorderFactory.createTitledBorder(null, "DANH SÁCH NHỮNG HÓA ĐƠN ĐÃ LẬP", TitledBorder.LEFT,
				TitledBorder.CENTER, new Font(Font.SANS_SERIF, Font.BOLD, 16)));
		jScrollPane.setBackground(new Color(173, 216, 230));

		pCenter.add(jScrollPane);
	}

	public void taoSouthPanel() {
		pSouth = new JPanel();
		pSouth.setBackground(new Color(0, 128, 128));
		Box box = Box.createHorizontalBox();
		
		JLabel label_1 = new JLabel("Tên khách hàng:");
		label_1.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		box.add(label_1);
		box.add(Box.createHorizontalStrut(10));
		box.add(txtTenKH = new JTextField(15));
		txtTenKH.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		
		box.add(Box.createHorizontalStrut(10));
		JLabel label_2 = new JLabel("Tên nhân viên:");
		label_2.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		box.add(label_2);
		box.add(Box.createHorizontalStrut(10));
		box.add(txtTenNV = new JTextField(15));
		txtTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 16));

		btnTimKiem = new JButton("Tìm Kiếm");
		btnTimKiem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		btnTimKiem.setBackground(Color.white);
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		btnLamMoi.setBackground(Color.white);

		pSouth.add(box);
		pSouth.add(btnTimKiem);
		pSouth.add(btnLamMoi);
		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
	}

//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.setSize(1200, 500);
//		PanelThuHoiThuocHetHan hetHan = new PanelThuHoiThuocHetHan();
//		frame.add(hetHan);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

	public void lamMoiUI() {
		((DefaultTableModel)tb.getModel()).setRowCount(0);
	}
	
	public void LamMoiHoaDon(List<HoaDonDTO> dshd) {
		 hoaDons.clear();
		 hoaDons.addAll(dshd);
	}
	
	public void addBegin() {
		List<HoaDonDTO> lstHD = hoaDonDAO.layDanhSachHoaDonDTO();
		hoaDons.clear();
		hoaDons.addAll(lstHD);
	}
	
	public void LamMoiALL() {
		lamMoiUI();
		addBegin();
		lamMoiDSHoaDon();
	}

	public void lamMoiDSHoaDon() {
		
		for (HoaDonDTO hoaDonDTO : hoaDons) {
			String[] arr = {hoaDonDTO.getMaHD(), String.valueOf(hoaDonDTO.getNgayLHD())
					,hoaDonDTO.getTenKH(),hoaDonDTO.getSdtKH(),hoaDonDTO.getEmail(),hoaDonDTO.getDiaChiKH()
					,hoaDonDTO.getTenNV(),hoaDonDTO.getSdtNV()};
			
			tbModel.addRow(arr);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.equals(btnTimKiem)) {
			XuLyTimKiem();
		}
		if(obj.equals(btnLamMoi)) {
			LamMOiToanBo();
		}
		
	}
	
	public void LamMOiToanBo() {
		txtTenKH.setText("");
		txtTenNV.setText("");
		LamMoiALL();
	}
	
	public void XuLyTimKiem() {
		if(txtTenKH.getText().trim().equals("") && txtTenNV.getText().trim().equals("")) {
			JOptionPane.showConfirmDialog(this, "Tên khách hàng và nhân viên không để trống!");
			return;
		}
		if(!txtTenKH.getText().trim().equals("") && txtTenNV.getText().trim().equals("")) {
			TimKiemKH();
		}
		if(txtTenKH.getText().trim().equals("") && !txtTenNV.getText().trim().equals("")) {
			TimKiemNV();
		}
		if(!txtTenKH.getText().trim().equals("") && !txtTenNV.getText().trim().equals("")) {
			JOptionPane.showConfirmDialog(this, "Một trong hai phải để trống!");
			return;
		}
	}
	
	public void TimKiemKH() {
		if(kiemTraTen(txtTenKH.getText().trim())==false) {
			JOptionPane.showConfirmDialog(this, "Tên Khách Hàng Không Đúng Định Dạng Chữ!");
			return;
		}
		List<HoaDonDTO> dsHD = hoaDonDAO.timDanhSachHoaDonDTOKH(txtTenKH.getText().trim());
			if(dsHD.size() >0) {
				lamMoiUI();
				LamMoiHoaDon(dsHD);
				lamMoiDSHoaDon();
				txtTenKH.setText("");
			}
			else JOptionPane.showConfirmDialog(this, "Không tìm thấy khách hàng!");
		
	}
	
	public void TimKiemNV() {
		
		if(kiemTraTen(txtTenNV.getText().trim())==false) {
			JOptionPane.showConfirmDialog(this, "Tên Nhân Viên Không Đúng Định Dạng Chữ!");
			return;
		}
		List<HoaDonDTO> dsHD = hoaDonDAO.timDanhSachHoaDonDTONV(txtTenNV.getText().trim());
		if(dsHD.size() >0) {
			lamMoiUI();
			LamMoiHoaDon(dsHD);
			lamMoiDSHoaDon();
			txtTenNV.setText("");
		}
		else JOptionPane.showConfirmDialog(this, "Không tìm thấy nhân viên!");
	}
	
	public boolean kiemTraTen(String ten) {
		String regex = "^([a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ:,%+-]+)|([a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ:,%+-]+[\\s]*)+$";						
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ten);
		return matcher.matches();
	}

//	public void lamMoiBangLoThuoc() {
//		tbModel.getDataVector().removeAllElements();
//		Vector<String> vector;
//		for (LoThuoc loThuoc : loThuocs) {
//			vector = new Vector<String>();
//			vector.add(loThuoc.getMaLo() + "");
//			vector.add(loThuoc.getThuoc().getMaThuoc());
//			vector.add(loThuoc.getThuoc().getTenThuoc());
//			vector.add(loThuoc.getNgayNhap() + "");
//			vector.add(loThuoc.getHanSuDung() + "");
//			vector.add(loThuoc.getSoLuongNhapVao() + "");
//			vector.add(loThuoc.getSoLuongBanRa() + "");
//			vector.add((loThuoc.getSoLuongNhapVao() - loThuoc.getSoLuongBanRa()) + "");
//			tbModel.addRow(vector);
//		}
//		tb.updateUI();
//		tb.clearSelection();
//	}
//
//	public void thuHoiThuocHetHan() {
//		ThuocDAO dao = new ThuocDAO();
//		boolean rs = dao.thuHoiThuocHetHan((ArrayList<LoThuoc>) loThuocs);
//		if (rs) {
//			JOptionPane.showMessageDialog(this, "Thu hồi thuốc thành công!");
//			lamMoiUI();
//		} else
//			JOptionPane.showMessageDialog(this, "Thu hồi thuốc không thành công!");
//	}
//
//	public void themListener() {
//		btnThuHoi.addActionListener(this);
//		btnLamMoi.addActionListener(this);
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		Object o = e.getSource();
//		if (o.equals(btnThuHoi))
//			thuHoiThuocHetHan();
//		else if (o.equals(btnLamMoi))
//			lamMoiUI();
//	}
}
