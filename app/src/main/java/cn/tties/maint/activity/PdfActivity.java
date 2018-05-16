package cn.tties.maint.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.tties.maint.R;
import cn.tties.maint.view.CriProgressDialog;

/**
 * 在线pdf预览
 * Created by wyouflf on 15/11/4.
 */
@ContentView(R.layout.activity_pdf)
public class PdfActivity extends BaseActivity {

    @ViewInject(R.id.btn_back)
    private Button btnBack;

    @ViewInject(R.id.text_name)
    private TextView textName;


    @ViewInject(R.id.pdfView)
    private PDFView pdfView;

    CriProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path  = getIntent().getStringExtra("path");
        String name  = getIntent().getStringExtra("name");
        textName.setText(name);
        dialog = new CriProgressDialog(this);
        dialog.loadDialog("加载中...");
        lookPdf(path);
    }

    private void lookPdf(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection)
                            url.openConnection();
                    connection.setRequestMethod("GET");//试过POST 可能报错
                    connection.setDoInput(true);
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    //实现连接
                    connection.connect();

                    System.out.println("connection.getResponseCode()=" + connection.getResponseCode());
                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        //这里给过去就行了
                        pdfView.fromStream(is)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .onDraw(new OnDrawListener() {
                                    @Override
                                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                    }
                                })
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        dialog.removeDialog();
//                                        Toast.makeText(x.app(), "加载完成", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {

                                    }
                                })
                                .onPageScroll(new OnPageScrollListener() {
                                    @Override
                                    public void onPageScrolled(int page, float positionOffset) {

                                    }
                                })
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {
                                        dialog.removeDialog();
                                        t.printStackTrace();
                                        Toast.makeText(x.app(), "加载失败", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .enableAnnotationRendering(false)
                                .password(null)
                                .scrollHandle(null)
                                .load();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Event(value = {R.id.btn_back})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                super.finish();
                onBackPressed();
        }
    }
}
