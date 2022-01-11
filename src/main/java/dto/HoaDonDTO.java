package dto;

public class HoaDonDTO {
	
	private String maHD;
	private String ngayLHD;
	private String tenKH;
	private String sdtKH;
	private String email;
	private String diaChiKH;	
	private String tenNV;
	private String sdtNV;
	
	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public String getNgayLHD() {
		return ngayLHD;
	}

	public void setNgayLHD(String ngayLHD) {
		this.ngayLHD = ngayLHD;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public String getSdtKH() {
		return sdtKH;
	}

	public void setSdtKH(String sdtKH) {
		this.sdtKH = sdtKH;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiaChiKH() {
		return diaChiKH;
	}

	public void setDiaChiKH(String diaChiKH) {
		this.diaChiKH = diaChiKH;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public String getSdtNV() {
		return sdtNV;
	}

	public void setSdtNV(String sdtNV) {
		this.sdtNV = sdtNV;
	}

	
	
	public HoaDonDTO(String maHD, String ngayLHD, String tenKH, String sdtKH, String email, String diaChiKH,
			String tenNV, String sdtNV) {
		super();
		this.maHD = maHD;
		this.ngayLHD = ngayLHD;
		this.tenKH = tenKH;
		this.sdtKH = sdtKH;
		this.email = email;
		this.diaChiKH = diaChiKH;
		this.tenNV = tenNV;
		this.sdtNV = sdtNV;
	}
	
	

	@Override
	public String toString() {
		return "HoaDonDTO [maHD=" + maHD + ", ngayLHD=" + ngayLHD + ", tenKH=" + tenKH + ", sdtKH=" + sdtKH + ", email="
				+ email + ", diaChiKH=" + diaChiKH + ", tenNV=" + tenNV + ", sdtNV=" + sdtNV + "]";
	}

	public HoaDonDTO() {
		// TODO Auto-generated constructor stub
	}
}
