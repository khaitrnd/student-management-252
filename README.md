Danh sách nhóm:
    1. Trần Tiến Khải - 2311566

Hướng dẫn chạy dự án:

Trả lời lý thuyết:
    1. Cố tình Insert một sinh viên có id trùng với một người đã có sẵn. Tại sao Database lại chặn thao tác này (báo lỗi UNIQUE constraint failed)?
        Do cột 'id' đã được khai báo là INTERGER PRIMARY KEY, do đó nó kèm ràng buộc UNIQUE và NOT NULL.

        Vì để tránh trường hợp trùng id dẫn đến database không phân biệt được nếu muốn xóa/chỉnh sửa hồ sơ của sinh viên. DB đã chặn việc làm này từ bước INSERT để 
        bảo vệ tính toàn vẹn của dữ liệu.
    
    2. Thử Insert một sinh viên nhưng bỏ trống cột name (để NULL). Database có báo lỗi không? Sự thiếu chặt chẽ này ảnh hưởng gì khi code Java đọc dữ liệu lên?
        Database không báo lỗi do cột 'name TEXT' chưa được thêm ràng buộc NOT NULL. Do đó SQLite cho phép lưu một sinh viên với phần tên bị rỗng.

        Sự thiếu chặt chễ này sẽ khiến DB gán giá trị null vào tên của đối tượng student, khiến cho các thao tác xử lí chuỗi tên này sẽ lập tức gây lỗi và làm sập      trang web.
    
    3. Tại sao mỗi lần tắt ứng dụng và chạy lại, dữ liệu cũ trong Database lại bị mất hết?
        Nguyên nhân nằm ở cấu hình trong file application.properties: spring.jpa.hibernate.ddl-auto=create.
        Chế độ create sẽ khiến mỗi lần khởi động lại Spring boot sẽ xóa sạch cấu trúc CSDL cũ và tạo lại bảng mới dựa trên các class @Entity của java.
        