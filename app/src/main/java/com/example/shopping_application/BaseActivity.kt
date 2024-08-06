package com.example.shopping_application

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//open: để các class khác có thể kế thừa class này
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

//Tham số đầu tiên là các cờ bạn muốn thiết lập.
//Tham số thứ hai là các cờ mà bạn muốn thay đổi. Nếu tham số này bao gồm cờ nào,
// cờ đó sẽ được thiết lập hoặc xóa tùy theo giá trị của tham số đầu tiên.

//FLAG_LAYOUT_NO_LIMITS: Đây là cờ được sử dụng để cho phép layout của Activity mở rộng ra toàn màn hình,
// bao gồm các khu vực dưới thanh trạng thái và thanh điều hướng.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}