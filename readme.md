# API Aplikasi Ecommerce Sederhana

API yang dibuat dengan bahasa pemrograman JAVA untuk memanipulasi data dari database dengan menggunakan method GET, POST, PUT dan DELETE.

## Instalasi

1. Clone repositori ini.
2. Buka proyek di IDE IntelIj IDEA.
3. Atur konfigurasi database yang sudah ada pada folder database di project ini.
4. Kompilasi dan jalankan aplikasi.
5. Gunakan POSTMAN untuk testing API.

## Spesifikasi API GET

### 1. GET /users
Untuk mendapatkan semua daftar users yang ada.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/633dca3e-a201-43e2-8eb8-20f5cfa37dfc)

### 2. GET /users/{id}
Untuk mendapatkan informasi user dan alamatnya.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/b1061f7c-819d-44d2-8e0a-8feacd908744)
jika tidak ada maka akan muncul pesan error.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/d376758d-9b45-4043-9b63-739dafacc77b)

### 3. GET /users/{id}/products
Mendapatkan daftar produk milik user.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/abdf51b6-5814-44d0-a9d9-3aaa943baedf)

### 4. GET /users/{id}/orders
Mendapatkan daftar order milik user.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/f9d84e1e-b8fb-4afd-a535-7f7620b57936)

### 5. GET /users/{id}/reviews
Mendapatkan daftar review yg dibuat user.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/6eb324a3-9355-481e-96af-b96f8bf17a2f)

### 6. GET /orders/{id}
Mendapatkan informasi order, buyer, order detail, review, produk: title, price.

### 7. GET /products
Mendapatkan daftar semua produk.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/2ba1fba7-20a3-4b25-ac9d-23502e2e4bec)

### 8. GET /products/{id}
Mendapatkan informasi produk dan seller\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/9bd38c85-af99-4086-9706-76c0ce32dfe4)

### 9. Filter GET /products?field=stock&cond=largerEqual&val=10
Mendapatkan stock lebih atau sama dengan dengan 10\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/4b630d33-f6f8-49f0-8d1e-b56aa52cc6b1)

### 10. GET /users?type=buyer
Mendapatkan buyer dengan type buyer.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/b12917ac-3aef-41ce-980f-ecfced29f05a)

### 11. GET /users?type=seller
Mendapatkan seller dengan type seller.\
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/b67a29b2-89d5-4e15-8210-bad7071048c0)

## Spesifikasi API POST

### 1. POST /users
Menambahkan data users.
<img width="635" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/dda5121a-646a-42dc-b573-dbecacaab515">

### 2. POST /products
Menambahkan data products.
<img width="630" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/c773f68d-9720-4cf7-901a-f4a6811fe8c6">

### 3. POST /orders
Menambahkan data orders.
<img width="641" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/09d13742-c62c-4939-bf0a-dc7ef01743da">

### 4. POST /address
Menambahkan data alamat.
<img width="645" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/083bc39b-9018-4d8f-8b77-94d466ce097a">

### 5. POST /order/details
Menambahkan data order details.
<img width="634" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/628eeb6d-7629-4427-ad03-e98d948eb97e">

## Spesifikasi API PUT

### 1. PUT /users/{id}
Memperbarui data users.
<img width="637" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/af8cd870-15eb-472d-8747-4b8151297c5b">

### 2. PUT /products/{id}
Memperbarui data products.
<img width="622" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/aac3e923-8f79-4872-b317-fd633533a242">

### 3. PUT /orders/{id}
Memperbarui data orders.
<img width="622" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/bf5be3ca-5c88-4d00-94a4-63ec139440af">

### 4. PUT /address/{id}
Memperbarui data address.
<img width="633" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/51ab478c-b0ed-4fa5-b9c2-a8d17d61a7d7">

### 5. PUT /order/details/{id}
Memperbarui data order details.
<img width="627" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/66f40e3a-c9e4-4dd8-9ace-6a41eb998790">

## Spesifikasi API DELETE

### 1. DELETE /users/{id}
Menghapus data users.
<img width="633" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/35ddae64-785b-4471-8553-940079ee3720">

### 2. DELETE /products/{id}
Menghapus data products.
<img width="627" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/4abad23b-6455-43c4-ac42-d469c1051100">

### 3. DELETE /orders/{id}
Menghapus data orders.
<img width="625" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/2fdc4b42-7ab8-4607-beb9-e9be1cbd86f3">

### 4. DELETE /address/{id}
Menghapus data address.
<img width="635" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/a5e6a587-5672-4bf9-b1b7-b986e5b7b4c1">

### 5. DELETE /order/details/{id}
Menghapus data order details.
<img width="628" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/baf098cf-cccb-485a-a39d-b2fb499ef95c">

