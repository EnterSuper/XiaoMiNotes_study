package net.micode.notes.tool;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;

/**
 * Utility class for text formatting operations
 */
public class TextFormattingTools {

    /**
     * Toggles bold formatting on the selected text
     * @param editText The EditText containing the text to format
     */
    public static void toggleBold(EditText editText) {  // 加粗
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        
        if (selectionStart < selectionEnd) {
            Editable editable = editText.getText();
            
            boolean hasBold = false;
            StyleSpan[] spans = editable.getSpans(selectionStart, selectionEnd, StyleSpan.class);
            for (StyleSpan span : spans) {
                if (span.getStyle() == android.graphics.Typeface.BOLD) {
                    hasBold = true;
                    editable.removeSpan(span);
                }
            }
            
            if (!hasBold) {
                editable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 
                    selectionStart, selectionEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /**
     * Toggles italic formatting on the selected text
     * @param editText The EditText containing the text to format
     */
    public static void toggleItalic(EditText editText) {  // 斜体
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        
        if (selectionStart < selectionEnd) {
            Editable editable = editText.getText();
            
            boolean hasItalic = false;
            StyleSpan[] spans = editable.getSpans(selectionStart, selectionEnd, StyleSpan.class);
            for (StyleSpan span : spans) {
                if (span.getStyle() == android.graphics.Typeface.ITALIC) {
                    hasItalic = true;
                    editable.removeSpan(span);
                }
            }
            
            if (!hasItalic) {
                editable.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 
                    selectionStart, selectionEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /**
     * Toggles underline formatting on the selected text
     * @param editText The EditText containing the text to format
     */
    public static void toggleUnderline(EditText editText) {
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        
        if (selectionStart < selectionEnd) {
            Editable editable = editText.getText();
            
            boolean hasUnderline = false;
            UnderlineSpan[] spans = editable.getSpans(selectionStart, selectionEnd, UnderlineSpan.class);
            if (spans.length > 0) {
                hasUnderline = true;
                for (UnderlineSpan span : spans) {
                    editable.removeSpan(span);
                }
            }
            
            if (!hasUnderline) {
                editable.setSpan(new UnderlineSpan(), 
                    selectionStart, selectionEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /**
     * Toggles strikethrough formatting on the selected text
     * @param editText The EditText containing the text to format
     */
    public static void toggleStrikethrough(EditText editText) {
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        
        if (selectionStart < selectionEnd) {
            Editable editable = editText.getText();
            
            boolean hasStrikethrough = false;
            StrikethroughSpan[] spans = editable.getSpans(selectionStart, selectionEnd, StrikethroughSpan.class);
            if (spans.length > 0) {
                hasStrikethrough = true;
                for (StrikethroughSpan span : spans) {
                    editable.removeSpan(span);
                }
            }
            
            if (!hasStrikethrough) {
                editable.setSpan(new StrikethroughSpan(), 
                    selectionStart, selectionEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    /**
     * Sets the color of the selected text
     * @param editText The EditText containing the text to format
     * @param color The color to apply
     */
    public static void setTextColor(EditText editText, int color) {
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        
        if (selectionStart < selectionEnd) {
            Editable editable = editText.getText();
            
            // Remove any existing text color spans
            ForegroundColorSpan[] spans = editable.getSpans(selectionStart, selectionEnd, ForegroundColorSpan.class);
            for (ForegroundColorSpan span : spans) {
                editable.removeSpan(span);
            }
            
            // Apply the new color
            editable.setSpan(new ForegroundColorSpan(color), 
                selectionStart, selectionEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * Sets the background highlight color of the selected text
     * @param editText The EditText containing the text to format
     * @param color The background color to apply
     */
    public static void setHighlightColor(EditText editText, int color) {
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        
        if (selectionStart < selectionEnd) {
            Editable editable = editText.getText();
            
            // Remove any existing background color spans
            BackgroundColorSpan[] spans = editable.getSpans(selectionStart, selectionEnd, BackgroundColorSpan.class);
            for (BackgroundColorSpan span : spans) {
                editable.removeSpan(span);
            }
            
            // Apply the new background color
            editable.setSpan(new BackgroundColorSpan(color), 
                selectionStart, selectionEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
} 