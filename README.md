---

# Website Thương Mại Điện Tử với Hệ Thống Gợi Ý Sản Phẩm

Đây là kho chứa mã nguồn cho một website thương mại điện tử, bao gồm back-end được xây dựng bằng Spring Boot, front-end được xây dựng bằng Vue.js và hệ thống gợi ý được xây dựng bằng Python. Hệ thống gợi ý cung cấp một API được sử dụng bởi back-end để đề xuất sản phẩm cho người dùng.

## Mục Lục
- [Tính Năng](#tính-năng)
- [Yêu Cầu](#yêu-cầu)
- [Cài Đặt](#cài-đặt)
  - [Cài Đặt Back-end (Spring Boot)](#cài-đặt-back-end-spring-boot)
  - [Cài Đặt Front-end (Vue.js)](#cài-đặt-front-end-vuejs)
  - [Cài Đặt Hệ Thống Gợi Ý (Python)](#cài-đặt-hệ-thống-gợi-ý-python)
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

### Cài Đặt Hệ Thống Gợi Ý (Python)
1. Di chuyển đến thư mục hệ thống gợi ý:
    ```bash
    cd ../recommendation-system
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

## Chạy Ứng Dụng
1. Đảm bảo rằng back-end, front-end và hệ thống gợi ý đều đang chạy như đã hướng dẫn ở phần cài đặt.

2. Truy cập ứng dụng front-end bằng cách điều hướng đến `http://localhost:8080` trong trình duyệt web của bạn.

3. Back-end sẽ chạy trên `http://localhost:8081` và API hệ thống gợi ý trên `http://localhost:5000`.

## Sử Dụng
- Đăng ký và đăng nhập vào ứng dụng.
- Duyệt sản phẩm, thêm vào giỏ hàng và thực hiện mua hàng.
- Hệ thống gợi ý sẽ đề xuất sản phẩm dựa trên hành vi duyệt và mua hàng của bạn.
