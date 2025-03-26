package net.micode.notes.tool;

import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import org.xml.sax.XMLReader;

import java.util.HashMap;
import java.util.Map;

/**
 * 工具类用于在富文本和HTML之间转换
 */
public class HtmlUtils {

    /**
     * 将带有格式的文本转换为HTML字符串
     * @param text 带有格式的文本
     * @return HTML格式的字符串
     */
    public static String toHtml(Spannable text) {
        if (text == null) {
            return "";
        }
        
        // 获取文本中所有的BackgroundColorSpan，暂时移除
        // Android的Html.toHtml默认不处理BackgroundColorSpan
        BackgroundColorSpan[] bgSpans = text.getSpans(0, text.length(), BackgroundColorSpan.class);
        int[] bgSpanColors = new int[bgSpans.length];
        int[] bgSpanStarts = new int[bgSpans.length];
        int[] bgSpanEnds = new int[bgSpans.length];
        
        // 记录所有BackgroundColorSpan的信息
        for (int i = 0; i < bgSpans.length; i++) {
            bgSpanColors[i] = bgSpans[i].getBackgroundColor();
            bgSpanStarts[i] = text.getSpanStart(bgSpans[i]);
            bgSpanEnds[i] = text.getSpanEnd(bgSpans[i]);
            text.removeSpan(bgSpans[i]);
        }
        
        // 转换为HTML
        String html = Html.toHtml(text);
        
        // 手动添加背景色标签
        StringBuilder sb = new StringBuilder(html);
        // 从后向前添加，以避免位置偏移
        for (int i = bgSpans.length - 1; i >= 0; i--) {
            // 将颜色转换为十六进制
            String colorHex = String.format("#%06X", (0xFFFFFF & bgSpanColors[i]));
            
            // 查找HTML文本中对应位置的标签
            // 这里的实现比较简单，实际应用中可能需要更复杂的HTML解析
            // 简单起见，我们假设HTML文本和原文本的位置关系是一致的
            
            // 这里的实现有局限性，仅作为示例
            sb.insert(bgSpanEnds[i], "</span>");
            sb.insert(bgSpanStarts[i], "<span style=\"background-color:" + colorHex + ";\">");
        }
        
        return sb.toString();
    }

    /**
     * 将HTML字符串转换为带有格式的文本
     * @param html HTML格式的字符串
     * @return 带有格式的文本
     */
    public static Spanned fromHtml(String html) {
        if (TextUtils.isEmpty(html)) {
            return new SpannableString("");
        }
        
        // 对HTML内容进行预处理，确保所有格式标签都能被正确识别
        // Android的Html.fromHtml有时候无法正确处理某些标签，需要进行预处理
        html = html.replace("background-color:", "bgcolor:");
        
        // 使用Html.fromHtml处理基本格式（粗体、斜体、下划线）
        Spanned spanned = Html.fromHtml(html, null, new BackgroundColorTagHandler());
        return spanned;
    }
    
    /**
     * 自定义HTML标签处理器，用于处理背景颜色等特殊标签
     */
    static class BackgroundColorTagHandler implements Html.TagHandler {
        private static final String BACKGROUND_COLOR_TAG = "bgcolor";
        
        private int mBgColorStart = 0;
        private int mBgColorEnd = 0;
        private String mBgColor = null;
        
        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            if (tag.equalsIgnoreCase(BACKGROUND_COLOR_TAG)) {
                if (opening) {
                    mBgColorStart = output.length();
                    try {
                        // 获取标签的属性 - 简化处理，直接从span标签的style属性中提取
                        // 在实际使用中，CSS的background-color会被转换为HTML的bgcolor属性
                        mBgColor = "#FFFF00"; // 默认黄色，实际应用中应从属性中读取
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    mBgColorEnd = output.length();
                    if (mBgColor != null) {
                        try {
                            int color = Color.parseColor(mBgColor);
                            output.setSpan(new BackgroundColorSpan(color),
                                    mBgColorStart, mBgColorEnd,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mBgColor = null;
                }
            }
        }
        
        // 获取XML标签的属性 - 简化实现
        private Map<String, String> getAttributes(XMLReader xmlReader) {
            return new HashMap<>(); // 简化实现，实际应用中应使用反射获取属性
        }
    }
} 