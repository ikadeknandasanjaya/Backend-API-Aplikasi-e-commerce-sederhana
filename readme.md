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
<img width="632" alt="image" src="https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/3b4f366d-ddfe-4003-8656-78b4df1d3fe6">

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
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/df07c444-48d4-4bbe-b901-d27703d1b1df)

### 2. POST /products
Menambahkan data products.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/98a2c2b9-8857-4ddf-a15a-05a04408e6d2)

### 3. POST /orders
Menambahkan data orders.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/7040bf69-8915-4a7b-97e5-48edcd582847)

### 4. POST /address
Menambahkan data alamat.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/ffaf6727-211b-416c-997b-083484758e54)

### 5. POST /order/details
Menambahkan data order details.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/8dd77c83-f111-4f69-95ba-d7d2147125f6)

## Spesifikasi API PUT

### 1. PUT /users/{id}
Memperbarui data users.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/7c5e4342-6443-40af-95bd-1680275ad5f3)

### 2. PUT /products/{id}
Memperbarui data products.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/1c7f29c8-19cd-4521-811a-52c6d61d1e84)

### 3. PUT /orders/{id}
Memperbarui data orders.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/535cda2d-da12-4565-8893-3308489825dc)

### 4. PUT /address/{id}
Memperbarui data address.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/3361c35b-4c22-4fae-9d58-b07f9f86741d)

### 5. PUT /order/details/{id}
Memperbarui data order details.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/2a105f49-da67-43fb-8b94-1ffdf2cacf28)

## Spesifikasi API DELETE

### 1. DELETE /users/{id}
Menghapus data users.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/df574f18-9cb7-432e-8bdf-7de900833661)

### 2. DELETE /products/{id}
Menghapus data products.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/9a7838f8-4322-4b6c-bcd7-c386b96a3d54)

### 3. DELETE /orders/{id}
Menghapus data orders.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/5c0616c5-236e-4413-b543-0109476e2680)

### 4. DELETE /address/{id}
Menghapus data address.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/e3604ab5-143f-4d4e-9b34-bff0832d46a6)

### 5. DELETE /order/details/{id}
Menghapus data order details.
![image](https://github.com/ikadeknandasanjaya/Backend-API-Aplikasi-e-commerce-sederhana/assets/112923574/ccff80bb-2737-4249-8576-53b7cbcc0846)

