package customsfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;


public class MyEditText extends EditText {
    public MyEditText (Context context){
        super(context);
        init();
    }

    public MyEditText (Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public MyEditText (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        if(!isInEditMode()){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MavenPro-Regular.ttf");
            setTypeface(tf);
        }
    }
}
