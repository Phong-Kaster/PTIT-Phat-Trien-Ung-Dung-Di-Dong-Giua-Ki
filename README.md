<h1 align="center">Phát Triển Ứng Dụng Cho Các Thiết Bị Di Động<br/>
    Đồ Án Giữa Kỳ - Quản Lý Học Sinh/Sinh Viên
</h1>

<p align="center">
    <img src="./avatar/rus.jpg" width="1280" />
</p>


# [**Table Of Content**](#table-of-content)
- [**Table Of Content**](#table-of-content)
- [**Introduction**](#introduction)
- [**Usage**](#usage)
- [**Base-lined Knowledge**](#base-lined-knowledge)
- [**Reading Comprehension**](#reading-comprehension)
- [**Video**](#video)
- [**Features**](#features)
  - [**1. Login**](#1-login)
  - [**2. Home**](#2-home)
  - [**3. Classroom**](#3-classroom)
  - [**4. Subject**](#4-subject)
  - [**5. Statistics**](#5-statistics)
  - [**6. Event**](#6-event)
  - [**7. Score**](#7-score)
  - [**8. Account**](#8-account)
  - [**9. Settings**](#9-settings)
- [**Post Script**](#post-script)
- [**Our Team**](#our-team)
- [**Made with 💘 and Java <img src="https://www.vectorlogo.zone/logos/java/java-horizontal.svg" width="60">**](#made-with--and-java-)

# [**Introduction**](#introduction)

Sau đây là toàn bộ nội dung đề tài của nhóm mình tới đề tài quản lý sinh viên

<p align="center">
    <img src="./avatar/deTai(1).png" width="640" />
</p>
<p align="center">
    <img src="./avatar/deTai(2).png" width="640" />
</p>

<p align="center">
    <img src="./avatar/deTai(3).png" width="640" />
</p>
<p align="center">
    <img src="./avatar/deTai(4).png" width="640" />
</p>
<p align="center">
    <img src="./avatar/deTai(5).png" width="640" />
</p>

# [**Usage**](#usage)

Để chạy được dự án này, yêu cầu Android Studio Bumblebee phiên bản 2021.1.1 Patch 2 hoặc mới hơn. Dự án này có thể tải bằng 2 cách sau đây:

- Tải về bằng `Code->Download Zip`

- Tải về bằng câu lệnh `git clone`

Sau khi tải về, để chạy được ứng dụng này cần lưu ý ở lần chạy đầu tiên. Hãy chọn nút màu xanh như hình minh họa để khởi tạo dữ liệu mặc định


<p align="center">
    <img src="./avatar/screenshot41.png" width="640" />
</p>
<h3 align="center">

***Nút đăng ký demo giúp tạo dữ liệu ban đầu***
</h3>

# [**Base-lined Knowledge**](#base-lined-knowledge)

Những kiến thức nền tảng được sử dụng trong đồ án này bao gồm

1. SQLite và các giao tiếp thông qua SQLiteOpenHelper

2. ListView và các tùy biến chuyên sâu

3. Tùy biến các layout với @style 

4. MenuInflater - xây dựng menu phụ trợ

5. Alert - hiển thị cảnh báo

6. Bitmap - chụp ảnh màn hình

7. Thư viện iText7 tạo tệp tin PDF 

8. Thư viện Picasso để hiển thị hình ảnh

9. Hỗ trợ tạo biểu đồ 

10. Tùy biến button với xml nằm trong `res/drawable`

11. Sử dụng Tab Host để xây dựng menu đa màn hình

# [**Reading Comprehension**](#reading-comprehension)

Đồ án này có 6 chức năng chính nhưng cách xây dựng giữa các chức năng gần như tương tự nhau. Để dễ đọc hiểu đồ án này, các bạn hãy chú đọc các Activity sau đây:

- **Login Activity**: chức năng đăng nhập của đồ án là chức năng quan trọng nên các bạn hãy dành thời gian để đọc kĩ hơn chút xíu 😋

- **Thư mục Classroom**: trong này chứa tất cả các Activity liên quan tới xử lý lớp học. Các chức năng còn lại như Môn học, Sự kiện, Điểm đều có cấu trúc tương tự như phần của mình. Tuy nhiên, trong quá trình làm
thì chỉ có mình giữ thói quen ghi lại chú thích cho các đoạn chương trình. Các bạn đọc phần của mình sẽ dễ theo dõi hơn các Activity kia hơn xíu 😝😝

- **Thư mục Statistics**: các Activity này để tạo báo cáo bằng dạng file PDF gồm biểu đồ hình tròn ☮ và biểu đồ cột 📊

Nếu chưa biết bắt đầu học từ đâu thì hãy tham khảo 2 danh sách video này nha 

 [**Danh sách video của mình**](https://www.youtube.com/playlist?list=PLkPVg51dQOybWORTScWY1ddflgnkiFhEW)

 [**Danh sách video tham khảo**](https://www.youtube.com/playlist?list=PLqBZuthgOGwj90FaULSh7OPCR_lGk6yzl)

# [**Video**](#video)

<div align="center">
    
[![Watch the video](https://i.imgur.com/vKb2F1B.png)](https://www.youtube.com/watch?v=ZtrGcIkKv4U&ab_channel=PhongKaster)

</div>

<h3 align="center">

***Video giới thiệu các chức năng***
</h3>

# [**Features**](#features)

Trong chương trình này, có tổng cộng 6 chức năng chính. Bao gồm: 

**Danh sách lớp**: quản lý học sinh/sinh viên trong một lớp học. Mỗi giáo viên là chủ nhiệm của một lớp học duy nhất

**Môn học**: quản lý thông tin của môn học. Ví dụ: Toán, Văn, Anh.....

**Sự kiện**: chức năng dạng như ghi chú vắn tắt.

**Điểm**: quản lý điểm của sinh viên theo từng môn học

**Thống kê**: chức năng tạo báo cáo dạng biểu đồ tròn hoặc cột

**Tài khoản**: chức năng giúp thay đổi tên tài khoản, ảnh đại diện hoặc mật khẩu..

## [**1. Login**](#1login)

<p align="center">
    <img src="./avatar/screenshot2.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot3.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot4.png" height="400" />
</p>
<h3 align="center">

***Đăng nhập***
</h3>

## [**2. Home**](#2home)

<p align="center">
    <img src="./avatar/screenshot5.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot6.png" height="400" />
</p>
<h3 align="center">

***Trang chủ***
</h3>

## [**3. Classroom**](#3classroom)

<p align="center">
    <img src="./avatar/screenshot7.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot8.png" height="400" />
    &nbsp;&nbsp;
</p>

<p align="center">
    <img src="./avatar/screenshot9.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot10.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot11.png" height="400" />
    &nbsp;&nbsp;
</p>

<h3 align="center">

***Quản lý danh sách sinh viên với mỗi giáo viên làm chủ nhiệm của 1 lớp***
</h3>

<p align="center">
    <img src="./avatar/screenshot12.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot13.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot14.png" height="400" />
    &nbsp;&nbsp;
</p>
<h3 align="center">

***Danh sách sinh viên hỗ trợ xuất ra dạng JPEG và PDF***
</h3>

## [**4. Subject**](#4subject)

<p align="center">
    <img src="./avatar/screenshot15.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot16.png" height="400" />
    &nbsp;&nbsp;
</p>

<p align="center">
    <img src="./avatar/screenshot17.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot18.png" height="400" />
    &nbsp;&nbsp;
</p>
<h3 align="center">

***Môn học***
</h3>

## [**5. Statistics**](#5statistics)

<p align="center">
    <img src="./avatar/screenshot23.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot24.png" height="400" />
    &nbsp;&nbsp;
</p>
<p align="center">
    <img src="./avatar/screenshot39.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot40.png" height="400" />
    &nbsp;&nbsp;
</p>
<h3 align="center">

***Thống kê***
</h3>

## [**6. Event**](#6event)

<p align="center">
    <img src="./avatar/screenshot33.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot34.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot35.png" height="400" />
    &nbsp;&nbsp;
</p>
<h3 align="center">

***Sự kiện***
</h3>

## [**7. Score**](#7score)

<p align="center">
    <img src="./avatar/screenshot36.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot37.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot38.png" height="400" />
    &nbsp;&nbsp;
</p>
<h3 align="center">

***Điểm***
</h3>

## [**8. Account**](#8account)

<p align="center">
    <img src="./avatar/screenshot19.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot20.png" height="400" />
    &nbsp;&nbsp;
</p>

<p align="center">
    <img src="./avatar/screenshot21.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot22.png" height="400" />
    &nbsp;&nbsp;
</p>
<h3 align="center">

***Tài khoản***

## [**9. Settings**](#9settings)

</h3>

<p align="center">
    <img src="./avatar/screenshot25.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot26.png" height="400" />
    &nbsp;&nbsp;
</p>
<h3 align="center">

***Thiết lập cài đặt với đa màn hình***
</h3>

<p align="center">
    <img src="./avatar/screenshot27.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot28.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot29.png" height="400" />
    &nbsp;&nbsp;
</p>
<p align="center">
    <img src="./avatar/screenshot30.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot31.png" height="400" />
    &nbsp;&nbsp;
    <img src="./avatar/screenshot32.png" height="400" />
    &nbsp;&nbsp;
</p>

<h3 align="center">

***Hỗ trợ chế độ ban đêm***

# [**Post Script**](#post-script)

Nhóm 5 tụi mình thi giữa kì ngày hôm qua, 18-04-2022. Với đầy đủ các chức năng được thầy yêu cầu, nên nhóm mình đều được điểm 10 tuyệt đối, trừ bạn Chung được 9 do có đôi chút sai sót🤣🤣

Chúc các bạn đang đọc những dòng này dù bây giờ hay mãi về sau, sẽ luôn gặp nhiều may mắn trong học tập lẫn công việc😎😎

# [**Our Team**](#our-team)

<table>
        <tr>
            <td align="center">
                <a href="https://github.com/Phong-Kaster">
                    <img src="./avatar/Blue.jpg" width="100px;" alt=""/>
                    <br />
                    <sub><b>Nguyễn Thành Phong</b></sub>
                </a>
            </td>
            <td align="center">
                <a href="https://github.com/ngdanghau">
                    <img src="./avatar/Hau.jpg" width="100px;" alt=""/>
                    <br />
                    <sub><b>Nguyễn Đăng Hậu</b></sub>
                </a>
            </td>
            <td align="center">
                <a href="https://github.com/chungnv0501">
                    <img src="./avatar/Chung.jpg" width="100px;" alt=""/>
                    <br />
                    <sub><b>Nguyễn Văn Chung</b></sub>
                </a>
            </td>
            <td align="center">
                <a href="#">
                    <img src="./avatar/Khang.jpg" width="100px;" alt=""/>
                    <br />
                    <sub><b>Lương Đình Khang</b></sub>
                </a>
            </td>
            <td align="center">
                <a href="#">
                    <img src="./avatar/Khang.jpg" width="100px;" alt=""/>
                    <br />
                    <sub><b>Hoàng Đức Thuận</b></sub>
                </a>
            </td>
        </tr>
</table>
 
# [**Made with 💘 and Java <img src="https://www.vectorlogo.zone/logos/java/java-horizontal.svg" width="60">**](#made-with-love-and-java)