package net.micode.notes.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.micode.notes.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 颜色选择器对话框
 */
public class ColorPickerDialog extends Dialog implements View.OnClickListener {
// 构造方法，初始化对话框，设置颜色类型和监听器。
    private static final Map<Integer, Integer> COLOR_MAP = new HashMap<>();
    
    static {
        COLOR_MAP.put(R.id.color_red, 0xFFFF0000);
        COLOR_MAP.put(R.id.color_blue, 0xFF0000FF);
        COLOR_MAP.put(R.id.color_green, 0xFF00FF00);
        COLOR_MAP.put(R.id.color_yellow, 0xFFFFFF00);
        COLOR_MAP.put(R.id.color_purple, 0xFF800080);
        COLOR_MAP.put(R.id.color_orange, 0xFFFFA500);
        COLOR_MAP.put(R.id.color_pink, 0xFFFFC0CB);
        COLOR_MAP.put(R.id.color_gray, 0xFF808080);
        COLOR_MAP.put(R.id.color_cyan, 0xFF00FFFF);
        COLOR_MAP.put(R.id.color_brown, 0xFFA52A2A);
        COLOR_MAP.put(R.id.color_black, 0xFF000000);
        COLOR_MAP.put(R.id.color_white, 0xFFFFFFFF);
    }

    /**
     * 颜色选择监听器接口
     */
    public interface OnColorSelectedListener {
        void onColorSelected(int color);
    }

    private OnColorSelectedListener mListener;
    private boolean mIsTextColor; // true=文本颜色, false=高亮背景色

    public ColorPickerDialog(Context context, boolean isTextColor, OnColorSelectedListener listener) {
        super(context);
        mListener = listener;
        mIsTextColor = isTextColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_dialog);
        
        // Set dialog title based on color type
        TextView title = findViewById(R.id.color_picker_title);
        if (mIsTextColor) {
            title.setText(R.string.format_color_picker_text);
            setTitle(R.string.format_color_picker_text);
        } else {
            title.setText(R.string.format_color_picker_highlight);
            setTitle(R.string.format_color_picker_highlight);
        }

        // Register click listeners for all color buttons
        for (Integer buttonId : COLOR_MAP.keySet()) {
            findViewById(buttonId).setOnClickListener(this);
        }
    }

    @Override  // 处理颜色按钮的点击事件
    public void onClick(View v) {
        Integer color = COLOR_MAP.get(v.getId());
        if (color != null && mListener != null) {
            mListener.onColorSelected(color);
            dismiss();
        }
    }
} 