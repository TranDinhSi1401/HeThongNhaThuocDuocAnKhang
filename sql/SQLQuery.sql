USE master
GO

IF DB_ID('DuocAnKhang') IS NOT NULL
BEGIN
    ALTER DATABASE DuocAnKhang SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE DuocAnKhang;
END
GO

CREATE DATABASE DuocAnKhang
GO

USE DuocAnKhang
GO

CREATE TABLE NhanVien (
    maNV NVARCHAR(7) PRIMARY KEY,
    hoTenDem NVARCHAR(50) NOT NULL,
    ten NVARCHAR(20) NOT NULL,
    sdt VARCHAR(10) NOT NULL UNIQUE,
    cccd VARCHAR(12) NOT NULL UNIQUE,
    gioiTinh BIT NOT NULL, -- true: Nam, false: Nữ
    ngaySinh DATE NOT NULL,
    diaChi NVARCHAR(255) NULL,
    nghiViec BIT NOT NULL CONSTRAINT DF_NhanVien_NghiViec DEFAULT 0, -- true: Đã nghỉ việc, false: Đang làm việc
    CONSTRAINT CK_NhanVien_MaNV_Format CHECK (maNV LIKE 'NV-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_NhanVien_Sdt_Format CHECK (LEN(sdt) = 10 AND sdt LIKE '0%'),
    CONSTRAINT CK_NhanVien_Cccd_Format CHECK (LEN(cccd) = 12 AND ISNUMERIC(cccd) = 1),
    CONSTRAINT CK_NhanVien_NgaySinh_Age CHECK (DATEDIFF(YEAR, ngaySinh, GETDATE()) >= 18)
);
GO

CREATE TABLE CaLam (
    maCa NVARCHAR(10) PRIMARY KEY,
    tenCa NVARCHAR(50) NOT NULL,
    thoiGianBatDau TIME NOT NULL,
    thoiGianKetThuc TIME NOT NULL,
    CONSTRAINT CK_CaLam_ThoiGianHopLe CHECK (thoiGianKetThuc > thoiGianBatDau)
);
GO

CREATE TABLE NhaCungCap (
    maNCC NVARCHAR(8) PRIMARY KEY,
    tenNCC NVARCHAR(100) NOT NULL,
    diaChi NVARCHAR(255) NULL,
    sdt VARCHAR(10) NOT NULL UNIQUE,
    email NVARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT CK_NhaCungCap_MaNCC_Format CHECK (maNCC LIKE 'NCC-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_NhaCungCap_Sdt_Format CHECK (LEN(sdt) = 10 AND sdt LIKE '0%'),
    CONSTRAINT CK_NhaCungCap_Email_Format CHECK (email LIKE '%_@gmail.com')
);
GO

CREATE TABLE KhachHang (
    maKH NVARCHAR(8) PRIMARY KEY,
    hoTenDem NVARCHAR(50) NOT NULL,
    ten NVARCHAR(10) NOT NULL,
    sdt VARCHAR(10) NOT NULL UNIQUE,
    diemTichLuy INT NOT NULL CONSTRAINT DF_KhachHang_DiemTichLuy DEFAULT 0,
    CONSTRAINT CK_KhachHang_MaKH_Format CHECK (maKH LIKE 'KH-[0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_KhachHang_Sdt_Format CHECK (LEN(sdt) = 10 AND sdt LIKE '0%'),
    CONSTRAINT CK_KhachHang_DiemTichLuy CHECK (diemTichLuy >= 0)
);
GO

CREATE TABLE KhuyenMai (
    maKhuyenMai NVARCHAR(7) PRIMARY KEY,
    moTa NVARCHAR(255) NULL,
    phanTram DECIMAL(5, 2) NOT NULL,
    loaiKhuyenMai NVARCHAR(50) NOT NULL,
    ngayBatDau DATETIME2 NOT NULL,
    ngayKetThuc DATETIME2 NOT NULL,
    soLuongToiThieu INT NOT NULL,
    soLuongToiDa INT NOT NULL,
    ngayChinhSua DATETIME2 NOT NULL 
    CONSTRAINT DF_KhuyenMai_NgayChinhSua DEFAULT GETDATE(),
    CONSTRAINT CK_KhuyenMai_MaKM_Format CHECK (maKhuyenMai LIKE 'KM-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_KhuyenMai_PhanTram CHECK (phanTram > 0 AND phanTram <= 100),
    CONSTRAINT CK_KhuyenMai_LoaiKM_Enum CHECK (loaiKhuyenMai IN (N'SO_LUONG', N'MUA', N'NHA_SAN_XUAT', N'NGUNG_BAN')),
    CONSTRAINT CK_KhuyenMai_NgayHopLe CHECK (ngayKetThuc >= ngayBatDau),
    CONSTRAINT CK_KhuyenMai_SoLuongHopLe CHECK (soLuongToiDa >= soLuongToiThieu AND soLuongToiThieu > 0)
)
GO

CREATE TABLE TaiKhoan (
    tenDangNhap NVARCHAR(7) PRIMARY KEY,
    matKhau NVARCHAR(256) NOT NULL,
    quanLy BIT NOT NULL CONSTRAINT DF_TaiKhoan_QuanLy DEFAULT 0, -- true: Quản lý, false: Nhân viên
    biKhoa BIT NOT NULL CONSTRAINT DF_TaiKhoan_BiKhoa DEFAULT 1, -- true: Bị khóa, false: Hoạt động
    email NVARCHAR(255) NOT NULL UNIQUE,
    ngayTao DATETIME NOT NULL CONSTRAINT DF_TaiKhoan_NgayTao DEFAULT GETDATE(),
    CONSTRAINT FK_TaiKhoan_NhanVien FOREIGN KEY (tenDangNhap) REFERENCES NhanVien(maNV),
    CONSTRAINT CK_TaiKhoan_Email_Format CHECK (email LIKE '%_@gmail.com')
);
GO

CREATE TABLE SanPham (
    maSP NVARCHAR(7) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    moTa NVARCHAR(MAX) NOT NULL,
    thanhPhan NVARCHAR(MAX) NOT NULL,
    loaiSanPham NVARCHAR(50) NOT NULL,
    tonToiThieu INT NOT NULL,
    tonToiDa INT NOT NULL,
    CONSTRAINT CK_SanPham_MaSP_Format CHECK (maSP LIKE 'SP-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_SanPham_LoaiSanPham_Enum CHECK (loaiSanPham IN (N'THUOC', N'THUC_PHAM_CHUC_NANG')),
    CONSTRAINT CK_SanPham_TonKhoHopLe CHECK (tonToiDa >= tonToiThieu AND tonToiThieu >= 0)
);
GO

CREATE TABLE DonViTinh (
    maDonViTinh NVARCHAR(20) PRIMARY KEY,
    maSP NVARCHAR(7) NOT NULL,
    tenDonVi NVARCHAR(30) NOT NULL,
    heSoQuyDoi INT NOT NULL,
    giaBanTheoDonVi DECIMAL(18, 2) NOT NULL,
    donViTinhCoBan BIT NOT NULL,
    CONSTRAINT FK_DonViTinh_SanPham FOREIGN KEY (maSP) REFERENCES SanPham(maSP) ON DELETE CASCADE,
    CONSTRAINT CK_DVT_MaDVT_Format CHECK (maDonViTinh LIKE 'DVT-[0-9][0-9][0-9][0-9]-%'),
    CONSTRAINT CK_DVT_HeSoQuyDoi CHECK (heSoQuyDoi > 0),
    CONSTRAINT CK_DVT_GiaBan CHECK (giaBanTheoDonVi >= 0 AND giaBanTheoDonVi <= 20000000)
);
GO

CREATE TABLE LoSanPham (
    maLoSanPham NVARCHAR(50) PRIMARY KEY,
    maSP NVARCHAR(7) NOT NULL,
    soLuong INT NOT NULL,
    ngaySanXuat DATE NOT NULL,
    ngayHetHan DATE NOT NULL,
    CONSTRAINT FK_LoSanPham_SanPham FOREIGN KEY (maSP) REFERENCES SanPham(maSP) ON DELETE CASCADE,
    CONSTRAINT CK_LoSanPham_SoLuong CHECK (soLuong >= 0),
    CONSTRAINT CK_LoSanPham_NgayHopLe CHECK (ngayHetHan >= ngaySanXuat)
);
GO

CREATE TABLE SanPhamCungCap (
    maSP NVARCHAR(7) NOT NULL,
    maNCC NVARCHAR(8) NOT NULL,
    trangThaiHopTac BIT NOT NULL,
    giaNhap DECIMAL(18, 2) NOT NULL,
    CONSTRAINT PK_SanPhamCungCap PRIMARY KEY (maSP, maNCC),
    CONSTRAINT FK_SPCC_SanPham FOREIGN KEY (maSP) REFERENCES SanPham(maSP) ON DELETE CASCADE,
    CONSTRAINT FK_SPCC_NhaCungCap FOREIGN KEY (maNCC) REFERENCES NhaCungCap(maNCC) ON DELETE CASCADE,
    CONSTRAINT CK_SPCC_GiaNhap CHECK (giaNhap >= 0)
);
GO

CREATE TABLE KhuyenMai_SanPham (
    maKhuyenMai NVARCHAR(7) NOT NULL,
    maSP NVARCHAR(7) NOT NULL,
    ngayChinhSua DATETIME2 NOT NULL CONSTRAINT DF_KMSP_NgayChinhSua DEFAULT GETDATE(),
    CONSTRAINT PK_KhuyenMai_SanPham PRIMARY KEY (maKhuyenMai, maSP),
    CONSTRAINT FK_KMSP_KhuyenMai FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai) ON DELETE CASCADE,
    CONSTRAINT FK_KMSP_SanPham FOREIGN KEY (maSP) REFERENCES SanPham(maSP) ON DELETE CASCADE
);
GO

CREATE TABLE LichSuCaLam (
    maNV NVARCHAR(7) NOT NULL,
    maCa NVARCHAR(10) NOT NULL,
    ngayLamViec DATE NOT NULL CONSTRAINT DF_LichSuCaLam_NgayLamViec DEFAULT GETDATE(),
    thoiGianVaoCa TIME NOT NULL,
    thoiGianRaCa TIME NULL,
    ghiChu NVARCHAR(500) NULL,
    CONSTRAINT FK_LichSuCaLam_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    CONSTRAINT FK_LichSuCaLam_CaLam FOREIGN KEY (maCa) REFERENCES CaLam(maCa),
    CONSTRAINT CK_LichSuCaLam_ThoiGianRaCa CHECK (thoiGianRaCa > thoiGianVaoCa)
);
GO

CREATE TABLE PhieuDatHang (
    maPhieuDatHang NVARCHAR(15) PRIMARY KEY,
    ngayLap DATE NOT NULL CONSTRAINT DF_PhieuDatHang_NgayLap DEFAULT GETDATE(),
    maNCC NVARCHAR(8) NOT NULL,
    maNV NVARCHAR(7) NOT NULL,
    tongTien DECIMAL(18, 2) NOT NULL,
    CONSTRAINT CK_PhieuDatHang_MaPhieuDatHang_Format CHECK (maPhieuDatHang LIKE 'PDH-[0-9][0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT FK_PhieuDatHang_NhaCungCap FOREIGN KEY (maNCC) REFERENCES NhaCungCap(maNCC),
    CONSTRAINT FK_PhieuDatHang_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    CONSTRAINT CK_PhieuDatHang_TongTien CHECK (tongTien >= 0)
);
GO

CREATE TABLE ChiTietPhieuDat (
    maPhieuDatHang NVARCHAR(15) NOT NULL,
    maSP NVARCHAR(7) NOT NULL,
    soLuong INT NOT NULL,
    donGia DECIMAL(18, 2) NOT NULL,
    thanhTien AS (CAST(soLuong AS DECIMAL(18,2)) * donGia),
    CONSTRAINT PK_ChiTietPhieuDat PRIMARY KEY (maPhieuDatHang, maSP),
    CONSTRAINT FK_ChiTietPhieuDatHang_PhieuDatHang FOREIGN KEY (maPhieuDatHang) REFERENCES PhieuDatHang(maPhieuDatHang) ON DELETE CASCADE,
    CONSTRAINT FK_ChiTietPhieuDatHang_SanPham FOREIGN KEY (maSP) REFERENCES SanPham(maSP),
    CONSTRAINT CK_ChiTietPhieuDatHang_SoLuong CHECK (soLuong > 0 AND soLuong <= 999),
    CONSTRAINT CK_ChiTietPhieuDatHang_DonGia CHECK (donGia >= 0)
);
GO

CREATE TABLE HoaDon (
    maHoaDon NVARCHAR(14) PRIMARY KEY,
    maNV NVARCHAR(7) NOT NULL,
    ngayLapHoaDon DATETIME2 NOT NULL CONSTRAINT DF_HoaDon_NgayLap DEFAULT GETDATE(),
    maKH NVARCHAR(8) NOT NULL,
    chuyenKhoan BIT NOT NULL CONSTRAINT DF_HoaDon_ChuyenKhoan DEFAULT 0, -- false: Tiền mặt, true: Chuyển khoản
    trangThai BIT NOT NULL CONSTRAINT DF_HoaDon_TrangThai DEFAULT 0, -- false: Chưa thanh toán, true: Đã thanh toán
    tongTien DECIMAL(18, 2) NOT NULL,
    CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    CONSTRAINT FK_HoaDon_KhachHang FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    CONSTRAINT CK_HoaDon_MaHD_Format CHECK (maHoaDon LIKE 'HD-[0-9][0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_HoaDon_TongTien CHECK (tongTien > 0)
);
GO

CREATE TABLE ChiTietHoaDon (
    maChiTietHoaDon NVARCHAR(16) PRIMARY KEY,
    maHoaDon NVARCHAR(14) NOT NULL,
    maDonViTinh NVARCHAR(20) NOT NULL,
    soLuong INT NOT NULL,
    donGia DECIMAL(18, 2) NOT NULL,
    giamGia DECIMAL(5, 2) NOT NULL CONSTRAINT DF_CTHD_GiamGia DEFAULT 0,
    thanhTien AS CAST(soLuong AS DECIMAL(18,2)) * donGia * (1 - giamGia),
    
    CONSTRAINT FK_ChiTietHoaDon_HoaDon FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon) ON DELETE CASCADE,
    CONSTRAINT FK_ChiTietHoaDon_DonViTinh FOREIGN KEY (maDonViTinh) REFERENCES DonViTinh(maDonViTinh),
    CONSTRAINT CK_CTHD_MaCTHD_Format CHECK (maChiTietHoaDon LIKE 'CTHD-[0-9][0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_ChiTietHoaDon_SoLuong CHECK (soLuong > 0),
    CONSTRAINT CK_ChiTietHoaDon_DonGia CHECK (donGia > 0),
    CONSTRAINT CK_ChiTietHoaDon_GiamGia CHECK (giamGia >= 0 AND giamGia <= 1)
);
GO

CREATE TABLE ChiTietXuatLo (
    maLoSanPham NVARCHAR(50) NOT NULL,
    maChiTietHoaDon NVARCHAR(16) NOT NULL,
    soLuong INT NOT NULL,
    CONSTRAINT PK_ChiTietXuatLo PRIMARY KEY (maLoSanPham, maChiTietHoaDon),
    CONSTRAINT FK_CTXuatLo_LoSanPham FOREIGN KEY (maLoSanPham) REFERENCES LoSanPham(maLoSanPham),
    CONSTRAINT FK_CTXuatLo_ChiTietHoaDon FOREIGN KEY (maChiTietHoaDon) REFERENCES ChiTietHoaDon(maChiTietHoaDon),
    CONSTRAINT CK_CTXuatLo_SoLuong CHECK (soLuong >= 0)
);
GO

CREATE TABLE PhieuTraHang (
    maPhieuTraHang NVARCHAR(15) PRIMARY KEY,
    ngayLapPhieuTraHang DATETIME2 NOT NULL CONSTRAINT DF_PhieuTraHang_NgayLap DEFAULT GETDATE(),
    maNV NVARCHAR(7) NOT NULL,
    maHoaDon NVARCHAR(14) NOT NULL,
    tongTienHoanTra DECIMAL(18, 2) NOT NULL,
    CONSTRAINT FK_PhieuTraHang_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    CONSTRAINT FK_PhieuTraHang_HoaDon FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    CONSTRAINT CK_PTH_MaPTH_Format CHECK (maPhieuTraHang LIKE 'PTH-[0-9][0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_PhieuTraHang_TongTien CHECK (tongTienHoanTra >= 0)
);
GO

CREATE TABLE ChiTietPhieuTraHang (
    maPhieuTraHang NVARCHAR(15) NOT NULL,
    maChiTietHoaDon NVARCHAR(16) NOT NULL,
    soLuong INT NOT NULL,
    truongHopDoiTra NVARCHAR(50) NOT NULL,
    tinhTrangSanPham NVARCHAR(30) NOT NULL,
    giaTriHoanTra NVARCHAR(20) NOT NULL,
    thanhTienHoanTra DECIMAL(18, 2) NOT NULL,
    CONSTRAINT PK_ChiTietPhieuTraHang PRIMARY KEY (maPhieuTraHang, maChiTietHoaDon),
    CONSTRAINT FK_CTPTH_PhieuTraHang FOREIGN KEY (maPhieuTraHang) REFERENCES PhieuTraHang(maPhieuTraHang) ON DELETE CASCADE,
    CONSTRAINT FK_CTPTH_ChiTietHoaDon FOREIGN KEY (maChiTietHoaDon) REFERENCES ChiTietHoaDon(maChiTietHoaDon),
    CONSTRAINT CK_CTPTH_SoLuong CHECK (soLuong >= 0 AND soLuong <= 999),
    CONSTRAINT CK_CTPTH_TruongHopDoiTra_Enum CHECK (truongHopDoiTra IN (N'HANG_LOI_DO_NHA_SAN_XUAT', N'DI_UNG_MAN_CAM', N'NHU_CAU_KHACH_HANG')),
    CONSTRAINT CK_CTPTH_TinhTrangSanPham_Enum CHECK (tinhTrangSanPham IN (N'HANG_NGUYEN_VEN', N'HANG_KHONG_NGUYEN', N'HANG_DA_SU_DUNG')),
    CONSTRAINT CK_CTPTH_GiaTriHoanTra_Enum CHECK (giaTriHoanTra IN (N'100%', N'70%', N'Miễn trả hàng')),
    CONSTRAINT CK_CTPTH_ThanhTienHoanTra CHECK (thanhTienHoanTra >= 0)
);
GO

-- ===================================================================
-- 1. Bảng NhanVien (4 người)
-- ===================================================================
INSERT INTO NhanVien (maNV, hoTenDem, ten, sdt, cccd, gioiTinh, ngaySinh, diaChi, nghiViec) VALUES
('NV-0001', N'Võ Tiến', N'Khoa', '0905123456', '001085001234', 1, '2005-02-24', N'123 Lê Duẩn, Quận Hải Châu, Đà Nẵng', 0),
('NV-0002', N'Hồ Minh', N'Khang', '0913789012', '001090005678', 1, '1995-10-20', N'456 Hùng Vương, Quận Thanh Khê, Đà Nẵng', 0),
('NV-0003', N'Nguyễn Khánh', N'Quân', '0988111222', '001200003344', 1, '2000-02-01', N'789 Ngô Quyền, Quận Sơn Trà, Đà Nẵng', 0),
('NV-0004', N'Trần Đình', N'Sĩ', '0977333444', '001198007788', 1, '1998-11-30', N'K120/05 Trần Cao Vân, Quận Thanh Khê, Đà Nẵng', 0);
GO

-- ===================================================================
-- 2. Bảng CaLam (2 ca làm)
-- ===================================================================
INSERT INTO CaLam (maCa, tenCa, thoiGianBatDau, thoiGianKetThuc) VALUES
('SANG', N'Ca Sáng (07:00 - 15:00)', '07:00:00', '15:00:00'),
('TOI', N'Ca Tối (15:00 - 22:00)', '15:00:00', '22:00:00');
GO

-- ===================================================================
-- 3. Bảng NhaCungCap (10 nhà cung cấp)
-- ===================================================================
INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, sdt, email) VALUES
('NCC-0001', N'Công ty CP Dược Hậu Giang', N'288 Bis Nguyễn Văn Cừ, An Hòa, Ninh Kiều, Cần Thơ', '0901111001', 'dhgpharma.contact@gmail.com'),
('NCC-0002', N'Công ty CP Traphaco', N'75 Yên Ninh, Ba Đình, Hà Nội', '0901111002', 'traphaco.contact@gmail.com'),
('NCC-0003', N'Công ty CP Imexpharm', N'Số 4, Đường 30/4, Phường 1, TP. Cao Lãnh, Đồng Tháp', '0901111003', 'imexpharm.contact@gmail.com'),
('NCC-0004', N'Công ty CP Pymepharco', N'166 – 170 Nguyễn Huệ, Tuy Hòa, Phú Yên', '0901111004', 'pymepharco.contact@gmail.com'),
('NCC-0005', N'Công ty Sanofi Việt Nam', N'Lô I-8-2, Đường D8, Khu Công nghệ cao, Q.9, TP. HCM', '0901111005', 'sanovivn.contact@gmail.com'),
('NCC-0006', N'Zuellig Pharma Việt Nam', N'Lô G, KCN Tân Đông Hiệp B, Dĩ An, Bình Dương', '0901111006', 'zuelligvn.contact@gmail.com'),
('NCC-0007', N'Eco Pharma (Dược phẩm Eco)', N'180 Trường Chinh, P. Khương Thượng, Q. Đống Đa, Hà Nội', '0901111007', 'ecopharma.contact@gmail.com'),
('NCC-0008', N'Domesco Medical Import Export JSC', N'66 Quốc lộ 30, Phường Mỹ Phú, TP. Cao Lãnh, Đồng Tháp', '0901111008', 'domesco.contact@gmail.com'),
('NCC-0009', N'Công ty CP Dược - TB Y tế Bình Định (Bidiphar)', N'498 Nguyễn Thái Học, TP. Quy Nhơn, Bình Định', '0901111009', 'bidiphar.contact@gmail.com'),
('NCC-0010', N'Công ty CP Hoá - Dược phẩm Mekophar', N'297/5 Lý Thường Kiệt, Phường 15, Quận 11, TP. HCM', '0901111010', 'mekophar.contact@gmail.com');
GO

-- ===================================================================
-- 4. Bảng KhachHang (50 khách hàng)
-- ===================================================================
INSERT INTO KhachHang (maKH, hoTenDem, ten, sdt, diemTichLuy) VALUES
('KH-00001', N'Nguyễn Thị', N'Lan', '0909000001', 50),
('KH-00002', N'Trần Văn', N'Hùng', '0909000002', 120),
('KH-00003', N'Lê Thị', N'Mai', '0909000003', 0),
('KH-00004', N'Phạm Văn', N'Tuấn', '0909000004', 35),
('KH-00005', N'Hoàng Thị', N'Thu', '0909000005', 200),
('KH-00006', N'Vũ Văn', N'Nam', '0909000006', 75),
('KH-00007', N'Đặng Thị', N'Hương', '0909000007', 0),
('KH-00008', N'Bùi Văn', N'Minh', '0909000008', 90),
('KH-00009', N'Đỗ Thị', N'Linh', '0909000009', 150),
('KH-00010', N'Ngô Văn', N'Sơn', '0909000010', 0),
('KH-00011', N'Trịnh Thị', N'Hà', '0909000011', 5),
('KH-00012', N'Lý Văn', N'Hải', '0909000012', 42),
('KH-00013', N'Vương Thị', N'Yến', '0909000013', 300),
('KH-00014', N'Dương Văn', N'Phong', '0909000014', 10),
('KH-00015', N'Mai Thị', N'Nga', '0909000015', 0),
('KH-00016', N'Tô Văn', N'Tài', '0909000016', 88),
('KH-00017', N'Hồ Thị', N'Cúc', '0909000017', 12),
('KH-00018', N'Châu Văn', N'Kiệt', '0909000018', 0),
('KH-00019', N'Đinh Thị', N'Trang', '0909000019', 55),
('KH-00020', N'Đoàn Văn', N'Dũng', '0909000020', 210),
('KH-00021', N'Lâm Thị', N'Thảo', '0909000021', 0),
('KH-00022', N'Phùng Văn', N'Long', '0909000022', 17),
('KH-00023', N'Giang Thị', N'Huyền', '0909000023', 63),
('KH-00024', N'Huỳnh Văn', N'Đức', '0909000024', 0),
('KH-00025', N'Lưu Thị', N'Diệp', '0909000025', 99),
('KH-00026', N'Mạc Văn', N'Trung', '0909000026', 130),
('KH-00027', N'Nguyễn Trần', N'Anh', '0909000027', 0),
('KH-00028', N'Phan Lê', N'Quỳnh', '0909000028', 25),
('KH-00029', N'Thái Văn', N'Bảo', '0909000029', 80),
('KH-00030', N'Trần Lê', N'Uyên', '0909000030', 0),
('KH-00031', N'Võ Hoàng', N'Tú', '0909000031', 45),
('KH-00032', N'Lê Nguyễn', N'Phúc', '0909000032', 112),
('KH-00033', N'Ngô Đặng', N'My', '0909000033', 0),
('KH-00034', N'Vương Đình', N'Khánh', '0909000034', 22),
('KH-00035', N'HoàngPhúc', N'Thịnh', '0909000035', 77),
('KH-00036', N'Bùi Trần', N'Ngọc', '0909000036', 0),
('KH-00037', N'Cao Minh', N'Tâm', '0909000037', 180),
('KH-00038', N'Đàm Vĩnh', N'Phát', '0909000038', 33),
('KH-00039', N'Đinh Gia', N'Hân', '0909000039', 0),
('KH-00040', N'Hà Tuấn', N'Vũ', '0909000040', 68),
('KH-00041', N'Kiều Thị', N'Trinh', '0909000041', 105),
('KH-00042', N'Lại Văn', N'Quyết', '0909000042', 0),
('KH-00043', N'Lương Thị', N'Dịu', '0909000043', 52),
('KH-00044', N'Ngụy Văn', N'Hiếu', '0909000044', 19),
('KH-00045', N'Ôn Thị', N'Như', '0909000045', 0),
('KH-00046', N'Quách Văn', N'Tân', '0909000046', 91),
('KH-00047', N'Sơn Thị', N'Kim', '0909000047', 240),
('KH-00048', N'Thạch Văn', N'Phi', '0909000048', 0),
('KH-00049', N'Tiêu Thị', N'Ngân', '0909000049', 14),
('KH-00050', N'Uông Văn', N'Đạt', '0909000050', 38),
('KH-99999', N'Khách', N'Vãng Lai', '0000000000', 0);
GO

-- ===================================================================
-- 5. Bảng KhuyenMai (10 khuyến mãi)
-- ===================================================================
INSERT INTO KhuyenMai (maKhuyenMai, moTa, phanTram, loaiKhuyenMai, ngayBatDau, ngayKetThuc, soLuongToiThieu, soLuongToiDa, ngayChinhSua) VALUES
('KM-0001', N'Giảm 10% khi mua từ 3 sản phẩm', 10.00, N'SO_LUONG', '2025-10-01 00:00:00', '2025-10-31 23:59:59', 3, 999, GETDATE()),
('KM-0002', N'Mua 5 tặng 1 (Giảm 20%)', 20.00, N'MUA', '2025-10-15 00:00:00', '2025-11-15 23:59:59', 6, 6, GETDATE()),
('KM-0003', N'Giảm 15% cho sản phẩm của Traphaco (Đã hết hạn)', 15.00, N'NHA_SAN_XUAT', '2025-09-01 00:00:00', '2025-09-30 23:59:59', 1, 999, '2025-09-01 08:00:00'),
('KM-0004', N'Giảm giá Black Friday 20% (Sắp diễn ra)', 20.00, N'SO_LUONG', '2025-11-28 00:00:00', '2025-11-30 23:59:59', 1, 999, GETDATE()),
('KM-0005', N'Xả hàng cận date giảm 50%', 50.00, N'NGUNG_BAN', '2025-10-20 00:00:00', '2025-10-25 23:59:59', 1, 10, GETDATE()),
('KM-0006', N'Giảm 30% cho Thực phẩm chức năng (TPCN)', 30.00, N'SO_LUONG', '2025-10-01 00:00:00', '2025-10-31 23:59:59', 1, 999, GETDATE()),
('KM-0007', N'Mua 2 Tặng 1 (Giảm 33.33%)', 33.33, N'MUA', '2025-10-10 00:00:00', '2025-11-10 23:59:59', 3, 3, GETDATE()),
('KM-0008', N'Giảm 10% cho sản phẩm của Sanofi', 10.00, N'NHA_SAN_XUAT', '2025-10-15 00:00:00', '2025-11-15 23:59:59', 1, 999, GETDATE()),
('KM-0009', N'Giảm 5% tổng hóa đơn (chạy cả năm)', 5.00, N'MUA', '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1, 1000, GETDATE()),
('KM-0010', N'Giảm 100% (miễn phí) cho hàng tặng', 100.00, N'MUA', '2025-01-01 00:00:00', '2025-12-31 23:59:59', 1, 1, GETDATE());
GO

-- ===================================================================
-- 6. Bảng SanPham (100 sản phẩm)
-- ===================================================================
INSERT INTO SanPham (maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa) VALUES
('SP-0001', N'Paracetamol 500mg (Hộp 10 vỉ x 10 viên)', N'Thuốc giảm đau, hạ sốt không kê đơn.', N'Paracetamol 500mg', N'THUOC', 50, 300),
('SP-0002', N'Amoxicillin 500mg (Hộp 10 vỉ x 10 viên)', N'Kháng sinh Penicillin điều trị nhiễm khuẩn (kê đơn).', N'Amoxicillin 500mg', N'THUOC', 20, 100),
('SP-0003', N'Vitamin C 500mg (Tuýp 20 viên sủi)', N'Bổ sung Vitamin C, tăng cường đề kháng.', N'Ascorbic Acid 500mg', N'THUC_PHAM_CHUC_NANG', 100, 500),
('SP-0004', N'Omega 3 Fish Oil 1000mg (Lọ 100 viên)', N'Bổ sung Omega 3, tốt cho mắt và tim mạch.', N'Dầu cá 1000mg (chứa EPA, DHA)', N'THUC_PHAM_CHUC_NANG', 30, 150),
('SP-0005', N'Berberin 100mg (Lọ 100 viên)', N'Điều trị tiêu chảy, lỵ trực khuẩn.', N'Berberin Clorid 100mg', N'THUOC', 50, 200),
('SP-0006', N'Atorvastatin 20mg (Hộp 3 vỉ x 10 viên)', N'Thuốc điều trị mỡ máu (kê đơn).', N'Atorvastatin 20mg', N'THUOC', 15, 80),
('SP-0007', N'Blackmores Glucosamine 1500mg (Lọ 180 viên)', N'Hỗ trợ xương khớp, giảm đau viêm khớp.', N'Glucosamine Sulfate 1500mg', N'THUC_PHAM_CHUC_NANG', 20, 100),
('SP-0008', N'Hoạt huyết dưỡng não Traphaco (Hộp 5 vỉ x 20 viên)', N'Bổ não, tăng cường tuần hoàn máu não.', N'Cao Đinh lăng, Cao Bạch quả', N'THUC_PHAM_CHUC_NANG', 40, 250),
('SP-0009', N'Clorpheniramin 4mg (Vỉ 20 viên)', N'Thuốc chống dị ứng, sổ mũi.', N'Clorpheniramin Maleat 4mg', N'THUOC', 100, 500),
('SP-0010', N'Canxi Corbiere 5ml (Hộp 30 ống)', N'Bổ sung Canxi cho trẻ em, bà bầu.', N'Calcium Glucoheptonate', N'THUC_PHAM_CHUC_NANG', 30, 120),
('SP-0011', N'Losartan 50mg (Hộp 3 vỉ x 10 viên)', N'Thuốc điều trị cao huyết áp (kê đơn).', N'Losartan Potassium 50mg', N'THUOC', 20, 100),
('SP-0012', N'Siro ho Prospan (Chai 100ml)', N'Trị ho long đờm chiết xuất lá thường xuân.', N'Cao khô lá thường xuân', N'THUOC', 50, 200),
('SP-0013', N'Men vi sinh Bifina R (Hộp 20 gói)', N'Bổ sung lợi khuẩn đường ruột.', N'Bifidobacterium, Lactobacillus', N'THUC_PHAM_CHUC_NANG', 25, 100),
('SP-0014', N'Oresol 245 (Hộp 20 gói)', N'Bù nước và điện giải khi bị tiêu chảy, sốt.', N'Glucose khan, Natri Clorid, Kali Clorid', N'THUOC', 100, 400),
('SP-0015', N'Salbutamol 2mg (Hộp 10 vỉ x 10 viên)', N'Thuốc giãn phế quản, trị hen suyễn (kê đơn).', N'Salbutamol 2mg', N'THUOC', 30, 80),
('SP-0016', N'Sắt Ferrovit (Hộp 5 vỉ x 10 viên)', N'Bổ sung sắt, acid folic cho người thiếu máu.', N'Sắt Fumarat, Acid Folic, Vitamin B12', N'THUC_PHAM_CHUC_NANG', 40, 150),
('SP-0017', N'Cialis 20mg (Vỉ 2 viên)', N'Điều trị rối loạn cương dương (kê đơn).', N'Tadalafil 20mg', N'THUOC', 10, 50),
('SP-0018', N'Boganic (Hộp 5 vỉ x 10 viên)', N'Thanh nhiệt, giải độc gan, mát gan.', N'Cao Actiso, Cao Rau đắng đất, Cao Bìm bìm', N'THUC_PHAM_CHUC_NANG', 60, 300),
('SP-0019', N'Omeprazol 20mg (Lọ 14 viên)', N'Điều trị viêm loét dạ dày, trào ngược (kê đơn).', N'Omeprazole 20mg', N'THUOC', 30, 150),
('SP-0020', N'Ginkgo Biloba 120mg (Lọ 60 viên)', N'Bổ não, cải thiện trí nhớ.', N'Chiết xuất Bạch quả 120mg', N'THUC_PHAM_CHUC_NANG', 50, 200),
('SP-0021', N'Metformin 500mg (Hộp 10 vỉ x 10 viên)', N'Thuốc điều trị tiểu đường Type 2 (kê đơn).', N'Metformin HCl 500mg', N'THUOC', 40, 120),
('SP-0022', N'Decolgen Forte (Hộp 25 vỉ x 4 viên)', N'Trị cảm cúm, sốt, nghẹt mũi.', N'Paracetamol, Phenylephrin, Clorpheniramin', N'THUOC', 80, 400),
('SP-0023', N'Nature Made Vitamin E 400 IU (Lọ 100 viên)', N'Bổ sung Vitamin E, đẹp da, chống oxy hóa.', N'Vitamin E 400 IU', N'THUC_PHAM_CHUC_NANG', 30, 90),
('SP-0024', N'Alprazolam 0.5mg (Hộp 3 vỉ x 10 viên)', N'Thuốc an thần, trị lo âu (kê đơn, kiểm soát đặc biệt).', N'Alprazolam 0.5mg', N'THUOC', 10, 30),
('SP-0025', N'Betadine 10% (Chai 125ml)', N'Dung dịch sát khuẩn vết thương.', N'Povidone-Iodine 10%', N'THUOC', 100, 300),
('SP-0026', N'Centrum Silver 50+ (Lọ 125 viên)', N'Vitamin tổng hợp cho người trên 50 tuổi.', N'Vitamin (A, B, C, D, E, K), Khoáng chất', N'THUC_PHAM_CHUC_NANG', 20, 80),
('SP-0027', N'Ciprofloxacin 500mg (Hộp 2 vỉ x 10 viên)', N'Kháng sinh Quinolon (kê đơn).', N'Ciprofloxacin 500mg', N'THUOC', 15, 60),
('SP-0028', N'Efferagan 500mg (Hộp 4 tuýp x 10 viên sủi)', N'Giảm đau, hạ sốt (viên sủi).', N'Paracetamol 500mg', N'THUOC', 40, 150),
('SP-0029', N'Collagen AEC 12000mg (Hộp 10 lọ)', N'Bổ sung collagen, làm đẹp da.', N'Collagen thủy phân, Vitamin C', N'THUC_PHAM_CHUC_NANG', 10, 50),
('SP-0030', N'Prednison 5mg (Lọ 200 viên)', N'Thuốc Corticoid kháng viêm (kê đơn).', N'Prednisolone 5mg', N'THUOC', 30, 100),
('SP-0031', N'Dầu gió xanh Thiên Thảo (Chai 24ml)', N'Giảm đau đầu, sổ mũi, say tàu xe.', N'Menthol, Methyl Salicylate, Eucalyptol', N'THUOC', 200, 1000),
('SP-0032', N'Sữa Ensure Gold (Lon 850g)', N'Dinh dưỡng bổ sung cho người lớn tuổi, người bệnh.', N'Đạm, Chất béo, Vitamin, Khoáng chất', N'THUC_PHAM_CHUC_NANG', 50, 150),
('SP-0033', N'Aspirin 81mg (Lọ 100 viên)', N'Thuốc chống kết tập tiểu cầu (kê đơn).', N'Acid Acetylsalicylic 81mg', N'THUOC', 40, 200),
('SP-0034', N'Tiffy (Hộp 25 vỉ x 4 viên)', N'Trị cảm cúm, sốt, ho, sổ mũi.', N'Paracetamol, Clorpheniramin, Phenylpropanolamin', N'THUOC', 100, 500),
('SP-0035', N'One A Day Men''s Health (Lọ 100 viên)', N'Vitamin tổng hợp cho nam giới.', N'Vitamin, Khoáng chất (Kẽm, Selen...)', N'THUC_PHAM_CHUC_NANG', 15, 60),
('SP-0036', N'Domperidon 10mg (Hộp 10 vỉ x 10 viên)', N'Điều trị nôn, buồn nôn.', N'Domperidone 10mg', N'THUOC', 30, 150),
('SP-0037', N'Dầu cá Nature''s Bounty 1200mg (Lọ 200 viên)', N'Bổ sung Omega 3.', N'Dầu cá 1200mg', N'THUC_PHAM_CHUC_NANG', 20, 80),
('SP-0038', N'Telfast 180mg (Hộp 1 vỉ x 10 viên)', N'Thuốc chống dị ứng thế hệ mới.', N'Fexofenadine 180mg', N'THUOC', 50, 200),
('SP-0039', N'Cephalexin 500mg (Hộp 10 vỉ x 10 viên)', N'Kháng sinh Cephalosporin (kê đơn).', N'Cephalexin 500mg', N'THUOC', 25, 100),
('SP-0040', N'Viên uống DHC rau củ (Gói 60 ngày)', N'Bổ sung chất xơ từ rau củ.', N'Bột chiết xuất 32 loại rau củ', N'THUC_PHAM_CHUC_NANG', 60, 200),
('SP-0041', N'Diazepam 5mg (Hộp 10 vỉ x 10 viên)', N'Thuốc an thần, gây ngủ (kê đơn, KSTT).', N'Diazepam 5mg', N'THUOC', 10, 40),
('SP-0042', N'Urgo (Hộp 20 miếng)', N'Băng dán cá nhân.', N'Băng vải co giãn, gạc', N'THUC_PHAM_CHUC_NANG', 200, 800),
('SP-0043', N'Melatonin 10mg (Lọ 60 viên)', N'Hỗ trợ điều hòa giấc ngủ.', N'Melatonin 10mg', N'THUC_PHAM_CHUC_NANG', 30, 70),
('SP-0044', N'Panadol Extra (Hộp 10 vỉ x 12 viên)', N'Giảm đau, hạ sốt (chứa Cafein).', N'Paracetamol 500mg, Caffeine 65mg', N'THUOC', 100, 500),
('SP-0045', N'Enervon-C (Hộp 10 vỉ x 10 viên)', N'Bổ sung Vitamin B, C.', N'Vitamin B-complex, Vitamin C', N'THUC_PHAM_CHUC_NANG', 50, 300),
('SP-0046', N'Nước muối sinh lý Natri Clorid 0.9% (Chai 500ml)', N'Rửa mắt, mũi, súc miệng, rửa vết thương.', N'Natri Clorid 0.9%', N'THUOC', 300, 1500),
('SP-0047', N'Crest 3D White (Tuýp 116g)', N'Kem đánh răng làm trắng răng.', N'Sodium Fluoride, Hydrated Silica', N'THUC_PHAM_CHUC_NANG', 50, 150),
('SP-0048', N'Strepsils (Hộp 24 viên ngậm)', N'Viên ngậm sát khuẩn, giảm đau họng.', N'Amylmetacresol, Dichlorobenzyl Alcohol', N'THUOC', 150, 600),
('SP-0049', N'Viên uống mọc tóc Biotin 10000mcg (Lọ 100 viên)', N'Hỗ trợ mọc tóc, móng chắc khỏe.', N'Biotin 10000mcg', N'THUC_PHAM_CHUC_NANG', 20, 80),
('SP-0050', N'Phosphalugel (Hộp 26 gói)', N'Thuốc chữ P, trị đau dạ dày.', N'Aluminium Phosphate 20%', N'THUOC', 80, 250),
('SP-0051', N'Doxycyclin 100mg (Hộp 10 vỉ x 10 viên)', N'Kháng sinh (kê đơn).', N'Doxycycline 100mg', N'THUOC', 30, 100),
('SP-0052', N'Bảo Xuân Gold (Hộp 3 vỉ x 10 viên)', N'Cân bằng nội tiết tố nữ.', N'Tinh chất mầm đậu nành, Collagen', N'THUC_PHAM_CHUC_NANG', 40, 120),
('SP-0053', N'Nifedipin 20mg (Hộp 3 vỉ x 10 viên)', N'Thuốc hạ huyết áp (kê đơn).', N'Nifedipine 20mg', N'THUOC', 15, 70),
('SP-0054', N'Kem chống nắng La Roche-Posay Anthelios (Tuýp 50ml)', N'Kem chống nắng vật lý lai hóa học.', N'Mexoryl XL, Titanium Dioxide', N'THUC_PHAM_CHUC_NANG', 30, 100),
('SP-0055', N'Ibuprofen 400mg (Hộp 10 vỉ x 10 viên)', N'Thuốc kháng viêm non-steroid (NSAID), giảm đau.', N'Ibuprofen 400mg', N'THUOC', 50, 200),
('SP-0056', N'Sữa ong chúa 1600mg (Lọ 365 viên)', N'Bồi bổ sức khỏe, đẹp da.', N'Sữa ong chúa tươi', N'THUC_PHAM_CHUC_NANG', 10, 40),
('SP-0057', N'Levothyroxin 100mcg (Hộp 4 vỉ x 25 viên)', N'Thuốc hormone tuyến giáp (kê đơn).', N'Levothyroxine Sodium 100mcg', N'THUOC', 10, 50),
('SP-0058', N'Oxy già 3% (Chai 500ml)', N'Dung dịch sát khuẩn, rửa vết thương.', N'Hydrogen Peroxide 3%', N'THUOC', 100, 400),
('SP-0059', N'C sủi Plusssz (Tuýp 20 viên)', N'Bổ sung Vitamin C và Kẽm.', N'Vitamin C, Kẽm', N'THUC_PHAM_CHUC_NANG', 80, 300),
('SP-0060', N'Warfarin 5mg (Lọ 100 viên)', N'Thuốc chống đông máu (kê đơn).', N'Warfarin Sodium 5mg', N'THUOC', 5, 20),
('SP-0061', N'Băng gạc y tế (Gói 10 miếng)', N'Băng gạc vô trùng.', N'Vải không dệt, bông', N'THUC_PHAM_CHUC_NANG', 300, 1000),
('SP-0062', N'Tinh dầu tràm (Chai 50ml)', N'Giữ ấm, chống cảm, xua đuổi côn trùng.', N'Cineol (từ tinh dầu tràm)', N'THUC_PHAM_CHUC_NANG', 50, 150),
('SP-0063', N'Furosemid 40mg (Hộp 5 vỉ x 10 viên)', N'Thuốc lợi tiểu (kê đơn).', N'Furosemide 40mg', N'THUOC', 30, 100),
('SP-0064', N'Panadol Cảm Cúm (Hộp 10 vỉ x 12 viên)', N'Giảm triệu chứng cảm cúm.', N'Paracetamol, Phenylephrin, Vitamin C', N'THUOC', 100, 400),
('SP-0065', N'Yến sào Sanest (Hộp 6 lọ)', N'Bồi bổ sức khỏe.', N'Yến sào, đường phèn', N'THUC_PHAM_CHUC_NANG', 20, 60),
('SP-0066', N'Amlodipin 5mg (Hộp 10 vỉ x 10 viên)', N'Thuốc hạ huyết áp (kê đơn).', N'Amlodipine 5mg', N'THUOC', 60, 200),
('SP-0067', N'Kim tiền thảo (Hộp 100 viên)', N'Hỗ trợ điều trị sỏi thận.', N'Cao Kim tiền thảo', N'THUC_PHAM_CHUC_NANG', 30, 90),
('SP-0068', N'Smecta (Hộp 30 gói)', N'Điều trị tiêu chảy cấp.', N'Diosmectite 3g', N'THUOC', 50, 150),
('SP-0069', N'Tinh chất hàu Úc (Lọ 60 viên)', N'Tăng cường sinh lý nam.', N'Chiết xuất hàu, Kẽm', N'THUC_PHAM_CHUC_NANG', 10, 40),
('SP-0070', N'Voltaren Emulgel 1% (Tuýp 20g)', N'Kem bôi giảm đau, kháng viêm tại chỗ.', N'Diclofenac Diethylammonium 1%', N'THUOC', 70, 200),
('SP-0071', N'Azithromycin 500mg (Hộp 1 vỉ x 3 viên)', N'Kháng sinh (kê đơn).', N'Azithromycin 500mg', N'THUOC', 40, 100),
('SP-0072', N'Bột nghệ Curcumin (Lọ 250g)', N'Hỗ trợ dạ dày, làm đẹp da.', N'Tinh bột nghệ (Curcumin)', N'THUC_PHAM_CHUC_NANG', 20, 50),
('SP-0073', N'Cồn 90 độ (Chai 500ml)', N'Sát khuẩn dụng cụ, bề mặt.', N'Ethanol 90%', N'THUOC', 200, 600),
('SP-0074', N'Viên uống chống nắng (Lọ 60 viên)', N'Bảo vệ da khỏi tia UV từ bên trong.', N'Chiết xuất lựu, dương xỉ', N'THUC_PHAM_CHUC_NANG', 10, 30),
('SP-0075', N'Loratadin 10mg (Hộp 1 vỉ x 10 viên)', N'Thuốc chống dị ứng.', N'Loratadine 10mg', N'THUOC', 100, 400),
('SP-0076', N'Trà gừng (Hộp 20 gói)', N'Làm ấm cơ thể, hỗ trợ tiêu hóa.', N'Gừng, đường', N'THUC_PHAM_CHUC_NANG', 50, 150),
('SP-0077', N'Insulin (Bút tiêm)', N'Thuốc điều trị tiểu đường (kê đơn, bảo quản lạnh).', N'Insulin Human', N'THUOC', 5, 20),
('SP-0078', N'Erythromycin 500mg (Hộp 10 vỉ x 10 viên)', N'Kháng sinh (kê đơn).', N'Erythromycin 500mg', N'THUOC', 20, 80),
('SP-0079', N'Dầu gội Nizoral (Chai 100ml)', N'Trị gàu, nấm da đầu.', N'Ketoconazole 2%', N'THUOC', 30, 90),
('SP-0080', N'Sâm Alipas (Lọ 60 viên)', N'Tăng cường sinh lực phái mạnh.', N'Eurycoma Longifolia, Tinh chất hàu', N'THUC_PHAM_CHUC_NANG', 15, 50),
('SP-0081', N'Cetirizin 10mg (Hộp 10 vỉ x 10 viên)', N'Thuốc chống dị ứng.', N'Cetirizine 10mg', N'THUOC', 80, 300),
('SP-0082', N'Bình sữa Comotomo (250ml)', N'Bình sữa cho trẻ sơ sinh.', N'Silicon y tế', N'THUC_PHAM_CHUC_NANG', 20, 60),
('SP-0083', N'Gabapentin 300mg (Hộp 3 vỉ x 10 viên)', N'Thuốc điều trị đau thần kinh (kê đơn).', N'Gabapentin 300mg', N'THUOC', 10, 40),
('SP-0084', N'Dầu cám gạo (Chai 1 lít)', N'Dầu ăn tốt cho tim mạch.', N'Dầu cám gạo nguyên chất', N'THUC_PHAM_CHUC_NANG', 10, 30),
('SP-0085', N'Movicol (Hộp 20 gói)', N'Thuốc nhuận tràng, trị táo bón.', N'Macrogol 3350', N'THUOC', 25, 75),
('SP-0086', N'Viên uống Lutein (Lọ 60 viên)', N'Bổ mắt, tăng cường thị lực.', N'Lutein, Zeaxanthin', N'THUC_PHAM_CHUC_NANG', 15, 45),
('SP-0087', N'Montelukast 10mg (Hộp 3 vỉ x 10 viên)', N'Thuốc dự phòng hen suyễn (kê đơn).', N'Montelukast 10mg', N'THUOC', 10, 30),
('SP-0088', N'Khẩu trang y tế (Hộp 50 cái)', N'Khẩu trang 4 lớp kháng khuẩn.', N'Vải không dệt, giấy lọc kháng khuẩn', N'THUC_PHAM_CHUC_NANG', 500, 2000),
('SP-0089', N'Thực phẩm bảo vệ gan (Hộp 60 viên)', N'Hỗ trợ giải độc gan.', N'Silymarin, L-Arginine', N'THUC_PHAM_CHUC_NANG', 30, 100),
('SP-0090', N'Augmentin 625mg (Hộp 2 vỉ x 7 viên)', N'Kháng sinh (Amoxicillin + Acid Clavulanic) (kê đơn).', N'Amoxicillin 500mg, Acid Clavulanic 125mg', N'THUOC', 20, 80),
('SP-0091', N'Eugica (Hộp 10 vỉ x 10 viên)', N'Viên ngậm ho thảo dược.', N'Eucalyptol, Tinh dầu gừng, Menthol', N'THUOC', 100, 400),
('SP-0092', N'Viên uống DHC Vitamin C (Gói 60 ngày)', N'Bổ sung Vitamin C liều cao.', N'Vitamin C 1000mg', N'THUC_PHAM_CHUC_NANG', 80, 250),
('SP-0093', N'Spirulina (Lọ 2200 viên)', N'Tảo xoắn Nhật Bản.', N'Bột tảo xoắn Spirulina', N'THUC_PHAM_CHUC_NANG', 10, 30),
('SP-0094', N'Clotrimazol 1% (Tuýp 10g)', N'Kem bôi trị nấm da.', N'Clotrimazole 1%', N'THUOC', 50, 150),
('SP-0095', N'Enfagrow A+ 4 (Lon 1.7kg)', N'Sữa bột cho trẻ trên 2 tuổi.', N'Sữa bột, DHA, ARA, Vitamin', N'THUC_PHAM_CHUC_NANG', 30, 80),
('SP-0096', N'Nexium 40mg (Lọ 14 viên)', N'Thuốc ức chế bơm proton, trị loét dạ dày (kê đơn).', N'Esomeprazole 40mg', N'THUOC', 20, 60),
('SP-0097', N'Que thử thai Quickstick (Hộp 1 que)', N'Phát hiện thai sớm.', N'Giấy thử HCG', N'THUC_PHAM_CHUC_NANG', 200, 700),
('SP-0098', N'Acemuc (Hộp 30 gói)', N'Thuốc long đờm.', N'Acetylcysteine 200mg', N'THUOC', 40, 120),
('SP-0099', N'Viên uống CoQ10 100mg (Lọ 60 viên)', N'Hỗ trợ sức khỏe tim mạch.', N'Coenzyme Q10 100mg', N'THUC_PHAM_CHUC_NANG', 15, 40),
('SP-0100', N'Morphin 10mg/ml (Ống 1ml)', N'Thuốc giảm đau Opioid (kê đơn, KSTT).', N'Morphine HCl 10mg', N'THUOC', 5, 15);
GO

-- ===================================================================
-- 7. Bảng TaiKhoan
-- ===================================================================
INSERT INTO TaiKhoan (tenDangNhap, matKhau, quanLy, biKhoa, email, ngayTao) VALUES
('NV-0001', N'hashed_password_placeholder_1', 0, 0, N'vtkhoa_staff@gmail.com', '2025-01-01 08:00:00'),
('NV-0002', N'hashed_password_placeholder_2', 1, 0, N'h.m.khang_manager@gmail.com', '2025-01-01 08:01:00'),
('NV-0003', N'hashed_password_placeholder_3', 0, 0, N'n.k.quan_staff@gmail.com', '2025-01-01 08:02:00'),
('NV-0004', N'hashed_password_placeholder_4', 0, 0, N't.d.si_locked@gmail.com', '2025-01-01 08:03:00');
GO


-- ===================================================================
-- 8. Bảng DonViTinh
-- ===================================================================
INSERT INTO DonViTinh (maDonViTinh, maSP, tenDonVi, heSoQuyDoi, giaBanTheoDonVi, donViTinhCoBan) VALUES
-- SP-0001: Paracetamol 500mg (Hộp 10 vỉ x 10 viên)
('DVT-0001-VIEN', 'SP-0001', N'Viên', 1, 1000.00, 1),
('DVT-0001-VI', 'SP-0001', N'Vỉ', 10, 10000.00, 0),
('DVT-0001-HOP', 'SP-0001', N'Hộp', 100, 95000.00, 0),

-- SP-0003: Vitamin C 500mg (Tuýp 20 viên sủi)
('DVT-0003-VIEN', 'SP-0003', N'Viên', 1, 2000.00, 1),
('DVT-0003-TUYP', 'SP-0003', N'Tuýp', 20, 38000.00, 0),

-- SP-0012: Siro ho Prospan (Chai 100ml)
('DVT-0012-CHAI', 'SP-0012', N'Chai', 1, 70000.00, 1),

-- SP-0044: Panadol Extra (Hộp 10 vỉ x 12 viên)
('DVT-0044-VIEN', 'SP-0044', N'Viên', 1, 1500.00, 1),
('DVT-0044-VI', 'SP-0044', N'Vỉ', 12, 17000.00, 0),
('DVT-0044-HOP', 'SP-0044', N'Hộp', 120, 165000.00, 0),

-- SP-0088: Khẩu trang y tế (Hộp 50 cái)
('DVT-0088-CAI', 'SP-0088', N'Cái', 1, 1000.00, 1),
('DVT-0088-HOP', 'SP-0088', N'Hộp', 50, 45000.00, 0);
GO

-- ===================================================================
-- 8. Bảng LoSanPham
-- ===================================================================
INSERT INTO LoSanPham (maLoSanPham, maSP, soLuong, ngaySanXuat, ngayHetHan) VALUES
-- Lô cho Paracetamol (SP-0001)
('PARA-HG-001', 'SP-0001', 10000, '2024-01-01', '2027-01-01'), -- 10000 viên
('PARA-HG-002', 'SP-0001', 5000, '2024-06-01', '2027-06-01'), -- 5000 viên

-- Lô cho Vitamin C (SP-0003)
('VTC-SANOFI-001', 'SP-0003', 2000, '2024-03-01', '2026-03-01'), -- 2000 viên

-- Lô cho Prospan (SP-0012)
('PROSPAN-ECO-001', 'SP-0012', 1000, '2024-09-01', '2026-09-01'), -- 1000 chai

-- Lô cho Panadol Extra (SP-0044)
('PANAEX-GSK-001', 'SP-0044', 12000, '2024-02-01', '2027-02-01'), -- 12000 viên

-- Lô cho Khẩu trang (SP-0088)
('KT-BIDIPHAR-001', 'SP-0088', 50000, '2025-01-01', '2028-01-01'); -- 50000 cái
GO

-- ===================================================================
-- 9. Bảng SanPhamCungCap
-- ===================================================================
INSERT INTO SanPhamCungCap (maSP, maNCC, trangThaiHopTac, giaNhap) VALUES
-- SP-0001 (Paracetamol) từ NCC-0001 (Dược Hậu Giang)
('SP-0001', 'NCC-0001', 1, 800.00), -- Giá nhập 1 viên

-- SP-0008 (Hoạt huyết) & SP-0018 (Boganic) từ NCC-0002 (Traphaco)
('SP-0008', 'NCC-0002', 1, 800.00),
('SP-0018', 'NCC-0002', 1, 1000.00),

-- SP-0003 (Vitamin C) từ NCC-0005 (Sanofi)
('SP-0003', 'NCC-0005', 1, 1500.00),

-- SP-0012 (Prospan) từ NCC-0007 (Eco Pharma)
('SP-0012', 'NCC-0007', 1, 50000.00), -- Giá nhập 1 chai

-- SP-0044 (Panadol Extra) từ NCC-0006 (Zuellig Pharma)
('SP-0044', 'NCC-0006', 1, 1200.00), -- Giá nhập 1 viên

-- SP-0088 (Khẩu trang) từ NCC-0009 (Bidiphar)
('SP-0088', 'NCC-0009', 1, 700.00); -- Giá nhập 1 cái
GO

-- ===================================================================
-- 10. Bảng KhuyenMai_SanPham
-- ===================================================================
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP, ngayChinhSua) VALUES
-- KM-0001 (Giảm 10% khi mua từ 3 SP) áp dụng cho Paracetamol (SP-0001)
('KM-0001', 'SP-0001', GETDATE()),

-- KM-0002 (Mua 5 tặng 1) áp dụng cho Khẩu trang (SP-0088)
('KM-0002', 'SP-0088', GETDATE()),

-- KM-0006 (Giảm 30% cho TPCN) áp dụng cho Vitamin C (SP-0003) và Omega 3 (SP-0004)
('KM-0006', 'SP-0003', GETDATE()),
('KM-0006', 'SP-0004', GETDATE()),

-- KM-0008 (Giảm 10% cho Sanofi) áp dụng cho SP của Sanofi: Vitamin C (SP-0003) và Canxi Corbiere (SP-0010)
('KM-0008', 'SP-0003', GETDATE()),
('KM-0008', 'SP-0010', GETDATE());
GO

-- ===================================================================
-- 11. Bảng LichSuCaLam
-- ===================================================================
INSERT INTO LichSuCaLam (maNV, maCa, ngayLamViec, thoiGianVaoCa, thoiGianRaCa, ghiChu) VALUES
('NV-0001', 'SANG', '2025-10-25', '06:58:00', '15:05:00', N'Hoàn thành ca'),
('NV-0003', 'TOI', '2025-10-25', '14:59:00', '22:01:00', NULL),
('NV-0002', 'SANG', '2025-10-25', '07:00:00', '15:00:00', N'Quản lý ca'),
('NV-0001', 'SANG', '2025-10-26', '06:59:00', NULL, N'Đang trong ca');
GO

-- ===================================================================
-- 12. Bảng PhieuDatHang
-- ===================================================================
INSERT INTO PhieuDatHang (maPhieuDatHang, ngayLap, maNCC, maNV, tongTien) VALUES
('PDH-201025-0001', '2025-10-20', 'NCC-0001', 'NV-0002', 15000000.00),
('PDH-221025-0001', '2025-10-22', 'NCC-0002', 'NV-0002', 8500000.00);
GO

-- ===================================================================
-- 13. Bảng HoaDon
-- ===================================================================
-- Hóa đơn cho 5 khách hàng có tài khoản
INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, trangThai, tongTien) VALUES
('HD-251025-0001', 'NV-0001', '2025-10-25 09:30:00', 'KH-00001', 0, 1, 190000.00), 
('HD-251025-0002', 'NV-0001', '2025-10-25 10:15:00', 'KH-00002', 1, 1, 70000.00), 
('HD-251025-0003', 'NV-0003', '2025-10-25 16:00:00', 'KH-00005', 0, 1, 26600.00),
('HD-251025-0004', 'NV-0003', '2025-10-25 17:30:00', 'KH-00013', 0, 1, 330000.00),
('HD-251025-0005', 'NV-0001', '2025-10-25 14:00:00', 'KH-00020', 1, 1, 62000.00);  

-- Hóa đơn cho 5 khách vãng lai
INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, trangThai, tongTien) VALUES
('HD-251025-0006', 'NV-0001', '2025-10-25 08:10:00', 'KH-99999', 0, 1, 10000.00),
('HD-251025-0007', 'NV-0003', '2025-10-25 15:30:00', 'KH-99999', 0, 1, 45000.00),
('HD-251025-0008', 'NV-0003', '2025-10-25 18:45:00', 'KH-99999', 0, 1, 165000.00),
('HD-251025-0009', 'NV-0001', '2025-10-25 11:00:00', 'KH-99999', 1, 1, 34000.00),
('HD-251025-0010', 'NV-0003', '2025-10-25 20:00:00', 'KH-99999', 0, 1, 45000.00); -- Mua 5 vỉ Para (50k) được giảm 10% (KM-0001)
GO

-- ===================================================================
-- 14. Bảng ChiTietHoaDon
-- ===================================================================

-- Chi tiết cho 5 hóa đơn của khách hàng
INSERT INTO ChiTietHoaDon (maChiTietHoaDon, maHoaDon, maDonViTinh, soLuong, donGia, giamGia) VALUES
-- HD-251025-0001 (Tổng 190,000.00)
('CTHD-251025-0001', 'HD-251025-0001', 'DVT-0001-HOP', 2, 95000.00, 0.00), -- 2 Hộp Paracetamol

-- HD-251025-0002 (Tổng 70,000.00)
('CTHD-251025-0002', 'HD-251025-0002', 'DVT-0012-CHAI', 1, 70000.00, 0.00), -- 1 Chai Prospan

-- HD-251025-0003 (Tổng 26,600.00)
('CTHD-251025-0003', 'HD-251025-0003', 'DVT-0003-TUYP', 1, 38000.00, 0.30), -- 1 Tuýp VitC (Giá 38k, giảm 30% (KM-0006) còn 26,600)

-- HD-251025-0004 (Tổng 330,000.00)
('CTHD-251025-0004', 'HD-251025-0004', 'DVT-0044-HOP', 2, 165000.00, 0.00), -- 2 Hộp Panadol Extra

-- HD-251025-0005 (Tổng 62,000.00)
('CTHD-251025-0005', 'HD-251025-0005', 'DVT-0088-HOP', 1, 45000.00, 0.00), -- 1 Hộp Khẩu trang (45,000)
('CTHD-251025-0006', 'HD-251025-0005', 'DVT-0044-VI', 1, 17000.00, 0.00); -- 1 Vỉ Panadol Extra (17,000)

-- Chi tiết cho 5 hóa đơn của khách vãng lai
INSERT INTO ChiTietHoaDon (maChiTietHoaDon, maHoaDon, maDonViTinh, soLuong, donGia, giamGia) VALUES
-- HD-251025-0006 (Tổng 10,000.00)
('CTHD-251025-0007', 'HD-251025-0006', 'DVT-0001-VI', 1, 10000.00, 0.00), -- 1 Vỉ Paracetamol

-- HD-251025-0007 (Tổng 45,000.00)
('CTHD-251025-0008', 'HD-251025-0007', 'DVT-0088-HOP', 1, 45000.00, 0.00), -- 1 Hộp Khẩu trang

-- HD-251025-0008 (Tổng 165,000.00)
('CTHD-251025-0009', 'HD-251025-0008', 'DVT-0044-HOP', 1, 165000.00, 0.00), -- 1 Hộp Panadol Extra

-- HD-251025-0009 (Tổng 34,000.00)
('CTHD-251025-0010', 'HD-251025-0009', 'DVT-0044-VI', 2, 17000.00, 0.00), -- 2 Vỉ Panadol Extra

-- HD-251025-0010 (Tổng 45,000.00)
('CTHD-251025-0011', 'HD-251025-0010', 'DVT-0001-VI', 5, 10000.00, 0.10); -- 5 Vỉ Paracetamol (Giá 50k, giảm 10% (KM-0001) còn 45,000)
GO

-- ===================================================================
-- 15. Bảng ChiTietPhieuDat
-- ===================================================================
INSERT INTO ChiTietPhieuDat (maPhieuDatHang, maSP, soLuong, donGia) VALUES
-- Chi tiết cho PDH-201025-0001 (NCC-0001 - Dược Hậu Giang)
('PDH-201025-0001', 'SP-0001', 999, 800.00), -- Đặt Paracetamol (viên), giá nhập 800.00

-- Chi tiết cho PDH-221025-0001 (NCC-0002 - Traphaco)
('PDH-221025-0001', 'SP-0008', 500, 800.00), -- Đặt Hoạt huyết dưỡng não, giá nhập 800.00
('PDH-221025-0001', 'SP-0018', 300, 1000.00); -- Đặt Boganic, giá nhập 1000.00
GO

-- ===================================================================
-- 16. Bảng PhieuTraHang
-- ===================================================================
INSERT INTO PhieuTraHang (maPhieuTraHang, ngayLapPhieuTraHang, maNV, maHoaDon, tongTienHoanTra) VALUES
-- KH-00005 trả Tuýp Vitamin C (CTHD-251025-0003) từ HD-251025-0003 do dị ứng
('PTH-261025-0001', '2025-10-26 09:00:00', 'NV-0001', 'HD-251025-0003', 26600.00),

-- KH-00020 trả Vỉ Panadol Extra (CTHD-251025-0006) từ HD-251025-0005 do đổi ý
('PTH-261025-0002', '2025-10-26 10:15:00', 'NV-0003', 'HD-251025-0005', 17000.00);
GO

-- ===================================================================
-- 17. Bảng ChiTietXuatLo
-- ===================================================================
INSERT INTO ChiTietXuatLo (maLoSanPham, maChiTietHoaDon, soLuong) VALUES
-- CTHD-251025-0001: 2 Hộp Para (2 * 100 = 200 viên)
('PARA-HG-001', 'CTHD-251025-0001', 200),

-- CTHD-251025-0002: 1 Chai Prospan (1 * 1 = 1 chai)
('PROSPAN-ECO-001', 'CTHD-251025-0002', 1),

-- CTHD-251025-0003: 1 Tuýp VitC (1 * 20 = 20 viên)
('VTC-SANOFI-001', 'CTHD-251025-0003', 20),

-- CTHD-251025-0004: 2 Hộp Panadol Extra (2 * 120 = 240 viên)
('PANAEX-GSK-001', 'CTHD-251025-0004', 240),

-- CTHD-251025-0005: 1 Hộp Khẩu trang (1 * 50 = 50 cái)
('KT-BIDIPHAR-001', 'CTHD-251025-0005', 50),

-- CTHD-251025-0006: 1 Vỉ Panadol Extra (1 * 12 = 12 viên)
('PANAEX-GSK-001', 'CTHD-251025-0006', 12),

-- CTHD-251025-0007: 1 Vỉ Para (1 * 10 = 10 viên)
-- Giả sử 10 viên này được lấy từ 2 lô khác nhau
('PARA-HG-001', 'CTHD-251025-0007', 5),
('PARA-HG-002', 'CTHD-251025-0007', 5),

-- CTHD-251025-0008: 1 Hộp Khẩu trang (1 * 50 = 50 cái)
('KT-BIDIPHAR-001', 'CTHD-251025-0008', 50),

-- CTHD-251025-0009: 1 Hộp Panadol Extra (1 * 120 = 120 viên)
('PANAEX-GSK-001', 'CTHD-251025-0009', 120),

-- CTHD-251025-0010: 2 Vỉ Panadol Extra (2 * 12 = 24 viên)
('PANAEX-GSK-001', 'CTHD-251025-0010', 24),

-- CTHD-251025-0011: 5 Vỉ Para (5 * 10 = 50 viên)
('PARA-HG-002', 'CTHD-251025-0011', 50);
GO

-- ===================================================================
-- 18. Bảng ChiTietPhieuTraHang
-- ===================================================================
INSERT INTO ChiTietPhieuTraHang (maPhieuTraHang, maChiTietHoaDon, soLuong, truongHopDoiTra, tinhTrangSanPham, giaTriHoanTra, thanhTienHoanTra) VALUES
-- Chi tiết cho PTH-261025-0001 (Trả CTHD-251025-0003 - 1 Tuýp VitC)
(
    'PTH-261025-0001', 
    'CTHD-251025-0003', 
    1, 
    N'DI_UNG_MAN_CAM', 
    N'HANG_DA_SU_DUNG', 
    N'100%', 
    26600.00 -- Hoàn đúng số tiền đã thanh toán (sau giảm giá)
),

-- Chi tiết cho PTH-261025-0002 (Trả CTHD-251025-0006 - 1 Vỉ Panadol)
(
    'PTH-261025-0002', 
    'CTHD-251025-0006', 
    1, 
    N'NHU_CAU_KHACH_HANG', 
    N'HANG_NGUYEN_VEN', 
    N'100%', 
    17000.00 -- Hoàn đúng giá gốc của sản phẩm
);
GO