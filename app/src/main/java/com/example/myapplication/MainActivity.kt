package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Email
import com.example.myapplication.EmailAdapter
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var emailRecyclerView: RecyclerView
    private lateinit var emailAdapter: EmailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        emailRecyclerView = findViewById(R.id.recyclerViewEmails)
        emailRecyclerView.layoutManager = LinearLayoutManager(this)

        // Sample Data in Vietnamese
        val emails = listOf(
            Email("Zalo", "Cập nhật điều khoản mới", "Chúng tôi đã cập nhật các điều khoản sử dụng...", "12:34 PM"),
            Email("Ngân hàng ACB", "Thông báo tài khoản của bạn", "Tài khoản của bạn có giao dịch mới...", "11:22 AM"),
            Email("Shopee", "Chương trình khuyến mãi 10.10", "Mua sắm thỏa thích với giảm giá 50%...", "11:04 AM"),
            Email("Điện Máy Xanh", "Giảm giá mùa hè", "Mua sắm tiết kiệm với các ưu đãi hấp dẫn...", "10:26 AM"),
            Email("Vietjet", "Mở bán vé máy bay giá rẻ", "Chỉ từ 199k, đặt vé ngay để nhận ưu đãi...", "9:45 AM"),
            Email("FPT Shop", "Chương trình giảm giá cuối năm", "Giảm giá 20% cho tất cả sản phẩm công nghệ...", "8:30 AM"),
            Email("VnExpress", "Tin tức nóng hôm nay", "Cập nhật thông tin nhanh chóng, tin cậy từ...", "7:15 AM"),
            Email("Tiki", "Mua hàng giá sốc", "Giảm ngay 50% cho các sản phẩm công nghệ...", "6:50 AM"),
            Email("Netflix", "Phim mới bạn không thể bỏ lỡ", "Khám phá những bộ phim hấp dẫn trên Netflix...", "5:20 AM")
        )

        // Initialize Adapter and set it to RecyclerView
        emailAdapter = EmailAdapter(emails)
        emailRecyclerView.adapter = emailAdapter
    }
}
