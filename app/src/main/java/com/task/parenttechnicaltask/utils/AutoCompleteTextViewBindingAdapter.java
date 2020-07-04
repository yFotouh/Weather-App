package com.task.parenttechnicaltask.utils;

import android.widget.AutoCompleteTextView;
import android.widget.AutoCompleteTextView.Validator;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.AdapterViewBindingAdapter;

@BindingMethods({
        @BindingMethod(type = AutoCompleteTextView.class, attribute = "android:completionThreshold", method = "setThreshold"),
        @BindingMethod(type = AutoCompleteTextView.class, attribute = "android:popupBackground", method = "setDropDownBackgroundDrawable"),
        @BindingMethod(type = AutoCompleteTextView.class, attribute = "android:onDismiss", method = "setOnDismissListener"),
        @BindingMethod(type = AutoCompleteTextView.class, attribute = "android:onItemClick", method = "setOnItemClickListener"),
})
public class AutoCompleteTextViewBindingAdapter {
    @BindingAdapter("android:fixText")
    public static void setListener(AutoCompleteTextView view, FixText listener) {
        setListener(view, listener, null);
    }
    @BindingAdapter("android:isValid")
    public static void setListener(AutoCompleteTextView view, IsValid listener) {
        setListener(view, null, listener);
    }
    @BindingAdapter({"android:fixText", "android:isValid"})
    public static void setListener(AutoCompleteTextView view, final FixText fixText,
                                   final IsValid isValid) {
        if (fixText == null && isValid == null) {
            view.setValidator(null);
        } else {
            view.setValidator(new Validator() {
                @Override
                public boolean isValid(CharSequence text) {
                    if (isValid != null) {
                        return isValid.isValid(text);
                    } else {
                        return true;
                    }
                }
                @Override
                public CharSequence fixText(CharSequence invalidText) {
                    if (fixText != null) {
                        return fixText.fixText(invalidText);
                    } else {
                        return invalidText;
                    }
                }
            });
        }
    }
    @BindingAdapter("android:onItemSelected")
    public static void setListener(AutoCompleteTextView view, AdapterViewBindingAdapter.OnItemSelected listener) {
        setListener(view, listener, null);
    }
    @BindingAdapter("android:onNothingSelected")
    public static void setListener(AutoCompleteTextView view, AdapterViewBindingAdapter.OnNothingSelected listener) {
        setListener(view, null, listener);
    }
    @BindingAdapter({"android:onItemSelected", "android:onNothingSelected"})
    public static void setListener(AutoCompleteTextView view, final AdapterViewBindingAdapter.OnItemSelected selected,
                                   final AdapterViewBindingAdapter.OnNothingSelected nothingSelected) {
        if (selected == null && nothingSelected == null) {
            view.setOnItemSelectedListener(null);
        } else {
            view.setOnItemSelectedListener(
                    new AdapterViewBindingAdapter.OnItemSelectedComponentListener(selected, nothingSelected, new InverseBindingListener() {
                        @Override
                        public void onChange() {

                        }
                    }));
        }
    }
    public interface IsValid {
        boolean isValid(CharSequence text);
    }
    public interface FixText {
        CharSequence fixText(CharSequence invalidText);
    }
}
