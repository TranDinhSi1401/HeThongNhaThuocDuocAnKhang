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

-- 1. Bảng Nhân Viên (Đã thêm daXoa)
CREATE TABLE NhanVien (
    maNV NVARCHAR(7) PRIMARY KEY,
    hoTenDem NVARCHAR(50) NOT NULL,
    ten NVARCHAR(20) NOT NULL,
    sdt VARCHAR(10) NOT NULL UNIQUE,
    cccd VARCHAR(12) NOT NULL UNIQUE,
    gioiTinh BIT NOT NULL, -- true: Nam, false: Nữ
    ngaySinh DATE NOT NULL,
    diaChi NVARCHAR(255) NULL,
    nghiViec BIT NOT NULL CONSTRAINT DF_NhanVien_NghiViec DEFAULT 0, -- true: Đã nghỉ việc (Logic NV), false: Đang làm
    --daXoa BIT NOT NULL CONSTRAINT DF_NhanVien_DaXoa DEFAULT 0, -- true: Đã xóa mềm (Logic hệ thống)
    
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

-- 2. Bảng Nhà Cung Cấp (Đã thêm daXoa)
CREATE TABLE NhaCungCap (
    maNCC NVARCHAR(8) PRIMARY KEY,
    tenNCC NVARCHAR(100) NOT NULL,
    diaChi NVARCHAR(255) NULL,
    sdt VARCHAR(10) NOT NULL UNIQUE,
    email NVARCHAR(255) NOT NULL UNIQUE,
    --daXoa BIT NOT NULL CONSTRAINT DF_NhaCungCap_DaXoa DEFAULT 0,

    CONSTRAINT CK_NhaCungCap_MaNCC_Format CHECK (maNCC LIKE 'NCC-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_NhaCungCap_Sdt_Format CHECK (LEN(sdt) = 10 AND sdt LIKE '0%'),
    CONSTRAINT CK_NhaCungCap_Email_Format CHECK (email LIKE '%_@gmail.com')
);
GO

-- 3. Bảng Khách Hàng (Đã thêm daXoa)
CREATE TABLE KhachHang (
    maKH NVARCHAR(8) PRIMARY KEY,
    hoTenDem NVARCHAR(50) NOT NULL,
    ten NVARCHAR(10) NOT NULL,
    sdt VARCHAR(10) NOT NULL UNIQUE,
    diemTichLuy INT NOT NULL CONSTRAINT DF_KhachHang_DiemTichLuy DEFAULT 0,
    --daXoa BIT NOT NULL CONSTRAINT DF_KhachHang_DaXoa DEFAULT 0,

    CONSTRAINT CK_KhachHang_MaKH_Format CHECK (maKH LIKE 'KH-[0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_KhachHang_Sdt_Format CHECK (LEN(sdt) = 10 AND sdt LIKE '0%'),
    CONSTRAINT CK_KhachHang_DiemTichLuy CHECK (diemTichLuy >= 0)
);
GO

-- 4. Bảng Khuyến Mãi (Đã thêm daXoa)
CREATE TABLE KhuyenMai (
    maKhuyenMai NVARCHAR(7) PRIMARY KEY,
    moTa NVARCHAR(255) NULL,
    phanTram DECIMAL(5, 2) NOT NULL,
    loaiKhuyenMai NVARCHAR(50) NOT NULL,
    ngayBatDau DATETIME2 NOT NULL,
    ngayKetThuc DATETIME2 NOT NULL,
    soLuongToiThieu INT NOT NULL,
    soLuongToiDa INT NOT NULL,
    ngayChinhSua DATETIME2 NOT NULL CONSTRAINT DF_KhuyenMai_NgayChinhSua DEFAULT GETDATE(),
    --daXoa BIT NOT NULL CONSTRAINT DF_KhuyenMai_DaXoa DEFAULT 0,

    CONSTRAINT CK_KhuyenMai_MaKM_Format CHECK (maKhuyenMai LIKE 'KM-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_KhuyenMai_PhanTram CHECK (phanTram > 0 AND phanTram <= 100),
    CONSTRAINT CK_KhuyenMai_LoaiKM_Enum CHECK (loaiKhuyenMai IN (N'SO_LUONG', N'MUA', N'NHA_SAN_XUAT', N'NGUNG_BAN')),
    CONSTRAINT CK_KhuyenMai_NgayHopLe CHECK (ngayKetThuc >= ngayBatDau),
    CONSTRAINT CK_KhuyenMai_SoLuongHopLe CHECK (soLuongToiDa >= soLuongToiThieu AND soLuongToiThieu > 0)
)
GO

-- 5. Bảng Tài Khoản (Đã thêm daXoa)
CREATE TABLE TaiKhoan (
    tenDangNhap NVARCHAR(7) PRIMARY KEY,
    matKhau NVARCHAR(256) NOT NULL,
    quanLy BIT NOT NULL CONSTRAINT DF_TaiKhoan_QuanLy DEFAULT 0, -- true: Quản lý, false: Nhân viên
    biKhoa BIT NOT NULL CONSTRAINT DF_TaiKhoan_BiKhoa DEFAULT 1, -- true: Bị khóa, false: Hoạt động
    email NVARCHAR(255) NOT NULL UNIQUE,
    ngayTao DATETIME NOT NULL CONSTRAINT DF_TaiKhoan_NgayTao DEFAULT GETDATE(),
    --daXoa BIT NOT NULL CONSTRAINT DF_TaiKhoan_DaXoa DEFAULT 0,

    CONSTRAINT FK_TaiKhoan_NhanVien FOREIGN KEY (tenDangNhap) REFERENCES NhanVien(maNV),
    CONSTRAINT CK_TaiKhoan_Email_Format CHECK (email LIKE '%_@gmail.com')
);
GO

-- 6. Bảng Sản Phẩm (Đã thêm daXoa)
CREATE TABLE SanPham (
    maSP NVARCHAR(7) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    moTa NVARCHAR(MAX) NOT NULL,
    thanhPhan NVARCHAR(MAX) NOT NULL,
    loaiSanPham NVARCHAR(50) NOT NULL,
    tonToiThieu INT NOT NULL,
    tonToiDa INT NOT NULL,
    daXoa BIT NOT NULL CONSTRAINT DF_SanPham_DaXoa DEFAULT 0,

    CONSTRAINT CK_SanPham_MaSP_Format CHECK (maSP LIKE 'SP-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_SanPham_LoaiSanPham_Enum CHECK (loaiSanPham IN (N'THUOC_KE_DON', N'THUOC_KHONG_KE_DON', N'THUC_PHAM_CHUC_NANG')),
    CONSTRAINT CK_SanPham_TonKhoHopLe CHECK (tonToiDa >= tonToiThieu AND tonToiThieu >= 0)
);
GO

CREATE TABLE MaVachSanPham (
    maVach VARCHAR(20) NOT NULL,
    maSP NVARCHAR(7) NOT NULL,     
    
    CONSTRAINT PK_MaVachSanPham PRIMARY KEY (maVach),
    CONSTRAINT FK_MaVach_SanPham FOREIGN KEY (maSP) REFERENCES SanPham(maSP) ON DELETE CASCADE
);
GO

-- 7. Bảng Đơn Vị Tính (Đã thêm daXoa)
CREATE TABLE DonViTinh (
    maDonViTinh NVARCHAR(20) PRIMARY KEY,
    maSP NVARCHAR(7) NOT NULL,
    tenDonVi NVARCHAR(30) NOT NULL,
    heSoQuyDoi INT NOT NULL,
    giaBanTheoDonVi DECIMAL(18, 2) NOT NULL,
    donViTinhCoBan BIT NOT NULL,
    daXoa BIT NOT NULL CONSTRAINT DF_DonViTinh_DaXoa DEFAULT 0,

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
    daHuy BIT NOT NULL DEFAULT 0,
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
    chuyenKhoan BIT NOT NULL CONSTRAINT DF_HoaDon_ChuyenKhoan DEFAULT 0,  
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
    CONSTRAINT CK_CTPTH_TinhTrangSanPham_Enum CHECK (tinhTrangSanPham IN (N'HANG_NGUYEN_VEN', N'HANG_KHONG_NGUYEN_VEN', N'HANG_DA_SU_DUNG')),
    CONSTRAINT CK_CTPTH_GiaTriHoanTra_Enum CHECK (giaTriHoanTra IN (N'100%', N'70%', N'Miễn trả hàng')),
    CONSTRAINT CK_CTPTH_ThanhTienHoanTra CHECK (thanhTienHoanTra >= 0)
);
GO

CREATE TABLE PhieuNhap (
    maPhieuNhap NVARCHAR(7) NOT NULL,
    ngayTao DATE DEFAULT GETDATE() NOT NULL,
    maNV NVARCHAR(7) NOT NULL,
    tongTien DECIMAL(18, 2) DEFAULT 0,
    ghiChu NVARCHAR(255) NULL,
    CONSTRAINT PK_PhieuNhap PRIMARY KEY (maPhieuNhap),
    CONSTRAINT FK_PhieuNhap_NhanVien FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    CONSTRAINT CK_PhieuNhap_Format CHECK (maPhieuNhap LIKE 'PN-[0-9][0-9][0-9][0-9]'),
    CONSTRAINT CK_PhieuNhap_NgayTao CHECK (ngayTao <= GETDATE()),
    CONSTRAINT CK_PhieuNhap_TongTien CHECK (tongTien >= 0)
);
GO

CREATE TABLE ChiTietPhieuNhap (
    maPhieuNhap NVARCHAR(7) NOT NULL,
    maLoSanPham NVARCHAR(50) NOT NULL, 
    maNCC NVARCHAR(8) NOT NULL,
    soLuong INT NOT NULL,
    soLuongYeuCau INT NOT NULL,
    donGia DECIMAL(18, 2) NOT NULL,
    thanhTien DECIMAL(18, 2) NOT NULL, 
    ghiChu NVARCHAR(255) NULL,
    CONSTRAINT PK_ChiTietPhieuNhap PRIMARY KEY (maPhieuNhap, maLoSanPham),
    CONSTRAINT FK_CTPN_PhieuNhap FOREIGN KEY (maPhieuNhap) REFERENCES PhieuNhap(maPhieuNhap) ON DELETE CASCADE,
    CONSTRAINT FK_CTPN_LoSanPham FOREIGN KEY (maLoSanPham) REFERENCES LoSanPham(maLoSanPham),
    CONSTRAINT FK_PhieuNhap_NhaCungCap FOREIGN KEY (maNCC) REFERENCES NhaCungCap(maNCC),
    CONSTRAINT CK_CTPN_SoLuong CHECK (soLuong > 0),
    CONSTRAINT CK_CTPN_SoLuongYeuCau CHECK (soLuongYeuCau >= 0),
    CONSTRAINT CK_CTPN_DonGia CHECK (donGia >= 0),
    CONSTRAINT CK_CTPN_ThanhTien CHECK (thanhTien >= 0)
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
('KH-00000', N'Khách', N'Vãng Lai', '0000000000', 0),
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
('KH-00050', N'Uông Văn', N'Đạt', '0909000050', 38);
GO

-- ===================================================================
-- 5. Bảng KhuyenMai (10 khuyến mãi)
-- ===================================================================
INSERT INTO KhuyenMai (maKhuyenMai, moTa, phanTram, loaiKhuyenMai, ngayBatDau, ngayKetThuc, soLuongToiThieu, soLuongToiDa, ngayChinhSua) VALUES
('KM-0001', N'Giảm 10% khi mua từ 3 sản phẩm', 10.00, N'SO_LUONG', '2025-10-01 00:00:00', '2026-02-28 23:59:59', 3, 999, GETDATE()),
('KM-0002', N'Mua 5 tặng 1 (Giảm 20%)', 20.00, N'MUA', '2025-11-15 00:00:00', '2026-01-28 23:59:59', 6, 6, GETDATE()),
('KM-0003', N'Giảm 15% cho sản phẩm của Traphaco', 15.00, N'NHA_SAN_XUAT', '2025-09-01 00:00:00', '2026-01-15 23:59:59', 1, 999, GETDATE()),
('KM-0004', N'Giảm giá Tết Nguyên Đán 20% (Sắp diễn ra)', 20.00, N'SO_LUONG', '2026-01-10 00:00:00', '2026-02-28 23:59:59', 1, 999, GETDATE()),
('KM-0005', N'Xả hàng cận date giảm 50%', 50.00, N'NGUNG_BAN', '2025-11-01 00:00:00', '2026-02-28 23:59:59', 1, 10, GETDATE()),
('KM-0006', N'Giảm 30% cho Thực phẩm chức năng (TPCN)', 30.00, N'SO_LUONG', '2025-12-01 00:00:00', '2026-02-28 23:59:59', 1, 999, GETDATE()),
('KM-0007', N'Mua 2 Tặng 1 (Giảm 33.33%)', 33.33, N'MUA', '2025-12-15 00:00:00', '2026-02-28 23:59:59', 3, 3, GETDATE()),
('KM-0008', N'Giảm 10% cho sản phẩm của Sanofi', 10.00, N'NHA_SAN_XUAT', '2026-01-15 00:00:00', '2026-02-28 23:59:59', 1, 999, GETDATE()),
('KM-0009', N'Giảm 5% tổng hóa đơn (chạy dài hạn)', 5.00, N'MUA', '2025-09-01 00:00:00', '2026-02-28 23:59:59', 1, 1000, GETDATE()),
('KM-0010', N'Giảm 100% (miễn phí) cho hàng tặng', 100.00, N'MUA', '2025-10-01 00:00:00', '2025-12-30 23:59:59', 1, 1, GETDATE());
GO

-- ===================================================================
-- 6. Bảng SanPham (50 sản phẩm) - Đã bổ sung cột daXoa = 0
-- ===================================================================
INSERT INTO SanPham (maSP, ten, moTa, thanhPhan, loaiSanPham, tonToiThieu, tonToiDa, daXoa) VALUES
-- THUỐC KHÔNG KÊ ĐƠN (OTC)
('SP-0001', N'Paracetamol 500mg (Hộp 10 vỉ x 10 viên)', N'Thuốc giảm đau, hạ sốt không kê đơn.', N'Paracetamol 500mg', N'THUOC_KHONG_KE_DON', 50, 300, 0),
('SP-0005', N'Berberin 100mg (Lọ 100 viên)', N'Điều trị tiêu chảy, lỵ trực khuẩn.', N'Berberin Clorid 100mg', N'THUOC_KHONG_KE_DON', 50, 200, 0),
('SP-0009', N'Clorpheniramin 4mg (Vỉ 20 viên)', N'Thuốc chống dị ứng, sổ mũi.', N'Clorpheniramin Maleat 4mg', N'THUOC_KHONG_KE_DON', 100, 500, 0),
('SP-0012', N'Siro ho Prospan (Chai 100ml)', N'Trị ho long đờm chiết xuất lá thường xuân.', N'Cao khô lá thường xuân', N'THUOC_KHONG_KE_DON', 50, 200, 0),
('SP-0014', N'Oresol 245 (Hộp 20 gói)', N'Bù nước và điện giải khi bị tiêu chảy, sốt.', N'Glucose khan, Natri Clorid, Kali Clorid', N'THUOC_KHONG_KE_DON', 100, 400, 0),
('SP-0022', N'Decolgen Forte (Hộp 25 vỉ x 4 viên)', N'Trị cảm cúm, sốt, nghẹt mũi.', N'Paracetamol, Phenylephrin, Clorpheniramin', N'THUOC_KHONG_KE_DON', 80, 400, 0),
('SP-0025', N'Betadine 10% (Chai 125ml)', N'Dung dịch sát khuẩn vết thương.', N'Povidone-Iodine 10%', N'THUOC_KHONG_KE_DON', 100, 300, 0),
('SP-0028', N'Efferagan 500mg (Hộp 4 tuýp x 10 viên sủi)', N'Giảm đau, hạ sốt (viên sủi).', N'Paracetamol 500mg', N'THUOC_KHONG_KE_DON', 40, 150, 0),
('SP-0031', N'Dầu gió xanh Thiên Thảo (Chai 24ml)', N'Giảm đau đầu, sổ mũi, say tàu xe.', N'Menthol, Methyl Salicylate, Eucalyptol', N'THUOC_KHONG_KE_DON', 200, 1000, 0),
('SP-0034', N'Tiffy (Hộp 25 vỉ x 4 viên)', N'Trị cảm cúm, sốt, ho, sổ mũi.', N'Paracetamol, Clorpheniramin, Phenylpropanolamin', N'THUOC_KHONG_KE_DON', 100, 500, 0),
('SP-0038', N'Telfast 180mg (Hộp 1 vỉ x 10 viên)', N'Thuốc chống dị ứng thế hệ mới.', N'Fexofenadine 180mg', N'THUOC_KHONG_KE_DON', 50, 200, 0),
('SP-0042', N'Urgo (Hộp 20 miếng)', N'Băng dán cá nhân.', N'Băng vải co giãn, gạc', N'THUOC_KHONG_KE_DON', 200, 800, 0),
('SP-0044', N'Panadol Extra (Hộp 10 vỉ x 12 viên)', N'Giảm đau, hạ sốt (chứa Cafein).', N'Paracetamol 500mg, Caffeine 65mg', N'THUOC_KHONG_KE_DON', 100, 500, 0),
('SP-0046', N'Nước muối sinh lý Natri Clorid 0.9% (Chai 500ml)', N'Rửa mắt, mũi, súc miệng, rửa vết thương.', N'Natri Clorid 0.9%', N'THUOC_KHONG_KE_DON', 300, 1500, 0),
('SP-0048', N'Strepsils (Hộp 24 viên ngậm)', N'Viên ngậm sát khuẩn, giảm đau họng.', N'Amylmetacresol, Dichlorobenzyl Alcohol', N'THUOC_KHONG_KE_DON', 150, 600, 0),
('SP-0050', N'Phosphalugel (Hộp 26 gói)', N'Thuốc chữ P, trị đau dạ dày.', N'Aluminium Phosphate 20%', N'THUOC_KHONG_KE_DON', 80, 250, 0),

-- THUỐC KÊ ĐƠN (Rx)
('SP-0002', N'Amoxicillin 500mg (Hộp 10 vỉ x 10 viên)', N'Kháng sinh Penicillin điều trị nhiễm khuẩn (kê đơn).', N'Amoxicillin 500mg', N'THUOC_KE_DON', 20, 100, 0),
('SP-0006', N'Atorvastatin 20mg (Hộp 3 vỉ x 10 viên)', N'Thuốc điều trị mỡ máu (kê đơn).', N'Atorvastatin 20mg', N'THUOC_KE_DON', 15, 80, 0),
('SP-0011', N'Losartan 50mg (Hộp 3 vỉ x 10 viên)', N'Thuốc điều trị cao huyết áp (kê đơn).', N'Losartan Potassium 50mg', N'THUOC_KE_DON', 20, 100, 0),
('SP-0015', N'Salbutamol 2mg (Hộp 10 vỉ x 10 viên)', N'Thuốc giãn phế quản, trị hen suyễn (kê đơn).', N'Salbutamol 2mg', N'THUOC_KE_DON', 30, 80, 0),
('SP-0017', N'Cialis 20mg (Vỉ 2 viên)', N'Điều trị rối loạn cương dương (kê đơn).', N'Tadalafil 20mg', N'THUOC_KE_DON', 10, 50, 0),
('SP-0019', N'Omeprazol 20mg (Lọ 14 viên)', N'Điều trị viêm loét dạ dày, trào ngược (kê đơn).', N'Omeprazole 20mg', N'THUOC_KE_DON', 30, 150, 0),
('SP-0021', N'Metformin 500mg (Hộp 10 vỉ x 10 viên)', N'Thuốc điều trị tiểu đường Type 2 (kê đơn).', N'Metformin HCl 500mg', N'THUOC_KE_DON', 40, 120, 0),
('SP-0024', N'Alprazolam 0.5mg (Hộp 3 vỉ x 10 viên)', N'Thuốc an thần, trị lo âu (kê đơn, kiểm soát đặc biệt).', N'Alprazolam 0.5mg', N'THUOC_KE_DON', 10, 30, 0),
('SP-0027', N'Ciprofloxacin 500mg (Hộp 2 vỉ x 10 viên)', N'Kháng sinh Quinolon (kê đơn).', N'Ciprofloxacin 500mg', N'THUOC_KE_DON', 15, 60, 0),
('SP-0030', N'Prednison 5mg (Lọ 200 viên)', N'Thuốc Corticoid kháng viêm (kê đơn).', N'Prednisolone 5mg', N'THUOC_KE_DON', 30, 100, 0),
('SP-0033', N'Aspirin 81mg (Lọ 100 viên)', N'Thuốc chống kết tập tiểu cầu (kê đơn).', N'Acid Acetylsalicylic 81mg', N'THUOC_KE_DON', 40, 200, 0),
('SP-0036', N'Domperidon 10mg (Hộp 10 vỉ x 10 viên)', N'Điều trị nôn, buồn nôn.', N'Domperidone 10mg', N'THUOC_KE_DON', 30, 150, 0),
('SP-0039', N'Cephalexin 500mg (Hộp 10 vỉ x 10 viên)', N'Kháng sinh Cephalosporin (kê đơn).', N'Cephalexin 500mg', N'THUOC_KE_DON', 25, 100, 0),
('SP-0041', N'Diazepam 5mg (Hộp 10 vỉ x 10 viên)', N'Thuốc an thần, gây ngủ (kê đơn, KSTT).', N'Diazepam 5mg', N'THUOC_KE_DON', 10, 40, 0),

-- THỰC PHẨM CHỨC NĂNG (TPCN)
('SP-0003', N'Vitamin C 500mg (Tuýp 20 viên sủi)', N'Bổ sung Vitamin C, tăng cường đề kháng.', N'Ascorbic Acid 500mg', N'THUC_PHAM_CHUC_NANG', 100, 500, 0),
('SP-0004', N'Omega 3 Fish Oil 1000mg (Lọ 100 viên)', N'Bổ sung Omega 3, tốt cho mắt và tim mạch.', N'Dầu cá 1000mg (chứa EPA, DHA)', N'THUC_PHAM_CHUC_NANG', 30, 150, 0),
('SP-0007', N'Blackmores Glucosamine 1500mg (Lọ 180 viên)', N'Hỗ trợ xương khớp, giảm đau viêm khớp.', N'Glucosamine Sulfate 1500mg', N'THUC_PHAM_CHUC_NANG', 20, 100, 0),
('SP-0008', N'Hoạt huyết dưỡng não Traphaco (Hộp 5 vỉ x 20 viên)', N'Bổ não, tăng cường tuần hoàn máu não.', N'Cao Đinh lăng, Cao Bạch quả', N'THUC_PHAM_CHUC_NANG', 40, 250, 0),
('SP-0010', N'Canxi Corbiere 5ml (Hộp 30 ống)', N'Bổ sung Canxi cho trẻ em, bà bầu.', N'Calcium Glucoheptonate', N'THUC_PHAM_CHUC_NANG', 30, 120, 0),
('SP-0013', N'Men vi sinh Bifina R (Hộp 20 gói)', N'Bổ sung lợi khuẩn đường ruột.', N'Bifidobacterium, Lactobacillus', N'THUC_PHAM_CHUC_NANG', 25, 100, 0),
('SP-0016', N'Sắt Ferrovit (Hộp 5 vỉ x 10 viên)', N'Bổ sung sắt, acid folic cho người thiếu máu.', N'Sắt Fumarat, Acid Folic, Vitamin B12', N'THUC_PHAM_CHUC_NANG', 40, 150, 0),
('SP-0018', N'Boganic (Hộp 5 vỉ x 10 viên)', N'Thanh nhiệt, giải độc gan, mát gan.', N'Cao Actiso, Cao Rau đắng đất, Cao Bìm bìm', N'THUC_PHAM_CHUC_NANG', 60, 300, 0),
('SP-0020', N'Ginkgo Biloba 120mg (Lọ 60 viên)', N'Bổ não, cải thiện trí nhớ.', N'Chiết xuất Bạch quả 120mg', N'THUC_PHAM_CHUC_NANG', 50, 200, 0),
('SP-0023', N'Nature Made Vitamin E 400 IU (Lọ 100 viên)', N'Bổ sung Vitamin E, đẹp da, chống oxy hóa.', N'Vitamin E 400 IU', N'THUC_PHAM_CHUC_NANG', 30, 90, 0),
('SP-0026', N'Centrum Silver 50+ (Lọ 125 viên)', N'Vitamin tổng hợp cho người trên 50 tuổi.', N'Vitamin (A, B, C, D, E, K), Khoáng chất', N'THUC_PHAM_CHUC_NANG', 20, 80, 0),
('SP-0029', N'Collagen AEC 12000mg (Hộp 10 lọ)', N'Bổ sung collagen, làm đẹp da.', N'Collagen thủy phân, Vitamin C', N'THUC_PHAM_CHUC_NANG', 10, 50, 0),
('SP-0032', N'Sữa Ensure Gold (Lon 850g)', N'Dinh dưỡng bổ sung cho người lớn tuổi, người bệnh.', N'Đạm, Chất béo, Vitamin, Khoáng chất', N'THUC_PHAM_CHUC_NANG', 50, 150, 0),
('SP-0035', N'One A Day Men''s Health (Lọ 100 viên)', N'Vitamin tổng hợp cho nam giới.', N'Vitamin, Khoáng chất (Kẽm, Selen...)', N'THUC_PHAM_CHUC_NANG', 15, 60, 0),
('SP-0037', N'Dầu cá Nature''s Bounty 1200mg (Lọ 200 viên)', N'Bổ sung Omega 3.', N'Dầu cá 1200mg', N'THUC_PHAM_CHUC_NANG', 20, 80, 0),
('SP-0040', N'Viên uống DHC rau củ (Gói 60 ngày)', N'Bổ sung chất xơ từ rau củ.', N'Bột chiết xuất 32 loại rau củ', N'THUC_PHAM_CHUC_NANG', 60, 200, 0),
('SP-0043', N'Melatonin 10mg (Lọ 60 viên)', N'Hỗ trợ điều hòa giấc ngủ.', N'Melatonin 10mg', N'THUC_PHAM_CHUC_NANG', 30, 70, 0),
('SP-0045', N'Enervon-C (Hộp 10 vỉ x 10 viên)', N'Bổ sung Vitamin B, C.', N'Vitamin B-complex, Vitamin C', N'THUC_PHAM_CHUC_NANG', 50, 300, 0),
('SP-0047', N'Crest 3D White (Tuýp 116g)', N'Kem đánh răng làm trắng răng.', N'Sodium Fluoride, Hydrated Silica', N'THUC_PHAM_CHUC_NANG', 50, 150, 0),
('SP-0049', N'Viên uống mọc tóc Biotin 10000mcg (Lọ 100 viên)', N'Hỗ trợ mọc tóc, móng chắc khỏe.', N'Biotin 10000mcg', N'THUC_PHAM_CHUC_NANG', 20, 80, 0);
GO

-- ===================================================================
-- 7. Bảng MaVachSanPham
-- ===================================================================
INSERT INTO MaVachSanPham (maSP, maVach) VALUES 
-- Sản phẩm 1 (SP-0001) có 2 mã vạch
('SP-0001', '893460200101'),
('SP-0001', '893460200102'),

-- Các sản phẩm còn lại (SP-0002 đến SP-0050) có 1 mã vạch
('SP-0002', '893460200201'),
('SP-0003', '893460200301'),
('SP-0004', '893460200401'),
('SP-0005', '893460200501'),
('SP-0006', '893460200601'),
('SP-0007', '893460200701'),
('SP-0008', '893460200801'),
('SP-0009', '893460200901'),
('SP-0010', '893460201001'),
('SP-0011', '893460201101'),
('SP-0012', '893460201201'),
('SP-0013', '893460201301'),
('SP-0014', '893460201401'),
('SP-0015', '893460201501'),
('SP-0016', '893460201601'),
('SP-0017', '893460201701'),
('SP-0018', '893460201801'),
('SP-0019', '893460201901'),
('SP-0020', '893460202001'),
('SP-0021', '893460202101'),
('SP-0022', '893460202201'),
('SP-0023', '893460202301'),
('SP-0024', '893460202401'),
('SP-0025', '893460202501'),
('SP-0026', '893460202601'),
('SP-0027', '893460202701'),
('SP-0028', '893460202801'),
('SP-0029', '893460202901'),
('SP-0030', '893460203001'),
('SP-0031', '893460203101'),
('SP-0032', '893460203201'),
('SP-0033', '893460203301'),
('SP-0034', '893460203401'),
('SP-0035', '893460203501'),
('SP-0036', '893460203601'),
('SP-0037', '893460203701'),
('SP-0038', '893460203801'),
('SP-0039', '893460203901'),
('SP-0040', '893460204001'),
('SP-0041', '893460204101'),
('SP-0042', '893460204201'),
('SP-0043', '893460204301'),
('SP-0044', '893460204401'),
('SP-0045', '893460204501'),
('SP-0046', '893460204601'),
('SP-0047', '893460204701'),
('SP-0048', '893460204801'),
('SP-0049', '893460204901'),
('SP-0050', '893460205001');
GO

-- ===================================================================
-- 7. Bảng TaiKhoan
-- ===================================================================
INSERT INTO TaiKhoan (tenDangNhap, matKhau, quanLy, biKhoa, email, ngayTao) VALUES
('NV-0001', N'Votienkhoa123@', 0, 0, N'vtkhoa_staff@gmail.com', '2025-01-01 08:00:00'),
('NV-0002', N'Hominhkhang123@', 1, 0, N'h.m.khang_manager@gmail.com', '2025-01-01 08:01:00'),
('NV-0003', N'Nguyenkhanhquan123@', 0, 0, N'n.k.quan_staff@gmail.com', '2025-01-01 08:02:00'),
('NV-0004', N'Trandinhsi123@', 0, 0, N't.d.si_locked@gmail.com', '2025-01-01 08:03:00');
GO

-- ===================================================================
-- 8. Bảng DonViTinh - Đã bổ sung cột daXoa = 0
-- ===================================================================
INSERT INTO DonViTinh (maDonViTinh, maSP, tenDonVi, heSoQuyDoi, giaBanTheoDonVi, donViTinhCoBan, daXoa) VALUES
-- SP-0001: Paracetamol 500mg (Hộp 10 vỉ x 10 viên)
('DVT-0001-VIEN', 'SP-0001', N'Viên', 1, 1000.00, 1, 0),
('DVT-0001-VI', 'SP-0001', N'Vỉ', 10, 10000.00, 0, 0),
('DVT-0001-HOP', 'SP-0001', N'Hộp', 100, 100000.00, 0, 0),

-- SP-0002: Amoxicillin 500mg (Hộp 10 vỉ x 10 viên)
('DVT-0002-VIEN', 'SP-0002', N'Viên', 1, 1500.00, 1, 0),
('DVT-0002-VI', 'SP-0002', N'Vỉ', 10, 15000.00, 0, 0),
('DVT-0002-HOP', 'SP-0002', N'Hộp', 100, 150000.00, 0, 0),

-- SP-0003: Vitamin C 500mg (Tuýp 20 viên sủi)
('DVT-0003-VIENSUI', 'SP-0003', N'Viên sủi', 1, 3000.00, 1, 0),
('DVT-0003-TUYP', 'SP-0003', N'Tuýp', 20, 60000.00, 0, 0),

-- SP-0004: Omega 3 Fish Oil 1000mg (Lọ 100 viên)
('DVT-0004-VIEN', 'SP-0004', N'Viên', 1, 2500.00, 1, 0),
('DVT-0004-LO', 'SP-0004', N'Lọ', 100, 250000.00, 0, 0),

-- SP-0005: Berberin 100mg (Lọ 100 viên)
('DVT-0005-VIEN', 'SP-0005', N'Viên', 1, 500.00, 1, 0),
('DVT-0005-LO', 'SP-0005', N'Lọ', 100, 50000.00, 0, 0),

-- SP-0006: Atorvastatin 20mg (Hộp 3 vỉ x 10 viên)
('DVT-0006-VIEN', 'SP-0006', N'Viên', 1, 4000.00, 1, 0),
('DVT-0006-VI', 'SP-0006', N'Vỉ', 10, 40000.00, 0, 0),
('DVT-0006-HOP', 'SP-0006', N'Hộp', 30, 120000.00, 0, 0),

-- SP-0007: Blackmores Glucosamine 1500mg (Lọ 180 viên)
('DVT-0007-VIEN', 'SP-0007', N'Viên', 1, 3500.00, 1, 0),
('DVT-0007-LO', 'SP-0007', N'Lọ', 180, 630000.00, 0, 0),

-- SP-0008: Hoạt huyết dưỡng não Traphaco (Hộp 5 vỉ x 20 viên)
('DVT-0008-VIEN', 'SP-0008', N'Viên', 1, 1200.00, 1, 0),
('DVT-0008-VI', 'SP-0008', N'Vỉ', 20, 24000.00, 0, 0),
('DVT-0008-HOP', 'SP-0008', N'Hộp', 100, 120000.00, 0, 0),

-- SP-0009: Clorpheniramin 4mg (Vỉ 20 viên)
('DVT-0009-VIEN', 'SP-0009', N'Viên', 1, 300.00, 1, 0),
('DVT-0009-VI', 'SP-0009', N'Vỉ', 20, 6000.00, 0, 0),

-- SP-0010: Canxi Corbiere 5ml (Hộp 30 ống)
('DVT-0010-ONG', 'SP-0010', N'Ống', 1, 5000.00, 1, 0),
('DVT-0010-HOP', 'SP-0010', N'Hộp', 30, 150000.00, 0, 0),

-- SP-0011: Losartan 50mg (Hộp 3 vỉ x 10 viên)
('DVT-0011-VIEN', 'SP-0011', N'Viên', 1, 3000.00, 1, 0),
('DVT-0011-VI', 'SP-0011', N'Vỉ', 10, 30000.00, 0, 0),
('DVT-0011-HOP', 'SP-0011', N'Hộp', 30, 90000.00, 0, 0),

-- SP-0012: Siro ho Prospan (Chai 100ml)
('DVT-0012-CHAI', 'SP-0012', N'Chai', 1, 70000.00, 1, 0),

-- SP-0013: Men vi sinh Bifina R (Hộp 20 gói)
('DVT-0013-GOI', 'SP-0013', N'Gói', 1, 15000.00, 1, 0),
('DVT-0013-HOP', 'SP-0013', N'Hộp', 20, 300000.00, 0, 0),

-- SP-0014: Oresol 245 (Hộp 20 gói)
('DVT-0014-GOI', 'SP-0014', N'Gói', 1, 2500.00, 1, 0),
('DVT-0014-HOP', 'SP-0014', N'Hộp', 20, 50000.00, 0, 0),

-- SP-0015: Salbutamol 2mg (Hộp 10 vỉ x 10 viên)
('DVT-0015-VIEN', 'SP-0015', N'Viên', 1, 800.00, 1, 0),
('DVT-0015-VI', 'SP-0015', N'Vỉ', 10, 8000.00, 0, 0),
('DVT-0015-HOP', 'SP-0015', N'Hộp', 100, 80000.00, 0, 0),

-- SP-0016: Sắt Ferrovit (Hộp 5 vỉ x 10 viên)
('DVT-0016-VIEN', 'SP-0016', N'Viên', 1, 1800.00, 1, 0),
('DVT-0016-VI', 'SP-0016', N'Vỉ', 10, 18000.00, 0, 0),
('DVT-0016-HOP', 'SP-0016', N'Hộp', 50, 90000.00, 0, 0),

-- SP-0017: Cialis 20mg (Vỉ 2 viên)
('DVT-0017-VIEN', 'SP-0017', N'Viên', 1, 70000.00, 1, 0),
('DVT-0017-VI', 'SP-0017', N'Vỉ', 2, 140000.00, 0, 0),

-- SP-0018: Boganic (Hộp 5 vỉ x 10 viên)
('DVT-0018-VIEN', 'SP-0018', N'Viên', 1, 1500.00, 1, 0),
('DVT-0018-VI', 'SP-0018', N'Vỉ', 10, 15000.00, 0, 0),
('DVT-0018-HOP', 'SP-0018', N'Hộp', 50, 75000.00, 0, 0),

-- SP-0019: Omeprazol 20mg (Lọ 14 viên)
('DVT-0019-VIEN', 'SP-0019', N'Viên', 1, 2000.00, 1, 0),
('DVT-0019-LO', 'SP-0019', N'Lọ', 14, 28000.00, 0, 0),

-- SP-0020: Ginkgo Biloba 120mg (Lọ 60 viên)
('DVT-0020-VIEN', 'SP-0020', N'Viên', 1, 3000.00, 1, 0),
('DVT-0020-LO', 'SP-0020', N'Lọ', 60, 180000.00, 0, 0),

-- SP-0021: Metformin 500mg (Hộp 10 vỉ x 10 viên)
('DVT-0021-VIEN', 'SP-0021', N'Viên', 1, 700.00, 1, 0),
('DVT-0021-VI', 'SP-0021', N'Vỉ', 10, 7000.00, 0, 0),
('DVT-0021-HOP', 'SP-0021', N'Hộp', 100, 70000.00, 0, 0),

-- SP-0022: Decolgen Forte (Hộp 25 vỉ x 4 viên)
('DVT-0022-VIEN', 'SP-0022', N'Viên', 1, 1500.00, 1, 0),
('DVT-0022-VI', 'SP-0022', N'Vỉ', 4, 6000.00, 0, 0),
('DVT-0022-HOP', 'SP-0022', N'Hộp', 100, 150000.00, 0, 0),

-- SP-0023: Nature Made Vitamin E 400 IU (Lọ 100 viên)
('DVT-0023-VIEN', 'SP-0023', N'Viên', 1, 2800.00, 1, 0),
('DVT-0023-LO', 'SP-0023', N'Lọ', 100, 280000.00, 0, 0),

-- SP-0024: Alprazolam 0.5mg (Hộp 3 vỉ x 10 viên)
('DVT-0024-VIEN', 'SP-0024', N'Viên', 1, 1500.00, 1, 0),
('DVT-0024-VI', 'SP-0024', N'Vỉ', 10, 15000.00, 0, 0),
('DVT-0024-HOP', 'SP-0024', N'Hộp', 30, 45000.00, 0, 0),

-- SP-0025: Betadine 10% (Chai 125ml)
('DVT-0025-CHAI', 'SP-0025', N'Chai', 1, 35000.00, 1, 0),

-- SP-0026: Centrum Silver 50+ (Lọ 125 viên)
('DVT-0026-VIEN', 'SP-0026', N'Viên', 1, 4000.00, 1, 0),
('DVT-0026-LO', 'SP-0026', N'Lọ', 125, 500000.00, 0, 0),

-- SP-0027: Ciprofloxacin 500mg (Hộp 2 vỉ x 10 viên)
('DVT-0027-VIEN', 'SP-0027', N'Viên', 1, 3500.00, 1, 0),
('DVT-0027-VI', 'SP-0027', N'Vỉ', 10, 35000.00, 0, 0),
('DVT-0027-HOP', 'SP-0027', N'Hộp', 20, 70000.00, 0, 0),

-- SP-0028: Efferagan 500mg (Hộp 4 tuýp x 10 viên sủi)
('DVT-0028-VIENSUI', 'SP-0028', N'Viên sủi', 1, 3000.00, 1, 0),
('DVT-0028-TUYP', 'SP-0028', N'Tuýp', 10, 30000.00, 0, 0),
('DVT-0028-HOP', 'SP-0028', N'Hộp', 40, 120000.00, 0, 0),

-- SP-0029: Collagen AEC 12000mg (Hộp 10 lọ)
('DVT-0029-LO', 'SP-0029', N'Lọ', 1, 80000.00, 1, 0),
('DVT-0029-HOP', 'SP-0029', N'Hộp', 10, 800000.00, 0, 0),

-- SP-0030: Prednison 5mg (Lọ 200 viên)
('DVT-0030-VIEN', 'SP-0030', N'Viên', 1, 300.00, 1, 0),
('DVT-0030-LO', 'SP-0030', N'Lọ', 200, 60000.00, 0, 0),

-- SP-0031: Dầu gió xanh Thiên Thảo (Chai 24ml)
('DVT-0031-CHAI', 'SP-0031', N'Chai', 1, 45000.00, 1, 0),

-- SP-0032: Sữa Ensure Gold (Lon 850g)
('DVT-0032-LON', 'SP-0032', N'Lon', 1, 750000.00, 1, 0),

-- SP-0033: Aspirin 81mg (Lọ 100 viên)
('DVT-0033-VIEN', 'SP-0033', N'Viên', 1, 600.00, 1, 0),
('DVT-0033-LO', 'SP-0033', N'Lọ', 100, 60000.00, 0, 0),

-- SP-0034: Tiffy (Hộp 25 vỉ x 4 viên)
('DVT-0034-VIEN', 'SP-0034', N'Viên', 1, 1300.00, 1, 0),
('DVT-0034-VI', 'SP-0034', N'Vỉ', 4, 5200.00, 0, 0),
('DVT-0034-HOP', 'SP-0034', N'Hộp', 100, 130000.00, 0, 0),

-- SP-0035: One A Day Men's Health (Lọ 100 viên)
('DVT-0035-VIEN', 'SP-0035', N'Viên', 1, 4500.00, 1, 0),
('DVT-0035-LO', 'SP-0035', N'Lọ', 100, 450000.00, 0, 0),

-- SP-0036: Domperidon 10mg (Hộp 10 vỉ x 10 viên)
('DVT-0036-VIEN', 'SP-0036', N'Viên', 1, 1000.00, 1, 0),
('DVT-0036-VI', 'SP-0036', N'Vỉ', 10, 10000.00, 0, 0),
('DVT-0036-HOP', 'SP-0036', N'Hộp', 100, 100000.00, 0, 0),

-- SP-0037: Dầu cá Nature's Bounty 1200mg (Lọ 200 viên)
('DVT-0037-VIEN', 'SP-0037', N'Viên', 1, 2000.00, 1, 0),
('DVT-0037-LO', 'SP-0037', N'Lọ', 200, 400000.00, 0, 0),

-- SP-0038: Telfast 180mg (Hộp 1 vỉ x 10 viên)
('DVT-0038-VIEN', 'SP-0038', N'Viên', 1, 12000.00, 1, 0),
('DVT-0038-VI', 'SP-0038', N'Vỉ', 10, 120000.00, 0, 0),
('DVT-0038-HOP', 'SP-0038', N'Hộp', 10, 120000.00, 0, 0),

-- SP-0039: Cephalexin 500mg (Hộp 10 vỉ x 10 viên)
('DVT-0039-VIEN', 'SP-0039', N'Viên', 1, 1800.00, 1, 0),
('DVT-0039-VI', 'SP-0039', N'Vỉ', 10, 18000.00, 0, 0),
('DVT-0039-HOP', 'SP-0039', N'Hộp', 100, 180000.00, 0, 0),

-- SP-0040: Viên uống DHC rau củ (Gói 60 ngày)
('DVT-0040-GOI', 'SP-0040', N'Gói', 1, 250000.00, 1, 0),

-- SP-0041: Diazepam 5mg (Hộp 10 vỉ x 10 viên)
('DVT-0041-VIEN', 'SP-0041', N'Viên', 1, 900.00, 1, 0),
('DVT-0041-VI', 'SP-0041', N'Vỉ', 10, 9000.00, 0, 0),
('DVT-0041-HOP', 'SP-0041', N'Hộp', 100, 90000.00, 0, 0),

-- SP-0042: Urgo (Hộp 20 miếng)
('DVT-0042-MIENG', 'SP-0042', N'Miếng', 1, 1500.00, 1, 0),
('DVT-0042-HOP', 'SP-0042', N'Hộp', 20, 30000.00, 0, 0),

-- SP-0043: Melatonin 10mg (Lọ 60 viên)
('DVT-0043-VIEN', 'SP-0043', N'Viên', 1, 4000.00, 1, 0),
('DVT-0043-LO', 'SP-0043', N'Lọ', 60, 240000.00, 0, 0),

-- SP-0044: Panadol Extra (Hộp 10 vỉ x 12 viên)
('DVT-0044-VIEN', 'SP-0044', N'Viên', 1, 1800.00, 1, 0),
('DVT-0044-VI', 'SP-0044', N'Vỉ', 12, 21600.00, 0, 0),
('DVT-0044-HOP', 'SP-0044', N'Hộp', 120, 216000.00, 0, 0),

-- SP-0045: Enervon-C (Hộp 10 vỉ x 10 viên)
('DVT-0045-VIEN', 'SP-0045', N'Viên', 1, 2000.00, 1, 0),
('DVT-0045-VI', 'SP-0045', N'Vỉ', 10, 20000.00, 0, 0),
('DVT-0045-HOP', 'SP-0045', N'Hộp', 100, 200000.00, 0, 0),

-- SP-0046: Nước muối sinh lý Natri Clorid 0.9% (Chai 500ml)
('DVT-0046-CHAI', 'SP-0046', N'Chai', 1, 10000.00, 1, 0),

-- SP-0047: Crest 3D White (Tuýp 116g)
('DVT-0047-TUYP', 'SP-0047', N'Tuýp', 1, 120000.00, 1, 0),

-- SP-0048: Strepsils (Hộp 24 viên ngậm)
('DVT-0048-VIENGAM', 'SP-0048', N'Viên ngậm', 1, 2500.00, 1, 0),
('DVT-0048-HOP', 'SP-0048', N'Hộp', 24, 60000.00, 0, 0),

-- SP-0049: Viên uống mọc tóc Biotin 10000mcg (Lọ 100 viên)
('DVT-0049-VIEN', 'SP-0049', N'Viên', 1, 3000.00, 1, 0),
('DVT-0049-LO', 'SP-0049', N'Lọ', 100, 300000.00, 0, 0),

-- SP-0050: Phosphalugel (Hộp 26 gói)
('DVT-0050-GOI', 'SP-0050', N'Gói', 1, 4000.00, 1, 0),
('DVT-0050-HOP', 'SP-0050', N'Hộp', 26, 104000.00, 0, 0);
GO

-- ===================================================================
-- 9. Bảng LoSanPham
-- ===================================================================
INSERT INTO LoSanPham (maLoSanPham, maSP, soLuong, ngaySanXuat, ngayHetHan) VALUES
-- SP-0001: Paracetamol 500mg
('LO-SP-0001-20240510-1', 'SP-0001', 1500, '2024-05-10', '2027-05-10'),
('LO-SP-0001-20250120-2', 'SP-0001', 1000, '2025-01-20', '2028-01-20'),

-- SP-0002: Amoxicillin 500mg
('LO-SP-0002-20241101-1', 'SP-0002', 500, '2024-11-01', '2026-11-01'),
('LO-SP-0002-20250315-2', 'SP-0002', 300, '2025-03-15', '2027-03-15'),

-- SP-0003: Vitamin C 500mg
('LO-SP-0003-20240801-1', 'SP-0003', 1000, '2024-08-01', '2026-08-01'),

-- SP-0004: Omega 3 Fish Oil 1000mg
('LO-SP-0004-20240630-1', 'SP-0004', 500, '2024-06-30', '2027-06-30'),
('LO-SP-0004-20250401-2', 'SP-0004', 400, '2025-04-01', '2028-04-01'),

-- SP-0005: Berberin 100mg
('LO-SP-0005-20240715-1', 'SP-0005', 1000, '2024-07-15', '2027-07-15'),

-- SP-0006: Atorvastatin 20mg
('LO-SP-0006-20241201-1', 'SP-0006', 200, '2024-12-01', '2027-12-01'),
('LO-SP-0006-20250510-2', 'SP-0006', 150, '2025-05-10', '2028-05-10'),

-- SP-0007: Blackmores Glucosamine 1500mg
('LO-SP-0007-20240220-1', 'SP-0007', 300, '2024-02-20', '2027-02-20'),

-- SP-0008: Hoạt huyết dưỡng não Traphaco
('LO-SP-0008-20240905-1', 'SP-0008', 1000, '2024-09-05', '2027-09-05'),
('LO-SP-0008-20250415-2', 'SP-0008', 800, '2025-04-15', '2028-04-15'),

-- SP-0009: Clorpheniramin 4mg
('LO-SP-0009-20240110-1', 'SP-0009', 2000, '2024-01-10', '2027-01-10'),

-- SP-0010: Canxi Corbiere 5ml
('LO-SP-0010-20241030-1', 'SP-0010', 500, '2024-10-30', '2026-10-30'),
('LO-SP-0010-20250520-2', 'SP-0010', 400, '2025-05-20', '2027-05-20'),

-- SP-0011: Losartan 50mg
('LO-SP-0011-20240818-1', 'SP-0011', 300, '2024-08-18', '2027-08-18'),

-- SP-0012: Siro ho Prospan
('LO-SP-0012-20241101-1', 'SP-0012', 300, '2024-11-01', '2026-11-01'),
('LO-SP-0012-20250601-2', 'SP-0012', 200, '2025-06-01', '2027-06-01'),

-- SP-0013: Men vi sinh Bifina R
('LO-SP-0013-20240910-1', 'SP-0013', 200, '2024-09-10', '2026-03-10'),

-- SP-0014: Oresol 245
('LO-SP-0014-20240301-1', 'SP-0014', 1000, '2024-03-01', '2027-03-01'),
('LO-SP-0014-20250105-2', 'SP-0014', 800, '2025-01-05', '2028-01-05'),

-- SP-0015: Salbutamol 2mg
('LO-SP-0015-20240725-1', 'SP-0015', 500, '2024-07-25', '2027-07-25'),

-- SP-0016: Sắt Ferrovit
('LO-SP-0016-20241001-1', 'SP-0016', 600, '2024-10-01', '2027-10-01'),
('LO-SP-0016-20250420-2', 'SP-0016', 400, '2025-04-20', '2028-04-20'),

-- SP-0017: Cialis 20mg
('LO-SP-0017-20240515-1', 'SP-0017', 100, '2024-05-15', '2027-05-15'),

-- SP-0018: Boganic
('LO-SP-0018-20240830-1', 'SP-0018', 1000, '2024-08-30', '2027-08-30'),
('LO-SP-0018-20250214-2', 'SP-0018', 500, '2025-02-14', '2028-02-14'),

-- SP-0019: Omeprazol 20mg
('LO-SP-0019-20241120-1', 'SP-0019', 400, '2024-11-20', '2027-11-20'),

-- SP-0020: Ginkgo Biloba 120mg
('LO-SP-0020-20240610-1', 'SP-0020', 300, '2024-06-10', '2027-06-10'),
('LO-SP-0020-20250130-2', 'SP-0020', 200, '2025-01-30', '2028-01-30'),

-- SP-0021: Metformin 500mg
('LO-SP-0021-20240922-1', 'SP-0021', 0, '2024-09-22', '2027-09-22'),

-- SP-0022: Decolgen Forte
('LO-SP-0022-20240405-1', 'SP-0022', 2000, '2024-04-05', '2027-04-05'),
('LO-SP-0022-20250310-2', 'SP-0022', 1500, '2025-03-10', '2028-03-10'),

-- SP-0023: Nature Made Vitamin E 400 IU
('LO-SP-0023-20240701-1', 'SP-0023', 200, '2024-07-01', '2027-07-01'),

-- SP-0024: Alprazolam 0.5mg
('LO-SP-0024-20241010-1', 'SP-0024', 100, '2024-10-10', '2026-10-10'),
('LO-SP-0024-20250501-2', 'SP-0024', 50, '2025-05-01', '2027-05-01'),

-- SP-0025: Betadine 10%
('LO-SP-0025-20240315-1', 'SP-0025', 400, '2024-03-15', '2027-03-15'),

-- SP-0026: Centrum Silver 50+
('LO-SP-0026-20240808-1', 'SP-0026', 150, '2024-08-08', '2026-08-08'),
('LO-SP-0026-20250225-2', 'SP-0026', 100, '2025-02-25', '2027-02-25'),

-- SP-0027: Ciprofloxacin 500mg
('LO-SP-0027-20240914-1', 'SP-0027', 200, '2024-09-14', '2027-09-14'),

-- SP-0028: Efferagan 500mg
('LO-SP-0028-20240707-1', 'SP-0028', 500, '2024-07-07', '2027-07-07'),
('LO-SP-0028-20250125-2', 'SP-0028', 300, '2025-01-25', '2028-01-25'),

-- SP-0029: Collagen AEC 12000mg
('LO-SP-0029-20241001-1', 'SP-0029', 100, '2024-10-01', '2026-10-01'),

-- SP-0030: Prednison 5mg
('LO-SP-0030-20240620-1', 'SP-0030', 500, '2024-06-20', '2027-06-20'),
('LO-SP-0030-20250210-2', 'SP-0030', 300, '2025-02-10', '2028-02-10'),

-- SP-0031: Dầu gió xanh Thiên Thảo
('LO-SP-0031-20240101-1', 'SP-0031', 1000, '2024-01-01', '2029-01-01'),

-- SP-0032: Sữa Ensure Gold
('LO-SP-0032-20240915-1', 'SP-0032', 100, '2024-09-15', '2026-09-15'),
('LO-SP-0032-20250301-2', 'SP-0032', 80, '2025-03-01', '2027-03-01'),

-- SP-0033: Aspirin 81mg
('LO-SP-0033-20240810-1', 'SP-0033', 500, '2024-08-10', '2027-08-10'),

-- SP-0034: Tiffy
('LO-SP-0034-20240505-1', 'SP-0034', 2500, '2024-05-05', '2027-05-05'),
('LO-SP-0034-20250115-2', 'SP-0034', 2000, '2025-01-15', '2028-01-15'),

-- SP-0035: One A Day Men's Health
('LO-SP-0035-20240712-1', 'SP-0035', 100, '2024-07-12', '2026-07-12'),

-- SP-0036: Domperidon 10mg
('LO-SP-0036-20241020-1', 'SP-0036', 800, '2024-10-20', '2027-10-20'),
('LO-SP-0036-20250410-2', 'SP-0036', 500, '2025-04-10', '2028-04-10'),

-- SP-0037: Dầu cá Nature's Bounty 1200mg
('LO-SP-0037-20240614-1', 'SP-0037', 200, '2024-06-14', '2027-06-14'),

-- SP-0038: Telfast 180mg
('LO-SP-0038-20240901-1', 'SP-0038', 500, '2024-09-01', '2027-09-01'),
('LO-SP-0038-20250501-2', 'SP-0038', 300, '2025-05-01', '2028-05-01'),

-- SP-0039: Cephalexin 500mg
('LO-SP-0039-20241105-1', 'SP-0039', 400, '2024-11-05', '2026-11-05'),

-- SP-0040: Viên uống DHC rau củ
('LO-SP-0040-20240820-1', 'SP-0040', 100, '2024-08-20', '2026-08-20'),
('LO-SP-0040-20250215-2', 'SP-0040', 80, '2025-02-15', '2027-02-15'),

-- SP-0041: Diazepam 5mg
('LO-SP-0041-20240730-1', 'SP-0041', 200, '2024-07-30', '2027-07-30'),

-- SP-0042: Urgo
('LO-SP-0042-20240201-1', 'SP-0042', 1000, '2024-02-01', '2029-02-01'),
('LO-SP-0042-20250110-2', 'SP-0042', 800, '2025-01-10', '2030-01-10'),

-- SP-0043: Melatonin 10mg
('LO-SP-0043-20240909-1', 'SP-0043', 150, '2024-09-09', '2026-09-09'),

-- SP-0044: Panadol Extra
('LO-SP-0044-20240625-1', 'SP-0044', 1200, '2024-06-25', '2027-06-25'),
('LO-SP-0044-20250305-2', 'SP-0044', 1000, '2025-03-05', '2028-03-05'),

-- SP-0045: Enervon-C
('LO-SP-0045-20240717-1', 'SP-0045', 1000, '2024-01-10', '2026-01-10'),

-- SP-0046: Nước muối sinh lý Natri Clorid 0.9%
('LO-SP-0046-20241001-1', 'SP-0046', 2000, '2024-01-10', '2026-01-10'),
('LO-SP-0046-20250515-2', 'SP-0046', 1500, '2024-01-10', '2026-01-10'),

-- SP-0047: Crest 3D White
('LO-SP-0047-20240801-1', 'SP-0047', 200, '2024-01-10', '2026-01-10'),

---
-- Giả sử ngày thống kê là hôm nay (2025-12-05) (cho 3 sản phẩm sau)
-- Ngày Hết Hạn mới: 2026-01-04 (30 ngày sau 05/12/2025)

-- SP-0048: Strepsils
('LO-SP-0048-20240920-1', 'SP-0048', 800, '2024-09-20', '2026-01-02'),
('LO-SP-0048-20250412-2', 'SP-0048', 600, '2025-04-12', '2026-01-02'),

-- SP-0049: Viên uống mọc tóc Biotin 10000mcg
('LO-SP-0049-20240530-1', 'SP-0049', 100, '2024-05-30', '2026-01-02'),

-- SP-0050: Phosphalugel
('LO-SP-0050-20240720-1', 'SP-0050', 500, '2024-07-20', '2026-01-02');
GO

INSERT INTO LoSanPham (maLoSanPham, maSP, soLuong, ngaySanXuat, ngayHetHan, daHuy) VALUES
('LO-SP-0050-20250410-2', 'SP-0050', 400, '2025-04-10', '2028-04-10', 1);
GO

-- ===================================================================
-- 10. Bảng SanPhamCungCap
-- ===================================================================
INSERT INTO SanPhamCungCap (maSP, maNCC, trangThaiHopTac, giaNhap) VALUES
-- SP-0001: Paracetamol (Giá bán viên: 1,000đ -> Thặng 15%. Gốc ~870đ. Hộp 100v)
('SP-0001', 'NCC-0001', 1, 87000.00),
('SP-0001', 'NCC-0002', 1, 87200.00),

-- SP-0002: Amoxicillin (Giá bán viên: 1,500đ -> Thặng 10%. Gốc ~1,364đ. Hộp 100v)
('SP-0002', 'NCC-0003', 1, 136400.00),

-- SP-0003: Vitamin C (Giá bán viên: 3,000đ -> Thặng 10%. Gốc ~2,727đ. Tuýp 20v)
('SP-0003', 'NCC-0004', 1, 54540.00),

-- SP-0004: Omega 3 (Giá bán viên: 2,500đ -> Thặng 10%. Gốc ~2,273đ. Lọ 100v)
('SP-0004', 'NCC-0005', 1, 227300.00),

-- SP-0005: Berberin (Giá bán viên: 500đ -> Thặng 15%. Gốc ~435đ. Lọ 100v)
('SP-0005', 'NCC-0006', 1, 43500.00),

-- SP-0006: Atorvastatin (Giá bán viên: 4,000đ -> Thặng 10%. Gốc ~3,636đ. Hộp 30v)
('SP-0006', 'NCC-0007', 1, 109100.00),

-- SP-0007: Blackmores (Giá bán viên: 3,500đ -> Thặng 10%. Gốc ~3,182đ. Lọ 180v)
('SP-0007', 'NCC-0008', 1, 572760.00),

-- SP-0008: Hoạt huyết (Giá bán viên: 1,200đ -> Thặng 10%. Gốc ~1,091đ. Hộp 100v)
('SP-0008', 'NCC-0002', 1, 109100.00), -- Traphaco

-- SP-0009: Clorpheniramin (Giá bán viên: 300đ -> Thặng 15%. Gốc ~261đ. Vỉ 20v)
('SP-0009', 'NCC-0009', 1, 5220.00),
('SP-0009', 'NCC-0001', 1, 5250.00),

-- SP-0010: Canxi Corbiere (Giá bán ống: 5,000đ -> Thặng 10%. Gốc ~4,545đ. Hộp 30 ống)
('SP-0010', 'NCC-0005', 1, 136350.00), -- Sanofi

-- SP-0011: Losartan (Giá bán viên: 3,000đ -> Thặng 10%. Gốc ~2,727đ. Hộp 30v)
('SP-0011', 'NCC-0010', 1, 81810.00),

-- SP-0012: Siro ho Prospan (Giá bán chai: 70,000đ -> Thặng 7%. Gốc ~65,420đ. Chai 1)
('SP-0012', 'NCC-0007', 1, 65420.00),

-- SP-0013: Men vi sinh Bifina (Giá bán gói: 15,000đ -> Thặng 7%. Gốc ~14,019đ. Hộp 20 gói)
('SP-0013', 'NCC-0006', 1, 280380.00),

-- SP-0014: Oresol 245 (Giá bán gói: 2,500đ -> Thặng 10%. Gốc ~2,273đ. Hộp 20 gói)
('SP-0014', 'NCC-0001', 1, 45460.00),

-- SP-0015: Salbutamol (Giá bán viên: 800đ -> Thặng 15%. Gốc ~696đ. Hộp 100v)
('SP-0015', 'NCC-0003', 1, 69600.00),

-- SP-0016: Sắt Ferrovit (Giá bán viên: 1,800đ -> Thặng 10%. Gốc ~1,636đ. Hộp 50v)
('SP-0016', 'NCC-0004', 1, 81800.00),

-- SP-0017: Cialis 20mg (Giá bán viên: 70,000đ -> Thặng 7%. Gốc ~65,420đ. Vỉ 2v)
('SP-0017', 'NCC-0006', 1, 130840.00),

-- SP-0018: Boganic (Giá bán viên: 1,500đ -> Thặng 10%. Gốc ~1,364đ. Hộp 50v)
('SP-0018', 'NCC-0002', 1, 68200.00), -- Traphaco

-- SP-0019: Omeprazol (Giá bán viên: 2,000đ -> Thặng 10%. Gốc ~1,818đ. Lọ 14v)
('SP-0019', 'NCC-0008', 1, 25452.00),

-- SP-0020: Ginkgo Biloba (Giá bán viên: 3,000đ -> Thặng 10%. Gốc ~2,727đ. Lọ 60v)
('SP-0020', 'NCC-0007', 1, 163620.00),
('SP-0020', 'NCC-0003', 1, 164000.00),

-- SP-0021: Metformin (Giá bán viên: 700đ -> Thặng 15%. Gốc ~609đ. Hộp 100v)
('SP-0021', 'NCC-0009', 1, 60900.00),

-- SP-0022: Decolgen (Giá bán viên: 1,500đ -> Thặng 10%. Gốc ~1,364đ. Hộp 100v)
('SP-0022', 'NCC-0001', 1, 136400.00),

-- SP-0023: Vitamin E (Giá bán viên: 2,800đ -> Thặng 10%. Gốc ~2,545đ. Lọ 100v)
('SP-0023', 'NCC-0005', 1, 254500.00),

-- SP-0024: Alprazolam (Giá bán viên: 1,500đ -> Thặng 10%. Gốc ~1,364đ. Hộp 30v)
('SP-0024', 'NCC-0010', 1, 40920.00),

-- SP-0025: Betadine (Giá bán chai: 35,000đ -> Thặng 7%. Gốc ~32,710đ. Chai 1)
('SP-0025', 'NCC-0004', 1, 32710.00),

-- SP-0026: Centrum Silver (Giá bán viên: 4,000đ -> Thặng 10%. Gốc ~3,636đ. Lọ 125v)
('SP-0026', 'NCC-0006', 1, 454500.00),

-- SP-0027: Ciprofloxacin (Giá bán viên: 3,500đ -> Thặng 10%. Gốc ~3,182đ. Hộp 20v)
('SP-0027', 'NCC-0003', 1, 63640.00),

-- SP-0028: Efferagan (Giá bán viên: 3,000đ -> Thặng 10%. Gốc ~2,727đ. Hộp 40v)
('SP-0028', 'NCC-0001', 1, 109080.00),

-- SP-0029: Collagen (Giá bán lọ: 80,000đ -> Thặng 7%. Gốc ~74,766đ. Hộp 10 lọ)
('SP-0029', 'NCC-0008', 1, 747660.00),

-- SP-0030: Prednison (Giá bán viên: 300đ -> Thặng 15%. Gốc ~261đ. Lọ 200v)
('SP-0030', 'NCC-0009', 1, 52200.00),

-- SP-0031: Dầu gió (Giá bán chai: 45,000đ -> Thặng 7%. Gốc ~42,056đ. Chai 1)
('SP-0031', 'NCC-0002', 1, 42056.00),

-- SP-0032: Sữa Ensure (Giá bán lon: 750,000đ -> Thặng 5%. Gốc ~714,286đ. Lon 1)
('SP-0032', 'NCC-0006', 1, 714286.00),

-- SP-0033: Aspirin 81mg (Giá bán viên: 600đ -> Thặng 15%. Gốc ~522đ. Lọ 100v)
('SP-0033', 'NCC-0010', 1, 52200.00),
('SP-0033', 'NCC-0001', 1, 52000.00),

-- SP-0034: Tiffy (Giá bán viên: 1,300đ -> Thặng 10%. Gốc ~1,182đ. Hộp 100v)
('SP-0034', 'NCC-0004', 1, 118200.00),

-- SP-0035: One A Day Men (Giá bán viên: 4,500đ -> Thặng 10%. Gốc ~4,091đ. Lọ 100v)
('SP-0035', 'NCC-0005', 1, 409100.00),

-- SP-0036: Domperidon (Giá bán viên: 1,000đ -> Thặng 15%. Gốc ~870đ. Hộp 100v)
('SP-0036', 'NCC-0008', 1, 87000.00),

-- SP-0037: Dầu cá (Giá bán viên: 2,000đ -> Thặng 10%. Gốc ~1,818đ. Lọ 200v)
('SP-0037', 'NCC-0007', 1, 363600.00),

-- SP-0038: Telfast (Giá bán viên: 12,000đ -> Thặng 7%. Gốc ~11,215đ. Hộp 10v)
('SP-0038', 'NCC-0005', 1, 112150.00), -- Sanofi

-- SP-0039: Cephalexin (Giá bán viên: 1,800đ -> Thặng 10%. Gốc ~1,636đ. Hộp 100v)
('SP-0039', 'NCC-0003', 1, 163600.00),

-- SP-0040: DHC rau củ (Giá bán gói: 250,000đ -> Thặng 5%. Gốc ~238,095đ. Gói 1)
('SP-0040', 'NCC-0008', 1, 238095.00),

-- SP-0041: Diazepam (Giá bán viên: 900đ -> Thặng 15%. Gốc ~783đ. Hộp 100v)
('SP-0041', 'NCC-0010', 1, 78300.00),

-- SP-0042: Urgo (Giá bán miếng: 1,500đ -> Thặng 10%. Gốc ~1,364đ. Hộp 20 miếng)
('SP-0042', 'NCC-0006', 1, 27280.00),

-- SP-0043: Melatonin (Giá bán viên: 4,000đ -> Thặng 10%. Gốc ~3,636đ. Lọ 60v)
('SP-0043', 'NCC-0007', 1, 218160.00),

-- SP-0044: Panadol Extra (Giá bán viên: 1,800đ -> Thặng 10%. Gốc ~1,636đ. Hộp 120v)
('SP-0044', 'NCC-0001', 1, 196320.00),

-- SP-0045: Enervon-C (Giá bán viên: 2,000đ -> Thặng 10%. Gốc ~1,818đ. Hộp 100v)
('SP-0045', 'NCC-0004', 1, 181800.00),

-- SP-0046: Nước muối (Giá bán chai: 10,000đ -> Thặng 7%. Gốc ~9,346đ. Chai 1)
('SP-0046', 'NCC-0009', 1, 9346.00),
('SP-0046', 'NCC-0002', 1, 9350.00),

-- SP-0047: Crest 3D (Giá bán tuýp: 120,000đ -> Thặng 5%. Gốc ~114,286đ. Tuýp 1)
('SP-0047', 'NCC-0006', 1, 114286.00),

-- SP-0048: Strepsils (Giá bán viên: 2,500đ -> Thặng 10%. Gốc ~2,273đ. Hộp 24v)
('SP-0048', 'NCC-0003', 1, 54552.00),

-- SP-0049: Biotin (Giá bán viên: 3,000đ -> Thặng 10%. Gốc ~2,727đ. Lọ 100v)
('SP-0049', 'NCC-0007', 1, 272700.00),

-- SP-0050: Phosphalugel (Giá bán gói: 4,000đ -> Thặng 10%. Gốc ~3,636đ. Hộp 26 gói)
('SP-0050', 'NCC-0005', 1, 94536.00);
GO

-- ===================================================================
-- 11. Bảng KhuyenMai_SanPham
-- ===================================================================
-- KM-0001: Giảm 10% khi mua từ 3 sản phẩm (Áp dụng cho các mặt hàng thông dụng)
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP) VALUES
('KM-0001', 'SP-0001'), -- Paracetamol
('KM-0001', 'SP-0003'), -- Vitamin C
('KM-0001', 'SP-0005'), -- Berberin
('KM-0001', 'SP-0009'), -- Clorpheniramin
('KM-0001', 'SP-0014'), -- Oresol
('KM-0001', 'SP-0022'), -- Decolgen
('KM-0001', 'SP-0025'), -- Betadine
('KM-0001', 'SP-0031'), -- Dầu gió
('KM-0001', 'SP-0044'), -- Panadol Extra
('KM-0001', 'SP-0046'), -- Nước muối sinh lý
('KM-0001', 'SP-0048'); -- Strepsils
GO

-- KM-0002: Mua 5 tặng 1 (Áp dụng cho một số TPCN)
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP) VALUES
('KM-0002', 'SP-0004'), -- Omega 3
('KM-0002', 'SP-0020'), -- Ginkgo Biloba
('KM-0002', 'SP-0045'); -- Enervon-C
GO

-- KM-0003: Giảm 15% cho sản phẩm của Traphaco
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP) VALUES
('KM-0003', 'SP-0008'), -- Hoạt huyết dưỡng não Traphaco
('KM-0003', 'SP-0018'); -- Boganic (Traphaco)
GO

-- KM-0004: Giảm giá Black Friday 20% (Sắp diễn ra - Áp dụng cho TPCN/Mỹ phẩm)
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP) VALUES
('KM-0004', 'SP-0007'), -- Blackmores Glucosamine
('KM-0004', 'SP-0023'), -- Nature Made Vitamin E
('KM-0004', 'SP-0029'), -- Collagen AEC
('KM-0004', 'SP-0037'), -- Dầu cá Nature's Bounty
('KM-0004', 'SP-0047'), -- Crest 3D White
('KM-0004', 'SP-0049'); -- Biotin
GO

-- KM-0005: Xả hàng cận date giảm 50% (Áp dụng cho 1 số thuốc kê đơn)
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP) VALUES
('KM-0005', 'SP-0006'), -- Atorvastatin
('KM-0005', 'SP-0017'); -- Cialis
GO

-- KM-0006: Giảm 30% cho Thực phẩm chức năng (TPCN)
-- Áp dụng cho TẤT CẢ sản phẩm có loaiSanPham = N'THUC_PHAM_CHUC_NANG'
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP) VALUES
('KM-0006', 'SP-0003'),
('KM-0006', 'SP-0004'),
('KM-0006', 'SP-0007'),
('KM-0006', 'SP-0008'),
('KM-0006', 'SP-0010'),
('KM-0006', 'SP-0013'),
('KM-0006', 'SP-0016'),
('KM-0006', 'SP-0018'),
('KM-0006', 'SP-0020'),
('KM-0006', 'SP-0023'),
('KM-0006', 'SP-0026'),
('KM-0006', 'SP-0029'),
('KM-0006', 'SP-0032'),
('KM-0006', 'SP-0035'),
('KM-0006', 'SP-0037'),
('KM-0006', 'SP-0040'),
('KM-0006', 'SP-0042'),
('KM-0006', 'SP-0043'),
('KM-0006', 'SP-0045'),
('KM-0006', 'SP-0047'),
('KM-0006', 'SP-0049');
GO

-- KM-0007: Mua 2 Tặng 1
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP) VALUES
('KM-0007', 'SP-0012'), -- Siro ho Prospan
('KM-0007', 'SP-0032'), -- Sữa Ensure Gold
('KM-0007', 'SP-0042'), -- Urgo
('KM-0007', 'SP-0050'); -- Phosphalugel
GO

-- KM-0008: Giảm 10% cho sản phẩm của Sanofi
INSERT INTO KhuyenMai_SanPham (maKhuyenMai, maSP) VALUES
('KM-0008', 'SP-0010'), -- Canxi Corbiere (Sanofi)
('KM-0008', 'SP-0028'), -- Efferagan (Sanofi)
('KM-0008', 'SP-0050'); -- Phosphalugel (Sanofi)
GO

-- Ghi chú: 
-- KM-0009 (Giảm 5% tổng hóa đơn) và KM-0010 (Hàng tặng) 
-- có thể không cần gán vào bảng này vì chúng được xử lý ở cấp độ Hóa đơn (logic nghiệp vụ)
-- chứ không phải áp dụng cho 1 sản phẩm cụ thể.

-- ===================================================================
-- 12. Bảng LichSuCaLam
-- ===================================================================

INSERT INTO LichSuCaLam (maNV, maCa, ngayLamViec, thoiGianVaoCa, thoiGianRaCa, ghiChu) VALUES
-- ===================================================================
-- Lịch sử cho NV-0001 (Võ Tiến Khoa)
-- ===================================================================
('NV-0001', 'SANG', '2025-11-01', '06:58:00', '15:02:00', NULL),
('NV-0001', 'TOI', '2025-11-02', '14:59:00', '22:01:00', NULL),
('NV-0001', 'SANG', '2025-11-03', '07:01:00', '15:00:00', NULL),
('NV-0001', 'TOI', '2025-11-04', '15:00:00', '22:03:00', NULL),
('NV-0001', 'SANG', '2025-11-05', '06:57:00', '15:05:00', NULL),
('NV-0001', 'TOI', '2025-11-06', '14:58:00', '22:00:00', NULL),
('NV-0001', 'SANG', '2025-11-07', '07:00:00', '15:01:00', NULL),
('NV-0001', 'TOI', '2025-11-08', '15:02:00', '22:05:00', N'Vào trễ 2 phút'),
('NV-0001', 'SANG', '2025-11-09', '06:59:00', '15:00:00', NULL),
('NV-0001', 'TOI', '2025-11-10', '14:59:00', '22:01:00', NULL),
('NV-0001', 'SANG', '2025-11-12', '07:00:00', '15:03:00', NULL),
-- Ca đang làm (Giả định hôm nay là 13/11/2025)
('NV-0001', 'SANG', '2025-11-13', '06:59:00', NULL, N'Đang trong ca'),

-- ===================================================================
-- Lịch sử cho NV-0002 (Hồ Minh Khang)
-- ===================================================================
('NV-0002', 'TOI', '2025-11-01', '14:57:00', '22:02:00', NULL),
('NV-0002', 'SANG', '2025-11-02', '06:59:00', '15:01:00', NULL),
('NV-0002', 'TOI', '2025-11-03', '15:00:00', '22:00:00', NULL),
('NV-0002', 'SANG', '2025-11-04', '07:02:00', '15:03:00', NULL),
('NV-0002', 'TOI', '2025-11-05', '14:58:00', '22:01:00', NULL),
('NV-0002', 'SANG', '2025-11-06', '06:58:00', '15:00:00', NULL),
('NV-0002', 'TOI', '2025-11-07', '15:01:00', '22:02:00', NULL),
('NV-0002', 'SANG', '2025-11-08', '07:00:00', '15:05:00', NULL),
('NV-0002', 'TOI', '2025-11-09', '14:59:00', '22:00:00', NULL),
('NV-0002', 'SANG', '2025-11-10', '06:57:00', '15:01:00', NULL),
('NV-0002', 'TOI', '2025-11-12', '15:00:00', '22:03:00', NULL),

-- ===================================================================
-- Lịch sử cho NV-0003 (Nguyễn Khánh Quân)
-- ===================================================================
('NV-0003', 'SANG', '2025-10-20', '06:55:00', '15:00:00', NULL),
('NV-0003', 'SANG', '2025-10-21', '06:58:00', '15:02:00', NULL),
('NV-0003', 'TOI', '2025-10-22', '14:59:00', '22:01:00', NULL),
('NV-0003', 'TOI', '2025-10-23', '15:01:00', '22:00:00', NULL),
('NV-0003', 'SANG', '2025-10-24', '07:00:00', '15:03:00', NULL),
('NV-0003', 'TOI', '2025-10-25', '14:57:00', '22:05:00', NULL),
('NV-0003', 'SANG', '2025-10-27', '06:59:00', '15:01:00', NULL),
('NV-0003', 'TOI', '2025-10-28', '15:00:00', '21:30:00', N'Xin về sớm (việc riêng)'),
('NV-0003', 'SANG', '2025-10-29', '07:01:00', '15:00:00', NULL),
('NV-0003', 'TOI', '2025-10-30', '14:58:00', '22:02:00', NULL),
('NV-0003', 'SANG', '2025-11-12', '06:58:00', '15:00:00', NULL),

-- ===================================================================
-- Lịch sử cho NV-0004 (Trần Đình Sĩ)
-- ===================================================================
('NV-0004', 'TOI', '2025-11-01', '15:00:00', '22:01:00', NULL),
('NV-0004', 'TOI', '2025-11-02', '14:58:00', '22:03:00', NULL),
('NV-0004', 'SANG', '2025-11-03', '06:59:00', '15:00:00', NULL),
('NV-0004', 'SANG', '2025-11-04', '07:00:00', '15:02:00', NULL),
('NV-0004', 'TOI', '2025-11-05', '14:59:00', '22:00:00', NULL),
('NV-0004', 'SANG', '2025-11-06', '06:58:00', '15:01:00', NULL),
('NV-0004', 'TOI', '2025-11-07', '15:01:00', '22:05:00', NULL),
('NV-0004', 'SANG', '2025-11-08', '07:01:00', '15:00:00', NULL),
('NV-0004', 'TOI', '2025-11-09', '14:57:00', '22:02:00', NULL),
('NV-0004', 'SANG', '2025-11-10', '06:59:00', '15:03:00', NULL),
('NV-0004', 'TOI', '2025-11-11', '15:00:00', '22:00:00', NULL),
('NV-0004', 'SANG', '2025-11-12', '07:00:00', '15:01:00', NULL);
GO

-- ===================================================================
-- 13. Bảng HoaDon (Đã cập nhật theo cấu trúc mới: Bỏ cột TrangThai)
-- ===================================================================
SET NOCOUNT ON;
GO

-- ===================================================================
-- 1. DỮ LIỆU THÁNG 6, 2025 (100 HÓA ĐƠN)
-- ===================================================================
GO
DECLARE @i_T6 INT = 1;
DECLARE @maHoaDon_T6 NVARCHAR(14);
DECLARE @maNV_T6 NVARCHAR(7);
DECLARE @maKH_T6 NVARCHAR(8);
DECLARE @ngayLap_T6 DATETIME2;
DECLARE @chuyenKhoan_T6 BIT;
-- Đã xóa @trangThai_T6
DECLARE @tongTien_T6 DECIMAL(18, 2);
DECLARE @day_T6 INT, @hour_T6 INT, @minute_T6 INT;
DECLARE @kh_id_T6 INT, @nv_id_T6 INT;

WHILE (@i_T6 <= 100)
BEGIN
    SET @day_T6 = ((@i_T6 - 1) % 30) + 1;
    SET @hour_T6 = 7 + ((@i_T6 - 1) % 15);
    SET @minute_T6 = @i_T6 % 60;
    SET @ngayLap_T6 = DATETIMEFROMPARTS(2025, 6, @day_T6, @hour_T6, @minute_T6, 0, 0);
    SET @maHoaDon_T6 = 'HD-' + FORMAT(@ngayLap_T6, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@i_T6 AS NVARCHAR(4)), 4);
    SET @nv_id_T6 = ((@i_T6 - 1) % 4) + 1;
    SET @maNV_T6 = 'NV-000' + CAST(@nv_id_T6 AS NVARCHAR(1));

    IF @i_T6 <= 60 
        SET @maKH_T6 = 'KH-00000';
    ELSE 
    BEGIN
        SET @kh_id_T6 = ((@i_T6 - 61) % 50) + 1;
        SET @maKH_T6 = 'KH-' + RIGHT('00000' + CAST(@kh_id_T6 AS NVARCHAR(2)), 5);
    END

    SET @chuyenKhoan_T6 = CASE WHEN @i_T6 % 5 = 0 THEN 1 ELSE 0 END;
    -- Đã xóa dòng SET @trangThai_T6
    SET @tongTien_T6 = ROUND((50000 + ((@i_T6 * 317) % 1000000)), -3);
    
    -- Insert không có cột trangThai
    INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, tongTien)
    VALUES (@maHoaDon_T6, @maNV_T6, @ngayLap_T6, @maKH_T6, @chuyenKhoan_T6, @tongTien_T6);

    SET @i_T6 = @i_T6 + 1;
END;
GO

-- ===================================================================
-- 2. DỮ LIỆU THÁNG 7, 2025 (100 HÓA ĐƠN)
-- ===================================================================
GO
DECLARE @i_T7 INT = 1;
DECLARE @maHoaDon_T7 NVARCHAR(14);
DECLARE @maNV_T7 NVARCHAR(7);
DECLARE @maKH_T7 NVARCHAR(8);
DECLARE @ngayLap_T7 DATETIME2;
DECLARE @chuyenKhoan_T7 BIT;
DECLARE @tongTien_T7 DECIMAL(18, 2);
DECLARE @day_T7 INT, @hour_T7 INT, @minute_T7 INT;
DECLARE @kh_id_T7 INT, @nv_id_T7 INT;

WHILE (@i_T7 <= 100)
BEGIN
    SET @day_T7 = ((@i_T7 - 1) % 31) + 1;
    SET @hour_T7 = 7 + ((@i_T7 - 1) % 15);
    SET @minute_T7 = @i_T7 % 60;
    SET @ngayLap_T7 = DATETIMEFROMPARTS(2025, 7, @day_T7, @hour_T7, @minute_T7, 0, 0);
    SET @maHoaDon_T7 = 'HD-' + FORMAT(@ngayLap_T7, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@i_T7 AS NVARCHAR(4)), 4);
    SET @nv_id_T7 = ((@i_T7 - 1) % 4) + 1;
    SET @maNV_T7 = 'NV-000' + CAST(@nv_id_T7 AS NVARCHAR(1));

    IF @i_T7 <= 60
        SET @maKH_T7 = 'KH-00000';
    ELSE 
    BEGIN
        SET @kh_id_T7 = ((@i_T7 - 61) % 50) + 1;
        SET @maKH_T7 = 'KH-' + RIGHT('00000' + CAST(@kh_id_T7 AS NVARCHAR(2)), 5);
    END

    SET @chuyenKhoan_T7 = CASE WHEN @i_T7 % 5 = 0 THEN 1 ELSE 0 END;
    SET @tongTien_T7 = ROUND((50000 + ((@i_T7 * 317) % 1000000)), -3);
    
    INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, tongTien)
    VALUES (@maHoaDon_T7, @maNV_T7, @ngayLap_T7, @maKH_T7, @chuyenKhoan_T7, @tongTien_T7);

    SET @i_T7 = @i_T7 + 1;
END;
GO

-- ===================================================================
-- 3. DỮ LIỆU THÁNG 8, 2025 (100 HÓA ĐƠN)
-- ===================================================================
GO
DECLARE @i_T8 INT = 1;
DECLARE @maHoaDon_T8 NVARCHAR(14);
DECLARE @maNV_T8 NVARCHAR(7);
DECLARE @maKH_T8 NVARCHAR(8);
DECLARE @ngayLap_T8 DATETIME2;
DECLARE @chuyenKhoan_T8 BIT;
DECLARE @tongTien_T8 DECIMAL(18, 2);
DECLARE @day_T8 INT, @hour_T8 INT, @minute_T8 INT;
DECLARE @kh_id_T8 INT, @nv_id_T8 INT;

WHILE (@i_T8 <= 100)
BEGIN
    SET @day_T8 = ((@i_T8 - 1) % 31) + 1;
    SET @hour_T8 = 7 + ((@i_T8 - 1) % 15);
    SET @minute_T8 = @i_T8 % 60;
    SET @ngayLap_T8 = DATETIMEFROMPARTS(2025, 8, @day_T8, @hour_T8, @minute_T8, 0, 0);
    SET @maHoaDon_T8 = 'HD-' + FORMAT(@ngayLap_T8, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@i_T8 AS NVARCHAR(4)), 4);
    SET @nv_id_T8 = ((@i_T8 - 1) % 4) + 1;
    SET @maNV_T8 = 'NV-000' + CAST(@nv_id_T8 AS NVARCHAR(1));

    IF @i_T8 <= 60 
        SET @maKH_T8 = 'KH-00000';
    ELSE 
    BEGIN
        SET @kh_id_T8 = ((@i_T8 - 61) % 50) + 1;
        SET @maKH_T8 = 'KH-' + RIGHT('00000' + CAST(@kh_id_T8 AS NVARCHAR(2)), 5);
    END

    SET @chuyenKhoan_T8 = CASE WHEN @i_T8 % 5 = 0 THEN 1 ELSE 0 END;
    SET @tongTien_T8 = ROUND((50000 + ((@i_T8 * 317) % 1000000)), -3);
    
    INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, tongTien)
    VALUES (@maHoaDon_T8, @maNV_T8, @ngayLap_T8, @maKH_T8, @chuyenKhoan_T8, @tongTien_T8);

    SET @i_T8 = @i_T8 + 1;
END;
GO

-- ===================================================================
-- 4. DỮ LIỆU THÁNG 9, 2025 (100 HÓA ĐƠN)
-- ===================================================================
GO
DECLARE @i_T9 INT = 1;
DECLARE @maHoaDon_T9 NVARCHAR(14);
DECLARE @maNV_T9 NVARCHAR(7);
DECLARE @maKH_T9 NVARCHAR(8);
DECLARE @ngayLap_T9 DATETIME2;
DECLARE @chuyenKhoan_T9 BIT;
DECLARE @tongTien_T9 DECIMAL(18, 2);
DECLARE @day_T9 INT, @hour_T9 INT, @minute_T9 INT;
DECLARE @kh_id_T9 INT, @nv_id_T9 INT;

WHILE (@i_T9 <= 100)
BEGIN
    SET @day_T9 = ((@i_T9 - 1) % 30) + 1;
    SET @hour_T9 = 7 + ((@i_T9 - 1) % 15);
    SET @minute_T9 = @i_T9 % 60;
    SET @ngayLap_T9 = DATETIMEFROMPARTS(2025, 9, @day_T9, @hour_T9, @minute_T9, 0, 0);
    SET @maHoaDon_T9 = 'HD-' + FORMAT(@ngayLap_T9, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@i_T9 AS NVARCHAR(4)), 4);
    SET @nv_id_T9 = ((@i_T9 - 1) % 4) + 1;
    SET @maNV_T9 = 'NV-000' + CAST(@nv_id_T9 AS NVARCHAR(1));

    IF @i_T9 <= 60
        SET @maKH_T9 = 'KH-00000';
    ELSE 
    BEGIN
        SET @kh_id_T9 = ((@i_T9 - 61) % 50) + 1;
        SET @maKH_T9 = 'KH-' + RIGHT('00000' + CAST(@kh_id_T9 AS NVARCHAR(2)), 5);
    END

    SET @chuyenKhoan_T9 = CASE WHEN @i_T9 % 5 = 0 THEN 1 ELSE 0 END;
    SET @tongTien_T9 = ROUND((50000 + ((@i_T9 * 317) % 1000000)), -3);
    
    INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, tongTien)
    VALUES (@maHoaDon_T9, @maNV_T9, @ngayLap_T9, @maKH_T9, @chuyenKhoan_T9, @tongTien_T9);

    SET @i_T9 = @i_T9 + 1;
END;
GO

-- ===================================================================
-- 5. DỮ LIỆU THÁNG 10, 2025 (100 HÓA ĐƠN)
-- ===================================================================
GO
DECLARE @i_T10 INT = 1;
DECLARE @maHoaDon_T10 NVARCHAR(14);
DECLARE @maNV_T10 NVARCHAR(7);
DECLARE @maKH_T10 NVARCHAR(8);
DECLARE @ngayLap_T10 DATETIME2;
DECLARE @chuyenKhoan_T10 BIT;
DECLARE @tongTien_T10 DECIMAL(18, 2);
DECLARE @day_T10 INT, @hour_T10 INT, @minute_T10 INT;
DECLARE @kh_id_T10 INT, @nv_id_T10 INT;

WHILE (@i_T10 <= 100)
BEGIN
    SET @day_T10 = ((@i_T10 - 1) % 31) + 1;
    SET @hour_T10 = 7 + ((@i_T10 - 1) % 15);
    SET @minute_T10 = @i_T10 % 60;
    SET @ngayLap_T10 = DATETIMEFROMPARTS(2025, 10, @day_T10, @hour_T10, @minute_T10, 0, 0);
    SET @maHoaDon_T10 = 'HD-' + FORMAT(@ngayLap_T10, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@i_T10 AS NVARCHAR(4)), 4);
    SET @nv_id_T10 = ((@i_T10 - 1) % 4) + 1;
    SET @maNV_T10 = 'NV-000' + CAST(@nv_id_T10 AS NVARCHAR(1));

    IF @i_T10 <= 60
        SET @maKH_T10 = 'KH-00000';
    ELSE 
    BEGIN
        SET @kh_id_T10 = ((@i_T10 - 61) % 50) + 1;
        SET @maKH_T10 = 'KH-' + RIGHT('00000' + CAST(@kh_id_T10 AS NVARCHAR(2)), 5);
    END

    SET @chuyenKhoan_T10 = CASE WHEN @i_T10 % 5 = 0 THEN 1 ELSE 0 END;
    SET @tongTien_T10 = ROUND((50000 + ((@i_T10 * 317) % 1000000)), -3);
    
    INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, tongTien)
    VALUES (@maHoaDon_T10, @maNV_T10, @ngayLap_T10, @maKH_T10, @chuyenKhoan_T10, @tongTien_T10);

    SET @i_T10 = @i_T10 + 1;
END;
GO

-- ===================================================================
-- 6. DỮ LIỆU THÁNG 11, 2025 (100 HÓA ĐƠN)
-- ===================================================================
GO
DECLARE @i_T11 INT = 1;
DECLARE @maHoaDon_T11 NVARCHAR(14);
DECLARE @maNV_T11 NVARCHAR(7);
DECLARE @maKH_T11 NVARCHAR(8);
DECLARE @ngayLap_T11 DATETIME2;
DECLARE @chuyenKhoan_T11 BIT;
DECLARE @tongTien_T11 DECIMAL(18, 2);
DECLARE @day_T11 INT, @hour_T11 INT, @minute_T11 INT;
DECLARE @kh_id_T11 INT, @nv_id_T11 INT;

WHILE (@i_T11 <= 100)
BEGIN
    SET @day_T11 = ((@i_T11 - 1) % 30) + 1;
    SET @hour_T11 = 7 + ((@i_T11 - 1) % 15);
    SET @minute_T11 = @i_T11 % 60;
    SET @ngayLap_T11 = DATETIMEFROMPARTS(2025, 11, @day_T11, @hour_T11, @minute_T11, 0, 0);
    SET @maHoaDon_T11 = 'HD-' + FORMAT(@ngayLap_T11, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@i_T11 AS NVARCHAR(4)), 4);
    SET @nv_id_T11 = ((@i_T11 - 1) % 4) + 1;
    SET @maNV_T11 = 'NV-000' + CAST(@nv_id_T11 AS NVARCHAR(1));

    IF @i_T11 <= 60
        SET @maKH_T11 = 'KH-00000';
    ELSE 
    BEGIN
        SET @kh_id_T11 = ((@i_T11 - 61) % 50) + 1;
        SET @maKH_T11 = 'KH-' + RIGHT('00000' + CAST(@kh_id_T11 AS NVARCHAR(2)), 5);
    END

    SET @chuyenKhoan_T11 = CASE WHEN @i_T11 % 5 = 0 THEN 1 ELSE 0 END;
    
    -- Đã xóa logic IF @day_T11 <= 13 liên quan đến status

    SET @tongTien_T11 = ROUND((50000 + ((@i_T11 * 317) % 1000000)), -3);
    
    INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, tongTien)
    VALUES (@maHoaDon_T11, @maNV_T11, @ngayLap_T11, @maKH_T11, @chuyenKhoan_T11, @tongTien_T11);

    SET @i_T11 = @i_T11 + 1;
END;
GO

-- ===================================================================
-- 7. DỮ LIỆU THÁNG 12, 2025 (100 HÓA ĐƠN)
-- ===================================================================
GO
DECLARE @i_T12 INT = 1;
DECLARE @maHoaDon_T12 NVARCHAR(14);
DECLARE @maNV_T12 NVARCHAR(7);
DECLARE @maKH_T12 NVARCHAR(8);
DECLARE @ngayLap_T12 DATETIME2;
DECLARE @chuyenKhoan_T12 BIT;
DECLARE @tongTien_T12 DECIMAL(18, 2);
DECLARE @day_T12 INT, @hour_T12 INT, @minute_T12 INT;
DECLARE @kh_id_T12 INT, @nv_id_T12 INT;

WHILE (@i_T12 <= 100)
BEGIN
    SET @day_T12 = ((@i_T12 - 1) % 31) + 1;
    SET @hour_T12 = 7 + ((@i_T12 - 1) % 15);
    SET @minute_T12 = @i_T12 % 60;
    SET @ngayLap_T12 = DATETIMEFROMPARTS(2025, 12, @day_T12, @hour_T12, @minute_T12, 0, 0);
    SET @maHoaDon_T12 = 'HD-' + FORMAT(@ngayLap_T12, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@i_T12 AS NVARCHAR(4)), 4);
    SET @nv_id_T12 = ((@i_T12 - 1) % 4) + 1;
    SET @maNV_T12 = 'NV-000' + CAST(@nv_id_T12 AS NVARCHAR(1));

    IF @i_T12 <= 60 
        SET @maKH_T12 = 'KH-00000';
    ELSE 
    BEGIN
        SET @kh_id_T12 = ((@i_T12 - 61) % 50) + 1;
        SET @maKH_T12 = 'KH-' + RIGHT('00000' + CAST(@kh_id_T12 AS NVARCHAR(2)), 5);
    END

    SET @chuyenKhoan_T12 = CASE WHEN @i_T12 % 5 = 0 THEN 1 ELSE 0 END;
    SET @tongTien_T12 = ROUND((50000 + ((@i_T12 * 317) % 1000000)), -3);
    
    INSERT INTO HoaDon (maHoaDon, maNV, ngayLapHoaDon, maKH, chuyenKhoan, tongTien)
    VALUES (@maHoaDon_T12, @maNV_T12, @ngayLap_T12, @maKH_T12, @chuyenKhoan_T12, @tongTien_T12);

    SET @i_T12 = @i_T12 + 1;
END;
GO

-- ===================================================================
-- 14. TẠO CHI TIẾT HÓA ĐƠN (CHO 700 HÓA ĐƠN MỚI)
-- ===================================================================

BEGIN TRANSACTION;
BEGIN
    DECLARE @cthd_global_counter INT = 1;
    DECLARE @maHoaDon NVARCHAR(14);
    DECLARE @ngayLap DATETIME2;

    DECLARE CTHD_Cursor CURSOR FAST_FORWARD FOR
    SELECT maHoaDon, ngayLapHoaDon
    FROM HoaDon
    ORDER BY ngayLapHoaDon, maHoaDon; -- Sắp xếp cố định

    IF OBJECT_ID('tempdb..#TempDVT') IS NOT NULL
        DROP TABLE #TempDVT;

    SELECT 
        ROW_NUMBER() OVER (ORDER BY maDonViTinh) AS id,
        maDonViTinh,
        giaBanTheoDonVi
    INTO #TempDVT
    FROM DonViTinh;

    DECLARE @maxDVT INT = (SELECT COUNT(*) FROM #TempDVT);

    OPEN CTHD_Cursor;
    FETCH NEXT FROM CTHD_Cursor INTO @maHoaDon, @ngayLap;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DECLARE @itemsPerInvoice INT = 3 + (@cthd_global_counter % 5);
        DECLARE @item_i INT = 1;

        WHILE (@item_i <= @itemsPerInvoice)
        BEGIN
            DECLARE @maCTHD NVARCHAR(16) = 'CTHD-' + FORMAT(@ngayLap, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@cthd_global_counter AS NVARCHAR(4)), 4);
            DECLARE @dvt_index INT = 1 + ((@cthd_global_counter - 1) % @maxDVT);
            DECLARE @maDonViTinh NVARCHAR(20);
            DECLARE @donGia DECIMAL(18, 2);
            
            SELECT 
                @maDonViTinh = maDonViTinh, 
                @donGia = giaBanTheoDonVi 
            FROM #TempDVT 
            WHERE id = @dvt_index;

            DECLARE @soLuong INT = 1 + (@cthd_global_counter % 3);
            DECLARE @giamGia DECIMAL(5, 2) = 0.00;
            IF @cthd_global_counter % 20 = 0 
                SET @giamGia = 0.05;

            INSERT INTO ChiTietHoaDon (maChiTietHoaDon, maHoaDon, maDonViTinh, soLuong, donGia, giamGia)
            VALUES (@maCTHD, @maHoaDon, @maDonViTinh, @soLuong, @donGia, @giamGia);

            SET @item_i = @item_i + 1;
            SET @cthd_global_counter = @cthd_global_counter + 1;
        END
        FETCH NEXT FROM CTHD_Cursor INTO @maHoaDon, @ngayLap;
    END

    CLOSE CTHD_Cursor;
    DEALLOCATE CTHD_Cursor;
    DROP TABLE #TempDVT;
END;
GO

-- ===================================================================
-- 9. CẬP NHẬT TỔNG TIỀN HÓA ĐƠN
-- ===================================================================
WITH HoaDonTotals AS (
    SELECT 
        maHoaDon, 
        SUM(thanhTien) AS newTongTien
    FROM ChiTietHoaDon
    GROUP BY maHoaDon
)
UPDATE HoaDon
SET tongTien = ht.newTongTien
FROM HoaDon hd
JOIN HoaDonTotals ht ON hd.maHoaDon = ht.maHoaDon
WHERE 
    ISNULL(hd.tongTien, 0) <> ht.newTongTien;

COMMIT TRANSACTION;
GO

-- ===================================================================
-- 10. TẠO CHI TIẾT XUẤT LÔ (CHO CTHD MỚI)
-- ===================================================================
BEGIN TRANSACTION;

IF OBJECT_ID('tempdb..#CTHD_Info') IS NOT NULL 
    DROP TABLE #CTHD_Info;

SELECT 
    cthd.maChiTietHoaDon, 
    cthd.soLuong, 
    dvt.maSP,
    ROW_NUMBER() OVER (ORDER BY cthd.maHoaDon, cthd.maChiTietHoaDon) as global_rn
INTO #CTHD_Info
FROM ChiTietHoaDon cthd
JOIN DonViTinh dvt ON cthd.maDonViTinh = dvt.maDonViTinh;

IF OBJECT_ID('tempdb..#Lo_Info') IS NOT NULL 
    DROP TABLE #Lo_Info;

SELECT 
    maSP, 
    maLoSanPham,
    ROW_NUMBER() OVER(PARTITION BY maSP ORDER BY ngaySanXuat, maLoSanPham) as rn_per_sp,
    COUNT(*) OVER(PARTITION BY maSP) as count_per_sp
INTO #Lo_Info
FROM LoSanPham;

-- Trường hợp 1:1 (90%)
INSERT INTO ChiTietXuatLo (maLoSanPham, maChiTietHoaDon, soLuong)
SELECT
    lo.maLoSanPham,
    cthd.maChiTietHoaDon,
    cthd.soLuong
FROM #CTHD_Info AS cthd
JOIN #Lo_Info AS lo ON cthd.maSP = lo.maSP
WHERE
    (cthd.global_rn % 10) <> 0
    AND lo.rn_per_sp = (cthd.global_rn % lo.count_per_sp) + 1;

-- Trường hợp 1:N (10%)
INSERT INTO ChiTietXuatLo (maLoSanPham, maChiTietHoaDon, soLuong)
SELECT
    lo.maLoSanPham,
    cthd.maChiTietHoaDon,
    cthd.soLuong / 2
FROM #CTHD_Info AS cthd
JOIN #Lo_Info AS lo ON cthd.maSP = lo.maSP
WHERE
    (cthd.global_rn % 10) = 0
    AND lo.count_per_sp > 1
    AND lo.rn_per_sp = 1

UNION ALL

SELECT
    lo.maLoSanPham,
    cthd.maChiTietHoaDon,
    cthd.soLuong - (cthd.soLuong / 2)
FROM #CTHD_Info AS cthd
JOIN #Lo_Info AS lo ON cthd.maSP = lo.maSP
WHERE
    (cthd.global_rn % 10) = 0
    AND lo.count_per_sp > 1
    AND lo.rn_per_sp = 2;

DROP TABLE #CTHD_Info;
DROP TABLE #Lo_Info;

COMMIT TRANSACTION;
GO

-- ===================================================================
-- 11. TẠO PHIẾU TRẢ HÀNG (20 PHIẾU TỪ HĐ MỚI)
-- ===================================================================
BEGIN TRANSACTION;

IF OBJECT_ID('tempdb..#HoaDonToReturn') IS NOT NULL
    DROP TABLE #HoaDonToReturn;

-- Chọn 20 hóa đơn CŨ NHẤT (từ 700 HĐ mới) để trả hàng
SELECT 
    ROW_NUMBER() OVER (ORDER BY ngayLapHoaDon, maHoaDon) AS id, 
    maHoaDon, 
    ngayLapHoaDon, 
    tongTien 
INTO #HoaDonToReturn 
FROM (
    SELECT TOP 20 maHoaDon, ngayLapHoaDon, tongTien 
    FROM HoaDon 
    WHERE ngayLapHoaDon < '2025-11-01'
    ORDER BY ngayLapHoaDon, maHoaDon
) AS T;

DECLARE @i_PTH INT = 1;
DECLARE @maPhieuTraHang NVARCHAR(15);
DECLARE @ngayLapPhieuTraHang DATETIME2;
DECLARE @maNV_PTH NVARCHAR(7);
DECLARE @maHoaDon_PTH NVARCHAR(14);
DECLARE @tongTienHoanTra DECIMAL(18, 2);
DECLARE @originalTongTien DECIMAL(18, 2);

WHILE (@i_PTH <= 20)
BEGIN
    SELECT 
        @maHoaDon_PTH = maHoaDon, 
        @originalTongTien = tongTien 
    FROM #HoaDonToReturn 
    WHERE id = @i_PTH;

    DECLARE @day_PTH INT = ((@i_PTH - 1) % 10) + 1;
    DECLARE @month_PTH INT;
    IF @i_PTH <= 10
        SET @month_PTH = 10;
    ELSE
        SET @month_PTH = 11;
    
    SET @ngayLapPhieuTraHang = DATETIMEFROMPARTS(2025, @month_PTH, @day_PTH, 10, @i_PTH, 0, 0);
    SET @maPhieuTraHang = 'PTH-' + FORMAT(@ngayLapPhieuTraHang, 'ddMMyy') + '-' + RIGHT('0000' + CAST(@i_PTH AS NVARCHAR(4)), 4);
    DECLARE @nv_id_PTH INT = ((@i_PTH - 1) % 4) + 1;
    SET @maNV_PTH = 'NV-000' + CAST(@nv_id_PTH AS NVARCHAR(1));
    SET @tongTienHoanTra = ROUND(@originalTongTien * 0.3, 0); -- Placeholder

    INSERT INTO PhieuTraHang (maPhieuTraHang, ngayLapPhieuTraHang, maNV, maHoaDon, tongTienHoanTra)
    VALUES (@maPhieuTraHang, @ngayLapPhieuTraHang, @maNV_PTH, @maHoaDon_PTH, @tongTienHoanTra);

    SET @i_PTH = @i_PTH + 1;
END;

DROP TABLE #HoaDonToReturn;
COMMIT TRANSACTION;
GO

-- ===================================================================
-- 12. TẠO CHI TIẾT PHIẾU TRẢ HÀNG VÀ CẬP NHẬT TỔNG TIỀN PTH
-- ===================================================================
BEGIN TRANSACTION;

BEGIN
    IF OBJECT_ID('tempdb..#CTHD_Data') IS NOT NULL
        DROP TABLE #CTHD_Data;

    SELECT 
        cthd.maHoaDon, 
        cthd.maChiTietHoaDon, 
        cthd.soLuong, 
        cthd.donGia,
        cthd.giamGia,
        ROW_NUMBER() OVER(PARTITION BY cthd.maHoaDon ORDER BY cthd.maChiTietHoaDon) AS rn
    INTO #CTHD_Data
    FROM ChiTietHoaDon cthd
    JOIN PhieuTraHang pth ON cthd.maHoaDon = pth.maHoaDon;

    DECLARE @i_CTPTH INT = 1; 
    DECLARE @maPhieuTraHang_CTPTH NVARCHAR(15);
    DECLARE @maHoaDon_Goc_CTPTH NVARCHAR(14);

    DECLARE PTH_Cursor CURSOR FAST_FORWARD FOR
    SELECT maPhieuTraHang, maHoaDon 
    FROM PhieuTraHang
    ORDER BY maPhieuTraHang;

    OPEN PTH_Cursor;
    FETCH NEXT FROM PTH_Cursor INTO @maPhieuTraHang_CTPTH, @maHoaDon_Goc_CTPTH;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DECLARE @itemsToReturn INT = 1 + (@i_CTPTH % 2);
        DECLARE @item_j INT = 1;

        WHILE (@item_j <= @itemsToReturn)
        BEGIN
            DECLARE @maCTHD_Goc NVARCHAR(16);
            DECLARE @soLuong_Goc INT;
            DECLARE @donGia_Goc DECIMAL(18, 2);
            DECLARE @giamGia_Goc DECIMAL(5, 2);

            SELECT 
                @maCTHD_Goc = maChiTietHoaDon,
                @soLuong_Goc = soLuong,
                @donGia_Goc = donGia,
                @giamGia_Goc = giamGia
            FROM #CTHD_Data
            WHERE maHoaDon = @maHoaDon_Goc_CTPTH AND rn = @item_j;
            
            IF @maCTHD_Goc IS NOT NULL
            BEGIN
                DECLARE @truongHop NVARCHAR(50);
                DECLARE @tinhTrang NVARCHAR(30);
                DECLARE @giaTriHoanTra_Str NVARCHAR(20);
                DECLARE @giaTriHoanTra_Percent DECIMAL(5, 2);

                SELECT 
                    @truongHop = CASE @i_CTPTH % 3
                                    WHEN 0 THEN N'HANG_LOI_DO_NHA_SAN_XUAT'
                                    WHEN 1 THEN N'DI_UNG_MAN_CAM'
                                    ELSE N'NHU_CAU_KHACH_HANG' END,
                    @tinhTrang = CASE @i_CTPTH % 3
                                    WHEN 0 THEN N'HANG_NGUYEN_VEN'
                                    WHEN 1 THEN N'HANG_DA_SU_DUNG'
                                    ELSE N'HANG_KHONG_NGUYEN_VEN' END;

                IF @truongHop = N'HANG_LOI_DO_NHA_SAN_XUAT'
                BEGIN
                    SET @giaTriHoanTra_Str = N'100%';
                    SET @giaTriHoanTra_Percent = 1.0;
                END
                ELSE IF @tinhTrang = N'HANG_DA_SU_DUNG'
                BEGIN
                    SET @giaTriHoanTra_Str = N'Miễn trả hàng';
                    SET @giaTriHoanTra_Percent = 0.0;
                END
                ELSE
                BEGIN
                    SET @giaTriHoanTra_Str = N'70%';
                    SET @giaTriHoanTra_Percent = 0.7;
                END;

                DECLARE @thanhTienHoanTra DECIMAL(18, 2);
                SET @thanhTienHoanTra = ROUND(((@soLuong_Goc * @donGia_Goc * (1 - @giamGia_Goc)) * @giaTriHoanTra_Percent), 0);

                INSERT INTO ChiTietPhieuTraHang 
                    (maPhieuTraHang, maChiTietHoaDon, soLuong, truongHopDoiTra, tinhTrangSanPham, giaTriHoanTra, thanhTienHoanTra)
                VALUES 
                    (@maPhieuTraHang_CTPTH, @maCTHD_Goc, @soLuong_Goc, @truongHop, @tinhTrang, @giaTriHoanTra_Str, @thanhTienHoanTra);
            END
            
            SET @item_j = @item_j + 1;
        END

        SET @i_CTPTH = @i_CTPTH + 1;
        FETCH NEXT FROM PTH_Cursor INTO @maPhieuTraHang_CTPTH, @maHoaDon_Goc_CTPTH;
    END

    CLOSE PTH_Cursor;
    DEALLOCATE PTH_Cursor;
    DROP TABLE #CTHD_Data; 
END;
GO

-- ===================================================================
-- 13. CẬP NHẬT TỔNG TIỀN PHIẾU TRẢ HÀNG
-- ===================================================================
WITH PTH_Totals AS (
    SELECT 
        maPhieuTraHang, 
        SUM(thanhTienHoanTra) AS newTongTienHoanTra
    FROM ChiTietPhieuTraHang
    GROUP BY maPhieuTraHang
)
UPDATE PhieuTraHang
SET tongTienHoanTra = ISNULL(pth_t.newTongTienHoanTra, 0) -- ISNULL để xử lý phiếu không trả được gì
FROM PhieuTraHang pth
LEFT JOIN PTH_Totals pth_t ON pth.maPhieuTraHang = pth_t.maPhieuTraHang
WHERE 
    ISNULL(pth.tongTienHoanTra, 0) <> ISNULL(pth_t.newTongTienHoanTra, 0);

COMMIT TRANSACTION;
GO

-- 1. TẠO 10 DÒNG DỮ LIỆU CHO BẢNG PHIEU NHAP (Ban đầu tongTien = 0)
INSERT INTO PhieuNhap (maPhieuNhap, ngayTao, maNV, tongTien, ghiChu) VALUES
('PN-0001', DATEADD(DAY, -9, GETDATE()), 'NV-0001', 0, N'Nhập hàng định kỳ tháng 5'),
('PN-0002', DATEADD(DAY, -8, GETDATE()), 'NV-0002', 0, N'Nhập thuốc kháng sinh'),
('PN-0003', DATEADD(DAY, -7, GETDATE()), 'NV-0003', 0, N'Nhập bổ sung vitamin'),
('PN-0004', DATEADD(DAY, -6, GETDATE()), 'NV-0004', 0, NULL),
('PN-0005', DATEADD(DAY, -5, GETDATE()), 'NV-0001', 0, N'Hàng nhập khẩu'),
('PN-0006', DATEADD(DAY, -4, GETDATE()), 'NV-0002', 0, NULL),
('PN-0007', DATEADD(DAY, -3, GETDATE()), 'NV-0003', 0, N'Nhập thực phẩm chức năng'),
('PN-0008', DATEADD(DAY, -2, GETDATE()), 'NV-0004', 0, N'Đơn hàng gấp'),
('PN-0009', DATEADD(DAY, -1, GETDATE()), 'NV-0001', 0, NULL),
('PN-0010', GETDATE(), 'NV-0002', 0, N'Nhập kho cuối tháng');
GO

DECLARE @MaPN NVARCHAR(7);
DECLARE @SoDongCanTao INT;
-- Biến tạm để lưu mã nhà cung cấp random cho mỗi phiếu hoặc mỗi dòng
DECLARE @RandomMaNCC NVARCHAR(8); 

-- Khai báo con trỏ duyệt qua các mã phiếu nhập vừa tạo
DECLARE pn_cursor CURSOR FOR 
SELECT maPhieuNhap FROM PhieuNhap;

OPEN pn_cursor;
FETCH NEXT FROM pn_cursor INTO @MaPN;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- 1. Random số dòng chi tiết từ 10 đến 15 cho phiếu này
    SET @SoDongCanTao = FLOOR(RAND() * 6) + 10; 

    -- 2. Insert dữ liệu vào ChiTietPhieuNhap
    -- Đã thêm cột soLuongYeuCau vào danh sách INSERT và SELECT
    INSERT INTO ChiTietPhieuNhap (
        maPhieuNhap, 
        maLoSanPham, 
        maNCC, 
        soLuong, 
        soLuongYeuCau, -- <--- Cột mới thêm
        donGia, 
        thanhTien, 
        ghiChu
    )
    SELECT TOP (@SoDongCanTao)
        @MaPN,                              -- Mã phiếu nhập
        LSP.maLoSanPham,                    -- Mã lô sản phẩm
        
        -- Lấy ngẫu nhiên 1 mã NCC từ bảng NhaCungCap cho dòng này
        (SELECT TOP 1 maNCC FROM NhaCungCap ORDER BY NEWID()), 
        
        ABS(CHECKSUM(NEWID()) % 100) + 10,  -- soLuong (Thực nhập): 10 - 109
        ABS(CHECKSUM(NEWID()) % 100) + 10,  -- soLuongYeuCau (Yêu cầu): 10 - 109
        
        ABS(CHECKSUM(NEWID()) % 500000) + 10000, -- Đơn giá: 10k - 510k
        0,                                  -- Thành tiền: Tạm để 0, sẽ update sau
        NULL                                -- Ghi chú
    FROM LoSanPham LSP
    ORDER BY NEWID(); -- Random lô sản phẩm để mỗi phiếu có các mặt hàng khác nhau

    -- 3. Cập nhật lại cột ThanhTien = SoLuong * DonGia
    -- Lưu ý: Thành tiền thường tính trên số lượng thực nhập (soLuong) chứ không phải yêu cầu
    UPDATE ChiTietPhieuNhap
    SET thanhTien = soLuong * donGia
    WHERE maPhieuNhap = @MaPN;

    -- 4. Cập nhật lại TongTien cho bảng PhieuNhap (Sum thành tiền từ chi tiết)
    UPDATE PhieuNhap
    SET tongTien = (SELECT SUM(thanhTien) FROM ChiTietPhieuNhap WHERE maPhieuNhap = @MaPN)
    WHERE maPhieuNhap = @MaPN;

    FETCH NEXT FROM pn_cursor INTO @MaPN;
END;

CLOSE pn_cursor;
DEALLOCATE pn_cursor;

-- 3. CẬP NHẬT TỔNG TIỀN CHO BẢNG PHIEU NHAP
-- Tính tổng thành tiền từ bảng chi tiết và update ngược lại bảng cha
UPDATE PhieuNhap
SET tongTien = (
    SELECT COALESCE(SUM(thanhTien), 0)
    FROM ChiTietPhieuNhap CTPN
    WHERE CTPN.maPhieuNhap = PhieuNhap.maPhieuNhap
);
GO

PRINT N'>>> TẤT CẢ DỮ LIỆU ĐÃ ĐƯỢC TẠO THÀNH CÔNG <<<';
GO

--select * 
--from SanPham sp join LoSanPham lsp
--on sp.maSP = lsp.maSP

--select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, soLuong = sum(lsp.soLuong)
--from SanPham sp join LoSanPham lsp
--on sp.maSP = lsp.maSP
--where soLuong <= sp.tonToiThieu
--group by sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa


--select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi, soLuong = sum(lsp.soLuong)
--from SanPham sp join LoSanPham lsp
--on sp.maSP = lsp.maSP join DonViTinh dvt
--on sp.maSP = dvt.maSP
--where soLuong <= sp.tonToiDa
--group by sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi


--select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi
--from SanPham sp join DonViTinh dvt
--on sp.maSP = dvt.maSP
--where dvt.donViTinhCoBan = '1'


--select *
--from SanPham sp join DonViTinh dvt
--on sp.maSP = dvt.maSP
--where dvt.donViTinhCoBan = '1'



--select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi, soLuong = sum(lsp.soLuong)
--from SanPham sp join LoSanPham lsp
--on sp.maSP = lsp.maSP join DonViTinh dvt
--on sp.maSP = dvt.maSP
--group by sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi
--order by sp.maSP



--select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi, soLuong = sum(lsp.soLuong)
--from SanPham sp join LoSanPham lsp
--on sp.maSP = lsp.maSP join DonViTinh dvt
--on sp.maSP = dvt.maSP
--where soLuong <= sp.tonToiDa and dvt.donViTinhCoBan = '1'
--group by sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi
--order by sp.maSP

--select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi, soLuong = sum(lsp.soLuong)
--from SanPham sp join LoSanPham lsp
--on sp.maSP = lsp.maSP join DonViTinh dvt
--on sp.maSP = dvt.maSP
--where dvt.donViTinhCoBan = '1'
--group by sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, dvt.tenDonVi
--having SUM(lsp.soLuong) <= sp.tonToiDa
--order by sp.maSP

--select *
--from LoSanPham

--select sp.maSP, sp.ten, soLuong = sum(soLuong)
--from LoSanPham lsp join SanPham sp
--on lsp.maSP = sp.maSP
--group by sp.maSP, sp.ten

--use [DuocAnKhang]

--select sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa, soLuong = sum(soLuong)
--from LoSanPham lsp join SanPham sp
--on lsp.maSP = sp.maSP
--group by sp.maSP, sp.ten, sp.moTa, sp.thanhPhan, sp.loaiSanPham, sp.tonToiThieu, sp.tonToiDa, sp.daXoa
--having sum(soLuong) = 0