# Y&U Fashion Store

## Deskripsi Proyek

Y&U Fashion Store adalah aplikasi berbasis Java Console yang dibuat untuk memenuhi tugas UAS Pemrograman Berorientasi Objek (PBO). Aplikasi ini digunakan untuk mengelola data produk fashion menggunakan konsep Object-Oriented Programming (OOP), CRUD, JDBC, DAO Pattern, Service Layer, dan MySQL Database.

## Fitur Utama

### Admin

* Login Admin menggunakan username dan password dari database MySQL.
* Menampilkan seluruh data barang.
* Menambahkan data barang.
* Mengubah data barang.
* Menghapus data barang.
* Melakukan transaksi penjualan.
* Validasi data menggunakan Service Layer.

### User

* Melihat daftar barang.
* Mencari barang berdasarkan ID.
* Simulasi keranjang belanja.
* Tidak dapat melakukan operasi CRUD.

## Teknologi yang Digunakan

* Java
* JDBC (Java Database Connectivity)
* MySQL
* DAO (Data Access Object)
* Service Layer
* Object-Oriented Programming (OOP)

## Konsep OOP yang Diterapkan

### Encapsulation

Data pada class entity disimpan dalam atribut private dan diakses menggunakan getter dan setter.

### Inheritance

Class turunan mewarisi atribut dan method dari class induk untuk mengurangi duplikasi kode.

### Polymorphism

Implementasi interface DAO menggunakan class DAO Implementation.

### Abstraction

Penggunaan interface DAO sebagai abstraksi akses database.

## Struktur Project

src/

├── config/

│ └── koneksi.java

├── entity/

│ ├── Barang.java

│ └── admin.java

├── dao/

│ ├── barangDAO.java

│ ├── barangDAOImpl.java

│ ├── AdminDAO.java

│ └── AdminDAOImpl.java

├── service/

│ ├── BarangService.java

│ └── AdminService.java

├── MenuHandler.java

└── main.java

## Database

Nama Database:

db_yu_fashionstore

### Tabel Barang

| Field       | Type    |
| ----------- | ------- |
| id          | INT     |
| nama_barang | VARCHAR |
| kategori    | ENUM    |
| harga       | DOUBLE  |
| stok        | INT     |

### Kategori Barang

* Atasan
* Bawahan
* Outwear
* Alas Kaki
* Aksesoris

### Tabel Admin

| Field    | Type    |
| -------- | ------- |
| id_admin | INT     |
| username | VARCHAR |
| password | VARCHAR |

## Validasi Sistem

### Login Admin

* Username tidak boleh kosong.
* Password tidak boleh kosong.
* Maksimal 3 kali percobaan login.
* Jika gagal 3 kali, program akan berhenti.

### Data Barang

* Nama barang tidak boleh kosong.
* Kategori harus sesuai daftar kategori yang tersedia.
* Harga harus lebih dari 0.
* Stok tidak boleh bernilai negatif.
* ID barang harus valid dan tersedia dalam database.

## Cara Menjalankan Program

1. Jalankan Apache dan MySQL melalui XAMPP.
2. Import database `db_yu_fashionstore`.
3. Tambahkan library MySQL Connector/J ke project.
4. Pastikan konfigurasi koneksi database sudah benar pada file `koneksi.java`.
5. Compile dan jalankan file `main.java`.

## Author

Nama : Aditya Putra Hertanto

Mata Kuliah : Pemrograman Berorientasi Objek (PBO)

Universitas : Universitas Sultan Ageng Tirtayasa

Tahun : 2026
