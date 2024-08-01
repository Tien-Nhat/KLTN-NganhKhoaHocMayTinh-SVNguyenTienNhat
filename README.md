# Website Thương Mại Điện Tử với Hệ Thống Gợi Ý Sản Phẩm

## Mục Lục
- [Tính Năng](#tính-năng)
- [Yêu Cầu](#yêu-cầu)
- [Cài Đặt](#cài-đặt)
  - [Cài Đặt Back-end Hệ Thống Gợi Ý (Python)](#cài-đặt-hệ-thống-gợi-ý-python)
  - [Cài Đặt Back-end (Spring Boot)](#cài-đặt-back-end-spring-boot)
  - [Cài Đặt Front-end (Vue.js)](#cài-đặt-front-end-vuejs)
- [Chạy Ứng Dụng](#chạy-ứng-dụng)
- [Sử Dụng](#sử-dụng)

## Tính Năng
- Xác thực và phân quyền người dùng
- Chức năng giỏ hàng
- Đề xuất sản phẩm dựa trên hành vi người dùng

## Yêu Cầu
- Java 11 hoặc mới hơn
- Maven
- Node.js và npm
- Python 3.7 hoặc mới hơn
- pip (trình quản lý gói Python)

## Cài Đặt
### Cài Đặt back-end Hệ Thống Gợi Ý (Python)
1. Di chuyển đến thư mục hệ thống gợi ý:
    ```bash
    cd ../recommend-system-python
    ```

2. Tạo môi trường ảo và kích hoạt nó:
    ```bash
    python -m venv venv
    source venv/bin/activate  # Trên Windows dùng `venv\Scripts\activate`
    ```

3. Cài đặt các phụ thuộc:
    ```bash
    pip install -r requirements.txt
    ```

4. Chạy API hệ thống gợi ý:
    ```bash
    python recommend-system.py
    ```

### Cài Đặt Back-end (Spring Boot)
1. Clone kho mã nguồn:
    ```bash
    git clone https://github.com/Tien-Nhat/KLTN-SVNguyenTienNhat.git
    cd KLTN-SVNguyenTienNhat
    ```

2. Di chuyển đến thư mục back-end:
    ```bash
    cd backend
    ```

3. Cài đặt các phụ thuộc và build dự án:
    ```bash
    mvn clean install
    ```

4. Cấu hình cài đặt cơ sở dữ liệu trong `src/main/resources/application.properties`.

5. Chạy ứng dụng Spring Boot:
    ```bash
    mvn spring-boot:run
    ```

### Cài Đặt Front-end (Vue.js)
1. Di chuyển đến thư mục front-end:
    ```bash
    cd ../frontend
    ```

2. Cài đặt các phụ thuộc:
    ```bash
    npm install
    ```

3. Chạy ứng dụng Vue.js:
    ```bash
    npm run serve
    ```
### Demo
![image](https://github.com/user-attachments/assets/da46af15-164b-4cfe-ad54-40f98cf306de)
![image](https://github.com/user-attachments/assets/0ecf6cfe-b5d0-4af3-8490-38444d63fa2d)


## Chạy Ứng Dụng
1. Đảm bảo rằng back-end, front-end và hệ thống gợi ý đều đang chạy như đã hướng dẫn ở phần cài đặt.

2. Truy cập ứng dụng front-end bằng cách điều hướng đến `http://localhost:3000` trong trình duyệt web của bạn.

3. Back-end sẽ chạy trên `http://localhost:8081` và API hệ thống gợi ý trên `http://localhost:5000`.

## Sử Dụng
- Đăng ký và đăng nhập vào ứng dụng.
- Duyệt sản phẩm, thêm vào giỏ hàng và thực hiện mua hàng.
- Hệ thống gợi ý sẽ đề xuất sản phẩm dựa trên hành vi duyệt và mua hàng của bạn.
