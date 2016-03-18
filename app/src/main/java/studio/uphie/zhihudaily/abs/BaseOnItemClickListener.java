package studio.uphie.zhihudaily.abs;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseOnItemClickListener<T> implements OnClickListener {

    private T data;

    public BaseOnItemClickListener(T data) {
        this.data = data;
    }

    @Override
    public void onClick(View v) {
        onClick(v, data);
    }

    public abstract void onClick(View view, T data);
}
