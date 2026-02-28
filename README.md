# README

## Danh sách nhóm:

1.  Trần Tiến Khải - 2311566

------------------------------------------------------------------------

## Hướng dẫn chạy dự án:

### 1. Truy cập trực tiếp bản được deploy trên cloud:

Dự án đã được triển khai trên nền tảng Render kết hợp với Neon
(PostgreSQL).\
Có thể truy cập tại đường dẫn:
https://student-management-252.onrender.com/

Lưu ý: Vì ứng dụng được lưu trữ trên gói Free của Render, nên nếu không
có truy cập trong một thời gian, máy chủ sẽ tự động ngủ (Spin down). Do
đó, lần truy cập đầu tiên có thể mất khoảng 30 giây đến 1 phút để máy
chủ khởi động lại (Cold start). Các thao tác sau đó sẽ diễn ra nhanh
chóng.

------------------------------------------------------------------------

### 2. Chạy trên máy cá nhân (Localhost):

#### Prerequisites:

-   JDK 17 hoặc 21.
-   Trình duyệt web (Chrome, Edge, Safari,...)

#### Các bước cài đặt và khởi động:

##### 1. Tải mã nguồn:

``` bash
git clone https://github.com/khaitrnd/student-management-252.git
cd student-management
```

##### 2. Cấu hình Environment:

Cần tạo 1 file là '.env' tại thư mục gốc của dự án, cung cấp các thông
tin kết nối CSDL(Local hoặc Cloud) theo định dạng sau:

``` env
# PostgreSQL Database Configuration
POSTGRES_HOST=địa_chỉ_host_của_bạn
POSTGRES_PORT=5432
POSTGRES_DB=tên_database
POSTGRES_USER=tên_đăng_nhập
POSTGRES_PASSWORD=mật_khẩu_database

# Spring Datasource
SPRING_DATASOURCE_URL=jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}?sslmode=require
SPRING_DATASOURCE_USERNAME=${POSTGRES_USER}
SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD}
```

##### 3. Khởi động ứng dụng:

**Windows:**

``` bash
.\mvnw spring-boot:run
```

**MacOS/Linux:**

``` bash
./mvnw spring-boot:run
```

Khi Terminal hiện thông báo thành công, mở trình duyệt và truy cập
http://localhost:8080/ để sử dụng.

##### 4. Sử dụng ứng dụng:

Ứng dụng cho phép người dùng thao tác trên một cơ sở dữ liệu cỡ nhỏ để
quán lí thông tin của các sinh viên.\
Các thông tin bao gôm: Mã số sinh viên, họ và tên, email và độ tuổi.\
Các thao tác có thể sử dụng bao gồm: Xem chi tiết, chỉnh sửa thông tin
và xóa thông tin sinh viên.

------------------------------------------------------------------------

## Trả lời lý thuyết:

### 1. Cố tình Insert một sinh viên có id trùng với một người đã có sẵn. Tại sao Database lại chặn thao tác này (báo lỗi UNIQUE constraint failed)?

Do cột 'id' đã được khai báo là INTERGER PRIMARY KEY, do đó nó kèm ràng
buộc UNIQUE và NOT NULL.

Vì để tránh trường hợp trùng id dẫn đến database không phân biệt được
nếu muốn xóa/chỉnh sửa hồ sơ của sinh viên. DB đã chặn việc làm này từ
bước INSERT để bảo vệ tính toàn vẹn của dữ liệu.

------------------------------------------------------------------------

### 2. Thử Insert một sinh viên nhưng bỏ trống cột name (để NULL). Database có báo lỗi không? Sự thiếu chặt chẽ này ảnh hưởng gì khi code Java đọc dữ liệu lên?

Database không báo lỗi do cột 'name TEXT' chưa được thêm ràng buộc NOT
NULL. Do đó SQLite cho phép lưu một sinh viên với phần tên bị rỗng.

Sự thiếu chặt chễ này sẽ khiến DB gán giá trị null vào tên của đối tượng
student, khiến cho các thao tác xử lí chuỗi tên này sẽ lập tức gây lỗi
và làm sập trang web.

------------------------------------------------------------------------

### 3. Tại sao mỗi lần tắt ứng dụng và chạy lại, dữ liệu cũ trong Database lại bị mất hết?

Nguyên nhân nằm ở cấu hình trong file application.properties:
spring.jpa.hibernate.ddl-auto=create.

Chế độ create sẽ khiến mỗi lần khởi động lại Spring boot sẽ xóa sạch cấu
trúc CSDL cũ và tạo lại bảng mới dựa trên các class @Entity của java.
