package com.example.SearchAround;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-24
 * Time: 下午6:54
 * To change this template use File | Settings | File Templates.
 */
public class ShowSavePicture extends Activity {
    private Gallery  gallery;
    private ImageSwitcher imageSwitcher;
    private List<String> imageList = new ArrayList<String>();
    private String imagePath;
    private ImageAdapter imageAdapter = new ImageAdapter(this, imageList);
    private ImageButton showSaveBackIBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.showsavepicture);
        gallery = (Gallery) findViewById(R.id.Gallery);
        imageSwitcher = (ImageSwitcher)findViewById(R.id.ImageSwitcher);
        showSaveBackIBt = (ImageButton) findViewById(R.id.showSaveBackIBt);
        imageSwitcher.setInAnimation(this,android.R.anim.fade_in);
        imageSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        showSaveBackIBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageSwitcher.setFactory(new ImageSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(ShowSavePicture.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.MATCH_PARENT, ImageSwitcher.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSwitcher.setImageURI(Uri.parse(imageList.get(position)));
                imagePath =   imageList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        imageSwitcher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final File fileImage = new File(imagePath);
                new AlertDialog.Builder(ShowSavePicture.this).setTitle("是否删除").setMessage("确定吗").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fileImage.delete();
                        Toast toast = Toast.makeText(ShowSavePicture.this, "删除成功", Toast.LENGTH_SHORT);
                        toast.show();
                        int number =  refreshGallery();
                        imageAdapter.notifyDataSetChanged();
                        if(number == 0){
                            imageSwitcher.setVisibility(View.GONE);
                        }
//                        imageSwitcher.setImageURI(Uri.parse(imageList.get(0)));
//                        Intent intent = new Intent();
//                        intent.setClass(ShowPictureActivity.this, ShowPictureActivity.class);
//                        startActivity(intent);
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                }
                ).show();

                return false;
            }
        });


        refreshGallery();
    }
    private int refreshGallery(){
        File file = new File("/mnt/sdcard/gqmap");
        File [] files = file.listFiles();
        if(null == files){
            Toast.makeText(ShowSavePicture.this,"还没收藏图片哦,请添加想保存的地图吧",Toast.LENGTH_SHORT).show();
        }else{
            if(files.length == 0){
                Toast.makeText(ShowSavePicture.this,"还没收藏图片哦,请添加想保存的地图吧",Toast.LENGTH_SHORT).show();
            }
            imageList.clear();
            for(int i = 0 ; i < files.length ; i++){
                imageList.add(files[i].getPath()) ;
            }
            gallery.setAdapter(imageAdapter);
        }
        return imageList.size();
    }
    //    SimpleAdapter simpleAdapter = new SimpleAdapter(this,data,R.layout.showpicture,new String []{"url"});
    class ImageAdapter extends BaseAdapter {
        private List<String> list;
        private Context context;

        public ImageAdapter(Context ctx, List<String> li) {
            context = ctx;
            list = li;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageURI(Uri.parse(list.get(position)));
//            imageView.setImageBitmap(BitmapFactory.decodeFile(list.get(position)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
            convertView = imageView;
            return convertView;
        }


    }
}
