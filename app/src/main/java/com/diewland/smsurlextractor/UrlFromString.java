package com.diewland.smsurlextractor;

public class UrlFromString {

    public static String extract(String text){
        int start = text.indexOf("http");
        if(start >= 0){
            text = text.substring(start);
            int end = text.indexOf(" ");
            if(end >= 0){
                text = text.substring(0, end);
            }
            return text;
        }
        else {
            return "";
        }
    }

    public static void main(String[] args){

        String url1 = "รู้ยัง!True Coffeeฟรีอัพไซต์วันนี้เท่านั้น คลิก!https://bit.ly/2UbGUHD";
        String url2 = "ำระ เดือน ม.ค. เบอร์ 0840304251 ยอด 640.93 บ.กำหนดชำระ 14/02/62 เช็กยอดค้างล่าสุด*121*3#ฟรี ขออภัยหากชำระแล้ว บิลของคุณจัดส่งไปที่อีเมล์แล้ว หรือเปิดดูบิลทันทีโดยใช้รหัสเปิดไฟล์คือ วันเดือนปีเกิดของผู้จดทะเบียน(เช่น 5ก.ค.1985 ใส่รหัส 05071985) ที่ https://myais.ais.co.th/e/r760lGP66qFUa9AjEQeOHw==";
        String url3 = "Special gift from Starbucks. Click to view & show this coupon at Starbucks Thailand http://modeal.com/0cglk Coupon expires: 01/02/19";

        System.out.println(UrlFromString.extract(url1));
        System.out.println(UrlFromString.extract(url2));
        System.out.println(UrlFromString.extract(url3));
    }

}
