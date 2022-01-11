package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DefaultStyledDocument;

import DAO.LoaiThuocDAO;
import Model.KhachHang;
//import DAO.LoaiThuocDAO;
import Model.NhomThuoc;
import Service.LoaiThuocService;
import dto.HoaDonDTO;

public class DiaLogLoaiThuoc extends JDialog implements MouseListener, ActionListener {

	private JPanel pLoai, pTTLoai, pDSLoai, pXuLy, pButton, pTimKiem;
	//
	private JTextField txtMa, txtTen;
	private JTextArea txaMoTa;
	//
	private DefaultTableModel tableModel;
	private JTable tbLoai;
	private JScrollPane scrollPane;
	//
	private JButton btnThem, btnCapNhat, btnXoa, btnLamMoi;
	//
	private JComboBox<String> cbTim;
	private JTextField txtTim;
	private JButton btnTim;
	//
	private List<NhomThuoc> loaiThuocs;
	private LoaiThuocService loaiThuocDAO = new LoaiThuocDAO();

	public DiaLogLoaiThuoc(Frame owner) {
		super(owner);
		buildUI();
		loaiThuocs = new ArrayList<NhomThuoc>();
		taoPanelLoai();
		taoPanelXuLy();

		this.add(pLoai, BorderLayout.CENTER);
		this.add(pXuLy, BorderLayout.EAST);

		//themListener();
		lamMoiUI();
		setVisible(true);
	}

	public void buildUI() {
		this.setTitle("Loại thuốc");
		setSize(1210, 490);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public void taoPanelLoai() {
		taoPanelTTLoai();
		taoPanelDSLoai();

		pLoai = new JPanel();
		pLoai.setBackground(new Color(0, 128, 128));
		pLoai.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.ipadx = 0;
		c.ipady = 0;
		c.insets = new Insets(5, 0, 5, 0);

		c.gridx = 0;
		c.gridy = 0;
		pLoai.add(pTTLoai, c);
		c.gridy = 1;
		pLoai.add(pDSLoai, c);
	}

	public void taoPanelTTLoai() {
		JLabel lblMa, lblTen, lblMoTa;
		lblMa = new JLabel("Mã");
		lblMa.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		lblTen = new JLabel("Tên:");
		lblTen.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
//		lblMoTa = new JLabel("Mô tả:");
//		lblMoTa.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

		txtMa = new JTextField(30);
		txtMa.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 16));
		txtMa.setEditable(false);
		txtTen = new JTextField(30);
		txtTen.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 16));

		txaMoTa = new JTextArea(2, 30);
		txaMoTa.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 16));
		txaMoTa.setLineWrap(true);
		txaMoTa.setBorder(BorderFactory.createLineBorder(Color.gray));

		pTTLoai = new JPanel();
		pTTLoai.setBackground(new Color(0, 128, 128));
		pTTLoai.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 0;
		c.ipady = 5;
		c.insets = new Insets(5, 5, 5, 5);

		c.gridy = 0;
		c.gridx = 0;
		pTTLoai.add(lblMa, c);
		c.gridx = 1;
		pTTLoai.add(txtMa, c);
		c.gridx = 2;
		pTTLoai.add(lblTen, c);
		c.gridx = 3;
		pTTLoai.add(txtTen, c);
//
//		c.gridx = 0;
//		c.gridy = 1;
//		pTTLoai.add(lblMoTa, c);
//		c.gridx = 1;
//		c.gridwidth = 3;
//		pTTLoai.add(txaMoTa, c);
	}
//	public void LoopValue() {
//		while(String.valueOf(DialogDangNhap.manv).equals("")) {
//			System.out.println("AAA");
//			while(!String.valueOf(DialogDangNhap.manv).equals("")) {
//				txtTen.setText(String.valueOf(DialogDangNhap.manv));
//				break;
//			}
//			if(!String.valueOf(DialogDangNhap.manv).equals("")) {
//				break;
//			}
//		}
//	}

	public void taoPanelDSLoai() {
		String[] column = { "Mã loại thuốc", "Tên loại thuốc" };
		tableModel = new DefaultTableModel(column, 20);
		tbLoai = new JTable(tableModel);
		tbLoai.setBackground(Color.white);
		tbLoai.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 14));
		tbLoai.setRowHeight(20);

		JTableHeader tableHeader = tbLoai.getTableHeader();
		tableHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

		scrollPane = new JScrollPane(tbLoai, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createTitledBorder(null, "Danh sách loại thuốc", TitledBorder.LEFT,
				TitledBorder.CENTER, new Font(Font.SANS_SERIF, Font.BOLD, 16)));
		scrollPane.setBackground(new Color(173, 216, 230));
		scrollPane.setMinimumSize(new Dimension(700, 300));

		pDSLoai = new JPanel();
		pDSLoai.setBackground(new Color(0, 128, 128));
		pDSLoai.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		pDSLoai.setLayout(new GridLayout(1, 1));
		pDSLoai.add(scrollPane);
		tbLoai.addMouseListener(this);
	}

	public void taoPanelXuLy() {
		taoPanelButton();
		taoPanelTimKiem();

		pXuLy = new JPanel();
		pXuLy.setBackground(new Color(0, 128, 128));
		pXuLy.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 5, 0, 5);

		c.gridy = 0;
		c.gridx = 0;
		pXuLy.add(pButton, c);
		c.gridy = 1;
		pXuLy.add(pTimKiem, c);
	}

	public void taoPanelButton() {

		btnThem = new JButton("Thêm");
		btnThem.setBackground(Color.white);
		btnThem.setPreferredSize(new Dimension(270, 30));
		btnThem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.setBackground(Color.white);
		btnCapNhat.setPreferredSize(new Dimension(270, 30));
		btnCapNhat.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		btnXoa = new JButton("Xóa");
		btnXoa.setBackground(Color.white);
		btnXoa.setPreferredSize(new Dimension(270, 30));
		btnXoa.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setBackground(Color.white);
		btnLamMoi.setPreferredSize(new Dimension(270, 30));
		btnLamMoi.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

		pButton = new JPanel();
		pButton.setBackground(new Color(0, 128, 128));
		pButton.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 5;
		c.insets = new Insets(5, 0, 5, 0);

		c.gridy = 0;
		c.gridx = 0;
		pButton.add(btnThem, c);
		c.gridy = 1;
		pButton.add(btnCapNhat, c);
		c.gridy = 2;
		pButton.add(btnXoa, c);
		c.gridy = 3;
		pButton.add(btnLamMoi, c);
		btnThem.addActionListener(this);
		btnCapNhat.addActionListener(this);
		btnXoa.addActionListener(this);
		btnLamMoi.addActionListener(this);
	}

	public void taoPanelTimKiem() {
		JLabel lblTimKiem, lblTheoMa, lblTheoTen;

		lblTimKiem = new JLabel("Tìm kiếm theo tên", JLabel.CENTER);
		lblTimKiem.setPreferredSize(new Dimension(200, 30));
		lblTimKiem.setOpaque(true);
		lblTimKiem.setBackground(new Color(173, 216, 230));
		lblTimKiem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));

//		String[] tieuchitim = {"Theo tên" };
//		cbTim = new JComboBox<String>(tieuchitim);
//		cbTim.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 16));

		txtTim = new JTextField(20);
		txtTim.setFont(new Font(Font.SANS_SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 16));

		btnTim = new JButton("Tìm");
		btnTim.setBackground(Color.white);
		btnTim.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

		pTimKiem = new JPanel();
		pTimKiem.setBackground(new Color(0, 128, 128));
		pTimKiem.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 0;
		c.ipady = 5;
		c.insets = new Insets(5, 0, 5, 0);

		c.gridy = 0;
		c.gridx = 0;
		pTimKiem.add(lblTimKiem, c);
		c.gridy = 1;
		pTimKiem.add(txtTim, c);
		c.gridy = 2;
		pTimKiem.add(btnTim, c);
//		c.gridy = 3;
//		pTimKiem.add(btnTim, c);
		btnTim.addActionListener(this);
	}

	public void lamMoiUI() {
		txtTen.setText("");
		txtTim.setText("");
		//cbTim.setSelectedIndex(0);
		xoaRow();
		lamMoiDSLoaiThuoc();
		lamMoiBangLoaiThuoc();
	}
	
	public void xoaRow() {
		((DefaultTableModel)tbLoai.getModel()).setRowCount(0);
	}

	public void lamMoiDSLoaiThuoc() {
		List<NhomThuoc> arrayList = loaiThuocDAO.layDanhSachLoaiThuoc();
		loaiThuocs.clear();
		loaiThuocs.addAll(arrayList);
	}
	
	public void lamMoiDLieu(List<NhomThuoc> arrayList) {		
		loaiThuocs.clear();
		loaiThuocs.addAll(arrayList);
	}

	public void lamMoiBangLoaiThuoc() {
		tableModel.getDataVector().removeAllElements();
		Vector<String> vector;
		for (NhomThuoc loaiThuoc : loaiThuocs) {
			vector = new Vector<String>();
			vector.add(loaiThuoc.getMaNhomThuoc());
			vector.add(loaiThuoc.getTenNhomThuoc());
			tableModel.addRow(vector);
		}
	}
	
	public Boolean KiemTraTheoTen(String ten) {
		String regex = "^([a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ:,%+-]+)|([a-zA-Z0-9ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ:,%+-]+[\\s]*)+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ten);
		return matcher.matches();
	}
	
	public Boolean kiemTraDuLieu() {
		if(txtMa.getText().trim().equals("")) {
			JOptionPane.showConfirmDialog(this, "Mã loại thuốc không để trống");
			return false;
		}
		if(txtTen.getText().trim().equals("")) {
			JOptionPane.showConfirmDialog(this, "Tên loại thuốc không để trống");
			return false;
		}
		if(KiemTraTheoTen(txtTen.getText().trim())==false) {
			JOptionPane.showConfirmDialog(this, "Tên loại thuốc không đúng định dạng chữ");
			return false;
		}
		return true;
	}
	
	public void xuLyThemNT() {
		txtMa.setText(loaiThuocDAO.setCodeEmployees());
		if (kiemTraDuLieu() == false)
			return;
		NhomThuoc nhomThuoc = chuyenSangNhomThuoc();
		if (nhomThuoc == null)
			return;
		if (loaiThuocDAO.themNhomThuoc(nhomThuoc)) {
			JOptionPane.showMessageDialog(this, "Thêm nhóm thuốc thành công!");
			lamMoiUI();
		} else
			JOptionPane.showMessageDialog(this, "Thêm nhóm thuốc không thành công!");
	}
	
	public NhomThuoc chuyenSangNhomThuoc() {
		String ma = txtMa.getText().trim();
		String ten = txtTen.getText().trim();
		NhomThuoc nt = new NhomThuoc(ma, ten);
		return nt;
	}
	
	
	public void xuLyCapNhat() {
		if (tbLoai.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn nhóm thuốc cần cập nhật!");
			return;
		}
		if (kiemTraDuLieu() == false)
			return;
		NhomThuoc nhomThuoc = chuyenSangNhomThuoc();
		//khachHang.setMaKH(khachHangDAO.setCodeEmployees());
		if (nhomThuoc == null)
			return;
		if (loaiThuocDAO.suaNhomThuoc(nhomThuoc)) {
			JOptionPane.showMessageDialog(this, "Cập nhật nhóm thuốc thành công!");
			lamMoiUI();
		} else
			JOptionPane.showMessageDialog(this, "Cập nhật nhóm thuốc không thành công!");
	}

	public void xuLyXoa() {
		if (tbLoai.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn nhóm thuốc cần xóa!");
			return;
		}
		int xoa = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa nhóm thuốc","Thông báo",JOptionPane.YES_NO_OPTION);
		if(xoa == JOptionPane.YES_OPTION) {
			if (loaiThuocDAO.xoaNhomThuoc(txtMa.getText().trim())) {
				JOptionPane.showMessageDialog(this, "Xóa nhóm thuốc thành công!");
				lamMoiUI();
			} else
				JOptionPane.showMessageDialog(this, "Xóa nhóm thuốc không thành công!");
		}
	}
	
	public void XuLyTimKiem() {
		if(txtTim.getText().trim().equals("") ) {
			JOptionPane.showConfirmDialog(this, "Tên nhóm thuốc không để trống!");
			return;
		}
		if(KiemTraTheoTen(txtTim.getText().trim())==false) {
			JOptionPane.showConfirmDialog(this, "Tên nhóm thuốc phải có định dạng là chữ!");
			return;
		}
		if(!txtTim.getText().trim().equals("") && KiemTraTheoTen(txtTim.getText().trim())==true) {
			TimKiemTheoTen();
		}
		
	}
	
	public void TimKiemTheoTen() {
		List<NhomThuoc> dsHD = loaiThuocDAO.timKiemTheoTen(txtTim.getText().trim());
		if(dsHD.size() >0) {
			xoaRow();
			lamMoiDLieu(dsHD);
			lamMoiBangLoaiThuoc();
			txtTim.setText("");
		}
		else JOptionPane.showConfirmDialog(this, "Không tìm thấy nhóm thuốc!");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tbLoai.getSelectedRow();
		txtMa.setText(String.valueOf(tbLoai.getValueAt(row, 0)));
		txtTen.setText(String.valueOf(tbLoai.getValueAt(row, 1)));
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.equals(btnThem)) {
			xuLyThemNT();
		}
		if(obj.equals(btnCapNhat)) {
			xuLyCapNhat();
		}
		if(obj.equals(btnXoa)) {
			xuLyXoa();
		}
		if(obj.equals(btnLamMoi)) {
			lamMoiUI();
		}
		if(obj.equals(btnTim)) {
			XuLyTimKiem();
		}
		
	}


}
