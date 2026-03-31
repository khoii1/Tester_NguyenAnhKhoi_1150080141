# Lab 11 - Selenium Maven Framework

[![Test Status](https://github.com/khoii1/Tester_NguyenAnhKhoi_1150080141/actions/workflows/selenium-full.yml/badge.svg)](https://github.com/khoii1/Tester_NguyenAnhKhoi_1150080141/actions)
[![Allure Report](https://img.shields.io/badge/Allure-Report-orange)](https://khoii1.github.io/Tester_NguyenAnhKhoi_1150080141/)

Project kiểm thử tự động dùng Selenium + TestNG + Maven.

## Yêu cầu

- Java 17
- Maven 3.8+
- Google Chrome

## Cách chạy

```bash
mvn test
```

Hoặc chỉ định browser và môi trường:

```bash
mvn test -Dbrowser=chrome -Denv=dev
```

## Kết quả

Sau khi chạy xong, báo cáo nằm ở `target/surefire-reports/`.  
Ảnh chụp lỗi (nếu có) nằm ở `target/screenshots/`.
